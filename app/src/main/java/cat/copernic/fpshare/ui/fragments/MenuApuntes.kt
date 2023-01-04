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
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * A simple [Fragment] subclass.
 * Use the [MenuApuntes.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuApuntes : Fragment() {
    private var _binding: FragmentMenuApuntesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: PubliAdapter
    private lateinit var cicloList: Deferred<MutableList<Publicacion>>

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
        /***
         * Iniciamos la corrutina con lifecycleScope.launch y llamamos a crearMenu como
         * una funcion asincrona con la opcion async.
         * Para realizar esto, tenemos que declarar nuestra variable cicloList como
         * Deferred<MutableList<Publicacion>>.
         */
        lifecycleScope.launch(Dispatchers.Main){
            cicloList = async{ crearMenu()}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun crearMenu(): MutableList<Publicacion>{
        var cicloList = mutableListOf<Publicacion>()


        var publicaciones = bd.collection("Ciclos").document(args.cicloId).collection("Modulos").document(args.moduloId).collection("UFs").document(args.ufId).collection("Publicaciones").get().await()
        /***
         * Añadimos las publicaciones en una MutableList<Publicacion>
         */
                for (document in publicaciones){
                    val idPubli = document.id
                    val checked = document["checked"].toString()
                    val publiDescr = document["descripcion"].toString()
                    val publiLink = document["enlace"].toString()
                    val publiProfile = document["perfil"].toString()
                    val publiTitle = document["titulo"].toString()
                    val imgPubli = document["imgPubli"].toString()
                    val publi = Publicacion(idPubli,publiProfile,publiTitle,publiDescr,checked,publiLink, imgPubli)
                    cicloList.add(publi)
                }
                /***
                 * Cargamos las UFs en el adapter y mostramos por pantalla.
                 */
                adapter= PubliAdapter(cicloList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return cicloList
    }

}