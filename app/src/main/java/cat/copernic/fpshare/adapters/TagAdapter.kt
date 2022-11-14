package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.clases.Cicle
import cat.copernic.fpshare.databinding.ItemTagBinding

class TagAdapter (private val etiquetas: List<Cicle>) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view) {
        val ViewB = ItemTagBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val usuario = etiquetas.get(position)
        with(holder){
            // ViewB.nombreTag.text = usuario.nombre
        }

    }

    override fun getItemCount(): Int {
        return etiquetas.size
    }
}