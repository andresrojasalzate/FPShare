package cat.copernic.fpshare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.databinding.ItemUserBinding

class UserAdapter(private val usuarios: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var contexto: Context

    inner class ViewHolder( var view: View) : RecyclerView.ViewHolder(view){
         val ViewB = ItemUserBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_user, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios.get(position)
        with(holder){
            ViewB.numUsuario.text = usuario.id.toString()
            ViewB.nombreUsuario.text = usuario.nombre
        }

    }

    override fun getItemCount(): Int {
        return usuarios.size
    }
}