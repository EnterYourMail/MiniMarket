package com.example.minimarket.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.BaseFragment
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.CartCounterBinding
import com.example.minimarket.databinding.FragmentListBinding
import com.example.minimarket.ui.list.item.ListItem
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener
import javax.inject.Inject

class ListFragment : BaseFragment() {

    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory
    private val viewModel: ListViewModel by viewModels {
        viewModelFactoryFactory.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MiniMarketApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        //binding.listToolbar.menu.clear()
        binding.listToolbar.inflateMenu(R.menu.toolbar_list_menu)
        initToolbar(binding.listToolbar, false)

        val layoutITem = binding.listToolbar.menu.findItem(R.id.menu_layout)
        val cartItem = binding.listToolbar.menu.findItem(R.id.cartFragment)
        val cartIconLayout = cartItem.actionView
        val cartIconCounter = CartCounterBinding.bind(cartIconLayout).cartCounterCounterText
        cartIconLayout.setOnClickListener {
            cartItem.onNavDestinationSelected(findNavController())
        }

        binding.listToolbar.setOnMenuItemClickListener {
            viewModel.menuItemIdClick(it.itemId)
        }
        binding.listSearchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.findProducts(text.toString())
        }

        val groupAdapter = GroupieAdapter().apply {
            setOnItemClickListener(onItemClickListener)
        }
        binding.listProductsList.adapter = groupAdapter
        binding.listProductsList.layoutManager = LinearLayoutManager(context)

        viewModel.viewState.observe(viewLifecycleOwner) {
            cartIconCounter.isVisible = it.isCartCounterVisible
            cartIconCounter.text = it.cartCount.toString()
            layoutITem.setIcon(it.layoutType.icon)
            binding.listProductsList.layoutManager = when (it.layoutType) {
                LayoutType.LINER -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, it.layoutType.spanCount)
            }
            groupAdapter.update(it.items)
        }

    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        val action = ListFragmentDirections
            .actionListFragmentToProductDetailsFragment(
                (item as ListItem<*>).productDTO.productId
            )
        findNavController().navigate(action)
    }


}