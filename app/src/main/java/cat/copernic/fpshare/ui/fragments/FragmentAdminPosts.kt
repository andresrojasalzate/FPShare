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
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.adapters.PubliAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminPostsBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentAdminPosts : Fragment(), PubliAdminAdapter.OnItemClickListener {
    private var _binding: FragmentAdminPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: PubliAdminAdapter
    private lateinit var postsList: Deferred<MutableList<Publicacion>>
    var publi = ""

    private val args: FragmentAdminPostsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.ViewApuntes

        lifecycleScope.launch(Dispatchers.Main){
            postsList = async{ crearMenu()}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun crearMenu(): MutableList<Publicacion>{
        val postsList = mutableListOf<Publicacion>()
        val publicaciones = bd.collection("Ciclos").document(args.idCiclo).collection("Modulos").document(args.idModulo).collection("UFs").document(args.idUf).collection("Publicaciones").get().await()
        for (document in publicaciones){
            val idPubli = document.id
            val checked = document["checked"].toString()
            val publiDescr = document["descripcion"].toString()
            val publiLink = document["enlace"].toString()
            val publiProfile = document["perfil"].toString()
            val publiTitle = document["titulo"].toString()
            val imgPubli = document["imgPubli"].toString()
            val publi = Publicacion(idPubli,publiProfile,publiTitle,publiDescr,checked,publiLink, imgPubli)
            postsList.add(publi)
        }
        adapter= PubliAdminAdapter(postsList, this)
        binding.ViewApuntes.adapter = adapter
        binding.ViewApuntes.layoutManager = LinearLayoutManager(requireContext())


        return postsList
    }

    override fun onItemClick(id: String) {
        bd.collection("Ciclos").document(args.idCiclo).
        collection("Modulos").document(args.idModulo).
        collection("UFs").document(args.idUf).
        collection("Publicaciones").document(id)
            .get().addOnSuccessListener {
                val view = binding.root
                val action = FragmentAdminPostsDirections.actionFragmentAdminPostsToFragmentAdminModPost(args.idCiclo, args.idModulo, args.idUf, id)
                view.findNavController().navigate(action)
            }
    }
}