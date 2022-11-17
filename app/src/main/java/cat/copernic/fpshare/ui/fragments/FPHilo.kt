package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MsgAdapter
import cat.copernic.fpshare.databinding.FragmentFpHiloBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore


class FPHilo : Fragment() {
    private var _binding: FragmentFpHiloBinding? = null
    private val binding get() = _binding!!
    private var bd = FirebaseFirestore.getInstance()

    private lateinit var botonSend: ImageButton
    private lateinit var inputMsg: EditText
    private lateinit var recyclerView: RecyclerView


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
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inicializadores() {
        botonSend = binding.buttonSend
        inputMsg = binding.timInput
        recyclerView = binding.recyclerViewHilo

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MsgAdapter(obtenerMensajes())
    }

    fun listeners() {
        botonSend.setOnClickListener() {
            val texto = inputMsg.text.toString()

            if (campoVacio(texto)) {
                val mensaje = Mensaje("ID", "autor@gmail.com", texto)
                addMensaje(mensaje, "ID")
            }
        }
    }

    fun obtenerMensajes(): MutableList<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()

        for (num in 1..30) {

            mensajes.add(Mensaje("Carles", "autor@gmail.com", "Hola Buenas"))

        }

        return mensajes
    }

    fun addMensaje(mensaje: Mensaje, id: String) {
        bd.collection("Foros").document(id)
            .collection("Hilos").document(id)
            .collection("Mensajes").document(id).set(mensaje)
    }

    fun campoVacio(mensaje: String): Boolean {
        return mensaje.isNotEmpty() && mensaje.isNotBlank()
    }
}