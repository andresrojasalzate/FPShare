package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Modul

class ModulAdminAdapter(private val modulos: MutableList<Modul>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ModulAdminAdapter.ViewHolder>() {
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
         */
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val id = modulos.get(position).idModul
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(id)
            }
        }
    }

    /**
     * Esta función es la que construye el aspecto de los items dentro del recyclerView a través
     * del archivo item (por ejemplo, item_tag)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModulAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    /**
     * Función que recoge las IDs de ciclos y las muestra en el recyclerView
     */
    override fun onBindViewHolder(holder: ModulAdminAdapter.ViewHolder, position: Int) {
        val modulos = modulos.get(position)
        with(holder) {
            viewB.nombreTag.text = modulos.nombre
        }

    }

    override fun getItemCount(): Int {
        return modulos.size
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}
