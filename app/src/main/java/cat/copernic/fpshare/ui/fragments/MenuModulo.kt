package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.ModulAdminAdapter
import cat.copernic.fpshare.databinding.FragmentMenuModuloBinding
import cat.copernic.fpshare.modelo.Modul
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MenuModulo : Fragment(), ModulAdminAdapter.OnItemClickListener {

    private var _binding: FragmentMenuModuloBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModulAdminAdapter
    private lateinit var cicloList: Deferred<MutableList<Modul>>

    val bd = FirebaseFirestore.getInstance()

    private val args: MenuModuloArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuModuloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        /***
         * Iniciamos la corrutina con lifecycleScope.launch y llamamos a crearMenu como
         * una funcion asincrona con la opcion async.
         * Para realizar esto, tenemos que declarar nuestra variable cicloList como
         * Deferred<MutableList<Modulo>>.
         */
        lifecycleScope.launch(Dispatchers.Main){
            cicloList = async{ crearMenu()}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /***
     * Declaramos nuestra funcion como suspend fun.
     */
    private suspend fun crearMenu(): MutableList<Modul> {
        val idCic = args.cicloid
        val cicloList = mutableListOf<Modul>()
        /***
         * En la consulta conseguimos el documento con .get().await(), de lo contrario,
         * siempre realizara consultas gastando muchos recursos de nuestro sistema.
         */
        val modulos = bd.collection("Ciclos").document(idCic).collection("Modulos").get().await()
                for (document in modulos) {
                    /***
                     * Recogemos los datos del modulo y lo añadimos al arrayList.
                     */
                    val idModul = document.id
                    val nombreModul = document["nombre"].toString()
                    val modulo = Modul(idModul, nombreModul)
                    cicloList.add(modulo)
                }
                /***
                 * Cargamos la lista en el Adapter y lo mostramos en pantalla.
                 */
                adapter = ModulAdminAdapter(cicloList, this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return cicloList
    }

    /***
     * Interfaz creada desde modulAdapter.
     */
    override fun onItemClick(id: String) {
        /***
         * Realizamos la navegacion con la id del Modulo seleccionada en el Adapter, y la id del
         * Ciclo, que es la que hemos seleccionado en la pantalla anterior.
         */
        val view = binding.root
        val action = MenuModuloDirections.actionMenuModuloToListaUFs(args.cicloid, id)
        view.findNavController().navigate(action)
    }

}