package com.example.food_delivery.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.OrderItem
import com.example.food_delivery.Utils.DataType.orderData
import com.example.food_delivery.ViewModal.BagModal
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.ViewModal.MenuModal
import com.example.food_delivery.ViewModal.OrderModal
import com.example.food_delivery.databinding.FragmentDetailMenuBinding
import com.example.food_delivery.databinding.FragmentValidateBinding


class validateFragment : Fragment() {
    lateinit var clientModal: ClientModal
    lateinit var binding: FragmentValidateBinding
    lateinit var bagModal: BagModal
    lateinit var orderModal: OrderModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentValidateBinding.inflate(layoutInflater)
        val args = arguments
        println(args?.getString("id"))
        clientModal = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        orderModal = ViewModelProvider(requireActivity()).get(OrderModal::class.java)
        bagModal = ViewModelProvider(requireActivity()).get(BagModal::class.java)
        bagModal.loadBags(requireActivity(),args?.getString("id")!!)

        bagModal.bags.observe(requireActivity()) { bags ->
            var price = 0.0;
            bagModal.bags.value?.forEach { bag ->
                price += bag.price!! * bag.qty!!
            }
            binding.subTotalValue.text = "$ " + price.toString();
            binding.priceTotalValue.text = "$ " + price.toString();
        }

        binding.apply {
            address.setText(clientModal.client.value?.address)
            phone.setText(clientModal.client.value?.phone)
            valid.setOnClickListener{
                val items : MutableList<OrderItem> = mutableListOf();
                bagModal.bags.value?.forEach { bag ->
                    val orderItem = OrderItem(bag.id, bag.qty?.toInt()!!)
                    items.add(orderItem)
                }
                val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
                val order = orderData(rest = args?.getString("id")!! , address =  address.text.toString() , note = note.text.toString() , priceTotal = "120".toFloat(),  orderItems = items , client = pref.getString("token_food_delivry",""))
                    orderModal.validateOrder(order);
            }
        }

        orderModal.isValidated.observe(requireActivity(), { validate ->
            if (validate == true) {
                Toast.makeText(requireContext(), "Order is validated :)", Toast.LENGTH_LONG).show()
                bagModal.deleteBagsOfRest(requireActivity(),args?.getString("id")!!)
               view?.findNavController()?.navigate(R.id.action_validateFragment_to_mainFragment)
            }
        })

        orderModal.loading.observe(requireActivity(),{loading ->
            if (loading == true) {
                binding.progressBarFinish.visibility = View.VISIBLE
                binding.titleFinishButton.visibility = View.GONE
                binding.finishIcon.visibility = View.GONE
            }else {
                binding.progressBarFinish.visibility = View.GONE
                binding.titleFinishButton.visibility = View.INVISIBLE
                binding.finishIcon.visibility = View.INVISIBLE
            }
        })
        orderModal.errorMessage.observe(requireActivity(), { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

}