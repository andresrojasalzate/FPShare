package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemForoBinding
import cat.copernic.fpshare.modelo.Foro


class ForoAdapter(private val foros: List<Foro>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ForoAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val viewB = ItemForoBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        /**
         * Esta función recoge el click que ha dado el usuario, y dependiendo de la posición
         * del click recoge la ID y la envia hacia la función onItemClick
         */
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val foroID = foros.get(position).id
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(foroID.toString())
            }
        }
    }

    /**
     * Esta función es la que construye el aspecto de los items dentro del recyclerView a través
     * del archivo item (por ejemplo, item_tag)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_foro, parent, false)
        return ViewHolder(vista)
    }

    /**
     * Función que recoge las IDs del foro y las muestra en el recyclerView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foro = foros.get(position)
        with(holder) {
            viewB.autor.text = foro.nombreApellido
            viewB.titulo.text = foro.titulo

        }

    }

    override fun getItemCount(): Int {
        return foros.size
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}
