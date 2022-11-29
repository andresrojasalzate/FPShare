package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemUserBinding
import cat.copernic.fpshare.modelo.User

class UserAdapter(private val usuarios: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val viewB = ItemUserBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_user, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios.get(position)
        with(holder) {
            viewB.numUsuario.text = (position + 1).toString()
            viewB.nombreUsuario.text = usuario.nombre

            /*Glide.with(contexto)
                .load(usuario.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ViewB.imagenPerfil)*/
        }

    }

    override fun getItemCount(): Int {
        return usuarios.size
    }
}