package cat.copernic.fpshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CicleAdapter(var cicle:List<String>): RecyclerView.Adapter<CicleAdapter.CicleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CicleHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CicleHolder(layoutInflater.inflate(R.layout.fragment_menu_ciclos, parent, false))
    }

    override fun getItemCount(): Int = cicle.size


    override fun onBindViewHolder(holder: CicleAdapter.CicleHolder, position: Int) {
        holder.render(cicle[position])
    }

    class CicleHolder(val view:View):RecyclerView.ViewHolder(view){
        fun render(cicle: String){
           view.txtCiclo.text = cicle.toString()
        }
    }

}