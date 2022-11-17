package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentCrearUBinding
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore


class CrearUF : Fragment() {

    private var _binding: FragmentCrearUBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddUf: Button
    private lateinit var buttonBack: Button

    // EditText
    private lateinit var inputIDUf: EditText
    private lateinit var inputNameUf: EditText

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearUBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    fun inicializadores() {
        buttonAddUf = binding.btnAddUf
        buttonBack = binding.btnBack

        inputIDUf = binding.inputIDuf
        inputNameUf = binding.inputNombreUf
    }

    fun listeners() {
        buttonBack.setOnClickListener() {
            val action = CrearCicloDirections.actionCrearCicloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
        buttonAddUf.setOnClickListener() {
            val ID = inputIDUf.text.toString()
            val nombre = inputNameUf.text.toString()

            if (campoVacio(ID, nombre)) {
                val uf = Uf(ID, nombre)
                addUF(uf, ID)
            }
        }
    }

    fun addUF(uf: Uf, id: String) {
        bd.collection("Ciclos").document(id)
            .collection("Modulos").document(id)
            .collection("UFs").document(id).set(uf)
    }

    fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }
}