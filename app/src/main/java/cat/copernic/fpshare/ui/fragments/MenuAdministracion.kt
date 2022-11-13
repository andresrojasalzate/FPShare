package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentMenuAdministracionBinding


class MenuAdministracion : Fragment() {

    private var _binding: FragmentMenuAdministracionBinding? = null
    private val binding get() = _binding!!
    private lateinit var  botonUsers : Button
    private lateinit var botonPosts : Button
    private lateinit var botonTags : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuAdministracionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonUsers = binding.buttonUsersAdminMenu
        botonPosts= binding.buttonPostsAdminMenu
        botonTags = binding.buttonTagsAdminMenu

        botonUsers.setOnClickListener {
            val action =
                MenuAdministracionDirections.actionMenuAdministracionToListaUsuariosAdministracion()
            view.findNavController().navigate(action)
        }
        botonPosts.setOnClickListener {
            val action =
                MenuAdministracionDirections.actionMenuAdministracionToListaPublicacionesAdministracion()
            view.findNavController().navigate(action)
        }
        botonTags.setOnClickListener {
            val action =
                MenuAdministracionDirections.actionMenuAdministracionToListaTagsAdministracion()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}