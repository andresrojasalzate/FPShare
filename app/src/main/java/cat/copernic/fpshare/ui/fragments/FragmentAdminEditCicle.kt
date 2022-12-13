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
import cat.copernic.fpshare.databinding.FragmentAdminEditCicleBinding
import com.google.firebase.firestore.FirebaseFirestore

class FragmentAdminEditCicle : Fragment() {

    // Binding
    private var _binding: FragmentAdminEditCicleBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private var bd = FirebaseFirestore.getInstance()

    // Botones
    private lateinit var botonGuardarCambios: Button
    private lateinit var botonBorrarCiclo: Button

    // EditText
    private lateinit var nombreNuevo: EditText

    // Args
    private val args: FragmentAdminEditCicleArgs by navArgs()

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
        _binding = FragmentAdminEditCicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun inicializadores() {
        botonBorrarCiclo = binding.btnDeleteCicle
        botonGuardarCambios = binding.btnSaveEdit

        nombreNuevo = binding.inputEditCicle
    }

    private fun listeners() {
        botonBorrarCiclo.setOnClickListener {
            borrarCiclo()
            modulosBack()
        }
        botonGuardarCambios.setOnClickListener {
            val nombre = nombreNuevo.text.toString()

            if (campoVacio(nombre)) {
                modificarCiclo(nombre)
            }
            modulosBack()
        }
    }

    private fun modulosBack() {
        val view = binding.root
        val action =
            FragmentAdminEditCicleDirections.actionFragmentAdminEditCicleToFragmentAdminModulos(args.idCiclo)
        view.findNavController().navigate(action)
    }

    private fun borrarCiclo() {
        bd.collection("Ciclos").document(args.idCiclo).delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Ciclo eliminado correctamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error en el borrado del ciclo", Toast.LENGTH_LONG).show()
            }
    }

    private fun modificarCiclo(nombreNuevo: String) {
        bd.collection("Ciclos").document(args.idCiclo).update("nombre", nombreNuevo)
    }

    // Comprobar que los campos no esten en blanco o vacíos
    private fun campoVacio(nombre: String): Boolean {
        return nombre.isNotEmpty() && nombre.isNotBlank()
    }
}