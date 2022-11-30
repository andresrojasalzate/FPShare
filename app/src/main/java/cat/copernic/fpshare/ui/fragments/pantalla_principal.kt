package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.modelo.Publicacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class pantalla_principal : Fragment() {
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PubliAdapter
    private lateinit var cicloList: MutableList<Publicacion>
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
            cicloList = withContext(Dispatchers.IO){ crearMenu()}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()
        val ciclo = bd.collection("Ciclos")
        ciclo.get().addOnSuccessListener { docuciclos ->
            for (docuciclo in docuciclos){
                val modulo =ciclo.document(docuciclo.id).collection("Modulos")
                modulo.get().addOnSuccessListener { documodulos ->
                    for (documodulo in documodulos){
                        val uf =modulo.document(documodulo.id).collection("UFs")
                            uf.get().addOnSuccessListener { docuufs ->
                                for (docuuf in docuufs){
                                     uf.document(docuuf.id).collection("Publicaciones")
                                        .get()
                                        .addOnSuccessListener { docupublis ->
                                            for (docupubli in docupublis){
                                                    val idPubli = docupubli.id
                                                    val checked = docupubli["checked"].toString()
                                                    val publiDescr = docupubli["descripcion"].toString()
                                                    val publiLink = docupubli["enlace"].toString()
                                                    val publiProfile = docupubli["perfil"].toString()
                                                    val publiTitle = docupubli["titulo"].toString()
                                                    val publi = Publicacion(idPubli,publiProfile,publiTitle,publiDescr,checked,publiLink)
                                                    cicloList.add(publi)
                                            }
                                            adapter= PubliAdapter(cicloList)
                                            binding.recyclerView.adapter = adapter
                                            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                        }
                                }

                            }
                    }

                }

            }

        }
        return cicloList
    }

}
