package cat.copernic.fpshare.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentCrearModuloBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore


class CrearModulo : Fragment() {
    private var _binding: FragmentCrearModuloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    private lateinit var spinnerCiclos: Spinner

    // Botones
    private lateinit var buttonAddModulo: Button
    private lateinit var buttonBack: Button

    // EditText
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
        val view = binding.root
        return view
    }

    private fun spinnerCiclos() {
        var arrayCiclo = mutableListOf<Cicle>()

        bd.collection("Ciclos").document().get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var idCiclo = document.id
                    var nombreCiclo = document["nombre"].toString()
                    val ciclo = Cicle(
                        idCiclo, nombreCiclo,
                        listOf(
                            Modul(
                                "", "",
                                listOf(
                                    Uf(
                                        "", "", listOf(
                                            Publicacion("", "", "", "", "", "")
                                                    arrayCiclo . add (ciclo)
                                            var ciclosSpinner = Cicle (it.id)
                }

                val resultado = ListAdapter(this, R.layout.simple_spinner_item, ciclosSpinner)
                resultado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
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
                val modulo = Modul(ID, nombre, emptyList())
                addModulo(modulo, ID)
            }
        }
        spinnerCiclos.
    }

    fun addModulo(modulo: Modul, id: String) {

    }

    fun campoVacio(ID: String, nombre: String): Boolean {
        return ID.isNotEmpty() && nombre.isNotEmpty() && ID.isNotBlank() && nombre.isNotBlank()
    }
}