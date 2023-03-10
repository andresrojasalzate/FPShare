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
import cat.copernic.fpshare.databinding.FragmentAdminEditModuleBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment de la pantalla de edición de Modulos
 *
 * @author FPShare
 */
class FragmentAdminEditModule : Fragment() {

    // Binding
    private var _binding: FragmentAdminEditModuleBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var botonGuardarCambios: Button

    // EditText
    private lateinit var nombreNuevo: EditText

    // Args
    private val args: FragmentAdminEditModuleArgs by navArgs()

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
        _binding = FragmentAdminEditModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        botonGuardarCambios = binding.btnSaveEdit

        nombreNuevo = binding.inputEditNameModule
    }

    private fun listeners() {
        botonGuardarCambios.setOnClickListener {
            val nombre = nombreNuevo.text.toString()

            if (campoVacio(nombre)) {
                modificarModulo(nombre)
            }
            ufsBack() // Navegación para volver al menú UFs
        }
    }

    /**
     * Esta función realiza la navegación para volver al menú despues de modificar el modulo
     */
    private fun ufsBack() {
        val view = binding.root
        val action =
            FragmentAdminEditModuleDirections.actionFragmentAdminEditModuleToFragmentAdminUFs(
                args.idModulo, args.idCiclo
            )
        view.findNavController().navigate(action)
    }

    /**
     * Función para modificar el nombre del ciclo
     *
     * @param nombreNuevo
     */
    private fun modificarModulo(nombreNuevo: String) {
        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo)
            .update("nombre", nombreNuevo)
    }

    /**
     * Comprobar que los campos no esten en blanco o vacíos
     *
     * @param nombre
     *
     * @return boolean
     */
    private fun campoVacio(nombre: String): Boolean {
        return nombre.isNotEmpty() && nombre.isNotBlank()
    }
}
