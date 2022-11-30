package cat.copernic.fpshare.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.PubliAdapter
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentPantallaPrincipalBinding
import cat.copernic.fpshare.modelo.Publicacion
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class pantalla_principal : Fragment() {
    private var _binding: FragmentPantallaPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var boton: Button
    private lateinit var btn_logout: Button
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
        boton = binding.button3
        btn_logout = binding.btnLogout
        recyclerView = binding.recyclerView

        lifecycleScope.launch(Dispatchers.Main){
            cicloList = withContext(Dispatchers.IO){ crearMenu()}
        }

        boton.setOnClickListener {
            val action =
                pantalla_principalDirections.actionPantallaPrincipalToVistaPreviaPublicacion()
            view.findNavController().navigate(action)
        }
        btn_logout.setOnClickListener{
            Firebase.auth.signOut()
            val action =
                pantalla_principalDirections.actionPantallaPrincipalToLogin()
            view.findNavController().navigate(action)


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SuspiciousIndentation")
    private fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()
        val ciclo = bd.collection("Ciclos")
        //val modulo = ciclo
        //bd.collection("Ciclos").document()
        ciclo.get().addOnSuccessListener { docuciclos ->
            for (docuciclo in docuciclos){
                val modulo =ciclo.document(docuciclo.id).collection("Modulos")
                modulo.get().addOnSuccessListener { documodulos ->
                    for (documodulo in documodulos){
                        val ufs =modulo.document(documodulo.id).collection("UFs")
                            ufs.get().addOnSuccessListener { docuufs ->
                                for (docuuf in docuufs){
                                     ufs.document(docuuf.id).collection("Publicaciones")
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
                                            //binding.recyclerView.adapter = adapter
                                            //binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                        }


                                }

                            }
                    }

                }

            }

        }
                /*
            .collection("Modulos").document()
            .collection("UFs").document()
            .collection("Publicaciones")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val idPubli = document.id
                    val checked = document["checked"].toString()
                    val publiDescr = document["descripcion"].toString()
                    val publiLink = document["enlace"].toString()
                    val publiProfile = document["perfil"].toString()
                    val publiTitle = document["titulo"].toString()
                    val publi = Publicacion(idPubli,publiProfile,publiTitle,publiDescr,checked,publiLink)
                    cicloList.add(publi)
                }
                adapter= PubliAdapter(cicloList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
    */

        return cicloList
    }

}
