package my.com.foodorderingstaffapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.com.foodorderingstaffapp.databinding.FragmentOrderDetailBinding

class OrderDetailFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderViewModel by activityViewModels()

    private val id by lazy { requireArguments().getString("id") ?: "" }
    var orderStatus : String = ""

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater,container,false)

        reset()
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        val o = vm.get(id)

        if( o == null) {
            nav.navigateUp()
            return
        }

        load(o)
    }

    private fun load(o: Order) {
        binding.txtOrderID.text = o.id
        binding.txtUserId.text = o.userID
        binding.txtItem.text = ""
        binding.txtQuantity.text = ""
        binding.txtTotalQuantity.text = o.totalQuantity.toString()
        binding.txtTotalPrice.text = String.format("%.2f", o.totalPrice)
        binding.txtPayMethod.text = o.paymentMethod
        binding.txtDate.text = o.orderDate
        when (o.orderStatus){
            "Pending" -> binding.rbPending.isChecked = true
            "Ready" -> binding.rbReady.isChecked = true
            "Completed" -> binding.rbCompleted.isChecked = true
        }
    }

    private fun submit() {
        if(binding.rbPending.isChecked)
            orderStatus = "Pending"
        else if (binding.rbReady.isChecked)
            orderStatus = "Ready"
        else if (binding.rbCompleted.isChecked)
            orderStatus = "Completed"

        val o = Order(
            id = id,
            userID = binding.txtUserId.text.toString(),
            item = ArrayList(),
            quantity = ArrayList(),
            totalQuantity = binding.txtTotalQuantity.text.toString().toInt(),
            totalPrice = binding.txtTotalPrice.text.toString().toDouble(),
            paymentMethod = binding.txtPayMethod.text.toString(),
            orderStatus = orderStatus,
            orderDate = binding.txtDate.text.toString()
        )

        vm.set(o)
        nav.navigateUp()
    }
}