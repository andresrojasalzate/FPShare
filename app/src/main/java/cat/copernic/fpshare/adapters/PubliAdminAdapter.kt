package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemPubliBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.squareup.picasso.Picasso

class PubliAdminAdapter(private val publicaciones: List<Publicacion>, private val listener: PubliAdminAdapter.OnItemClickListener) : RecyclerView.Adapter<PubliAdminAdapter.PubliViewHolder>() {
    private lateinit var contexto: Context

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
            Picasso.get().load(publicacion.imgPubli.toUri()).into(viewB.imgIcon)
        }
    }


    inner class PubliViewHolder(var view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
            val viewB = ItemPubliBinding.bind(view)

            init {
                view.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position: Int = adapterPosition
                val id = publicaciones.get(position).id
                val idCiclo = publicaciones.get(position).idCiclo
                val idModulo = publicaciones.get(position).idModulo
                val idUF = publicaciones.get(position).idUf
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(id, idCiclo, idModulo, idUF)
                }
            }

        }

    override fun getItemCount(): Int {
        return publicaciones.size
    }

    interface OnItemClickListener {
        //fun onItemClick(id: String)
        fun onItemClick(id: String, idCiclo: String, idModulo: String, idUF: String)
    }

}