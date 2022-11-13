package cat.copernic.fpshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import org.w3c.dom.Text

class PubliAdapter(private val publicacion: String, context: Context) :
    RecyclerView.Adapter<PubliAdapter.PubliViewHolder>() {

    class PubliViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imgIcon: ImageView = view.findViewById(R.id.imgIcon)
        val textProf: TextView = view.findViewById(R.id.txtProf)
        val txtDescr: TextView = view.findViewById(R.id.txtDescr)
        val textLink: TextView = view.findViewById(R.id.textLink)

    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PubliViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.publi_list_item, viewGroup, false)

        return PubliViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: PubliViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
       // viewHolder.imgIcon.ImageIcon = dataSet[position]
        viewHolder.textProf.text = dataSet[position]
        viewHolder.txtDescr.text = dataSet[position]
        viewHolder.textLink.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return 2
    }
    /*
    val ciclos = context.resources.getStringArray(R.array.publicacion).toList()
    publicacionList = ciclos
        .filter { it.equals(publicacion, ignoreCase = true) }
        .shuffled()

        */



}