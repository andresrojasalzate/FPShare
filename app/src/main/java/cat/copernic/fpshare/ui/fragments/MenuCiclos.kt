package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.databinding.FragmentMenuCiclosBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.InternalChannelz.id
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuCiclos : Fragment(), MenuAdapter.OnItemClickListener {
    private var _binding: FragmentMenuCiclosBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var recyclerView : RecyclerView
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: MenuAdapter
    private lateinit var cicloList: MutableList<Cicle>
    private lateinit var resultado: MutableList<Cicle>
    private lateinit var modulos: List<Modul>
    private lateinit var UFs: List<Uf>

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
        lifecycleScope.launch(Dispatchers.Main){
           cicloList = withContext(Dispatchers.IO){ crearMenu()}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
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
*/

    fun crearMenu(): MutableList<Cicle>{
        var cicloList = mutableListOf<Cicle>()
        bd.collection("Ciclos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val idCiclo = document.id
                    val nombreCiclo = document["nombre"].toString()
                    val ciclo = Cicle(idCiclo,nombreCiclo, listOf(Modul("", "", listOf(Uf("", "", listOf(
                        Publicacion("","","","","","")
                    ))))))
                    cicloList.add(ciclo)
                }
                adapter=MenuAdapter(cicloList, this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

        return cicloList
    }



    override fun onItemClick(id: String) {
        val view = binding.root
        val action = MenuCiclosDirections.actionMenuCiclosToMenuModulo(id)
        view.findNavController().navigate(action)
    }

}

