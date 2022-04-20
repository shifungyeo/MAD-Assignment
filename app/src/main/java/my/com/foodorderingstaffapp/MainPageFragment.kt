package my.com.foodorderingstaffapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.com.foodorderingstaffapp.databinding.FragmentMainPageBinding

class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainPageBinding.inflate(inflater,container,false)

        binding.iconProduct.setOnClickListener { nav.navigate(R.id.productFragment) }
        binding.txtProduct.setOnClickListener { nav.navigate(R.id.productFragment) }

        binding.iconOrder.setOnClickListener { nav.navigate(R.id.orderFragment) }
        binding.txtOrder.setOnClickListener { nav.navigate(R.id.orderFragment) }

        binding.iconReport.setOnClickListener { nav.navigate(R.id.reportFragment) }
        binding.txtReport.setOnClickListener { nav.navigate(R.id.reportFragment) }

        return binding.root
    }

}