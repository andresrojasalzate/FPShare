package cat.copernic.fpshare.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding

class pantalla_principal : Fragment() {
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button

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
        // botonNewPost = binding

        boton.setOnClickListener {
            val action = pantalla_principalDirections.actionPantallaPrincipalToVistaPreviaPublicacion()
            view.findNavController().navigate(action)

        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
