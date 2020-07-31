package com.breakit.driver.ui.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.breakit.driver.Constants
import com.breakit.driver.R
import com.breakit.driver.databinding.OrdersItemBinding
import com.breakit.driver.model.Order

class DeliveryAdapter(
    private var orders: List<Order>,
    private val handleOnViewClick: (Int) -> Unit,
    private val handleOnPickUpClick: (Int) -> Unit,
    private val handleOnCompleteClick: (Int) -> Unit,
    private val handleOnAbortClick: (Int) -> Unit
) : RecyclerView.Adapter<DeliveryAdapter.OrderItemViewHolder>() {

    fun setOrders(ordersParam: List<Order>) {
        this.orders = ordersParam
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderItemViewHolder {
        return OrderItemViewHolder.create(parent, R.layout.orders_item)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val order = orders[position]
        holder.bindTo(
            order = order,
            handleOnViewClick = { index ->
                handleOnViewClick(index)
            },
            handleOnPickUpClick = { index ->
                handleOnPickUpClick(index)
            },
            handleOnCompleteClick = { index ->
                handleOnCompleteClick(index)
            },
            handleOnAbortClick = { index ->
                handleOnAbortClick(index)
            }
        )
    }

    class OrderItemViewHolder(private val binding: OrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(
            order: Order,
            handleOnViewClick: (Int) -> Unit,
            handleOnPickUpClick: (Int) -> Unit,
            handleOnCompleteClick: (Int) -> Unit,
            handleOnAbortClick: (Int) -> Unit
        ) {
            binding.root.visibility = View.VISIBLE
            binding.order = order
            when(order.status) {
                Constants.ORDER_STATUS_BOOKED -> binding.deliveryNote = "To be Picked Up"
                Constants.ORDER_STATUS_OUT_FOR_DELIVERY -> binding.deliveryNote = "Delivery in progress"
                Constants.ORDER_STATUS_COMPLETED -> binding.deliveryNote = "Delivered"
                Constants.ORDER_STATUS_CANCELLED -> binding.deliveryNote = "Cancelled"
                Constants.ORDER_STATUS_FAILED -> binding.deliveryNote = "Failed to deliver"
            }
            binding.viewBtn.setOnClickListener {
                handleOnViewClick(adapterPosition)
            }
            binding.pickupBtn.setOnClickListener {
                handleOnPickUpClick(adapterPosition)
            }
            binding.completeBtn.setOnClickListener {
                handleOnCompleteClick(adapterPosition)
            }
            binding.abortBtn.setOnClickListener {
                handleOnAbortClick(adapterPosition)
            }
            binding.callLl.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_VIEW)
                callIntent.data = Uri.parse("tel:" + order.customerMobile!!)
                startActivity(this.itemView.context, callIntent, null)
            }
            binding.executePendingBindings()
        }

        companion object {
            /**
             * Factory function to create [OrderItemViewHolder]
             */
            fun create(parent: ViewGroup, layoutId: Int): OrderItemViewHolder =
                OrderItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        layoutId, parent, false
                    )
                )
        }
    }
}
