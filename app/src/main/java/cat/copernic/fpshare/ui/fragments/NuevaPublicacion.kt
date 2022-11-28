package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentNuevaPublicacionBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class NuevaPublicacion : Fragment() {

    private var _binding: FragmentNuevaPublicacionBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    private lateinit var perfil: String

    private lateinit var titulo: EditText
    private lateinit var descripcion: TextInputEditText
    private lateinit var enlace: TextInputEditText
    private var user = Firebase.auth.currentUser

    private lateinit var checked: String
    private lateinit var publicacion: Publicacion
    private lateinit var botonPublicar: Button

    private lateinit var idModulo: EditText
    private lateinit var idUf: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNuevaPublicacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonPublicar = binding.btnPublish
        titulo = binding.textPost
        descripcion = binding.textDescription
        enlace = binding.textLink

        idModulo = binding.setModule
        idUf = binding.setUF

        botonPublicar.setOnClickListener{

            val publicacion = llegirDades()
            if(publicacion.id.isNotEmpty() && publicacion.id.isNotBlank()){
                anadirPublicacion(idModulo.text.toString(), idUf.text.toString(), publicacion)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Funció que llegeix les dades introduïdes per un usuari i retorna el departament instanciat amb aquestes
    //dades.
    private fun llegirDades():Publicacion{

        //Guardem les dades introduïdes per l'usuari
        val id = "a"
        val perfil = user?.email.toString()
        val titulo = titulo.text.toString()
        val descripcion = descripcion.text.toString()

        if(binding.optionDam.isChecked){
            checked = "DAM"
        }else if(binding.optionDaw.isChecked){
            checked = "DAW"
        }else if(binding.optionSmix.isChecked){
            checked = "SMIR"
        }else if(binding.optionAsix.isChecked){
            checked = "ASIR"
        }
        val enlace = enlace.text.toString()


        return Publicacion(id, perfil, titulo, descripcion, checked, enlace)

    }

    fun anadirPublicacion(idModulo: String, idUf: String, publicacion:Publicacion){
        // val appContext = context

        bd.collection("Ciclos").document(checked)
            .collection("Modulos").document(idModulo)
            .collection("UFs").document(idUf)
            .collection("Publicaciones").document().set(publicacion)
    }
}