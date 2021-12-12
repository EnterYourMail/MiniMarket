package com.example.minimarket.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.minimarket.MiniMarketApplication
import com.example.minimarket.R
import com.example.minimarket.base.ViewModelFactory
import com.example.minimarket.database.Product
import com.example.minimarket.databinding.CartCounterBinding
import com.example.minimarket.databinding.FragmentListBinding
import com.example.minimarket.ui.list.item.ListItem
import com.example.minimarket.ui.list.item.ListItemCell
import com.example.minimarket.ui.list.item.ListItemLine
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener

class ListFragment : Fragment() {
    companion object {
        const val LAYOUT_TYPE_GRID = 0
        const val LAYOUT_TYPE_LINEAR = 1
        const val LAYOUT_KEY_NAME = "LAYOUT_TYPE"
    }

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!
    //private var bindingCartCounter: CartCounterBinding? = null

    private val sPref: SharedPreferences by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    private val viewModel: ListViewModel by viewModels {
        ViewModelFactory(
            (requireActivity().application as MiniMarketApplication).repository
        )
    }

    private lateinit var groupAdapter: GroupieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        groupAdapter = GroupieAdapter().apply {
            setOnItemClickListener(onItemClickListener)
        }
        binding.rvList.adapter = groupAdapter
        initRecycleViewAndIcon(null)
        
        binding.edListSearch.setOnEditorActionListener { v, actionId, _ ->
            viewModel.findProducts(v.text.toString())
            true
//            if ( actionId == EditorInfo.IME_ACTION_SEARCH ) {
//                viewModel.findProducts(v.text.toString())
//                true
//            } else {
//                false
//            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_list_menu, menu)
        val cartItem = menu.findItem(R.id.cartFragment)
        val actionView = cartItem.actionView
        // onOptionsItemSelected isn't called for a custom action view.
        actionView.setOnClickListener {
            cartItem.onNavDestinationSelected(findNavController())
        }

        CartCounterBinding.bind(actionView).let { bindingCC ->
            viewModel.cartCount.observe(this) { i ->
                setCounter(bindingCC.tvCounter, i)
            }
        }

        val layoutType = sPref.getInt(LAYOUT_KEY_NAME, LAYOUT_TYPE_GRID)
        val layoutItem = menu.findItem(R.id.menu_layout)
        setMenuItemIcon(layoutItem, layoutType)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_layout -> {
                viewModel.products.removeObservers(viewLifecycleOwner)
                val edPrefs = sPref.edit()
                when (sPref.getInt(LAYOUT_KEY_NAME, LAYOUT_TYPE_GRID)) {
                    LAYOUT_TYPE_GRID -> edPrefs.putInt(LAYOUT_KEY_NAME, LAYOUT_TYPE_LINEAR)
                    else -> edPrefs.putInt(LAYOUT_KEY_NAME, LAYOUT_TYPE_GRID)
                }
                edPrefs.apply()
                initRecycleViewAndIcon(item)
            }
            R.id.menu_delete_all -> viewModel.deleteAllProducts()
            R.id.menu_prepopulate -> viewModel.prepopulate()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //bindingCartCounter = null
    }

    private fun setCounter(tvCounter: TextView, i: Int) {
        if (i == 0) {
            tvCounter.visibility = View.INVISIBLE
            tvCounter.text = ""
        } else {
            tvCounter.text = i.toString()
            tvCounter.visibility = View.VISIBLE
        }
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        val action = ListFragmentDirections
            .actionListFragmentToProductDetailsFragment(
                (item as ListItem<*>).getProductId()
            )
        findNavController().navigate(action)
    }

    private fun initRecycleViewAndIcon(menuItem: MenuItem?) {
        val layoutType = sPref.getInt(LAYOUT_KEY_NAME, LAYOUT_TYPE_GRID)
        val listItem: (data: Product) -> (ListItem<out ViewBinding>)

        if (layoutType == LAYOUT_TYPE_GRID) {
            listItem = { ListItemCell(it) }
            binding.rvList.layoutManager = GridLayoutManager(context, 2)
        } else {
            listItem = { ListItemLine(it) }
            binding.rvList.layoutManager = LinearLayoutManager(context)
        }

        menuItem?.let { setMenuItemIcon(menuItem, layoutType) }
        viewModel.products.observe(viewLifecycleOwner) {
            groupAdapter.update(it.map { product -> listItem(product) })
        }
    }

    private fun setMenuItemIcon(menuItem: MenuItem, layoutType: Int) {
        if (layoutType == LAYOUT_TYPE_GRID) {
            menuItem.setIcon(R.drawable.ic_baseline_grid_24)
        } else {
            menuItem.setIcon(R.drawable.ic_baseline_linear_layout_24)
        }

    }

/*    private fun listItemByLayout(layoutType: Int): (data: Product) -> (ListItem<out ViewBinding>) {
        return when(layoutType) {
            LAYOUT_TYPE_GRID -> { data: Product -> ListItemCell(data) }
            LAYOUT_TYPE_LINEAR -> { data: Product -> ListItemLine(data) }
            else -> throw IllegalArgumentException("Unknown value for layout type")
        }
    }

    private fun getLayoutManager(layoutType:Int): LinearLayoutManager {
        return when(layoutType) {
            LAYOUT_TYPE_GRID -> GridLayoutManager(context, 2)
            LAYOUT_TYPE_LINEAR -> LinearLayoutManager(context)
            else -> throw IllegalArgumentException("Unknown value for layout type")
        }
    }*/

}