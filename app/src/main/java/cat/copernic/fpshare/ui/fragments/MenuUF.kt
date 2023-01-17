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
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentMenuUfBinding
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * Clase de la pantalla MenuUF
 *
 * @author FPShare
 */
class MenuUF : Fragment(), UfAdminAdapter.OnItemClickListener {
    private var _binding: FragmentMenuUfBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    val bd = FirebaseFirestore.getInstance();
    private lateinit var adapter: UfAdminAdapter
    private lateinit var cicloList: Deferred<MutableList<Uf>>

    val args: MenuUFArgs by navArgs()

    /**
     * Con esta función mostraremos el diseño de la pantalla ,mediante un View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuUfBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En esta función iniciamos  los diferentes elementos de la pantalla y creamos los listener de los eventos de los
     * elementos  de la vista
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView


        lifecycleScope.launch(Dispatchers.Main){
            cicloList = async{ crearMenu()}
        }
    }

    /**
     * Con esta función destruimos la vista del fragemnt y limpiamos recursos para que el sistema funcione correctamente
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun crearMenu(): MutableList<Uf>{
        val idCic = args.cicloId
        val idMod = args.moduloId

        val cicloList = mutableListOf<Uf>()

        /**
         * Cargamos la ID del ciclo escogido anteriormente, la ID del Moodulo escogido anteriormente y
         * la ID de la UF escogida en el adapter para definir la ruta donde Firebase tiene que cargar
         * los documentos y así, mostrar la lista de UFs que tiene el modulo preseleccionado.
         */
        val ufs = bd.collection("Ciclos").document(idCic).collection("Modulos").document(idMod).collection("UFs").get().await()
                for (document in ufs){
                    val idUf = document.id
                    val nombreUf = document["nombre"].toString()
                    val uf = Uf(idUf,nombreUf)
                    cicloList.add(uf)
                }
                /***
                 * Cargamos las UFs en el adapter y mostramos por pantalla.
                 */
                adapter=UfAdminAdapter(cicloList, this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return cicloList
    }

    /**
     * Interfaz UF adapter.
     */
    override fun onItemClick(id: String) {
        /**
         * Hacemos la navegacion a MenuApuntes y le pasamos la ID del Ciclo, la ID del Modulo y
         * la ID de la UF escogida en el adapter, que es la id que entra en la funcion.
         *
         * @param id
         */
        val view = binding.root
        val action = MenuUFDirections.actionListaUFsToMenuApuntes(args.cicloId,args.moduloId,id)
        view.findNavController().navigate(action)
    }

}