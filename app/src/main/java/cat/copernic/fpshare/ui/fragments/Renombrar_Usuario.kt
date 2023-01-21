package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.databinding.FragmentRenombrarUsuarioBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Clase de la pantalla renombrar usuario
 *
 * @author FPShare
 */
class Renombrar_Usuario : Fragment() {

    private var _binding: FragmentRenombrarUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var edittextRaname : EditText
    private lateinit var botonRename: Button
    private var bd = FirebaseFirestore.getInstance()
    private val args: Renombrar_UsuarioArgs by navArgs()

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
        _binding = FragmentRenombrarUsuarioBinding.inflate(inflater, container, false)
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
        inicializar()
        botonRename.setOnClickListener {

            bd.collection("Usuarios").document(args.idUsuario).update("nombre", edittextRaname.text.toString())
        }

    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun inicializar(){
        edittextRaname = binding.InputName
        botonRename = binding.btnCambiar
    }
}