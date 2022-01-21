package com.example.minimarket.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.BaseFragment
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.CartCounterBinding
import com.example.minimarket.databinding.FragmentListBinding
import com.example.minimarket.ui.list.item.ListItem
import com.google.android.material.transition.Hold
import com.xwray.groupie.GroupieAdapter
import javax.inject.Inject

class ListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    private lateinit var binding: FragmentListBinding
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

        val layoutMenuItem = binding.listToolbar.menu.findItem(R.id.menu_layout)
        val cartMenuItem = binding.listToolbar.menu.findItem(R.id.cartFragment)
        val cartIconLayout = cartMenuItem.actionView
        val cartIconCounter = CartCounterBinding.bind(cartIconLayout).cartCounterCounterText

        cartIconLayout.setOnClickListener(::cartIconOnClick)
        binding.listToolbar.setOnMenuItemClickListener {
            viewModel.menuItemIdClick(it.itemId)
        }
        binding.listSearchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.findProducts(text.toString())
        }

        val groupAdapter = GroupieAdapter().apply {
            setOnItemClickListener(::listItemOnClick)
        }
        binding.listProductsList.adapter = groupAdapter
        binding.listProductsList.layoutManager = LinearLayoutManager(context)

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            cartIconCounter.isVisible = viewState.isCartCounterVisible
            cartIconCounter.text = viewState.cartCount.toString()
            layoutMenuItem.setIcon(viewState.layoutType.icon)

            binding.listProductsList.layoutManager = when (viewState.layoutType) {
                LayoutType.LINER -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, viewState.layoutType.spanCount)
            }
            groupAdapter.update(viewState.items)
        }

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun cartIconOnClick(view: View) {
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong() / 2
        }
        val cartTransitionName = getString(R.string.cart_transition_name)
        val extras = FragmentNavigatorExtras(view to cartTransitionName)
        val action = ListFragmentDirections.actionListFragmentToCartFragment()
        findNavController().navigate(action, extras)
    }

    private fun listItemOnClick(item: com.xwray.groupie.Item<*>, view: View) {
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong() / 2
        }
        val productDetailsTransitionName = getString(R.string.product_details_transition_name)
        val extras = FragmentNavigatorExtras(
            (item as ListItem<*>).getView() to productDetailsTransitionName
        )
        val action = ListFragmentDirections
            .actionListFragmentToProductDetailsFragment(item.productDTO.productId)
        findNavController().navigate(action, extras)
    }

}