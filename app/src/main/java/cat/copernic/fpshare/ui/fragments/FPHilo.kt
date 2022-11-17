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


class FPHilo : Fragment() {
    private var _binding: FragmentFpHiloBinding? = null
    private val binding get() = _binding!!

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
        botonSend = binding.buttonSend
        inputMsg = binding.timInput
        recyclerView = binding.recyclerViewHilo

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MsgAdapter(obtenerMensajes())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun obtenerMensajes(): MutableList<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()

        for(num in 1..30){

            mensajes.add(Mensaje("Carles","Hola Buenas"))

        }

        return mensajes
    }
}