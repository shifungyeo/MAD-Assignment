package my.com.foodorderingstaffapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class ProductViewModel : ViewModel() {
    private val col = Firebase.firestore.collection("products")
    private val product = MutableLiveData<List<Product>>()

    init{
        col.addSnapshotListener{ snap, _ -> product.value = snap?.toObjects() }
    }

    fun get(id: String): Product? {
        return product.value?.find { p -> p.id == id }
    }

    fun getAll() = product

    fun delete(id: String){
        col.document(id).delete()
    }

    fun deleteAll(){
        product.value?.forEach{ p -> delete(p.id) }
    }

    fun set(p: Product) {
        col.document(p.id).set(p)
    }

    private fun idExists(id:String): Boolean{
        return product.value?.any{p -> p.id == id} ?: false
    }

    private fun nameExists(name: String):Boolean{
        return product.value?.any{p -> p.name == name} ?: false
    }

    fun validate(p:Product, insert: Boolean = true): String{
        val regexId = Regex("""^[0-9A-Z]{4}$""")
        var e = ""

        if (insert) {
            e += if (p.id == "") "- Id is required.\n"
            else if (!p.id.matches(regexId)) "- Id format is invalid.\n"
            else if (idExists(p.id)) "- Id is duplicated.\n"
            else ""

            e+= if (nameExists(p.name)) "- Name is duplicated.\n"
            else ""
        }

        e += if (p.name == "") "- Name is required.\n"
        else ""

        e += if (p.price == -1.00) "- Price is required.\n"
        else ""

        e += if (p.photo.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        return e
    }
}