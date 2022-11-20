package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.clases.Menu
import cat.copernic.fpshare.databinding.FragmentMenuModuloBinding

class MenuModulo : Fragment(), MenuAdapter.OnItemClickListener {
    private var _binding: FragmentMenuModuloBinding? = null
    private val binding get() = _binding!!
    private lateinit var button: Button
    private lateinit var recyclerView : RecyclerView

    val args: MenuModuloArgs by navArgs()



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

        var adapter = MenuAdapter(crearMenu(), this)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

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
        val numero = args.number
        if(numero == 0){
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

        }
        else if(numero == 1){

            var ciclo1 = Menu(1,"Sistemas Informaticos")
            var ciclo2 = Menu(2,"Programacion")
            var ciclo3 = Menu(3,"Bases de Datos")
            var ciclo4 = Menu(4,"Lenguaje de Marcas y Sistemas de Gestion de la informacion")
            var ciclo5 = Menu(5,"Entornos de Desarrollo")
            var ciclo6 = Menu(6,"Formacion y Orientacion Laboral")
            var ciclo7 = Menu(7,"Empresa Iniciativa Emprendedora")
            var ciclo8 = Menu(8,"Desarrollo Web en entorno Cliente")
            var ciclo9 = Menu(9,"Desarrollo Web en entorno Servidor")
            var ciclo10 = Menu(10,"Despliegue de aplicaciones Web")
            var ciclo11 = Menu(11,"Diseño de Interfaces Web")

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

        }else if(numero == 2){

            var ciclo1 = Menu(1,"Montaje i Mantenimiento de Equipos")
            var ciclo2 = Menu(2,"Sistemas Operativos Monousuario")
            var ciclo3 = Menu(3,"Aplicaciones Ofimaticas")
            var ciclo4 = Menu(4,"Sistemas Operativos en Red")
            var ciclo5 = Menu(5,"Redes Locales")
            var ciclo6 = Menu(6,"Seguridad Informatica")
            var ciclo7 = Menu(7,"Servicios de Red")
            var ciclo8 = Menu(8,"Aplicaciones Web")
            var ciclo9 = Menu(9,"Formacion y Orientacion Laboral")
            var ciclo10 = Menu(10,"Empresa Iniciativa Emprendedora")
            var ciclo11 = Menu(11,"Ingles")

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

        }else if(numero == 3){

            var ciclo1 = Menu(1,"Sistemas Informaticos")
            var ciclo2 = Menu(2,"Programacion")
            var ciclo3 = Menu(3,"Bases de Datos")
            var ciclo4 = Menu(4,"Lenguaje de Marcas y Sistemas de Gestion de la informacion")
            var ciclo5 = Menu(5,"Formacion y Orientacion Laboral")
            var ciclo6 = Menu(6,"Empresa Iniciativa Emprendedora")
            var ciclo7 = Menu(7,"Fundamentos de Maquinaria")
            var ciclo8 = Menu(8,"Administracion de Sistemas Operativos")
            var ciclo9 = Menu(9,"Planificacion y administracion de Redes")
            var ciclo10 = Menu(10,"Servicios de red y internet")
            var ciclo11 = Menu(11,"Administracion de Bases de Datos")
            var ciclo12 = Menu(12,"Seguridad i Alta Disponibilidad")
            var ciclo13 = Menu(13,"Implantacion de Aplicaciones Web")

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
            menucicle.add(ciclo12)
            menucicle.add(ciclo13)
        }


        return menucicle

    }

    override fun onItemClick(position: Int) {
        val view = binding.root
        val action = MenuModuloDirections.actionMenuModuloToListaUFs()
        view.findNavController().navigate(action)
    }

}