package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentCrearUBinding
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore


class CrearUF : Fragment() {

    // Binding
    private var _binding: FragmentCrearUBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var buttonAddUf: Button

    // EditText
    private lateinit var inputIDCiclo: EditText
    private lateinit var inputIDModulo: EditText
    private lateinit var inputIDUf: EditText
    private lateinit var inputNameUf: EditText

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
        return binding.root
    }

    private fun inicializadores() {
        buttonAddUf = binding.btnAddUf

        inputIDCiclo = binding.selectCiclo
        inputIDModulo = binding.selectIDModul
        inputIDUf = binding.inputIDuf
        inputNameUf = binding.inputNombreUf
    }

    private fun listeners() {

        buttonAddUf.setOnClickListener {
            val idCiclo = inputIDCiclo.text.toString()
            val idModulo = inputIDModulo.text.toString()
            val id = inputIDUf.text.toString()
            val nombre = inputNameUf.text.toString()

            if (campoVacio(idCiclo, idModulo, id, nombre)) {
                val uf = Uf(id, nombre)
                addUF(idCiclo, idModulo, uf, id)
            }
        }
    }

    /**
     * Función para añadir UFs dentro de un ciclo y de un modulo especificado por el usuario
     */
    private fun addUF(idCiclo: String, idModulo: String, uf: Uf, id: String) {
        bd.collection("Ciclos").document(idCiclo)
            .collection("Modulos").document(idModulo)
            .collection("UFs").document(id).set(uf)
    }

    // Función para comprobar que no está vacio o en blanco los campos introducidos
    private fun campoVacio(idCiclo: String, idModulo: String, ID: String, nombre: String): Boolean {
        return idCiclo.isNotEmpty() && idModulo.isNotEmpty() && ID.isNotEmpty() && nombre.isNotEmpty()
                && ID.isNotBlank() && nombre.isNotBlank() && idCiclo.isNotBlank() && idModulo.isNotBlank()
    }
}