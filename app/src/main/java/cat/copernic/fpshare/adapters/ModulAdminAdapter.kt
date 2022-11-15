package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Modul

class ModulAdminAdapter(private val modulos: List<Modul>) :
    RecyclerView.Adapter<ModulAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val ViewB = ItemTagBinding.bind(view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModulAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ModulAdminAdapter.ViewHolder, position: Int) {
        val modulos = modulos.get(position)
        with(holder) {
            ViewB.idTag.text = modulos.idModul
            ViewB.nombreTag.text = modulos.nombre
        }

    }

    override fun getItemCount(): Int {
        return modulos.size
    }
}
