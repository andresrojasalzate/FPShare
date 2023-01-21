package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentMenuAdministracionBinding

/**
 * Clase de la pantalla MenuAdmin
 *
 * @author FPShare
 */
class MenuAdministracion : Fragment() {

    private var _binding: FragmentMenuAdministracionBinding? = null
    private val binding get() = _binding!!
    private lateinit var  botonUsers : Button
    private lateinit var botonTags : Button

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuAdministracionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonUsers = binding.buttonUsersAdminMenu
        botonTags = binding.buttonTagsAdminMenu

        botonUsers.setOnClickListener {
            val action =
                MenuAdministracionDirections.actionMenuAdministracionToListaUsuariosAdministracion()
            view.findNavController().navigate(action)
        }

        botonTags.setOnClickListener {
            val action =
                MenuAdministracionDirections.actionMenuAdministracionToListaTagsAdministracion()
            view.findNavController().navigate(action)
        }
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}