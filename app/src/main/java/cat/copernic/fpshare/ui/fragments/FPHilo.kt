package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.adapters.MsgAdapter
import cat.copernic.fpshare.databinding.FragmentFpHiloBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore


class FPHilo : Fragment() {
    private var _binding: FragmentFpHiloBinding? = null
    private val binding get() = _binding!!
    private lateinit var botonSend: ImageButton
    private lateinit var inputMsg: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var autor : String
    private lateinit var  titulo : String
    private lateinit var  id: String
    private lateinit var textViewTitulo: TextView
    private lateinit var  textViewAutor: TextView
    private var bd = FirebaseFirestore.getInstance()

    companion object{
        val AUTOR = "autor"
        val TITULO = "titulo"
        val ID = "ID"
    }
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
        arguments?.let {
            autor = it.getString(AUTOR).toString()
            titulo = it.getString(TITULO).toString()
            id = it.getString(ID).toString()
        }
        inicializadores()
        llamarecycleview()

        botonSend.setOnClickListener() {
            val texto = inputMsg.text.toString()

            if (campoVacio(texto)) {
                val mensaje = Mensaje("ID", "autor@gmail.com", texto)
                addMensaje(mensaje, id)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun llamarecycleview(){
        val mensajesList = ArrayList<Mensaje>()
        val idForo = id
     
        bd.collection("Foros").document(idForo).collection("Mensajes").get().addOnSuccessListener { documents ->
            for (document in documents){
                val wallitem = document.toObject(Mensaje::class.java)
                mensajesList.add(wallitem)
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
        textViewTitulo.setText(titulo)
        textViewAutor.setText(autor)

    }

    fun addMensaje(mensaje: Mensaje, id: String) {
        bd.collection("Foros").document(id)
            .collection("Mensajes").document(id).set(mensaje)
    }

    fun campoVacio(mensaje: String): Boolean {
        return mensaje.isNotEmpty() && mensaje.isNotBlank()
    }

}