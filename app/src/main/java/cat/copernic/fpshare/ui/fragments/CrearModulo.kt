package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentCrearModuloBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment de pantalla Creación modulos
 *
 * @author FPShare
 */
class CrearModulo : Fragment() {

    // Binding
    private var _binding: FragmentCrearModuloBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddModulo: Button

    // EditText
    private lateinit var inputIDModulo: EditText
    private lateinit var inputNameModulo: EditText

    // Args
    private val args: CrearModuloArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadores() // Inicialización de elementos en pantalla
        listeners() // Escuchadores de pulsaciones
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearModuloBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        buttonAddModulo = binding.btnAddModul

        inputIDModulo = binding.inputIDModul
        inputNameModulo = binding.inputNombreModul
    }

    private fun listeners() {
        /**
         * Corrutina para la lectura de Modulos
         */
        buttonAddModulo.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    consultaModulos()
                }
            }
        }
    }

    /**
     * Escritura de nuevo modulo, añadido dentro del ciclo que hemos seleccionado anteriormente
     * en la pantalla de ciclos
     *
     * @param id
     * @param nombre
     */
    private fun addModulo(id: String, nombre: String) {
        if (campoVacio(id, nombre)) {
            val modulo = Modul(id, nombre)

            bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(id)
                .set(modulo)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        getString(R.string.moduloAñadirCorrecto),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    modulosBack()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        getString(R.string.moduloAñadirError),
                        Toast.LENGTH_LONG
                    ).show()
                }
        } else {
            Snackbar.make(
                binding.crearModulo, getString(R.string.errorCamposVacios), Snackbar.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Función para la consulta de modulos para obtener los que ya están registrados en la base
     * de datos
     */
    private suspend fun consultaModulos() {
        delay(300)
        val id = inputIDModulo.text.toString()
        val nombre = inputNameModulo.text.toString()

        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(id)
            .get()
            .addOnSuccessListener {
                /**
                 * Si el modulo dentro del ciclo seleccionado existe se avisa al usuario de que ya existe
                 * y no la creará
                 */
                if (it.exists()) {
                    Snackbar.make(
                        binding.crearModulo,
                        getString(R.string.moduloExiste),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else {
                    /**
                     * Si el modulo no existe lo crea
                     */
                    addModulo(id, nombre)
                }
            }
    }

    /**
     * Comprobar que los campos no esten en blanco o vacíos
     *
     * @param ID
     * @param nombre
     *
     * @return boolean
     */
    private fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }

    // Volver a la pantalla de modulos
    private fun modulosBack() {
        val view = binding.root
        val action = CrearModuloDirections.actionCrearModuloToFragmentAdminModulos(args.idCiclo)
        view.findNavController().navigate(action)
    }
}