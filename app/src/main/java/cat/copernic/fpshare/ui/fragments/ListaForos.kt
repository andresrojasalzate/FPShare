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
import cat.copernic.fpshare.adapters.UserAdapter
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.databinding.FragmentListaForosBinding
import cat.copernic.fpshare.modelo.Mensaje
import cat.copernic.fpshare.modelo.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            boton = binding.button5
            recyclerView = binding.recyclerView
            withContext(Dispatchers.IO) {
                llamarecycleview()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun llamarecycleview() {
        val foroList = ArrayList<Foro>()

        bd.collection("Foros").get().addOnSuccessListener { documents ->
            for (document in documents){
                val wallitem = document.toObject(Foro::class.java)
                foroList.add(wallitem)
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = ForoAdapter(foroList,this)
        }
    }

    override fun onItemClick(id: String) {
        val action =
            ListaForosDirections.actionListaForosToFPHilo(id)
        view?.findNavController()?.navigate(action)
    }
}