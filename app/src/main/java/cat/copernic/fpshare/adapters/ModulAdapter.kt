package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemMenuBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul


class ModulAdapter (private val modulos: MutableList<Modul>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ModulAdapter.ViewHolder>(){
        private lateinit var contexto: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            contexto = parent.context
            val vista = LayoutInflater.from(contexto).inflate(R.layout.item_menu, parent, false)

            return ViewHolder(vista)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var modulo = modulos.get(position)
            with(holder){
                ViewB.txtMenu.text = modulo.nombre
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
                val id = modulos.get(position).idModul
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(id)
                }
            }

        }

        override fun getItemCount(): Int {
            return modulos.size
        }

        interface OnItemClickListener {
            fun onItemClick(id: String)
        }

    }
