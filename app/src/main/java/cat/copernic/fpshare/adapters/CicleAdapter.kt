package cat.copernic.fpshare.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.ui.fragments.MenuModulo

class CicleAdapter(private val cicleArrayList: ArrayList<String>/*, context: Context*/) :
    RecyclerView.Adapter<CicleAdapter.CicleViewHolder>() {
    /*
    private val cicloElegido: List<String>

    init {
        val ciclos = context.resources.getStringArray(R.array.ciclo).toList()
        cicloElegido = ciclos
            .filter { it.equals(ciclo, ignoreCase = true) }
            .shuffled()
    }
    */

    override fun getItemCount(): Int {
        return cicleArrayList.size
    }

    class CicleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_list)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CicleViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        layout.accessibilityDelegate = Accessibility
        return CicleViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CicleViewHolder, position: Int) {
        val currentItem = cicleArrayList[position]
        holder.button.text = currentItem
        holder.button.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, MenuModulo::class.java)
            intent.putExtra("cicle", holder.button.text.toString())
        }

        /*val item = cicloElegido[position]

        holder.button.text = item

        holder.button.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MenuCiclos::class.java)
            intent.putExtra("cicle", holder.button.text.toString())
            context.startActivity(intent)
        }*/
    }

    companion object Accessibility : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host.context?.getString(R.string.enter_cicle)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}

