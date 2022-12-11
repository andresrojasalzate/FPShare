package cat.copernic.fpshare.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentPerfilBinding
import cat.copernic.fpshare.modelo.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File


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
    private var user =  Firebase.auth.currentUser
    private var storageRef = storage.reference.child("Imagenes/" + user?.email.toString()) // TODO hay que corregir esto
    private lateinit var imagen: ImageView

    private var photoSelectedUri: Uri?=null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            photoSelectedUri = it.data?.data //Assignem l'URI de la imatge
        }
    }


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
        val email = user?.email.toString()
        inicizalizar()

        imagen.setOnClickListener{
            subirArchivos()
        }

        botonGuardarCambios.setOnClickListener {
            bd.collection("Usuarios").document(email).get().addOnSuccessListener{
                bd.collection("Usuarios").document(email)
                    .update("email",emailEdittext.text.toString(),
                    "nombre",nombreEditText.text.toString(),
                    "apellidos",apellidosEditText.text.toString(),
                    "telefono",numero.text.toString(),
                    "instituto",insituto.text.toString(),
                    "imgPerfil",photoSelectedUri.toString())
                    .addOnSuccessListener {
                        Toast.makeText(requireActivity(),"Se ha realizado la modificacion con exito", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener {
                val user = User(
                    emailEdittext.text.toString(),
                    nombreEditText.text.toString(),
                    apellidosEditText.text.toString(),
                    numero.text.toString(),
                    insituto.text.toString(),
                    photoSelectedUri.toString(),
                    false
                )
                bd.collection("Usuarios").document(email).set(user)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun  inicizalizar(){
        val email = user?.email.toString()
        nombreEditText = binding.edittextName
        apellidosEditText = binding.edittextApellidos
        numero = binding.edittextNumero
        insituto = binding.edittextInstitute
        botonGuardarCambios = binding.buttonSaveChangesProfile
        emailEdittext = binding.editextEmail
        imagen = binding.imageProfile


        val appContext = context

        bd.collection("Usuarios").document(email).get()
            .addOnSuccessListener {
                var user = User(it.id,
                    it["nombre"].toString(),
                    it["apellidos"].toString(),
                    it["telefono"].toString(),
                    it["instituto"].toString(),
                    it["imgPerfil"].toString())

                nombreEditText.setText(user.nombre)
                apellidosEditText.setText(user.apellidos)
                numero.setText(user.telefono)
                insituto.setText(user.instituto)
                emailEdittext.setText(user.email)
                val localfile = File.createTempFile("tempImage","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.imageProfile.setImageBitmap(bitmap)
                }.addOnFailureListener{ //La carrega a fallat...

                    Toast.makeText(appContext,"La carrega de la imatge a fallat", Toast.LENGTH_LONG).show()

                }

                    //Picasso.get().load(user.imgPerfil.toUri())
                    //.into(imagen)

                //Picasso.get().load(photoSelectedUri).into(imagen)
                //Picasso.get().load(user.imgPerfil.toUri()).into(imagen)
            }


    }

    private fun subirArchivos(){
        val appContext = context
        resultLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        //Afegim la imatge seleccionada a storage
        photoSelectedUri?.let{uri-> //Hem seleccionat una imatge...
            //Afegim (pujem) la imatge que hem seleccionat mitjançant el mètode putFile de la classe FirebasStorage, passant-li com a
            //paràmetre l'URI de la imatge.
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(appContext,"La imatge s'ha pujat amb èxit", Toast.LENGTH_LONG).show()
                }
        }
        //Picasso.get().load(photoSelectedUri).into(imagen)
    }


}