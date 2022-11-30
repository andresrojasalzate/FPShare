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
        recyclerView = binding.recyclerView

        lifecycleScope.launch(Dispatchers.Main){
           cicloList = withContext(Dispatchers.IO){ crearMenu()}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun crearMenu(): MutableList<Cicle>{
        val cicloList = mutableListOf<Cicle>()
        bd.collection("Ciclos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val idCiclo = document.id
                    val nombreCiclo = document["nombre"].toString()
                    val ciclo = Cicle(idCiclo,nombreCiclo)
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

