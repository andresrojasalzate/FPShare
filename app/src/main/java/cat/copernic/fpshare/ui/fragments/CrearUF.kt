package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
    private lateinit var inputIDUf: EditText
    private lateinit var inputNameUf: EditText

    // Args
    private val args: CrearUFArgs by navArgs()

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

        inputIDUf = binding.inputIDuf
        inputNameUf = binding.inputNombreUf
    }

    private fun listeners() {

        buttonAddUf.setOnClickListener {
            val id = inputIDUf.text.toString()
            val nombre = inputNameUf.text.toString()

            if (campoVacio(id, nombre)) {
                val uf = Uf(id, nombre)
                addUF(uf, id)
            }
        }
    }

    /**
     * Función para añadir UFs dentro de un ciclo y de un modulo especificado por el usuario
     */
    private fun addUF(uf: Uf, id: String) {
        bd.collection("Ciclos").document(args.idCiclo)
            .collection("Modulos").document(args.idModulo)
            .collection("UFs").document(id).set(uf)
    }

    // Función para comprobar que no está vacio o en blanco los campos introducidos
    private fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty()
                && ID.isNotBlank() && nombre.isNotBlank()
    }
}