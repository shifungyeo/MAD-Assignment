package my.com.foodorderingstaffapp

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import my.com.foodorderingstaffapp.databinding.ItemOrderBinding
import java.util.*
import kotlin.collections.ArrayList

data class Product(
    @DocumentId
    var id      : String = "",
    var name    : String = "",
    var price   : Double = 0.00,
    var photo   : Blob = Blob.fromBytes(ByteArray(0)),
)

data class Order(
    @DocumentId
    var id              : String = "",
    var userID          : String = "",
    var item            : ArrayList<String> = ArrayList(),
    var quantity        : ArrayList<Int> = ArrayList(),
    var totalQuantity   : Int = 0,
    var totalPrice      : Double = 0.00,
    var paymentMethod   : String = "",
    var orderStatus     : String = "",
    var orderDate       : String = "",
)
