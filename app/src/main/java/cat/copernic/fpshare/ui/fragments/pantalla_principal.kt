package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class pantalla_principal : Fragment() {
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PubliAdapter
    private lateinit var cicloList: Deferred<MutableList<Publicacion>>
    val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaPrincipalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        lifecycleScope.launch(Dispatchers.Main){
            cicloList = async{ crearMenu()}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()
        val ciclo = bd.collection("Ciclos").get().await()
        for(doc1 in ciclo){
            val modulo = bd.collection("Ciclos").document(doc1.id).collection("Modulos").get().await()
            for(doc2 in modulo){
                val uf = bd.collection("Ciclos").document(doc1.id).collection("Modulos").document(doc2.id).collection("UFs").get().await()
                for(doc3 in uf){
                    val publi = bd.collection("Ciclos").document(doc1.id).collection("Modulos").document(doc2.id).collection("UFs").document(doc3.id).collection("Publicaciones").get().await()
                    for(doc4 in publi){
                        val idPubli = doc4.id
                        val checked = doc4["checked"].toString()
                        val publiDescr = doc4["descripcion"].toString()
                        var publiLink = doc4["enlace"].toString()
                        val publiProfile = doc4["perfil"].toString()
                        val publiTitle = doc4["titulo"].toString()
                        val imgPubli = doc4["imgPubli"].toString()
                        val publi = Publicacion(
                            idPubli,
                            publiProfile,
                            publiTitle,
                            publiDescr,
                            checked,
                            publiLink,
                            imgPubli
                        )
                        adapter = PubliAdapter(cicloList)
                         binding.recyclerView.adapter = adapter
                         binding.recyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        cicloList.add(publi)
                    }
                }
            }
        }
        return cicloList
    }

}