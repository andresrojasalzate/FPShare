package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.clases.Menu
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemMenuBinding

class MenuAdapter (private val menus: List<Menu>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view){
        var ViewB = ItemMenuBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(vista)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var menu = menus.get(position)

        with(holder){
            ViewB.txtMenu.text = menu.opcion
        }

    }

    override fun getItemCount(): Int {
        return menus.size
    }
}