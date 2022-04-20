package my.com.foodorderingstaffapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.com.foodorderingstaffapp.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val nav by lazy { findNavController() }
    private val vm: ProductViewModel by activityViewModels()

    private val launcher = registerForActivityResult(StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddProductBinding.inflate(inflater,container,false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        binding.edtId.text.clear()
        binding.edtName.text.clear()
        binding.edtPrice.text.clear()
        binding.imgPhoto.setImageDrawable(null)
        binding.edtId.requestFocus()
    }

    private fun submit() {
        val p = Product(
            id      = binding.edtId.text.toString().trim().uppercase(),
            name    = binding.edtName.text.toString().trim(),
            price   = binding.edtPrice.text.toString().toDoubleOrNull() ?: -1.00,
            photo   = binding.imgPhoto.cropToBlob(300,300),
        )

        val err = vm.validate(p)
        if(err != "") {
            errorDialog(err)
            return
        }

        vm.set(p)
        nav.navigateUp()
    }

}