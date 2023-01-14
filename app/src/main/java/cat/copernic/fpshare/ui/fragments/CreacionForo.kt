package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentCreacionForoBinding
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.Utils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreacionForo : Fragment() {
    private var _binding: FragmentCreacionForoBinding? = null
    private val binding get() = _binding!!
    private lateinit var titulo: EditText
    private lateinit var descripcion: EditText
    private lateinit var boton: Button
    private lateinit var utils: Utils
    private var bd = FirebaseFirestore.getInstance()
    private var user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreacionForoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializar()
        titulo.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthTitulo))
        descripcion.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(utils.maxlengthDescripcion))
        boton.setOnClickListener {

            if (titulo.text.isNotEmpty() || descripcion.text.isNotEmpty()) {
                lifecycleScope.launch{
                    withContext(Dispatchers.IO) {
                        crearForo()
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializar(){
        titulo = binding.txtThreadInput
        descripcion = binding.txtDescriptionInput
        boton = binding.btnSave
        utils = Utils()

    }

    private fun crearForo() {
        val email = user?.email.toString()
        bd.collection("Foros").get()
            .addOnSuccessListener { documents ->
                val foros = ArrayList<Foro>()
                for (document in documents) {
                    val wallitem = document.toObject(Foro::class.java)

                    wallitem.id = document.id.toInt()
                    wallitem.titulo = document["titulo"].toString()
                    wallitem.descripcion = document["descripcion"].toString()
                    wallitem.emailautor = document["emailautor"].toString()
                    foros.add(wallitem)

                }
                val idtxt :String
                var idint :Int
                if (foros.isNotEmpty()) {
                    foros.sortWith(compareBy({ it.id }))
                    idint = foros.get(foros.size - 1).id
                    idint += 1
                    idtxt = idint.toString()
                } else {
                    idint = 1
                    idtxt = "1"
                }

                bd.collection("Usuarios").document(user?.email.toString()).get().addOnSuccessListener { it ->
                    val usuario = it["nombre"].toString() + " " + it["apellidos"]

                    val foro = Foro(idint, titulo.text.toString(), descripcion.text.toString(), email, usuario)
                    val mensajeInicial = Mensaje("shtht", "mensaje de prueba", "fff")
                    bd.collection("Foros").document(idtxt).set(foro)
                    bd.collection("Foros").document(idtxt).collection("Mensajes").add(mensajeInicial)
                    cambiarPantalla(idtxt)
                }


                }
            }
    private fun cambiarPantalla(id: String){
        val action =
            CreacionForoDirections.actionCreacionForoToFPHilo(id)
        view?.findNavController()?.navigate(action)
    }

    }





