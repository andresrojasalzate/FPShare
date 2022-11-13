package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.adapters.UserAdapter
import cat.copernic.fpshare.clases.Foro
import cat.copernic.fpshare.clases.User
import cat.copernic.fpshare.databinding.FragmentListaForosBinding


class ListaForos : Fragment() {
    private var _binding: FragmentListaForosBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaForosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        boton = binding.button5
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ForoAdapter(obtenerForos())
        boton.setOnClickListener {
            val action =
                ListaForosDirections.actionListaForosToFPHilo()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerForos(): MutableList<Foro>{

        val foros = mutableListOf<Foro>()

        for(num in 1..30){

            foros.add(Foro("Titulo de foro","Andrés", "10"))

        }

        return foros

    }

}