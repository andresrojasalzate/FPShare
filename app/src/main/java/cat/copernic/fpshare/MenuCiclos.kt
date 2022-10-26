package cat.copernic.fpshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.fpshare.databinding.FragmentMenuCiclosBinding
import java.util.Collections.addAll


class MenuCiclos : Fragment() {

    private lateinit var valorciclo: ArrayList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var _binding: FragmentMenuCiclosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuCiclosBinding.inflate(inflater, container, false)
        val view = _binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        recyclerView = _binding.recyclerCiclo
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CicleAdapter()
    }


}