package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.databinding.ItemForoBinding


class ForoAdapter (private val foros: List<Foro>, private val listener: ForoAdapter.OnItemClickListener) : RecyclerView.Adapter<ForoAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val ViewB = ItemForoBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val foro = foros.get(position)
            if(position != RecyclerView.NO_POSITION)  {
                listener.onItemClick(foro)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_foro, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foro = foros.get(position)
        val numMensajes = foro.mensajes.size
        with(holder){
            ViewB.autor.text = foro.emailautor
            ViewB.titulo.text = foro.titulo
            ViewB.comentarios.text = numMensajes.toString()
        }

    }

    override fun getItemCount(): Int {
        return foros.size
    }

    interface OnItemClickListener {
        fun onItemClick(foro: Foro)


    }
}
