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
import cat.copernic.fpshare.databinding.FragmentCrearUBinding
import cat.copernic.fpshare.modelo.Uf
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment de la pantalla crear UF
 *
 * @author FPShare
 */
class CrearUF : Fragment() {

    // Binding
    private var _binding: FragmentCrearUBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddUf: Button

    // EditText
    private lateinit var inputIDUf: EditText
    private lateinit var inputNameUf: EditText

    // Args
    private val args: CrearUFArgs by navArgs()

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
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

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearUBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Con esta función inicilizamos los items de la pantalla
     */
    private fun inicializadores() {
        buttonAddUf = binding.btnAddUf

        inputIDUf = binding.inputIDuf
        inputNameUf = binding.inputNombreUf
    }

    private fun listeners() {
        /**
         * Corrutina para la lectura de UFs
         */
        buttonAddUf.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    consultaUF()
                }
            }
        }
    }

    /**
     * Función para añadir UFs dentro de un ciclo y de un modulo especificado por el usuario
     *
     * @param id
     * @param nombre
     */
    private fun addUF(id: String, nombre: String) {


        if (campoVacio(id, nombre)) {
            val uf = Uf(id, nombre)

            bd.collection("Ciclos").document(args.idCiclo)
                .collection("Modulos").document(args.idModulo)
                .collection("UFs").document(id).set(uf)
                .addOnSuccessListener {
                    Toast.makeText(context, getString(R.string.ufAñadirCorrecto), Toast.LENGTH_LONG)
                        .show()
                    ufBack()
                }
                .addOnFailureListener {
                    Toast.makeText(context, getString(R.string.ufAñadirError), Toast.LENGTH_LONG)
                        .show()
                }
        } else {
            Snackbar.make(
                binding.crearUF, getString(R.string.errorCamposVacios), Snackbar.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Función para obtener las UFs en la base de datos ya registrados
     */
    private suspend fun consultaUF() {
        delay(300)
        val id = inputIDUf.text.toString()
        val nombre = inputNameUf.text.toString()

        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos")
            .document(args.idModulo).collection("UFs").document(id).get()
            .addOnSuccessListener {
                /**
                 * Si el modulo dentro del ciclo seleccionado existe se avisa al usuario de que ya existe
                 * y no la creará
                 */
                if (it.exists()) {
                    Snackbar.make(
                        binding.crearUF,
                        getString(R.string.ufExists),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else {
                    /**
                     * Si el modulo no existe lo crea
                     */
                    addUF(id, nombre)
                }
            }
    }

    /**
     * Función para comprobar que no está vacio o en blanco los campos introducidos
     *
     * @param ID
     * @param nombre
     */
    private fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty()
                && ID.isNotBlank() && nombre.isNotBlank()
    }

    // Volver a la pantalla de modulos
    private fun ufBack() {
        val view = binding.root
        val action = CrearUFDirections.actionCrearUFToFragmentAdminUFs(args.idModulo, args.idCiclo)
        view.findNavController().navigate(action)
    }
}