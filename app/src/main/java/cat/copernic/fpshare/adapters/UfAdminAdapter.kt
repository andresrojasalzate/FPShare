package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Uf

/**
 * Adaptador para la visualización de UFs en el recyclerView de Administración de UFs, con un
 * listener para seleccionar uno y movernos entre los menús
 *
 * @author FPShare
 *
 * @param uf
 * @param listener
 */
class UfAdminAdapter(private val uf: MutableList<Uf>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UfAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var viewB = ItemTagBinding.bind(view)
        //var viewB = ItemMenuBinding.bind(view)
        init {
            view.setOnClickListener(this)
        }

        /**
         * Esta función recoge el click que ha dado el usuario, y dependiendo de la posición
         * del click recoge la ID y la envia hacia la función onItemClick
         *
         * @param v
         */
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val id = uf.get(position).idUf
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(id)
            }
        }
    }

    /**
     * Esta función es la que construye el aspecto de los items dentro del recyclerView a través
     * del archivo item (por ejemplo, item_tag)
     *
     * @param parent
     * @param viewType
     *
     * @return ViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UfAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    /**
     * Función que recoge las IDs de ufs y las muestra en el recyclerView
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: UfAdminAdapter.ViewHolder, position: Int) {
        val uf = uf[position]
        with(holder) {
            viewB.nombreTag.text = uf.nombre
        }

    }

    override fun getItemCount(): Int {
        return uf.size
    }

    /**
     * Interfaz onItemClickListener para el click de selección en el recyclerView
     */
    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}