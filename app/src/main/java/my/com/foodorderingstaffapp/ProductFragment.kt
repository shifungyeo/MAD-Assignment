package my.com.foodorderingstaffapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.com.foodorderingstaffapp.databinding.FragmentProductBinding
import com.google.firebase.firestore.ktx.toObjects

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val nav by lazy { findNavController() }
    private val vm: ProductViewModel by activityViewModels()

    private lateinit var adapter: ProductAdapter
    private var keyword: String = ""

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater,container,false)

        binding.btnAdd.setOnClickListener{ nav.navigate(R.id.addProductFragment) }
        binding.btnDeleteAll.setOnClickListener{ deleteAll() }
        binding.btnSearch.setOnClickListener { filter() }

        adapter = ProductAdapter() { holder, product ->
            holder.root.setOnClickListener{
                nav.navigate(R.id.editProductFragment, bundleOf("id" to product.id))
            }
        }
        load()
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
    }

    private fun deleteAll() {
        vm.deleteAll()
    }

    private fun filter() {
        keyword = binding.edtSearch.text.toString()
        load()
    }

    private fun load() {
        if (keyword == "") {
            vm.getAll().observe(viewLifecycleOwner) { product ->
                adapter.submitList(product)
                binding.txtCount.text = "${product.size} item(s)"
            }
        } else if (keyword != "") {
            Firebase.firestore
                .collection("products")
                .whereEqualTo("name", keyword)
                .get()
                .addOnSuccessListener { snap ->
                    val list = snap.toObjects<Product>()
                    adapter.submitList(list)
                    binding.txtCount.text = "${list.size} item(s)"
                }
        }
    }
}