package cat.copernic.fpshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ForoAdapter
import cat.copernic.fpshare.databinding.FragmentListaForosDeUnUsuarioBinding
import cat.copernic.fpshare.modelo.Foro
import cat.copernic.fpshare.ui.fragments.ListaForosDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class listaForosDeUnUsuario : Fragment(), ForoAdapter.OnItemClickListener {
    private var _binding: FragmentListaForosDeUnUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private var bd = FirebaseFirestore.getInstance()
    private var user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaForosDeUnUsuarioBinding.inflate(inflater, container, false)
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
                listaForosDeUnUsuarioDirections.actionTusForosToCreacionForo()
            view.findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private suspend fun llamarecycleview() {
        val foroList = ArrayList<Foro>()
        val email = user?.email.toString()
        val foros = bd.collection("Foros").whereEqualTo("emailautor", email).get().await()
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
            listaForosDeUnUsuarioDirections.actionTusForosToFPHilo(id)
        view?.findNavController()?.navigate(action)
    }
}