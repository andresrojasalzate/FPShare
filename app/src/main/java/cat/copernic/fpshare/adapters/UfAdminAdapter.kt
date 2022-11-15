package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Uf

class UfAdminAdapter(private val uf: List<Uf>) :
    RecyclerView.Adapter<UfAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val ViewB = ItemTagBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: UfAdminAdapter.ViewHolder, position: Int) {
        val uf = uf.get(position)
        with(holder) {
            ViewB.idTag.text = uf.idUf
            ViewB.nombreTag.text = uf.nombre
        }

    }

    override fun getItemCount(): Int {
        return uf.size
    }
}
