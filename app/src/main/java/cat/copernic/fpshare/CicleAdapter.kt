package cat.copernic.fpshare

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.Arrays
import java.util.Collections.addAll
import java.util.stream.Collectors.toList

class CicleAdapter(private val ciclo: String, context: Context) :
    RecyclerView.Adapter<CicleAdapter.CicleViewHolder>() {

    private val cicloElegido: List<String>

    init {
        val ciclos = context.resources.getStringArray(R.array.ciclo).toList()
        cicloElegido = ciclos
            .filter { it.equals(ciclo, ignoreCase = true) }
            .shuffled()
    }

    override fun getItemCount(): Int {
        return cicloElegido.size
    }

    class CicleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CicleViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        layout.accessibilityDelegate = Accessibility
        return CicleViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CicleViewHolder, position: Int) {
        val item = cicloElegido[position]

        val context = holder.view.context

        holder.button.text = item

        holder.button.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MenuCiclos::class.java)
            intent.putExtra("cicle", holder.button.text.toString())
            context.startActivity(intent)
        }
    }

    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }

}

