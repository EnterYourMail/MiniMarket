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
import com.example.minimarket.databinding.CartCounterBinding
import com.example.minimarket.databinding.FragmentListBinding
import com.example.minimarket.ui.MainActivityViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!
    private var bindingCartCounter: CartCounterBinding? = null

    private val viewModel: ListViewModel by viewModels{
        ListViewModel.ListViewModelFactory(
            (requireActivity().application as MiniMarketApplication).repository
        )
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

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_list_menu, menu)
        val item = menu.findItem(R.id.cartFragment)
        val actionView = item.actionView
        actionView.setOnClickListener {
            item.onNavDestinationSelected(findNavController())
        }

        bindingCartCounter = CartCounterBinding.bind(actionView)
        bindingCartCounter?.let { bindingCC ->
            viewModel.basketCount.observe(this) {
                setCounter(bindingCC.tvCounter, it)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bindingCartCounter = null
    }

    private fun setCounter(tvCounter: TextView, i: Int) {
        if ( i == 0 ) {
            tvCounter.visibility = View.INVISIBLE
        } else {
            tvCounter.text = i.toString()
            tvCounter.visibility = View.VISIBLE
        }
    }

}