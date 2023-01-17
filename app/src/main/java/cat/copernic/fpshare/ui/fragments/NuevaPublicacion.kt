package cat.copernic.fpshare.ui.fragments

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.fpshare.databinding.FragmentNuevaPublicacionBinding
import cat.copernic.fpshare.modelo.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class NuevaPublicacion : Fragment() {

    private var _binding: FragmentNuevaPublicacionBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var titulo: EditText
    private lateinit var descripcion: TextInputEditText
    private lateinit var enlace: TextInputEditText
    private var user = Firebase.auth.currentUser
    private lateinit var botonPublicar: Button
    private lateinit var idModuloSpinner: Spinner
    private lateinit var idUfSpinner: Spinner
    private lateinit var ciclo: String
    private lateinit var modulo: String
    private lateinit var uf: String
    private lateinit var arrayIdModulo: ArrayList<String>
    private lateinit var arrayIdUf: ArrayList<String>
    private lateinit var btnAdd: Button
    private val READ_REQUEST_CODE = 42
    private var storage = FirebaseStorage.getInstance()
    private lateinit var path: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevaPublicacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonPublicar = binding.btnPublish
        titulo = binding.textPost
        descripcion = binding.textDescription
        enlace = binding.textLink
        idModuloSpinner = binding.spinnerModulesNewPost
        idUfSpinner = binding.spinnerUfsNewPost
        btnAdd = binding.btnAdd!!


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

        idModuloSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                // Un item ha sido seleccionado
                modulo = arrayIdModulo[position]
                // Hacer algo con el item seleccionado
                cargarUfs(modulo)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ningún item
            }
        }

        idUfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                // Un item ha sido seleccionado
                uf = arrayIdUf[position]
                // Hacer algo con el item seleccionado

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ningún item
            }
        }


        botonPublicar.setOnClickListener {
            llegirDades()
        }

        btnAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.let { uri ->
                //upload the pdf to firebase storage
                val pdfRef = storage.reference.child("pdfs/${uri.lastPathSegment}")
                pdfRef.putFile(uri).addOnSuccessListener {
                    path = uri.toString()
                    Toast.makeText(context, "PDF Uploaded", Toast.LENGTH_LONG)
                        .show()
                }
                    .addOnFailureListener {
                        Toast.makeText(context, "error uploading pdf", Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }
    }




    private fun cargarUfs(idModulo: String) {
        val listaUfs = ArrayList<String>()
        arrayIdUf = ArrayList()
        bd.collection("Ciclos").document(ciclo).collection("Modulos").document(idModulo)
            .collection("UFs").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    listaUfs.add(document["nombre"].toString())
                    arrayIdUf.add(document.id)
                }

                val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
                adapter.addAll(listaUfs)
                idUfSpinner.adapter = adapter

            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun llegirDades() {
        val publi = Publicacion()
        val usuario = User()
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
                try {
                    anadirPublicacion(ciclo, modulo, uf, publi)
                } catch (e: UninitializedPropertyAccessException) {
                    Snackbar.make(
                        binding.root,
                        getString(cat.copernic.fpshare.R.string.errorUFNoEspecificada),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun cargarModulos(idCiclo: String) {
        val listaModulos = ArrayList<String>()
        arrayIdModulo = ArrayList()
        bd.collection("Ciclos").document(idCiclo).collection("Modulos").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayIdModulo.add(document.id)
                    listaModulos.add(document["nombre"].toString())
                }

                val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
                adapter.addAll(listaModulos)
                idModuloSpinner.adapter = adapter

            }
    }

    private fun anadirPublicacion(
        checked: String, idModulo: String, idUf: String, publi: Publicacion
    ) {
        val appContext = context
        /***
         * En añadir publicacion se define la ruta donde se guardara la publicacion.
         * La variable checked sera la id del Ciclo, el idModulo y idUf lo escribimos
         * en los EditText abajo.
         */

        if (!URLUtil.isValidUrl(publi.enlace)) {
            Snackbar.make(
                binding.constraintNuevaPublicacion,
                getString(cat.copernic.fpshare.R.string.invalidURL),
                Snackbar.LENGTH_LONG
            ).show()
        } else if (!algoVacio(publi.titulo, publi.descripcion, publi.enlace)) {
            Snackbar.make(
                binding.constraintNuevaPublicacion,
                getString(cat.copernic.fpshare.R.string.errorCamposVacios),
                Snackbar.LENGTH_LONG
            ).show()
        } else if (limiteCaracteresTitulo(publi.titulo)) {
            Snackbar.make(
                binding.constraintNuevaPublicacion,
                getString(cat.copernic.fpshare.R.string.errorTituloLargo),
                Snackbar.LENGTH_LONG
            ).show()
        } else if (limiteCaracteresDescripcion(publi.descripcion)) {
            Snackbar.make(
                binding.constraintNuevaPublicacion,
                getString(cat.copernic.fpshare.R.string.errorDescripcionLarga),
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            publi.idCiclo = checked
            publi.idModulo = idModulo
            publi.idUf = idUf
            publi.pathFile = path
            bd.collection("Ciclos").document(checked).collection("Modulos").document(idModulo)
                .collection("UFs").document(idUf).collection("Publicaciones").add(publi)
                .addOnSuccessListener { //S'ha afegit el departament...
                    val view = binding.root
                    val action =
                        NuevaPublicacionDirections.actionNuevaPublicacionToPantallaPrincipal()
                    view.findNavController().navigate(action)
                }.addOnFailureListener { //No s'ha afegit el departament...
                    Toast.makeText(appContext, "Documento no añadido", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun algoVacio(
        titulo: String, descripcion: String, enlace: String
    ): Boolean {
        return titulo.isNotEmpty() && titulo.isNotBlank()
                && descripcion.isNotEmpty() && descripcion.isNotBlank()
                && enlace.isNotEmpty() && enlace.isNotBlank()
    }

    private fun limiteCaracteresTitulo(titulo: String): Boolean {
        return titulo.length > 20
    }

    private fun limiteCaracteresDescripcion(descripcion: String): Boolean {
        return descripcion.length > 248
    }
}
