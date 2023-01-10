package cat.copernic.fpshare.ui.fragments

import android.R
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentNuevaPublicacionBinding
import cat.copernic.fpshare.modelo.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


class NuevaPublicacion : Fragment() {

    private var _binding: FragmentNuevaPublicacionBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var titulo: EditText
    private lateinit var descripcion: TextInputEditText
    private lateinit var enlace: TextInputEditText
    private var user = Firebase.auth.currentUser
    private lateinit var botonPublicar: Button
    private lateinit var botonPdf: Button
    private lateinit var idModulo: Spinner
    private lateinit var idUf: Spinner
    private lateinit var ciclo: String
    private lateinit var modulo: String
    private lateinit var  uf: String
    private lateinit var arrayIdModulo: ArrayList<String>
    private lateinit var arrayIdUf: ArrayList<String>
    private lateinit var usuario: User
    private lateinit var matcher: Matcher
    private lateinit var pattern: Pattern

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNuevaPublicacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonPublicar = binding.btnPublish
        titulo = binding.textPost
        descripcion = binding.textDescription
        enlace = binding.textLink
        idModulo = binding.spinnerModulesNewPost
        idUf = binding.spinnerUfsNewPost
        binding.tagsCicles.setOnCheckedChangeListener { group, checkedId ->
            if (binding.optionDam.isChecked) {

                cargarModulos("DAM")
                ciclo = "DAM"
            } else if (binding.optionDaw.isChecked) {

                cargarModulos("DAW")
                ciclo = "DAW"
            } else if (binding.optionSmix.isChecked) {

                cargarModulos("SMIR")
                ciclo = "SMIR"
            } else if (binding.optionAsix.isChecked) {

                cargarModulos("ASIR")
                ciclo = "ASIR"
            }
        }

        idModulo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Un item ha sido seleccionado
                modulo = arrayIdModulo.get(position)
                // Hacer algo con el item seleccionado
                cargarUfs(modulo)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ningún item
            }
        }

        idUf.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Un item ha sido seleccionado
                 uf = arrayIdUf.get(position)
                // Hacer algo con el item seleccionado

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ningún item
            }
        }

        botonPublicar.setOnClickListener {
            llegirDades()
        }
    }

    private fun cargarUfs(idModulo: String) {
        val listaUfs =  ArrayList<String>()
        arrayIdUf = ArrayList()
        bd.collection("Ciclos").document(ciclo)
            .collection("Modulos").document(idModulo).collection("UFs").get().addOnSuccessListener { documents ->
                for (document in documents){
                    listaUfs.add(document["nombre"].toString())
                    arrayIdUf.add(document.id)
                }

                val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
                adapter.addAll(listaUfs)
                idUf.adapter = adapter

            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun llegirDades() {
        var publi = Publicacion()
        var usuario = User()
        val correo = user?.email.toString()
        /**
         * Leemos los datos del usuario actual.
         * NOTA: Si queremos tratar con datos de dos publicaciones, debe de ser en la misma
         * snapshot. No podemos tratar con datos que esten fuera de la snapshot, porque kotlin
         * no puede guardarlos fuera.
         */
        bd.collection("Usuarios").document(correo).get().addOnSuccessListener { doc ->
            //val usuario = doc.toObject(User::class.java)
            usuario.nombre = doc["nombre"].toString()
            usuario.email = doc["email"].toString()
            usuario.telefono = doc["telefono"].toString()
            usuario.instituto = doc["instituto"].toString()
            usuario.apellidos = doc["apellidos"].toString()
            usuario.imgPerfil = doc["imgPerfil"].toString()
            usuario.esAdmin = doc["esAdmin"] as Boolean


            /**
             * Creamos una publicacion y utilizamos los datos del usuario para generar la publicacion.
             */
            publi.id = "a"
            publi.imgPubli = usuario.email
            publi.perfil = usuario.nombre + " " + usuario.apellidos
            publi.titulo = titulo.text.toString()
            publi.descripcion = descripcion.text.toString()
            publi.checked = ""

            if (binding.optionDam.isChecked) {
                publi.checked = "DAM"

            } else if (binding.optionDaw.isChecked) {
                publi.checked = "DAW"

            } else if (binding.optionSmix.isChecked) {
                publi.checked = "SMIR"

            } else if (binding.optionAsix.isChecked) {
                publi.checked = "ASIR"

            }
            publi.enlace = enlace.text.toString()
            /**
             * Si la ID no esta vacia, añadiremos la publicacion en el Storage.
             */
            if (publi.id.isNotEmpty() && publi.id.isNotBlank()) {
                anadirPublicacion(ciclo, modulo, uf, publi)
            }
        }

    }

    private fun cargarModulos(idCiclo: String){
        val listaModulos =  ArrayList<String>()
        arrayIdModulo = ArrayList()
        bd.collection("Ciclos").document(idCiclo)
            .collection("Modulos").get().addOnSuccessListener { documents ->
            for (document in documents){
                arrayIdModulo.add(document.id)
                listaModulos.add(document["nombre"].toString())
            }

                val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
                adapter.addAll(listaModulos)
                idModulo.adapter = adapter

            }
    }

    private fun anadirPublicacion(checked: String, idModulo: String, idUf: String, publi: Publicacion) {
        val appContext = context
        /***
         * En añadir publicacion se define la ruta donde se guardara la publicacion.
         * La variable checked sera la id del Ciclo, el idModulo y idUf lo escribimos
         * en los EditText abajo.
         */
         if(URLUtil.isValidUrl(publi.enlace)){
             bd.collection("Ciclos").document(checked)
                 .collection("Modulos").document(idModulo)
                 .collection("UFs").document(idUf)
                 .collection("Publicaciones").add(publi)
                 .addOnSuccessListener { //S'ha afegit el departament...
                     val view = binding.root
                     val action = NuevaPublicacionDirections.actionNuevaPublicacionToPantallaPrincipal()
                     view.findNavController().navigate(action)
                 }
                 .addOnFailureListener{ //No s'ha afegit el departament...
                     Toast.makeText(appContext,"Documento no añadido", Toast.LENGTH_LONG).show()
                 }
         }else{
             Snackbar.make(
                 binding.constraintNuevaPublicacion!!, "Introduce un enlace valido.", Snackbar.LENGTH_LONG
             ).show()

         }
    }
}
