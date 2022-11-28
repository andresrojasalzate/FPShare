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
    private lateinit var inputIDCiclo: EditText
    private lateinit var inputIDModulo: EditText
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

        inputIDCiclo = binding.selectCiclo
        inputIDModulo = binding.selectIDModul
        inputIDUf = binding.inputIDuf
        inputNameUf = binding.inputNombreUf
    }

    fun listeners() {
        buttonBack.setOnClickListener() {
            val action = CrearUFDirections.actionCrearUFToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
        buttonAddUf.setOnClickListener() {
            val idCiclo = inputIDCiclo.text.toString()
            val idModulo = inputIDModulo.text.toString()
            val ID = inputIDUf.text.toString()
            val nombre = inputNameUf.text.toString()

            if (campoVacio(idCiclo, idModulo, ID, nombre)) {
                val uf = Uf(ID, nombre)
                addUF(idCiclo, idModulo, uf, ID)
            }

            val action = CrearUFDirections.actionCrearUFToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
    }

    fun addUF(idCiclo: String, idModulo: String, uf: Uf, id: String) {
        bd.collection("Ciclos").document(idCiclo)
            .collection("Modulos").document(idModulo)
            .collection("UFs").document(id).set(uf)
    }

    fun campoVacio(idCiclo: String, idModulo: String, ID: String, nombre: String): Boolean {
        return idCiclo.isNotEmpty() && idModulo.isNotEmpty() && ID.isNotEmpty() && nombre.isNotEmpty()
                && ID.isNotBlank() && nombre.isNotBlank() && idCiclo.isNotBlank() && idModulo.isNotBlank()
    }
}