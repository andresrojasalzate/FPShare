package cat.copernic.fpshare.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemPubliBinding
import cat.copernic.fpshare.modelo.Publicacion
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
            viewB.txtProf.text = publicacion.perfil
            viewB.txtPubliTitle.text = publicacion.titulo
            viewB.txtDescr.text = publicacion.descripcion
            viewB.textLink.text = publicacion.enlace
            var storageRef = storage.reference.child("Imagenes/" + publicacion.imgPubli)

            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                viewB.imgIcon.setImageBitmap(bitmap)
            }
        }

    }

    override fun getItemCount(): Int {
        return publicaciones.size
    }

}