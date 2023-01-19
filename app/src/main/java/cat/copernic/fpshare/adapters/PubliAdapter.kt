package cat.copernic.fpshare.adapters

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getCodeCacheDir
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemPubliBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.tasks.await
import java.io.File

/**
 * Adaptador para la visualización de publicaciones en un recyclerView
 *
 * @author FPShare
 *
 * @param publicaciones
 */
class PubliAdapter(private val publicaciones: List<Publicacion>) : RecyclerView.Adapter<PubliAdapter
.PubliViewHolder>(), Filterable {

    private lateinit var contexto: Context
    var storage = FirebaseStorage.getInstance()

    /**
     * Cargamos en publiFilter la lista de publicaciones que entran por el Adapter.
     */
    var publiFilter: List<Publicacion> = publicaciones

    inner class PubliViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val viewB = ItemPubliBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PubliViewHolder {
        contexto = viewGroup.context
        val view = LayoutInflater.from(contexto)
            .inflate(R.layout.item_publi, viewGroup, false)

        return PubliViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PubliViewHolder, position: Int) {
        val publicacion = publiFilter.get(position)
        with(viewHolder) {
            /**
             * Ponemos los textos en cada recuadro del Item Publi.
             */
            viewB.txtProf.text = publicacion.perfil
            viewB.txtPubliTitle.text = publicacion.titulo
            viewB.txtDescr.text = publicacion.descripcion
            viewB.textLink.text = publicacion.enlace

            /*val db = FirebaseFirestore.getInstance()

            db.collection("Ciclos").document(publicacion.checked)
                .collection("Modulos").document(publicacion.idModulo)
                .get().addOnSuccessListener { document ->
                val nombreModulo = document["nombre"].toString()
                viewB.textModulo.setText(nombreModulo)
            }*/

            /**
             * Carga de la ruta del enlace a la imagen de la publicacion
             *
             * Colocamos la imagen en el ImageView del Item Publi.
             */

            val storageRef = storage.reference.child("Imagenes/" + publicacion.imgPubli)
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                viewB.imgIcon.setImageBitmap(bitmap)
            }

            /*
            val pdfRef = storageRef.child("pdfs/${path.lastPathSegment}")
            pdfRef.putFile(path).addOnSuccessListener { taskSnapshot ->
                val pdfName = taskSnapshot.metadata?.name
                viewB.textLink.text = pdfName
            }*/
            /*
            viewB.txtDescarga.setOnClickListener {
                val pdfRef = storage.reference.child("pdfs/" + publicacion.pathFile)
                val readfile = File.createTempFile("tempFile", "pdf")
                pdfRef.getFile(readfile).addOnSuccessListener {
                    val queryUrl: Uri = Uri.parse(readfile.path)
                    val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                    contexto.startActivity(intent)
                }
            */
            viewB.txtDescarga.setOnClickListener {
                val uri: Uri = Uri.parse(publicacion.pathFile)
                val pdfFileRef = storage.reference.child("pdfs/" + uri.lastPathSegment)
                val queryUrl: Uri = Uri.parse()
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                contexto.startActivity(intent)
            }
                /*
                val localFile = File.createTempFile("files", "pdf")

                pdfFileRef.getFile(localFile).addOnSuccessListener {
                    // Local temp file has been created
                    Log.d("TAG", "File Downloaded")
                }.addOnFailureListener {
                    // Handle any errors
                    Log.d("TAG", "Something went wrong")
                }
*/

                /*
                val localfile = File.createTempFile("tempFile", "pdf")
                var storageRef = storage.reference.child("pdfs/" + publicacion.pathFile)
                try {

                    storageRef.getFile(publicacion.pathFile.toUri()).addOnSuccessListener {
                        // Abrir el archivo pdf descargado
                        val pdfUri = FileProvider.getUriForFile(
                            contexto,
                            "com.your_package_name.fileprovider",
                            localfile
                        )
                        val pdfIntent = Intent(Intent.ACTION_VIEW)
                        pdfIntent.setDataAndType(pdfUri, "application/pdf")
                    }
                }catch (e: StorageException){
                    println("Error")
                }


                var pdfRef = storage.reference.child("pdfs/" + publicacion.pathFile)
                val localFile = File.createTempFile("apuntes", "pdf", contexto.cacheDir)
                pdfRef.getFile(localFile)
                    .addOnSuccessListener {
                        // Abrir el archivo pdf descargado
                        val pdfUri = FileProvider.getUriForFile(contexto, "com.fpshare.fileprovider", localFile)
                        val pdfIntent = Intent(Intent.ACTION_VIEW)
                        pdfIntent.setDataAndType(pdfUri, "application/pdf")
                        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        contexto.startActivity(pdfIntent)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error downloading PDF")
                    }

                 */

                /**
                 * Inicializacion del enlace
                 */
                viewB.textLink.setOnClickListener {
                    val queryUrl: Uri = Uri.parse(publicacion.enlace)
                    val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                    contexto.startActivity(intent)

                }


            }
        }
    }
    override fun getItemCount(): Int {
        return publiFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                /**
                 * Si no hay una query, el adapter devolvera todas las publicaciones. Para ello,
                 * las cargará en publiFilter, puesto que no hay ningun filtro.
                 */
                if (charString.isEmpty()) {
                    publiFilter = publicaciones
                } else {
                    /**
                     * Iniciamos una lista que contendrá los resultados filtrados.
                     */
                    val filteredList = mutableListOf<Publicacion>()
                    /**
                     * Definimos el filtro, donde comprobaremos si el titulo de la publicacion contiene la query escrita en el buscador.
                     */
                    publicaciones
                        .filter {
                            (it.titulo.toLowerCase()
                                .contains(constraint!!.toString().toLowerCase()))
                        }
                        /**
                         * Todos los resultados que contienen la query en el titulo de la
                         * publicacion seran añadidos en la lista para, despues, pasarlos a
                         * publiFilter.
                         */
                        .forEach { filteredList.add(it) }
                    publiFilter = filteredList

                }
                /**
                 * Retornamos todos los valores.
                 */
                return FilterResults().apply { values = publiFilter}
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                /**
                 * Si no hay un valor en values, publiFilter será una lista vacia.
                 */
                publiFilter = if (results?.values == null)
                    mutableListOf()
                /**
                 * Sino, añadira los valores a una Lista de publicaciones.
                 */
                else
                    results.values as List<Publicacion>
                notifyDataSetChanged()
            }
        }
    }
}

