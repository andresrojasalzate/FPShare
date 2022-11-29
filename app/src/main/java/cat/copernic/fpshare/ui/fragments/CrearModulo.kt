package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentCrearModuloBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore


class CrearModulo : Fragment() {
    private var _binding: FragmentCrearModuloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    // private lateinit var spinnerCiclos: Spinner
    // private lateinit var idSpinner: MutableList<String>

    // Botones
    private lateinit var buttonAddModulo: Button
    private lateinit var buttonBack: Button

    // EditText
    private lateinit var inputIDCiclo: EditText
    private lateinit var inputIDModulo: EditText
    private lateinit var inputNameModulo: EditText

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
        _binding = FragmentCrearModuloBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*private suspend fun leerIds(): MutableList<String> {

        val arrayCiclo = mutableListOf<String>()
        val resultado = bd.collection("Ciclos").get().await()

        for (document in resultado) {
            val idCiclo = document.id
            arrayCiclo.add(idCiclo)
        }
        return arrayCiclo
    }*/


    private fun inicializadores() {
        buttonAddModulo = binding.btnAddModul
        buttonBack = binding.btnBack

        inputIDCiclo = binding.selectCiclo
        inputIDModulo = binding.inputIDModul
        inputNameModulo = binding.inputNombreModul

        // spinnerCiclos = binding.selectCiclo
    }

    private fun listeners() {

        buttonBack.setOnClickListener {
            val action = CrearModuloDirections.actionCrearModuloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
        buttonAddModulo.setOnClickListener {
            val idCiclo = inputIDCiclo.text.toString()
            val id = inputIDModulo.text.toString()
            val nombre = inputNameModulo.text.toString()

            if (campoVacio(idCiclo, id, nombre)) {
                val modulo = Modul(id, nombre)
                addModulo(idCiclo, modulo, id)
            }

            val action = CrearModuloDirections.actionCrearModuloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
    }

    private fun addModulo(idCiclo: String, modulo: Modul, id: String) {
        bd.collection("Ciclos").document(idCiclo)
            .collection("Modulos").document(id).set(modulo)
    }

    private fun campoVacio(idCiclo: String, ID: String, nombre: String): Boolean {
        return idCiclo.isNotEmpty() && ID.isNotEmpty() && nombre.isNotEmpty() && idCiclo.isNotBlank() && ID.isNotBlank() && nombre.isNotBlank()
    }
}
