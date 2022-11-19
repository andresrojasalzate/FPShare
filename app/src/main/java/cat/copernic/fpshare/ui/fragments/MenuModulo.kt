package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.clases.Menu
import cat.copernic.fpshare.databinding.FragmentMenuModuloBinding

class MenuModulo : Fragment() {
    private var _binding: FragmentMenuModuloBinding? = null
    private val binding get() = _binding!!
    private lateinit var button: Button
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
        _binding = FragmentMenuModuloBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = binding.btnUf
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(crearMenu())

        button.setOnClickListener {
            val action = MenuModuloDirections.actionMenuModuloToListaUFs()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearMenu(): MutableList<Menu>{
        var menucicle = mutableListOf<Menu>()

        var ciclo1 = Menu(1,"Sistemas Informaticos")
        var ciclo2 = Menu(2,"Programacion")
        var ciclo3 = Menu(3,"Bases de Datos")
        var ciclo4 = Menu(4,"Lenguaje de Marcas y Sistemas de Gestion de la informacion")
        var ciclo5 = Menu(5,"Entornos de Desarrollo")
        var ciclo6 = Menu(6,"Formacion y Orientacion Laboral")
        var ciclo7 = Menu(7,"Empresa Iniciativa Emprendedora")
        var ciclo8 = Menu(8,"ABP: Proyecto Android")
        var ciclo9 = Menu(9,"ABP: Proyecto ERP")
        var ciclo10 = Menu(10,"ABP: Proyecto Unity")
        var ciclo11 = Menu(11,"Programacion Pack 2")

        menucicle.add(ciclo1)
        menucicle.add(ciclo2)
        menucicle.add(ciclo3)
        menucicle.add(ciclo4)
        menucicle.add(ciclo5)
        menucicle.add(ciclo6)
        menucicle.add(ciclo7)
        menucicle.add(ciclo8)
        menucicle.add(ciclo9)
        menucicle.add(ciclo10)
        menucicle.add(ciclo11)

        return menucicle

    }

}