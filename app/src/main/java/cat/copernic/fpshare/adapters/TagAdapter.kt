package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Tag

class TagAdapter(private val etiquetas: List<Tag>) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val ViewB = ItemTagBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: TagAdapter.ViewHolder, position: Int) {
        val etiqueta = etiquetas.get(position)
        with(holder) {
            ViewB.idTag.text = etiqueta.idTag
            ViewB.nombreTag.text = etiqueta.nombreTag
        }

    }

    override fun getItemCount(): Int {
        return etiquetas.size
    }
}