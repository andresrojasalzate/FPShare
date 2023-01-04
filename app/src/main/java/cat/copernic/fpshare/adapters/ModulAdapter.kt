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

            /***
             * Inicializamos el objeto.
             */
            var ViewB = ItemMenuBinding.bind(view)

            /***
             * Envolvemos el objeto en un setOnClickListener, para que pueda escuchar las pulsaciones del
             * usuario.
             */
            init {
                view.setOnClickListener(this)
            }

            /***
             * Dentro de la funcion onClick, recogemos el valor que queremos devolver por la interfaz.
             * A traves de la posicion podemos encontrar el objeto seleccionado en el Adapter, recogemos
             * la id del objeto, ya que no puede repetirse y pasamos la id a la interficie para que pueda
             * ser devuelta al fragment inicial.
             */
            override fun onClick(v: View?) {
                val position: Int = adapterPosition
                val id = modulos.get(position).idModul
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(id)
                }
            }

        }
        /***
         * Declaramos la cantidad de objetos que vamos a mostrar.
         */
        override fun getItemCount(): Int {
            return modulos.size
        }

        /***
         * Declaramos la interfaz y le indicamos el dato que vamos a pasar.
         */
        interface OnItemClickListener {
            fun onItemClick(id: String)
        }

    }
