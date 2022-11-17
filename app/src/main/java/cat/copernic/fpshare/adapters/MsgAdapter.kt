package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemHiloBinding
import cat.copernic.fpshare.modelo.Mensaje

class MsgAdapter(private val mensajes: List<Mensaje>) :
    RecyclerView.Adapter<MsgAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val ViewB = ItemHiloBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_hilo, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: MsgAdapter.ViewHolder, position: Int) {
        val mensajes = mensajes.get(position)
        with(holder) {
            ViewB.nomUser.text = mensajes.emailautor
            ViewB.txtMensaje.text = mensajes.mensaje
        }

    }

    override fun getItemCount(): Int {
        return mensajes.size
    }
}