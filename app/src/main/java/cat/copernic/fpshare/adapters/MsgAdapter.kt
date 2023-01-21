package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemHiloBinding
import cat.copernic.fpshare.modelo.Mensaje

/**
 * Adaptador para la visualización de mensajes en un recyclerView
 *
 * @author FPShare
 *
 * @param mensajes
 */
class MsgAdapter(private val mensajes: ArrayList<Mensaje>) :
    RecyclerView.Adapter<MsgAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val viewB = ItemHiloBinding.bind(view)
    }

    /**
     * Creación de los mensajes del foro para la visualización de los mensajes
     *
     * @param parent
     * @param viewType
     *
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_hilo, parent, false)
        return ViewHolder(vista)
    }

    /**
     * Lectura de los mensajes del foro
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: MsgAdapter.ViewHolder, position: Int) {
        val mensajes = mensajes.get(position)
        with(holder) {
            viewB.nomUser.text = mensajes.nombreApellido
            viewB.txtMensaje.text = mensajes.mensaje
        }

    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

}