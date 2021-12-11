package com.example.minimarket.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CartViewModel by viewModels {
        ViewModelFactory(
            (requireActivity().application as MiniMarketApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel.cart.observe(viewLifecycleOwner) { productDetails ->
            var total = 0

            val list: List<String> = productDetails.map {
                total += (it.product.price * it.cartItem.quantity)
                with(it.product) {
                    "$name $price Р х ${it.cartItem.quantity} = ${price * it.cartItem.quantity} Р"
                }
            }

            binding.tvCartDetails.text = list.joinToString("\n")
            binding.tvCartTotal.text = ("$total Р")
        }

        binding.btCartPay.setOnClickListener {
            viewModel.cartCount.observe(viewLifecycleOwner, object : Observer<Int> {
                override fun onChanged(t: Int?) {
                    if (t ?: 0 > 0) {
                        viewModel.cartCount.removeObserver(this)
                        viewModel.pay()
                        Snackbar.make(
                            binding.root,
                            R.string.message_paid_successfully,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }

        return binding.root
    }


}