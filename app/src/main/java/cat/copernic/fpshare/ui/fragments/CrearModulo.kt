package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentCrearModuloBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CrearModulo : Fragment() {
    private var _binding: FragmentCrearModuloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    private lateinit var spinnerCiclos: Spinner
    private lateinit var idSpinner: MutableList<String>

    // Botones
    private lateinit var buttonAddModulo: Button
    private lateinit var buttonBack: Button

    // EditText
    private lateinit var inputIDModulo: EditText
    private lateinit var inputNameModulo: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadores()
        listeners()
        lifecycleScope.launch {
            var resultado = withContext(Dispatchers.IO) {
                idSpinner = leerIds()

                
            }
        }
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
        val view = binding.root
        return view
    }

    suspend fun leerIds(): MutableList<String> {

        var arrayCiclo = mutableListOf<String>()
        var resultado = bd.collection("Ciclos").get().await()

        for (document in resultado) {
            var idCiclo = document.id
            arrayCiclo.add(idCiclo)
        }
        return arrayCiclo
    }


    fun inicializadores() {
        buttonAddModulo = binding.btnAddModul
        buttonBack = binding.btnBack

        inputIDModulo = binding.inputIDModul
        inputNameModulo = binding.inputNombreModul

        spinnerCiclos = binding.selectCiclo
    }

    fun listeners() {

        buttonBack.setOnClickListener() {
            val action = CrearModuloDirections.actionCrearModuloToListaTagsAdministracion()
            view?.findNavController()?.navigate(action)
        }
        buttonAddModulo.setOnClickListener() {
            val ID = inputIDModulo.text.toString()
            val nombre = inputNameModulo.text.toString()

            if (campoVacio(ID, nombre)) {
                val modulo = Modul(ID, nombre)
                addModulo(modulo, ID)
            }
        }
    }

    fun addModulo(modulo: Modul, id: String) {
        bd.collection("Ciclos").document(id)
            .collection("Modulos").document(id).set(modulo)
    }

    fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }
}
