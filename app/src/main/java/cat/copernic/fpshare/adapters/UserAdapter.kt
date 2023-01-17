package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.modelo.User
import cat.copernic.fpshare.databinding.ItemUserBinding

/**
 * Clase adaptador para mostrar una lista de usuarios en un recyclerview
 *
 * @param usuarios: lista de objetos User que se mostrarán en el recyclerview
 * @param listener: objeto OnItemClickListener para manejar los eventos de clic en los elementos del recyclerview
 */
class UserAdapter(private val usuarios: List<User>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    /**
     * Clase interna para almacenar y enlazar los elementos de la vista
     */
    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val ViewB = ItemUserBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        /**
         * funcion para manejar los eventos de clic en los elementos del recyclerview
         */
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val foroID = usuarios.get(position).email
            if(position != RecyclerView.NO_POSITION)  {
                listener.onItemClick(foroID)
            }
        }
    }

    /**
     * función para crear una nueva instancia de viewholder y configura la vista
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_user, parent, false)
        return ViewHolder(vista)
    }
    /**
     * con es función actualizamos la vista con los datos del usuario en la posición especificada
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios.get(position)
        with(holder){
            ViewB.numUsuario.text =(position + 1).toString()
            ViewB.nombreUsuario.text = usuario.nombre
        }

    }

    /**
     * función que devuelve el número de elementos en la lista de usuarios
     */
    override fun getItemCount(): Int {
        return usuarios.size
    }

    /**
     * interfaz para manejar los eventos de clic en los elementos del RecyclerView
     */
    interface OnItemClickListener {
        fun onItemClick(email: String)
    }
}