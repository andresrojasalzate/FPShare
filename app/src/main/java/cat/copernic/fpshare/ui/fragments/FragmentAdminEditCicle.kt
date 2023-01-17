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
import cat.copernic.fpshare.databinding.FragmentAdminEditCicleBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment de la pantalla de Adminstración de edición de ciclos
 *
 * @author FPShare
 */
class FragmentAdminEditCicle : Fragment() {

    // Binding
    private var _binding: FragmentAdminEditCicleBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var botonGuardarCambios: Button

    // EditText
    private lateinit var nombreNuevo: EditText

    // Args
    private val args: FragmentAdminEditCicleArgs by navArgs()

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
        _binding = FragmentAdminEditCicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        botonGuardarCambios = binding.btnSaveEdit

        nombreNuevo = binding.inputEditCicle
    }

    private fun listeners() {
        botonGuardarCambios.setOnClickListener {
            val nombre = nombreNuevo.text.toString()

            if (campoVacio(nombre)) {
                modificarCiclo(nombre) // Modificación del ciclo
            }
            modulosBack() // Navegación hacia atrás al menú modulos
        }
    }

    /**
     * Función para volver hacia atrás
     */
    private fun modulosBack() {
        val view = binding.root
        val action =
            FragmentAdminEditCicleDirections.actionFragmentAdminEditCicleToFragmentAdminModulos(args.idCiclo)
        view.findNavController().navigate(action)
    }

    /**
     * Esta función modifica el ciclo seleccionado
     */
    private fun modificarCiclo(nombreNuevo: String) {
        bd.collection("Ciclos").document(args.idCiclo).update("nombre", nombreNuevo)
    }

    /**
     * Comprobar que los campos no esten en blanco o vacíos
     */
    private fun campoVacio(nombre: String): Boolean {
        return nombre.isNotEmpty() && nombre.isNotBlank()
    }
}