package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.databinding.FragmentListaForosBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ListaForos : Fragment(), ForoAdapter.OnItemClickListener {
    private var _binding: FragmentListaForosBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var recyclerView : RecyclerView
    private var bd = FirebaseFirestore.getInstance()

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

        recyclerView = binding.recyclerView
        lifecycleScope.launch(Dispatchers.Main) {
            val algo = async{llamarecycleview()}
        }
        binding.fab.setOnClickListener {
            val action =
                ListaForosDirections.actionListaForosToCreacionForo()
            view.findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private suspend fun llamarecycleview() {
        val foroList = ArrayList<Foro>()

        val foros = bd.collection("Foros").get().await()
            for (document in foros){
                val wallitem = document.toObject(Foro::class.java)
                foroList.add(wallitem)
            }
            foroList.sortWith(compareBy({ it.id }))
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = ForoAdapter(foroList,this)

    }

    override fun onItemClick(id: String) {
        val action =
            ListaForosDirections.actionListaForosToFPHilo(id)
        view?.findNavController()?.navigate(action)
    }
}