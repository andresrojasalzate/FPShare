package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Uf

class UfAdminAdapter(private val uf: MutableList<Uf>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UfAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var ViewB = ItemTagBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val id = uf.get(position).idUf
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UfAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: UfAdminAdapter.ViewHolder, position: Int) {
        val uf = uf[position]
        with(holder) {
            ViewB.nombreTag.text = uf.nombre
        }

    }

    override fun getItemCount(): Int {
        return uf.size
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}