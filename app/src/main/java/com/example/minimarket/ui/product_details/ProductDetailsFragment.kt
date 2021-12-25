package com.example.minimarket.ui.product_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.BaseFragment
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.FragmentProductDetailsBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject







class ProductDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private val args by navArgs<ProductDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactoryFactory: ViewModelFactory.Factory

    private val viewModel: ProductDetailsViewModel by viewModels {
        viewModelFactoryFactory.create(args.productId)
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
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        initToolbar(binding.productDetailsToolbar)

        viewModel.viewState.observe(viewLifecycleOwner, ::observeViewState)

        binding.productDetailsPlusImage.setOnClickListener { viewModel.plus() }
        binding.productDetailsMinusImage.setOnClickListener { viewModel.minus() }
        binding.productDetailsGotoCartButton.setOnClickListener {
            val action = ProductDetailsFragmentDirections
                .actionProductDetailsFragmentToCartFragment()
            findNavController().navigate(action)
        }
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

            Picasso.get().load(imageLink)
                .fit().centerInside().into(binding.productDetailsProductImage)
        }
        binding.productDetailsQuantityInput.setText(viewState.quantity.toString())
    }

}