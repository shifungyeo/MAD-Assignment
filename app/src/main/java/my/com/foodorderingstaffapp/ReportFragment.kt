package my.com.foodorderingstaffapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import my.com.foodorderingstaffapp.databinding.FragmentReportBinding
import java.util.*

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderViewModel by activityViewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReportBinding.inflate(inflater,container,false)

        var totalSales = 0.0
        Firebase.firestore
            .collection("orders")
            .get()
            .addOnSuccessListener { snap ->
                val list = snap.toObjects<Order>()
                binding.txtOrderTotal.text = "${list.size}"
                for(order in list) {
                    totalSales += order.totalPrice
                }
                binding.txtSalesTotal.text = String.format("%.2f", totalSales)
                var donationAmount = totalSales * 0.5
                binding.txtDonationAmount.text = String.format("%.2f", donationAmount)
            }

        return binding.root
    }
}