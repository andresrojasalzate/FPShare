package cat.copernic.fpshare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.clases.Menu
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemMenuBinding

class MenuAdapter (private val menus: List<Menu>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_menu, parent, false)

        return ViewHolder(vista)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menus.get(position)
            with(holder){
                ViewB.txtMenu.text = menu.opcion
            }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{
        var ViewB = ItemMenuBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return menus.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
