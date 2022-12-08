package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.databinding.FragmentMostarInfoUsuarioBinding
import cat.copernic.fpshare.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore


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
        val usuario = Usuario()
        bd.collection("Usuarios").document(idUsuario).get().addOnSuccessListener { document ->

            usuario.email = document.id
            usuario.nombre = document["nombre"].toString()
            usuario.apellidos = document["apellidos"].toString()
            usuario.telefono = document["telefono"].toString()
            usuario.insituto = document["insituto"].toString()
            usuario.esAdmin = document["esAdmin"] as Boolean
            rellenarcampos(usuario)
        }

    }
    private fun rellenarcampos(usuario: Usuario){
        nombre.setText(comprobarcampos(usuario.nombre))
        apellidos.setText(comprobarcampos(usuario.apellidos))
        numeroTelefono.setText(comprobarcampos(usuario.telefono))
        institute.setText(comprobarcampos(usuario.insituto))
        email.setText(comprobarcampos(usuario.email))
        if(usuario.esAdmin) {
            esAdmin.setChecked(true);
        }
    }

    private fun comprobarcampos(campo: String): String{
        val ret: String
        if (campo.isEmpty()){
            ret = "(No especificado)"
        } else {
            ret = campo
        }
        return ret
    }
}