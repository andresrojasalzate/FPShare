package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.clases.Publicacion
import cat.copernic.fpshare.databinding.ItemPubliBinding
import cat.copernic.fpshare.databinding.ItemViewBinding

class PubliAdapter (private val publicaciones: List<Publicacion>) : RecyclerView.Adapter<PubliAdapter.PubliViewHolder>() {
    private lateinit var contexto: Context

    inner class PubliViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val viewB = ItemPubliBinding.bind(view)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PubliViewHolder {
        // Create a new view, which defines the UI of the list item
        contexto=viewGroup.context
        val view = LayoutInflater.from(contexto)
            .inflate(R.layout.item_publi, viewGroup, false)

        return PubliViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: PubliViewHolder, position: Int) {
        val publicacion = publicaciones.get(position)

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
       // viewHolder.imgIcon.ImageIcon = dataSet[position]
        with(viewHolder){

            //viewB.imgIcon.drawable =
            viewB.txtProf.text= publicacion.perfil
            viewB.textLink.text = publicacion.titulo
            viewB.txtDescr.text = publicacion.descripcion

        }
    }

    override fun getItemCount(): Int {
        return publicaciones.size
    }
    /*
    val ciclos = context.resources.getStringArray(R.array.publicacion).toList()
    publicacionList = ciclos
        .filter { it.equals(publicacion, ignoreCase = true) }
        .shuffled()

        */



}