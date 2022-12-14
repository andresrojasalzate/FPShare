package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.UfAdminAdapter
import cat.copernic.fpshare.databinding.FragmentAdminUFsBinding
import cat.copernic.fpshare.modelo.Uf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FragmentAdminUFs : Fragment(), UfAdminAdapter.OnItemClickListener {

    // Binding
    private var _binding: FragmentAdminUFsBinding? = null
    private val binding get() = _binding!!

    // Firebase
    private val bd = FirebaseFirestore.getInstance()

    private lateinit var ufList: Deferred<MutableList<Uf>>
    private lateinit var adapterU: UfAdminAdapter

    // Botones
    private lateinit var botonAddUF: Button
    private lateinit var botonEditModulo: Button

    private lateinit var recyclerViewUF: RecyclerView

    // Args
    private val args: FragmentAdminUFsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUFsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializadoresButton()
        inicializadoresRW()
        listeners()

        // Corrutina para cargar las UFs
        lifecycleScope.launch(Dispatchers.Main) {
            ufList = async { crearUF() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones de UF
        botonAddUF = binding.buttonAddUF
        botonEditModulo = binding.btnEditModule
    }

    private fun inicializadoresRW() {
        recyclerViewUF = binding.recyclerViewUFs
    }

    private fun listeners() {
        botonAddUF.setOnClickListener {
            val action =
                FragmentAdminUFsDirections.actionFragmentAdminUFsToCrearUF()
            view?.findNavController()?.navigate(action)
        }
        botonEditModulo.setOnClickListener {
            val action =
                FragmentAdminUFsDirections.actionFragmentAdminUFsToFragmentAdminEditModule(
                    args.idModulo,
                    args.idCiclo
                )
            view?.findNavController()?.navigate(action)
        }
    }

    /**
     * Función para la lectura de UFs dentro de un ciclo y un modulo especificado en anteriores
     * fragments
     */
    private suspend fun crearUF(): MutableList<Uf> {
        val ufList = mutableListOf<Uf>()
        val idCiclo = args.idCiclo
        val idModulo = args.idModulo

        val ufs = bd.collection("Ciclos").document(idCiclo).collection("Modulos")
            .document(idModulo).collection("UFs")
            .get().await()
        for (document in ufs) {
            val idUf = document.id
            val nombreUf = document["nombre"].toString()
            val uf = Uf(
                idUf,
                nombreUf
            )
            ufList.add(uf)
        }
        adapterU = UfAdminAdapter(ufList, this)
        binding.recyclerViewUFs.adapter = adapterU
        binding.recyclerViewUFs.layoutManager = LinearLayoutManager(requireContext())

        return ufList
    }

    /**
     * OnItemClick que lleva las IDs de idCiclo, idModulo y idUF hacia la pantalla de Publicaciones
     * para mostrar las publicaciones que se encuentran dentro de la UF especificada
     */
    override fun onItemClick(id: String) {
        val view = binding.root
        val action = FragmentAdminUFsDirections.actionFragmentAdminUFsToFragmentAdminPosts(
            args.idCiclo,
            args.idModulo,
            id
        )
        view.findNavController().navigate(action)
    }
}