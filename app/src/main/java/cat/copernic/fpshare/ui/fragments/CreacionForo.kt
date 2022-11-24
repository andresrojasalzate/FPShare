package cat.copernic.fpshare.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentCreacionForoBinding
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


class CreacionForo : Fragment() {
    private var _binding: FragmentCreacionForoBinding? = null
    private val binding get() = _binding!!
    private lateinit var titulo : EditText
    private lateinit var descripcion : EditText
    private lateinit var boton : Button
    private var bd = FirebaseFirestore.getInstance()
    private var  user =  Firebase.auth.currentUser

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
        titulo = binding.txtThreadInput
        descripcion = binding.txtDescriptionInput
        boton = binding.btnSave
        val email = user?.email.toString()


        boton.setOnClickListener {

            if (titulo.text.isNotEmpty() || descripcion.text.isNotEmpty()){

                val mensajes = ArrayList<Mensaje>()
                val foro = Foro(titulo.text.toString(), descripcion.text.toString(), email, mensajes)

                anadirForo(foro)
            }else {
               /* val dialog = AlertDialog.Builder(this)
                    .setTitle("ERROR AL CREAR EL FORO")
                    .setMessage("Loscampos no estan completos")
                    .setPositiveButton("Aceptar") { view, _ ->

                    }
                    .setCancelable(false)
                    .create()

                dialog.show()*/

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun  anadirForo(foro : Foro){
        bd.collection("Foros").add(foro)
    }

}



