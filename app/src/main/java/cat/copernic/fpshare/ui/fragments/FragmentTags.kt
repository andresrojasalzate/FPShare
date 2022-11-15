package cat.copernic.fpshare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.adapters.TagAdapter
import cat.copernic.fpshare.databinding.FragmentTagsBinding
import cat.copernic.fpshare.modelo.Tag

class FragmentTags : Fragment() {
    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!

    private lateinit var botonAdd: Button
    private lateinit var botonDelete: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        botonAdd = binding.buttonAddTag
        botonDelete = binding.buttonDeleteTag
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TagAdapter(obtenerEtiquetas())

        botonAdd.setOnClickListener {
            val action =
                FragmentTagsDirections.actionListaTagsAdministracionToCrearTag()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun obtenerEtiquetas(): MutableList<Tag> {
        val etiquetas = mutableListOf<Tag>()

        return etiquetas
    }
}