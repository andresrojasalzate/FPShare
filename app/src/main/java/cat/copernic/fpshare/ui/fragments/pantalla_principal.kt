package cat.copernic.fpshare.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.clases.Datasource
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.ui.activities.Login
import cat.copernic.fpshare.ui.activities.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class pantalla_principal : Fragment() {

    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var btn_logout: Button

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

        // Initialize data.
        val myDataset = Datasource().loadPublicacion()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        /*boton = binding.button3
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


        }*/

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
