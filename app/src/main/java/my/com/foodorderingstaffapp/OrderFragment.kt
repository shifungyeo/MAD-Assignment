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
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import my.com.foodorderingstaffapp.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderViewModel by activityViewModels()

    private lateinit var adapter: OrderAdapter
    private var date = ""

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrderBinding.inflate(inflater,container,false)

        binding.btnSearch.setOnClickListener { filter() }

        adapter = OrderAdapter() { holder, order ->
            holder.root.setOnClickListener{
                nav.navigate(R.id.orderDetailFragment, bundleOf("id" to order.id))
            }
        }
        load()
        binding.orderRv.adapter = adapter
        binding.orderRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
    }

    private fun filter() {
        date = binding.edtDate.text.toString()
        load()
    }

    private fun load(){
        if (date == "") {
            vm.getAll().observe(viewLifecycleOwner) { order ->
                adapter.submitList(order)
            }
        } else if (date != "") {
            Firebase.firestore
                .collection("orders")
                .whereEqualTo("orderDate", date)
                .get()
                .addOnSuccessListener { snap ->
                    val list = snap.toObjects<Order>()
                    adapter.submitList(list)
                }
        }
    }

}