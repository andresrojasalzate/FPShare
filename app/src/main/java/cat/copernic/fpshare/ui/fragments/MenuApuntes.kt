package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.R
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentMenuApuntesBinding
import cat.copernic.fpshare.databinding.FragmentMenuUfBinding
import cat.copernic.fpshare.modelo.Modul
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [MenuApuntes.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuApuntes : Fragment() {
    private var _binding: FragmentMenuApuntesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance();
    private lateinit var adapter: PubliAdapter
    private lateinit var cicloList: MutableList<Publicacion>

    val args: MenuApuntesArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuApuntesBinding.inflate(inflater, container, false)
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

    private fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()
        bd.collection("Ciclos").document(args.cicloId).collection("Modulos").document(args.moduloId).collection("UFs").document(args.ufId).collection("Publicaciones")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val idPubli = document.id
                    val publiTitle = document["titulo"].toString()
                    val publiDescr = document["nombre"].toString()
                    val publiProfile = document["perfil"].toString()
                    val checked = document["checked"].toString()
                    val publiLink = document["enlace"].toString()
                    val publi = Publicacion(idPubli,publiProfile,publiTitle,publiDescr,checked, publiLink)
                    cicloList.add(publi)
                }
                adapter= PubliAdapter(cicloList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

        return cicloList
    }

}