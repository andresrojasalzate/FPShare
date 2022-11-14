package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.clases.Foro
import cat.copernic.fpshare.databinding.ItemForoBinding


class ForoAdapter (private val foros: List<Foro>) : RecyclerView.Adapter<ForoAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view){
        val ViewB = ItemForoBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_foro, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foro = foros.get(position)

        with(holder){
            ViewB.autor.text = foro.autor
            ViewB.titulo.text = foro.titulo
            ViewB.comentarios.text = foro.numComentario
        }

    }

    override fun getItemCount(): Int {
        return foros.size
    }
}
