package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
    private lateinit var buttonBack: Button

    // EditText
    private lateinit var inputIDCicle: EditText
    private lateinit var inputNameCicle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        val view = binding.root
        return view
    }

    fun inicializadores() {
        buttonAddCicle = binding.btnAddCicle
        buttonBack = binding.btnBack

        inputIDCicle = binding.inputIDCiclo
        inputNameCicle = binding.inputNombreCiclo
    }

    fun listeners() {
        buttonBack.setOnClickListener() {
            val action = CrearCicloDirections.actionCrearCicloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
        buttonAddCicle.setOnClickListener() {
            val ID = inputIDCicle.text.toString()
            val nombre = inputNameCicle.text.toString()

            if (campoVacio(ID, nombre)) {
                val ciclo = Cicle(ID, nombre)
                addCiclo(ciclo, ID)
            }

            val action = CrearCicloDirections.actionCrearCicloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
    }

    fun addCiclo(ciclo: Cicle, id: String) {
        bd.collection("Ciclos").document(id).set(ciclo)
    }

    fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }
}