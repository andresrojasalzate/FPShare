package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.fpshare.databinding.FragmentEditarPublicacionBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.android.material.textfield.TextInputEditText


class Editar_Publicacion : Fragment() {
    private var _binding: FragmentEditarPublicacionBinding? = null
    private val binding get() = _binding!!
    private lateinit var botonGuardar: Button
    private lateinit var renombrarTitulo: EditText
    private lateinit var renombrarDescripcion: TextInputEditText
    private lateinit var renombrarEnlace: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarPublicacionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonGuardar=binding.btnSave
        renombrarTitulo=binding.renameTitle
        renombrarDescripcion=binding.renameDesc


        botonGuardar.setOnClickListener{
            //modificarPublicacion()



        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
    fun modificarPublicacion(publicacion: Publicacion){
        //Modigiquem el departament
        bd.collection("Publicaciones").add(publicacion)
            .update()

            .addOnSuccessListener { //S'ha modificat el departament...
                Toast.makeText(applicationContext,"El Deparatment s'ha modificat correctament", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha afegit el departament...
                Toast.makeText(applicationContext,"El Deparatment no s'ha modificat", Toast.LENGTH_LONG).show()
            }
    }

    fun modificarPlantaDepartament(codi:String, planta:String){
        //Cerquem el document on està el departamanet que volem modificar
        //Amb el mètode update modifiquem el valor del camp passat com a primer paràmetre (en el nostre cas "planta"), pel valor passat com a
        //segon paràmetre. Si volem modificar més d'un camp a l'hora, utilitzarem la col.lecció Map mitjançant la funció mapOf()
        bd.collection("Departaments").document(codi)
            .update("planta",planta)
            .addOnSuccessListener { //S'ha modificat la planta...
                Toast.makeText(applicationContext,"S'ha modificat la planta del departament per $planta", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha modificat la planta...
                Toast.makeText(applicationContext,"No s'ha modificat la planta del departament", Toast.LENGTH_LONG).show()
            }
    }

 */

}