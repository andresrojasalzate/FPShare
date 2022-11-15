package cat.copernic.fpshare.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentPerfilBinding
import cat.copernic.fpshare.modelo.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var nombreEditText: EditText
    private lateinit var  apellidosEditText: EditText
    private lateinit var numero : EditText
    private lateinit var insituto : EditText
    private lateinit var  botonGuardarCambios : Button
    private lateinit var emailEdittext : EditText
    private var storage = FirebaseStorage.getInstance()
    private var storageRef = storage.reference.child("image/imatges")
    private lateinit var imagen: ImageView

    private var photoSelectedUri: Uri?=null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge
        }
    }

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
        val imagen = binding.imageProfile

        imagen.setOnClickListener{
            subirArchivos()
        }

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

    private fun subirArchivos(){

        resultLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri-> //Hem seleccionat una imatge...
            //Afegim (pujem) la imatge que hem seleccionat mitjançant el mètode putFile de la classe FirebasStorage, passant-li com a
            //paràmetre l'URI de la imatge.
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(requireActivity(),"La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG).show()
                }
        }


    }

}