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
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentMostarInfoUsuarioBinding
import cat.copernic.fpshare.modelo.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File


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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostarInfoUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicilaizar()
        recuparUsuario()
        val idUsuario = args.email
        esAdmin.setOnClickListener {

            if (esAdmin.isChecked()){
                bd.collection("Usuarios").document(idUsuario).update("esAdmin", true)
            } else {
                bd.collection("Usuarios").document(idUsuario).update("esAdmin", false)
            }
        }
        borrarUsuario.setOnClickListener {
            bd.collection("Usuarios").document(idUsuario).delete()
        }
        renombarUsuario.setOnClickListener {
            val action = MostarInfoUsuarioDirections.actionMostarInfoUsuarioToFragmentRenombrarUsuario(idUsuario)
            view.findNavController().navigate(action)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicilaizar(){
        nombre = binding.nameUser
        apellidos = binding.lastsNameUser
        email = binding.emailUser
        numeroTelefono = binding.telephoneNumberUser
        institute = binding.institueUser
        esAdmin = binding.checkBoxEsAdmin
        borrarUsuario = binding.buttonDeleteUser
        renombarUsuario = binding.buttonRenameUser
    }

    private fun recuparUsuario(){
        val idUsuario = args.email
        val usuario = User()
        bd.collection("Usuarios").document(idUsuario).get().addOnSuccessListener { document ->

            usuario.email = document.id
            usuario.nombre = document["nombre"].toString()
            usuario.apellidos = document["apellidos"].toString()
            usuario.telefono = document["telefono"].toString()
            usuario.instituto = document["insituto"].toString()
            usuario.esAdmin = document["esAdmin"] as Boolean
            usuario.imgPerfil = document["imgPerfil"].toString()
            rellenarcampos(usuario)

            val storageRef = storage.reference.child("Imagenes/" +  usuario.email)
            val localfile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imagen.setImageBitmap(bitmap)

            }.addOnFailureListener{
                Toast.makeText(context,"La carga de la imagen ha fallado.", Toast.LENGTH_LONG).show()

            }
        }

    }
    private fun rellenarcampos(usuario: User){
        nombre.setText(comprobarcampos(usuario.nombre))
        apellidos.setText(comprobarcampos(usuario.apellidos))
        numeroTelefono.setText(comprobarcampos(usuario.telefono))
        institute.setText(comprobarcampos(usuario.instituto))
        email.setText(comprobarcampos(usuario.email))
        if(usuario.esAdmin) {
            esAdmin.setChecked(true);
        }
    }

    private fun comprobarcampos(campo: String): String{
        val ret: String
        if (campo.isEmpty()){
            ret = getString(R.string.info_no_especificada_mostrar_info_usuario)
        } else {
            ret = campo
        }
        return ret
    }
}