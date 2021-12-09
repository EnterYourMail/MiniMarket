package com.example.minimarket.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.databinding.FragmentListBinding
import com.example.minimarket.ui.MainActivityViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!
    private val viewModel: ListViewModel by viewModels{
        ListViewModel.ListViewModelFactory(
            (application as MiniMarketApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val actionView = binding.toolbar.menu.findItem(R.id.cartFragment).actionView
        val tvCounter = actionView.findViewById<TextView>(R.id.tvCounter)
        viewModel.basketCount.observe(this) {
            if (it == 0 ) {
                tvCounter.visibility = View.INVISIBLE
            } else {
                tvCounter.text = it.toString()
                tvCounter.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_list_menu, menu)
        val item = menu.findItem(R.id.cartFragment)
        val counterView = item.actionView
        counterView.setOnClickListener {
            item.onNavDestinationSelected(findNavController())
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}