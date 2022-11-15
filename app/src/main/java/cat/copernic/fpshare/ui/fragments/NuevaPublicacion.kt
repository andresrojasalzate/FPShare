package cat.copernic.fpshare.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.R
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

    private lateinit var id: String
    private lateinit var perfil: String
    private lateinit var titulo: TextInputEditText
    private lateinit var descripcion: TextInputEditText
    private lateinit var enlace: TextInputEditText

    private var user = Firebase.auth.currentUser

    private lateinit var publicaciones:ArrayList<Publicacion>
    private lateinit var publicacion: Publicacion
    private lateinit var botonPublicar: Button

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
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonPublicar = binding.btnPublish

        publicaciones = arrayListOf()

        botonPublicar.setOnClickListener{

            var publicacion = llegirDades()

            perfil = user?.email.toString()
            titulo = binding.textPost

            descripcion = binding.textDescription
            enlace = binding.textLink
            añadirPublicacion(publicacion)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Funció que llegeix les dades introduïdes per un usuari i retorna el departament instanciat amb aquestes
    //dades.
    fun llegirDades():Publicacion{
        //Guardem les dades introduïdes per l'usuari
        var id = id
        var perfil = perfil
        var titulo = titulo.text.toString()
        var descripcion = descripcion.text.toString()
        var enlace = enlace.text.toString()

        //Afegim els treballadors introduïts per l'usuari a l'atribut treballadors
        publicaciones.add(Publicacion(id, perfil, titulo, descripcion, enlace))

        return Publicacion(id, perfil, titulo, descripcion, enlace)

    }

    fun añadirPublicacion(publicacion:Publicacion){
        //Afegim una subcolecció igual que afegim una col.lecció però penjant de la col.lecció on està inclosa.
        bd.collection("Publicaciones").add(publicacion)//Col.lecció
            .addOnSuccessListener { //S'ha afegit el departament...
                Toast.makeText(requireActivity(),"Publicación añadida correctamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha afegit el departament...
                Toast.makeText(requireActivity(),"La publicación no se ha añadido", Toast.LENGTH_LONG).show()
            }
    }

}