package cat.copernic.fpshare.ui.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.fpshare.Manifest
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.databinding.ActivityMainBinding
import cat.copernic.fpshare.databinding.FragmentNuevaPublicacionBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.User
import cat.copernic.fpshare.ui.activities.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream


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
    private lateinit var idModulo: EditText
    private lateinit var idUf: EditText
    private lateinit var usuario: User
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var CODIGO_PERMISOS_WR = 2108

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
        idModulo = binding.setModule
        idUf = binding.setUF
        botonPdf = binding.btnPdf

        botonPublicar.setOnClickListener {
            var usuario = leerUsuario()
            var publi = llegirDades(usuario)

            if (publi.id.isNotEmpty() && publi.id.isNotBlank()) {
                anadirPublicacion(publi.checked, idModulo.text.toString(), idUf.text.toString(), publi)
            }
        }

        botonPdf.setOnClickListener {
            guardarPDF()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun guardarPDF(){
        var pdfDocument = PdfDocument()
        //var paint = Paint()
        var title = TextPaint()
        var descrip = TextPaint()
        var link = TextPaint()
        var descripcionText = descripcion.text.toString()


        var paginaInfo = PdfDocument.PageInfo.Builder(816,1054,1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        //var bitmap = BitmapFactory.decodeResource(resources, ..)

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        title.textSize = 20f
        canvas.drawText(titulo.text.toString(), 10f, 150f, title)

        link.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        link.textSize = 14f
        canvas.drawText(enlace.text.toString(), 10f, 150f, link)

        descrip.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        descrip.textSize = 14f

        var arrDescripcion = descripcionText.split("\n")

        var y = 200f
            for (item in arrDescripcion){
                canvas.drawText(item, 10f, y, descrip)
                y += 15
            }
            pdfDocument.finishPage(pagina1)

            val file = File(Environment.getExternalStorageDirectory(),"Archivo.pdf")
            //try{
                val appContext= context
                pdfDocument.writeTo(FileOutputStream(file))
                Toast.makeText(appContext,"PDF creado correctamente", Toast.LENGTH_LONG).show()
            //} catch (e: Exception) {
              //  e.printStackTrace()

            //}
        pdfDocument.close()
    }

    private fun leerUsuario():User{
         usuario = User()
         val correo = user?.email.toString()
         print(correo)
         bd.collection("Usuarios").document(user?.email.toString()).get().addOnSuccessListener { doc ->
             val usuario = doc.toObject(User::class.java)
             usuario!!.nombre = doc["nombre"].toString()
             usuario!!.email = doc["email"].toString()
             usuario!!.telefono = doc["telefono"].toString()
             usuario!!.instituto = doc["instituto"].toString()
             usuario!!.apellidos = doc["apellidos"].toString()
             usuario!!.imgPerfil = doc["imgPerfil"].toString()
             usuario!!.esAdmin = doc["esAdmin"] as Boolean
         }
         return usuario
     }

    private fun llegirDades(usuario:User): Publicacion {
        var publi = Publicacion()
        publi.id = "a"

            publi.imgPubli = usuario.imgPerfil
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

        return publi
    }

    private fun anadirPublicacion(checked: String, idModulo: String, idUf: String, publi: Publicacion) {
        val appContext = context
         bd.collection("Ciclos").document(checked)
             .collection("Modulos").document(idModulo)
             .collection("UFs").document(idUf)
             .collection("Publicaciones").add(publi)
            .addOnSuccessListener { //S'ha afegit el departament...
                Toast.makeText(appContext,"Documento añadido", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ //No s'ha afegit el departament...
                Toast.makeText(appContext,"Documento no añadido", Toast.LENGTH_LONG).show()
            }
    }
}
