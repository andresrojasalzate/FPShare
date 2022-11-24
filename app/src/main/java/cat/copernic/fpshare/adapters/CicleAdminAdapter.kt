package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ItemTagBinding
import cat.copernic.fpshare.modelo.Cicle

class CicleAdminAdapter(private val ciclos: List<Cicle>) :
    RecyclerView.Adapter<CicleAdminAdapter.ViewHolder>() {
    private lateinit var contexto: Context

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)

        {
            val ViewB = ItemTagBinding.bind(view)
        }




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CicleAdminAdapter.ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CicleAdminAdapter.ViewHolder, position: Int) {
        val ciclos = ciclos.get(position)
        with(holder) {
            ViewB.idTag.text = ciclos.idCiclo
            ViewB.nombreTag.text = ciclos.nombre
        }

    }

    override fun getItemCount(): Int {
        return ciclos.size
    }


}
