package my.com.foodorderingstaffapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(val fn: (ViewHolder, Order) -> Unit = { _, _ ->} ) : ListAdapter<Order, OrderAdapter.ViewHolder>(DiffCallback)
{
    companion object DiffCallback : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(a: Order, b: Order)    = a.id == b.id
        override fun areContentsTheSame(a: Order, b: Order) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val root = view
        val orderId : TextView = view.findViewById(R.id.txtOrderID)
        val totalPrice : TextView = view.findViewById(R.id.txtTotalPrice)
        val orderStatus : TextView = view.findViewById(R.id.txtOrderStatus)
        val orderDate : TextView = view.findViewById(R.id.txtOrderDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)

        holder.orderId.text = order.id
        holder.totalPrice.text = String.format("%.2f", order.totalPrice)
        holder.orderStatus.text = order.orderStatus
        holder.orderDate.text = order.orderDate

        fn(holder, order)
    }
}