package cat.copernic.fpshare.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.set
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemPubliBinding
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.ui.fragments.ENLACE
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PubliAdapter(private val publicaciones: List<Publicacion>) : RecyclerView.Adapter<PubliAdapter
.PubliViewHolder>() {
    private lateinit var contexto: Context
    var storage = FirebaseStorage.getInstance()

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
        val publicacion = publicaciones.get(position)

        with(viewHolder) {
            /***
             * Ponemos los textos en cada recuadro del Item Publi.
             */
            viewB.txtProf.text = publicacion.perfil
            viewB.txtPubliTitle.text = publicacion.titulo
            viewB.txtDescr.text = publicacion.descripcion
            viewB.textLink.text = publicacion.enlace

            /***
             * Carga de la ruta del enlace a la imagen de la publicacion
             */
            val storageRef = storage.reference.child("Imagenes/" + publicacion.imgPubli)

            /***
             * Colocamos la imagen en el ImageView del Item Publi.
             */
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                viewB.imgIcon.setImageBitmap(bitmap)
            }
            /***
             * Inicializacion del enlace
             */
            viewB.textLink.setOnClickListener{
                val queryUrl: Uri = Uri.parse(publicacion.enlace)
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                contexto?.startActivity(intent)

            }
        }

    }

    override fun getItemCount(): Int {
        return publicaciones.size
    }

}