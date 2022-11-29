package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.databinding.FragmentMenuModuloBinding
import cat.copernic.fpshare.modelo.Cicle
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuModulo : Fragment(), ModulAdminAdapter.OnItemClickListener {
    private var _binding: FragmentMenuModuloBinding? = null
    private val binding get() = _binding!!
    private lateinit var button: Button
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance();
    private lateinit var adapter: ModulAdminAdapter
    private lateinit var cicloList: MutableList<Modul>
    private lateinit var ciclo: Cicle

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
        recyclerView = binding.recyclerView


        lifecycleScope.launch(Dispatchers.Main){
            cicloList = withContext(Dispatchers.IO){ crearMenu()}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearMenu(): MutableList<Modul>{

        var idCic = args.cicloid

        var cicloList = mutableListOf<Modul>()
            bd.collection("Ciclos").document(idCic).collection("Modulos")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        val idModul = document.id
                        val nombreModul = document["nombre"].toString()
                        val modulo = Modul(idModul,nombreModul)
                        cicloList.add(modulo)
                    }
                    adapter=ModulAdminAdapter(cicloList, this)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }

            return cicloList
        }



    override fun onItemClick(id: String) {
        val view = binding.root
        val action = MenuModuloDirections.actionMenuModuloToListaUFs(args.cicloid,id)
        view.findNavController().navigate(action)
    }

}