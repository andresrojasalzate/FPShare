package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemMenuBinding
import cat.copernic.fpshare.modelo.Cicle

class MenuAdapter (private val ciclos: MutableList<Cicle>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_menu, parent, false)

        return ViewHolder(vista)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ciclo = ciclos.get(position)
            with(holder){
                viewB.txtMenu.text = ciclo.nombre
            }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{
        /***
         * Inicializamos el objeto.
         */
        var viewB = ItemMenuBinding.bind(view)

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
            val id = ciclos.get(position).idCiclo
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(id)
            }
        }
    }

    /***
     * Declaramos la cantidad de objetos que vamos a mostrar.
     */
    override fun getItemCount(): Int {
        return ciclos.size
    }

    /***
     * Declaramos la interfaz y le indicamos el dato que vamos a pasar.
     */
    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}
