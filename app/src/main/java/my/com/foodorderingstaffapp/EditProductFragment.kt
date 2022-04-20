package my.com.foodorderingstaffapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.com.foodorderingstaffapp.databinding.FragmentEditProductBinding

class EditProductFragment : Fragment() {

    private lateinit var binding: FragmentEditProductBinding
    private val nav by lazy { findNavController() }
    private val vm: ProductViewModel by activityViewModels()

    private val id by lazy { requireArguments().getString("id") ?: "" }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditProductBinding.inflate(inflater,container,false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        val p = vm.get(id)

        if (p == null) {
            nav.navigateUp()
            return
        }

        load(p)
    }

    private fun load(p: Product) {
        binding.txtId.text = p.id
        binding.edtName.setText(p.name)
        binding.edtPrice.setText(String.format("%.2f", p.price))
        binding.imgPhoto.setImageBitmap(p.photo.toBitmap())

        binding.edtName.requestFocus()
    }

    private fun submit() {
        val p = Product(
            id      = id,
            name    = binding.edtName.text.toString().trim(),
            price   = binding.edtPrice.text.toString().toDoubleOrNull() ?: -1.00,
            photo   = binding.imgPhoto.cropToBlob(300, 300)
        )

        val err = vm.validate(p, false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(p)
        nav.navigateUp()
    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()
    }

}