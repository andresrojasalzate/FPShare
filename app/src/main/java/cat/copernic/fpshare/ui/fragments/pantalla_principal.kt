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


}
