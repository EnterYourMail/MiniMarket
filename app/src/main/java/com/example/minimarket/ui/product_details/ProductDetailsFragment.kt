package com.example.minimarket.ui.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewState: ProductDetailsViewState by viewModels {
        ViewModelFactory(
            (requireActivity().application as MiniMarketApplication).repository,
            args.productId
        )
    }
    private val viewModel: ProductDetailsViewModel by viewModels {
        ViewModelFactory(
            (requireActivity().application as MiniMarketApplication).repository,
            args.productId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        viewState.product.observe(viewLifecycleOwner) {
            with(it) {
                binding.tvProduct.text = name
                binding.tvProducer.text = producer
                val text = """
                    |Белки: $protein г
                    |Жиры: $fat г
                    |Углеводы: $carbohydrates г
                    |Калорийность: $calories кКал
                    """.trimMargin()
                binding.tvDetails.text = text
                binding.tvPrice.text = price.toString()
            }
        }

        viewModel.quantity.observe(viewLifecycleOwner) {
            binding.etQuantity.setText(it.toString())
        }

        binding.etQuantity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.setQuantity(
                    binding.etQuantity.text.toString().toInt()
                )
                true
            } else false
        }

        binding.ivPlus.setOnClickListener { viewModel.plus() }
        binding.ivMinus.setOnClickListener { viewModel.minus() }
        binding.btGotoCart.setOnClickListener {
            val action = ProductDetailsFragmentDirections
                .actionProductDetailsFragmentToCartFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}