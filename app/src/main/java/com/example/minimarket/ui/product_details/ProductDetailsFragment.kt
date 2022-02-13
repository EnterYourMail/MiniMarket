package com.example.minimarket.ui.product_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.BaseFragment
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.FragmentProductDetailsBinding
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewModel: ProductDetailsViewModel by viewModels {
        viewModelFactoryFactory.create(args.productId)
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
            setAllContainerColors(colorBackground)
        }
//
//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
//            endContainerColor = colorBackground
//        }
//        sharedElementReturnTransition = MaterialContainerTransform().apply {
//            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
//            startContainerColor = colorBackground
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        initToolbar(binding.productDetailsToolbar)
        setInserts(binding.root)

        viewModel.viewState.observe(viewLifecycleOwner, ::observeViewState)

        binding.productDetailsPlusImage.setOnClickListener { viewModel.plus() }
        binding.productDetailsMinusImage.setOnClickListener { viewModel.minus() }
        binding.productDetailsCartButton.setOnClickListener(::gotoCartButtonOnClick)
        binding.productDetailsQuantityInput.setOnEditorActionListener { _, _, _ ->
            viewModel.setStringQuantity(binding.productDetailsQuantityInput.text.toString())
            true
        }
    }

    private fun observeViewState(viewState: ProductDetailsViewState) {
        with(viewState.productDTO) {
            binding.productDetailsProductTitle.text = name
            binding.productDetailsProducerTitle.text = producer

            binding.productDetailsProductDescription.text = getString(
                R.string.product_details_product_description,
                protein,
                fat,
                carbohydrates,
                calories
            )
            binding.productDetailsPriceText.text = price.toString()

            val metric = getMetric()
            val height = (metric.height * 0.3).toInt()
            val width = (metric.width * 0.3).toInt()

            Picasso.get().load(imageLink).resize(width, height)
                .centerInside().into(binding.productDetailsProductImage)
        }
        binding.productDetailsQuantityInput.setText(viewState.quantity.toString())
    }

    private fun gotoCartButtonOnClick(view: View) {
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.hold_duration_large).toLong()
        }
        val cartTransitionName = getString(R.string.cart_transition_name)
        val extras = FragmentNavigatorExtras(view to cartTransitionName)
        val action = ProductDetailsFragmentDirections
            .actionProductDetailsFragmentToCartFragment()
        findNavController().navigate(action, extras)
    }

}