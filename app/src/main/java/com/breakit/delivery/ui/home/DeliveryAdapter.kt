package com.breakit.delivery.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.breakit.delivery.Constants
import com.breakit.delivery.R
import com.breakit.delivery.databinding.OrdersItemBinding
import com.breakit.delivery.model.Order


class DeliveryAdapter(
    private var orders: List<Order>,
    private val handleOnViewClick: (Int) -> Unit,
    private val handleOnPickUpClick: (Int) -> Unit,
    private val handleOnCompleteClick: (Int) -> Unit,
    private val handleOnAbortClick: (Int, String) -> Unit
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
            handleOnAbortClick = { index, reason ->
                handleOnAbortClick(index, reason)
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
            handleOnAbortClick: (Int, String) -> Unit
        ) {
            binding.root.visibility = View.VISIBLE
            binding.order = order
            when (order.status) {
                Constants.ORDER_STATUS_BOOKED -> binding.deliveryNote = "To be Picked Up"
                Constants.ORDER_STATUS_OUT_FOR_DELIVERY -> binding.deliveryNote =
                    "Delivery in progress"
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
            binding.cancelBtn.setOnClickListener {
                showCancelOrderDialog(handleOnAbortClick)
            }
            binding.executePendingBindings()
        }

        private fun showCancelOrderDialog(handleOnAbortClick: (Int, String) -> Unit) {
            val alertDialog = AlertDialog.Builder(itemView.context);
            alertDialog.setTitle("Are you sure")
            alertDialog.setMessage("Please mention reason to to cancel this order")
            val editText = EditText(itemView.context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            editText.layoutParams = lp
            alertDialog.setView(editText)
            alertDialog.setPositiveButton("Yes") { _, _ ->
                if (editText.text.isNotBlank()) {
                    handleOnAbortClick(adapterPosition, editText.text.toString())
                } else {
                    Toast.makeText(itemView.context, "Enter reason", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
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
