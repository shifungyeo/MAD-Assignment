package my.com.foodorderingstaffapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter ( val fn: (ViewHolder, Product) -> Unit = { _, _ ->} ) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback)
{
    companion object DiffCallback : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(a: Product, b: Product)    = a.id == b.id
        override fun areContentsTheSame(a: Product, b: Product) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val root = view
        val imgPhoto  : ImageView = view.findViewById(R.id.imgPhoto)
        val txtId     : TextView = view.findViewById(R.id.txtId)
        val txtName   : TextView = view.findViewById(R.id.txtName)
        val txtPrice  : TextView = view.findViewById(R.id.txtPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)

        holder.txtId.text   = product.id
        holder.txtName.text = product.name
        holder.txtPrice.text = String.format("%.2f", product.price)
        holder.imgPhoto.setImageBitmap(product.photo.toBitmap())

        fn(holder, product)
    }
}