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
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.FragmentCrearCicloBinding
import cat.copernic.fpshare.modelo.Cicle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment de pantalla de Creación de Ciclos
 *
 * @author FPShare
 */
class CrearCiclo : Fragment() {
    private var _binding: FragmentCrearCicloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddCicle: Button

    // EditText
    private lateinit var inputIDCicle: EditText
    private lateinit var inputNameCicle: EditText

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearCicloBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        buttonAddCicle = binding.btnAddCicle

        inputIDCicle = binding.inputIDCiclo
        inputNameCicle = binding.inputNombreCiclo
    }

    private fun listeners() {
        /**
         * Corrutina para la lectura de Ciclos
         */
        buttonAddCicle.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    consultaCiclos() // Consulta de ciclos
                }
            }
        }
    }

    /**
     * Función para consultar la lista de ciclos que hay en la base de datos para conocer
     * las existentes
     */
    private suspend fun consultaCiclos() {
        delay(300)
        val id = inputIDCicle.text.toString()
        val nombre = inputNameCicle.text.toString()

        bd.collection("Ciclos").document(id).get().addOnSuccessListener {
            /**
             * Si el ciclo existe se avisa al usuario de que ya existe y no la creará
             */
            if (it.exists()) {
                Snackbar.make(
                    binding.crearCiclo,
                    getString(R.string.cicloExiste),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                /**
                 * Si el ciclo no existe lo crea
                 */
                addCiclo(id, nombre)
            }
        }
    }

    /**
     * Función para agregar el ciclo que ha introducido el usuario en la base de datos
     */
    private fun addCiclo(id: String, nombre: String) {
        if (campoVacio(id, nombre)) {
            val ciclo = Cicle(id, nombre)

            bd.collection("Ciclos").document(id).set(ciclo)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        getString(R.string.cicloAñadidoCorrecto),
                        Toast.LENGTH_LONG
                    ).show()
                    ciclosBack()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        getString(R.string.cicloAñadidoError),
                        Toast.LENGTH_LONG
                    ).show()
                }
        } else { // Si no introduce todos los campos...
            Snackbar.make(
                binding.crearCiclo, getString(R.string.errorCamposVacios), Snackbar.LENGTH_LONG
            ).show()
        }
    }

    // Comprobar que no tenga campos vacios o en blanco
    private fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }

    // Función para volver hacia atrás después de crear el nuevo ciclo
    private fun ciclosBack() {
        val view = binding.root
        val action =
            CrearCicloDirections.actionCrearCicloToListaTagsAdministracion()
        view.findNavController().navigate(action)
    }
}