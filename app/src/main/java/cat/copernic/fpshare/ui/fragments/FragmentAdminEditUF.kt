package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.databinding.FragmentAdminEditUBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment de pantalla de edición de UFs
 *
 * @author FPShare
 */
class FragmentAdminEditUF : Fragment() {

    // Binding
    private var _binding: FragmentAdminEditUBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var botonGuardarCambios: Button

    // EditText
    private lateinit var nombreNuevo: EditText

    // Args
    private val args: FragmentAdminEditUFArgs by navArgs()

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadores()
        listeners()
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminEditUBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        botonGuardarCambios = binding.btnSaveEdit

        nombreNuevo = binding.inputEditNameUF
    }

    private fun listeners() {
        botonGuardarCambios.setOnClickListener {
            val nombre = nombreNuevo.text.toString()

            if (campoVacio(nombre)) {
                modificarUF(nombre)
            }
            postsBack() // Navegación hacia la pantalla de publicaciones
        }
    }

    /**
     * Función para navegar hacia atrás de nuevo
     */
    private fun postsBack() {
        val view = binding.root
        val action =
            FragmentAdminEditUFDirections.actionFragmentAdminEditUFToFragmentAdminPosts(
                args.idCiclo, args.idModulo, args.idUF
            )
        view.findNavController().navigate(action)
    }

    /**
     * Función para modificar el nombre de la UF
     */
    private fun modificarUF(nombreNuevo: String) {
        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo)
            .collection("UFs").document(args.idUF).update("nombre", nombreNuevo)
    }

    /**
     * Comprobar que los campos no esten en blanco o vacíos
     */
    private fun campoVacio(nombre: String): Boolean {
        return nombre.isNotEmpty() && nombre.isNotBlank()
    }
}