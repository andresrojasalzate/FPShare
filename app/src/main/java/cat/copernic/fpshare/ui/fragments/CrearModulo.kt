package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.fpshare.databinding.FragmentCrearModuloBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore


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
        _binding = FragmentCrearModuloBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        buttonAddModulo = binding.btnAddModul

        inputIDModulo = binding.inputIDModul
        inputNameModulo = binding.inputNombreModul
    }

    private fun listeners() {
        buttonAddModulo.setOnClickListener {
            val id = inputIDModulo.text.toString()
            val nombre = inputNameModulo.text.toString()

            if (campoVacio(id, nombre)) {
                val modulo = Modul(id, nombre)
                addModulo(modulo, id)
            }
            modulosBack()
        }
    }

    /**
     * Escritura de nuevo modulo, añadido dentro del ciclo que hemos seleccionado anteriormente
     * en la pantalla de ciclos
     */
    private fun addModulo(modulo: Modul, id: String) {
        bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(id)
            .set(modulo).addOnSuccessListener {
                Toast.makeText(context, "Modulo creado correctamente", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Error al crear modulo", Toast.LENGTH_LONG).show()
            }
    }

    // Comprobar que los campos no esten en blanco o vacíos
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
