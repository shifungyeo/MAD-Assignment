package my.com.foodorderingstaffapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class OrderViewModel: ViewModel() {
    private val col = Firebase.firestore.collection("orders")
    private val order = MutableLiveData<List<Order>>()

    init{
        col.addSnapshotListener{ snap, _ -> order.value = snap?.toObjects() }
    }

    fun get(id: String): Order? {
        return order.value?.find { o -> o.id == id }
    }

    fun getAll() = order

    fun set(o: Order) {
        col.document(o.id).set(o)
    }
}