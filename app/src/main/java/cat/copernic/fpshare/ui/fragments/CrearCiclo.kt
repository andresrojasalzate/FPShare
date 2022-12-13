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
import cat.copernic.fpshare.databinding.FragmentCrearCicloBinding
import cat.copernic.fpshare.modelo.Cicle
import com.google.firebase.firestore.FirebaseFirestore

class CrearCiclo : Fragment() {
    private var _binding: FragmentCrearCicloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddCicle: Button

    // EditText
    private lateinit var inputIDCicle: EditText
    private lateinit var inputNameCicle: EditText

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

        buttonAddCicle.setOnClickListener {
            val id = inputIDCicle.text.toString()
            val nombre = inputNameCicle.text.toString()

            if (campoVacio(id, nombre)) {
                val ciclo = Cicle(id, nombre)
                addCiclo(ciclo, id)
            }
            ciclosBack()
        }
    }

    // Adición del ciclo nuevo en la base de datos
    private fun addCiclo(ciclo: Cicle, id: String) {
        val appContext = context
        bd.collection("Ciclos").document(id).set(ciclo)
            .addOnSuccessListener {
                Toast.makeText(appContext, "Ciclo añadido correctamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(appContext, "Error al añadir el ciclo", Toast.LENGTH_LONG).show()
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