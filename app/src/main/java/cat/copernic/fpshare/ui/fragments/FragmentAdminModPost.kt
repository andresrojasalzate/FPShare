package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentAdminModPostBinding
import cat.copernic.fpshare.databinding.FragmentAdminPostsBinding
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentAdminModPost : Fragment() {
    private var _binding: FragmentAdminModPostBinding? = null
    private val binding get() = _binding!!
    val bd = FirebaseFirestore.getInstance()
    private lateinit var tituloEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var enlaceEditText: EditText
    private lateinit var  botonGuardar: Button
    private lateinit var  botonEliminar: Button
    private var user =  Firebase.auth.currentUser

    private val args: FragmentAdminModPostArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminModPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicizalizar()
        botonGuardar.setOnClickListener {
            bd.collection("Ciclos").document(args.idCiclo).
            collection("Modulos").document(args.idModulo)
                .collection("UFs").document(args.idUf)
                .collection("Publicaciones")
                .document(args.idPubli).update(
                    "titulo",tituloEditText.text,
                    "descripcion",descripcionEditText.text,
                    "enlace",enlaceEditText.text
                )
        }
        botonEliminar.setOnClickListener {
            bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo).collection("UFs").document(args.idUf).collection("Publicaciones").document(args.idPubli).delete()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun  inicizalizar(){
        val email = user?.email.toString()
        tituloEditText = binding.textPost
        descripcionEditText = binding.textDescription
        enlaceEditText = binding.textLink

        botonGuardar= binding.btnPublish
        botonEliminar = binding.btnDelete
        bd.collection("Ciclos").document(args.idCiclo)
            .collection("Modulos").document(args.idModulo)
            .collection("UFs").document(args.idUf)
            .collection("Publicaciones").document(args.idPubli)
            .get().addOnSuccessListener {
                var publi = Publicacion(it.id,
                    it["perfil"].toString(),
                    it["titulo"].toString(),
                    it["descripcion"].toString(),
                    it["checked"].toString(),
                    it["enlace"].toString(),
                    it["imgPubli"].toString()
                )
                tituloEditText.setText(publi.titulo)
                descripcionEditText.setText(publi.descripcion)
                enlaceEditText.setText(publi.enlace)
            }
    }

}