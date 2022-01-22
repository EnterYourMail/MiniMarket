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
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class CartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels {
        viewModelFactoryFactory.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MiniMarketApplication)
            .appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorBackground = getThemeColor(android.R.attr.colorBackground)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            endContainerColor = colorBackground
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            startContainerColor = colorBackground
        }
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
        setInserts(binding.root)

        viewModel.viewState.observe(viewLifecycleOwner, ::observeViewState)
        binding.cartPayButton.setOnClickListener(::cartPayButtonClickListener)
    }

    private fun cartPayButtonClickListener(view: View) {
        viewModel.pay()
        Snackbar.make(binding.root, R.string.message_paid_successfully, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun observeViewState(viewState: CartViewState) {
        val list: List<String> = viewState.listCartDetails.map {
            getString(
                R.string.cart_details_line,
                it.productDTO.name,
                it.productDTO.price,
                it.cartItemDTO.quantity,
                it.productDTO.price * it.cartItemDTO.quantity
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