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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentAdminUFs : Fragment(), UfAdminAdapter.OnItemClickListener {

    private var _binding: FragmentAdminUFsBinding? = null
    private val binding get() = _binding!!
    private val bd = FirebaseFirestore.getInstance()

    private lateinit var ufList: MutableList<Uf>
    private lateinit var adapterU: UfAdminAdapter

    private lateinit var botonAddUF: Button
    private lateinit var botonDeleteUF: Button

    private lateinit var recyclerViewUF: RecyclerView
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
        lifecycleScope.launch(Dispatchers.Main) {
            ufList = withContext(Dispatchers.IO) { crearUF() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inicializadoresButton() {
        // inicializar botones de UF
        botonAddUF = binding.buttonAddUF
        botonDeleteUF = binding.buttonDeleteUF
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
    }

    private fun crearUF(): MutableList<Uf> {
        val ufList = mutableListOf<Uf>()
        val idCiclo = args.idCiclo
        val idModulo = args.idModulo


        bd.collection("Ciclos").document(idCiclo).collection("Modulos")
            .document(idModulo).collection("UFs")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
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
            }
        return ufList
    }
}