package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.databinding.FragmentMenuCiclosBinding
import cat.copernic.fpshare.clases.Menu
import cat.copernic.fpshare.databinding.FragmentMenuModuloBinding

class MenuCiclos : Fragment(), MenuAdapter.OnItemClickListener {
    private var _binding: FragmentMenuCiclosBinding? = null
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
        _binding = FragmentMenuCiclosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        boton = binding.btnModulo
        recyclerView = binding.recyclerView

        var adapter = MenuAdapter(crearMenu(), this)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        boton.setOnClickListener {
            val action = MenuCiclosDirections.actionMenuCiclosToMenuModulo()
            view.findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearMenu(): MutableList<Menu>{
        var menucicle = mutableListOf<Menu>()

        var ciclo1 = Menu(1,"DAM")
        var ciclo2 = Menu(2,"DAW")
        var ciclo3 = Menu(3,"SMIX")
        var ciclo4 = Menu(4,"ASIX")

        menucicle.add(ciclo1)
        menucicle.add(ciclo2)
        menucicle.add(ciclo3)
        menucicle.add(ciclo4)

        return menucicle

    }

    override fun onItemClick(position: Int) {
        val view = binding.root
        val action = MenuCiclosDirections.actionMenuCiclosToMenuModulo(position)
        view.findNavController().navigate(action)
    }

}

