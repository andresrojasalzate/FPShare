package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.MenuAdapter
import cat.copernic.fpshare.databinding.FragmentMenuCiclosBinding
import cat.copernic.fpshare.modelo.Cicle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * Clase de la pantalla MenuCiclos
 *
 * @author FPShare
 */
class MenuCiclos : Fragment(), MenuAdapter.OnItemClickListener {

    private var _binding: FragmentMenuCiclosBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private var bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: MenuAdapter
    private lateinit var cicloList: Deferred<MutableList<Cicle>>

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
        _binding = FragmentMenuCiclosBinding.inflate(inflater, container, false)
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
        /***
         * Iniciamos la corrutina con lifecycleScope.launch y llamamos a crearMenu como
         * una funcion asincrona con la opcion async.
         * Para realizar esto, tenemos que declarar nuestra variable cicloList como
         * Deferred<MutableList<Ciclo>>.
         */
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

    /***
     * Declaramos nuestra funcion como suspend fun.
     *
     * @return cicloList
     */
    private suspend fun crearMenu(): MutableList<Cicle>{
        val cicloList = mutableListOf<Cicle>()
        /***
         * En la consulta conseguimos el documento con .get().await(), de lo contrario,
         * siempre realizara consultas gastando muchos recursos de nuestro sistema.
         */
        val ciclos = bd.collection("Ciclos")
            .get().await()
                for (document in ciclos){
                    /***
                     * Recogemos los datos del ciclo y lo añadimos al arrayList.
                     */
                    val idCiclo = document.id
                    val nombreCiclo = document["nombre"].toString()
                    val ciclo = Cicle(idCiclo,nombreCiclo)
                    cicloList.add(ciclo)
                }
        /***
         * Cargamos la lista en el Adapter y lo mostramos en pantalla.
         */
        adapter=MenuAdapter(cicloList, this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return cicloList
    }

    /***
     * Interfaz creada desde CicloAdapter.
     *
     * @param id
     */
    override fun onItemClick(id: String) {
        /***
         * Realizamos la navegacion con la id del Ciclo seleccionada en el Adapter.
         */
        val view = binding.root
        val action = MenuCiclosDirections.actionMenuCiclosToMenuModulo(id)
        view.findNavController().navigate(action)
    }

}

