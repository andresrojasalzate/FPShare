package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Cicle

class CicleAdminAdapter(
    private val ciclos: MutableList<Cicle>, private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CicleAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var viewB = ItemTagBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val id = ciclos.get(position).idCiclo
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CicleAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CicleAdminAdapter.ViewHolder, position: Int) {
        val ciclos = ciclos.get(position)
        with(holder) {
            viewB.nombreTag.text = ciclos.nombre
        }

    }

    override fun getItemCount(): Int {
        return ciclos.size
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}
