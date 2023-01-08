package cat.copernic.fpshare.ui.fragments

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.adapters.MsgAdapter
import cat.copernic.fpshare.databinding.FragmentFpHiloBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.ui.activities.Login
import cat.copernic.fpshare.ui.activities.MainActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase


class FPHilo : Fragment() {
    private var _binding: FragmentFpHiloBinding? = null
    private val binding get() = _binding!!
    private lateinit var botonSend: ImageButton
    private lateinit var inputMsg: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var textviewDescripcion : TextView
    private lateinit var textViewTitulo: TextView
    private lateinit var  textViewAutor: TextView
    private lateinit var borrarForo: ImageView
    private lateinit var mensajesList: ArrayList<Mensaje>
    private lateinit var adapter: MsgAdapter
    private var bd = FirebaseFirestore.getInstance()
    private var user = Firebase.auth.currentUser

    private val args: FPHiloArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFpHiloBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        inicializadores()
        infoforo()
        llamarecycleviewmensajes()


        botonSend.setOnClickListener() {
            val texto = inputMsg.text.toString()

            if (campoVacio(texto)) {
                addMensaje(texto)

                inputMsg.setText("")
            }
        }

        borrarForo.setOnClickListener {
            crearalerta()
        }

    }

    private fun crearalerta() {
        val builder = AlertDialog.Builder(requireContext())

        // Establecer el título y el mensaje de la alerta
        builder.setTitle("¿BORRAR ESTE FORO?")
        builder.setMessage("¿Estas seguro de querer borrar este foro?")

        // Establecer el botón positivo y su acción
        builder.setPositiveButton("Aceptar") { dialog, which ->
            // Acción para el botón positivo
            bd.collection("Foros").document(args.idforo).delete()

        }

        // Establecer el botón negativo y su acción (opcional)
        builder.setNegativeButton("Cancelar") { dialog, which ->
            // Acción para el botón negativo


        }

        // Mostrar la alerta
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun infoforo(){
        val idForo = args.idforo
        val email = user?.email.toString()
        bd.collection("Foros").document(idForo).get().addOnSuccessListener { document ->
            textViewTitulo.setText(document["titulo"].toString())
            textViewAutor.setText(document["emailautor"].toString())
            textviewDescripcion.setText(document["descripcion"].toString())

            if (document["emailautor"].toString() != email){
                borrarForo.visibility = View.INVISIBLE
                borrarForo.alpha = 0f
            }
        }
    }
    private fun llamarecycleviewmensajes(){

        val idForo = args.idforo

        bd.collection("Foros").document(idForo).collection("Mensajes").get().addOnSuccessListener { documents ->
            for (document in documents){
                if(document["emailautor"].toString() != "shtht") {
                    val wallitem = document.toObject(Mensaje::class.java)
                    mensajesList.add(wallitem)
                }
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MsgAdapter(mensajesList)
        }
    }
    private fun inicializadores() {
        botonSend = binding.buttonSend
        inputMsg = binding.timInput
        recyclerView = binding.recyclerViewHilo
        textViewAutor = binding.autorForo
        textViewTitulo = binding.tituloForo
        textviewDescripcion = binding.descripcion
        borrarForo = binding.borrarForo
        mensajesList = ArrayList()
        adapter = MsgAdapter(mensajesList)

    }

    fun addMensaje(mensaje: String) {

        val idForo = args.idforo
        val email = user?.email.toString()
        val nuevomensaje = Mensaje(email, mensaje)
        bd.collection("Foros").document(idForo)
            .collection("Mensajes").add(nuevomensaje)
        mensajesList.add(0, nuevomensaje)
        adapter = MsgAdapter(mensajesList)
        recyclerView.adapter = adapter
        adapter.notifyItemInserted(0)


    }

    fun campoVacio(mensaje: String): Boolean {
        return mensaje.isNotEmpty() && mensaje.isNotBlank()
    }

}