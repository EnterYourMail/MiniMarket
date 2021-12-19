package com.example.minimarket.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.BaseFragment
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class CartFragment : BaseFragment() {

    private lateinit var binding: FragmentCartBinding

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory
    private val viewModel: CartViewModel by viewModels {
        viewModelFactoryFactory.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MiniMarketApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        initToolbar(binding.cartToolbar)

        viewModel.viewState.observe(viewLifecycleOwner, ::observeViewState)

        binding.cartPayButton.setOnClickListener {
            viewModel.pay()
            Snackbar.make(binding.root, R.string.message_paid_successfully, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun observeViewState(viewState: CartViewState) {
        val list: List<String> = viewState.listCartDetails.map {
            getString(
                R.string.cart_details_line,
                it.product.name,
                it.product.price,
                it.cartItem.quantity,
                it.product.price * it.cartItem.quantity
            )
        }

        binding.cartDetailsText.text = list.joinToString("\n")
        binding.cartTotalText.text = getString(
            R.string.cart_total_text,
            viewState.cartTotal
        )
        binding.cartPayButton.isEnabled = viewState.isPayButtonEnable
    }
}