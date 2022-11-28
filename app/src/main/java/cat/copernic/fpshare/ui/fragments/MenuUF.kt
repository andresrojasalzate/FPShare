package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentMenuUfBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuUF : Fragment(), UfAdminAdapter.OnItemClickListener {
    private var _binding: FragmentMenuUfBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance();
    private lateinit var adapter: UfAdminAdapter
    private lateinit var cicloList: MutableList<Uf>

    val args: MenuUFArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuUfBinding.inflate(inflater, container, false)
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

    private fun crearMenu(): MutableList<Uf>{

        var idCic = args.cicloId
        var idMod = args.moduloId

        var cicloList = mutableListOf<Uf>()
        bd.collection("Ciclos").document(idCic).collection("Modulos").document(idMod).collection("UFs")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val idUf = document.id
                    val nombreUf = document["nombre"].toString()
                    val uf = Uf(idUf,nombreUf)
                    cicloList.add(uf)
                }
                adapter=UfAdminAdapter(cicloList, this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

        return cicloList
    }

    override fun onItemClick(id: String) {
        val view = binding.root
        val action = MenuUFDirections.actionListaUFsToMenuApuntes(args.cicloId,args.moduloId,id)
        view.findNavController().navigate(action)
    }

}