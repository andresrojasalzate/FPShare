package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemMenuBinding
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Uf

class UfAdapter(private val ufs: MutableList<Uf>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UfAdapter.ViewHolder>(){


        private lateinit var contexto: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            contexto = parent.context
            val vista = LayoutInflater.from(contexto).inflate(R.layout.item_menu, parent, false)

            return ViewHolder(vista)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var uf = ufs.get(position)
            with(holder){
                ViewB.txtMenu.text = uf.nombre
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
                val id = ufs.get(position).idUf
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(id)
                }
            }

        }

        override fun getItemCount(): Int {
            return ufs.size
        }

        interface OnItemClickListener {
            fun onItemClick(id: String)
        }

    }
