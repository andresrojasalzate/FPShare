package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.clases.Publicacion
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class pantalla_principal : Fragment() {

    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var btn_logout: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaPrincipalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        boton = binding.button3
        btn_logout = binding.btnLogout
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PubliAdapter(crearPublicacion())

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        boton.setOnClickListener {
            val action =
                pantalla_principalDirections.actionPantallaPrincipalToVistaPreviaPublicacion()
            view.findNavController().navigate(action)
        }
        btn_logout.setOnClickListener{
            Firebase.auth.signOut()
            val action =
                pantalla_principalDirections.actionPantallaPrincipalToLogin()
            view.findNavController().navigate(action)


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearPublicacion(): MutableList<Publicacion>{

        val publicaciones = mutableListOf<Publicacion>()

        for(num in 1 .. 10){
            publicaciones.add(Publicacion(context.resources(R.drawable.perfil), "Albert Montero","Resumen BBDD","Resumen de la teoria de Bases de datos del modelo Relacional.", "link"))
        }
        return publicaciones
    }



}
