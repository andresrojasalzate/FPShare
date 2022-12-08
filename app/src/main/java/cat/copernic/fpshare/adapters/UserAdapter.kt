package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.modelo.User
import cat.copernic.fpshare.databinding.ItemUserBinding

class UserAdapter(private val usuarios: List<User>, private val listener: UserAdapter.OnItemClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val ViewB = ItemUserBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val foroID = usuarios.get(position).email
            if(position != RecyclerView.NO_POSITION)  {
                listener.onItemClick(foroID)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_user, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios.get(position)
        with(holder){
            ViewB.numUsuario.text =(position + 1).toString()
            ViewB.nombreUsuario.text = usuario.nombre
        }

    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    interface OnItemClickListener {
        fun onItemClick(email: String)
    }
}