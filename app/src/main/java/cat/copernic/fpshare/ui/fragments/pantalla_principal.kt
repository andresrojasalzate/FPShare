package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class pantalla_principal : Fragment() {
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var btn_logout: Button
    private lateinit var recyclerView: RecyclerView

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
        val boton2 = binding.button

        boton2.setOnClickListener {
            val action = pantalla_principalDirections.actionPantallaPrincipalToCreacionForo()
            view.findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearPublicacion(): MutableList<Publicacion>{
        var publi = mutableListOf<Publicacion>()

        var publicacion1 = Publicacion("a","Albert Montero","Resumen BBDD","En este enlace se explica todo sobre Modelo entidad relacion, se incluyen ejercicios.","a","https://www.google.es/")
        var publicacion2 = Publicacion("a","Carlos Trujillo","Teoria Programacion","En este enlace se explica el funcionamiento de las etiquetas en Android. Vienen con algunos ejemplos.","a","https://www.google.es/")
        var publicacion3 = Publicacion("a","Andres Rojas","Ejercicios JDBC","En este enlace se explica como hacer la conexion de MySQL con Java. Incluye ejercicios con una Base de Datos que contiene informacion de un aeropuerto.","a","https://www.google.es/")
        var publicacion4 = Publicacion("a","Albert Montero","Resumen BBDD","En este enlace se explica todo sobre Modelo entidad relacion, se incluyen ejercicios.","a","https://www.google.es/")

        publi.add(publicacion1)
        publi.add(publicacion2)
        publi.add(publicacion3)
        publi.add(publicacion4)

        return publi

    }


}
