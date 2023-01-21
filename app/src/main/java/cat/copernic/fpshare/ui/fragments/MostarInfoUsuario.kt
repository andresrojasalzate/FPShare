package cat.copernic.fpshare.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentMostarInfoUsuarioBinding
import cat.copernic.fpshare.modelo.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File

/**
 * Clase de la pantalla MostrarInfoUsuario
 *
 * @author FPShare
 */
class MostarInfoUsuario : Fragment() {
    private var _binding: FragmentMostarInfoUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var nombre: TextView
    private lateinit var  apellidos: TextView
    private lateinit var email: TextView
    private lateinit var numeroTelefono: TextView
    private lateinit var institute: TextView
    private lateinit var esAdmin: CheckBox
    private lateinit var borrarUsuario: Button
    private lateinit var renombarUsuario: Button
    private var bd = FirebaseFirestore.getInstance()
    private var storage = FirebaseStorage.getInstance()
    private val args: MostarInfoUsuarioArgs by navArgs()

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostarInfoUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //llamanos a la función
        inicilaizar()
        //llamamos a la función
        lifecycleScope.launch(Dispatchers.Main) {
            val x = async {
                recuparUsuario()
            }
        }
        //ponemos el email que llega a la pantalla en la variable
        val idUsuario = args.email
        //si el clickamos el checkbox
        esAdmin.setOnClickListener {
            //si lo marcamos cambiamos el valor de esAdmin de la base de datos a true
            if (esAdmin.isChecked()){
                bd.collection("Usuarios").document(idUsuario).update("esAdmin", true)
            } else {
                //si no cambiamos el valor a false
                bd.collection("Usuarios").document(idUsuario).update("esAdmin", false)
            }
        }

        //si pulsamos el boton  de borrar usurio
        borrarUsuario.setOnClickListener {

            //borramos el usuario de la base de datos
            bd.collection("Usuarios").document(idUsuario).delete()
        }

        //si pulsamos el boton de renombrar Usuario
        renombarUsuario.setOnClickListener {
            //cambiamos de pantalla
            val action = MostarInfoUsuarioDirections.actionMostarInfoUsuarioToFragmentRenombrarUsuario(idUsuario)
            view.findNavController().navigate(action)
        }


    }

    /**
     * Con est función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Con esta función iniciamos los items de la pantala
     */
    private fun inicilaizar(){

        //iniliziamos los items de la pantalla
        nombre = binding.nameUser
        apellidos = binding.lastsNameUser
        email = binding.emailUser
        numeroTelefono = binding.telephoneNumberUser
        institute = binding.institueUser
        esAdmin = binding.checkBoxEsAdmin
        borrarUsuario = binding.buttonDeleteUser
        renombarUsuario = binding.buttonRenameUser
    }

    /**
     * Con esta función recuperamos la información del usuario que seleccioonamos en la pantalla anterior
     */
    private suspend fun recuparUsuario(){
        //asignamos el email recibido en la pantalla a la variable
        val idUsuario = args.email
        //creamos un objeto de tipo User
        val usuario = User()

        //recuperamos la información del usuario seleccionado
       val document= bd.collection("Usuarios").document(idUsuario).get().await()

            usuario.email = document.id
            usuario.nombre = document["nombre"].toString()
            usuario.apellidos = document["apellidos"].toString()
            usuario.telefono = document["telefono"].toString()
            usuario.instituto = document["instituto"].toString()
            usuario.esAdmin = document["esAdmin"] as Boolean
            usuario.imgPerfil = document["imgPerfil"].toString()
            rellenarcampos(usuario)

            //Recuperamos la imagen de perfil del usuario
            val storageRef = storage.reference.child("Imagenes/" +  usuario.email)
            val localfile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                try {
                    binding.imageProfile.setImageBitmap(bitmap)
                }catch (e: java.lang.NullPointerException){
                    println("error")
                }

            }.addOnFailureListener{
                Toast.makeText(context, getString(R.string.imageLoadError), Toast.LENGTH_LONG)
                    .show()

            }

    }

    /**
     * con esta función asignamos la información recibida del uauario a los textViews correspondientes
     */
    private fun rellenarcampos(usuario: User){

        //establecemos los valores de los textViews pero antes los pasamos por la función comprobarcampos()
        nombre.setText(comprobarcampos(usuario.nombre))
        apellidos.setText(comprobarcampos(usuario.apellidos))
        numeroTelefono.setText(comprobarcampos(usuario.telefono))
        institute.setText(comprobarcampos(usuario.instituto))
        email.setText(comprobarcampos(usuario.email))
        //comprobamos el valor de esAdmin del objeto si es true marcamos el checkboc
        if(usuario.esAdmin) {
            esAdmin.setChecked(true);
        }
    }

    /**
     * Con esta función comprobamos si el paramatero que recibe esta vacio o no
     * @return ret
     * si el parametro que comprueba esta vacio devuele (Not spcidied), si no esta vacio devuelve el
     * parametro recibido
     */
    private fun comprobarcampos(campo: String): String{
        // estableceos la variable qye devolveremods
        val ret: String
        //comprobamos si el parametro recibido esta vacio
        if (campo.isEmpty() ){
            //si lo esta
            ret = getString(R.string.info_no_especificada_mostrar_info_usuario)
        } else {
            //si no lo esta
            ret = campo
        }
        //deolvemos el string
        return ret
    }
}