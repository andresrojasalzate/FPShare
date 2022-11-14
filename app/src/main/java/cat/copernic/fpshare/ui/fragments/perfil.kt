package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentPerfilBinding
import cat.copernic.fpshare.modelo.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var nombreEditText: EditText
    private lateinit var  apellidosEditText: EditText
    private lateinit var numero : EditText
    private lateinit var insituto : EditText
    private lateinit var  botonGuardarCambios : Button
    private lateinit var emailEdittext : EditText
    private var  user =  Firebase.auth.currentUser
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicizalizar()
        val email = user?.email.toString()
        botonGuardarCambios.setOnClickListener {
            val user = User(
                emailEdittext.text.toString(),
                nombreEditText.text.toString(),
                apellidosEditText.text.toString(),
                numero.text.toString(),
                insituto.text.toString(),
                false
            )
            bd.collection("Usuarios").document(email).set(user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun  inicizalizar(){

        nombreEditText = binding.edittextName
        apellidosEditText = binding.edittextApellidos
        numero = binding.edittextNumero
        insituto = binding.edittextInstitute
        botonGuardarCambios = binding.buttonSaveChangesProfile
        emailEdittext = binding.editextEmail

    }

}