package com.example.food_delivery.Fragments

import com.example.food_delivery.Adapters.adapterMenus
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.databinding.FragmentDetailsBinding


class menuFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val args = arguments;
        binding.recyclerView.adapter = adapterMenus(requireActivity(),loadData(args?.getInt("rest")!!))
        val root = binding.root
        return root
    }

    fun loadData(rest : Int):List<MenusData> {
        val data = mutableListOf<MenusData>()
        val menu1 = MenusData(rest,
            "Tacos $rest","https://th.bing.com/th/id/OIP.jQSXXB90bSSpR-4yenJnZgHaE8?w=247&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7","description of menu","20.00".toFloat())
        val menu2 = MenusData(rest ,
            "Pizza $rest","https://th.bing.com/th/id/OIP.B0j8_LEhso7zqJFBU8N8lAHaJQ?w=154&h=191&c=7&r=0&o=5&dpr=1.1&pid=1.7","description of menu","20.00".toFloat())
        val menu3 = MenusData(rest,
            "Burger $rest","https://th.bing.com/th/id/OIP.KGfic2kvFnf5skbqNJPe5AHaE8?w=281&h=187&c=7&r=0&o=5&dpr=1.1&pid=1.7","description of menu","20.00".toFloat())
        val menu4 = MenusData(rest,
            "Sardine $rest","https://th.bing.com/th/id/OIP.o5XlxrIzHvNheFjA-soMlwHaE6?w=298&h=198&c=7&r=0&o=5&dpr=1.1&pid=1.7","description of menu","20.00".toFloat())
        val menu5 = MenusData(rest,
            "Couscous $rest","https://th.bing.com/th/id/OIP.679y5k1YMt9f5DoNljWmnAHaFj?w=257&h=193&c=7&r=0&o=5&dpr=1.1&pid=1.7","description of menu","20.00".toFloat())


        data.add(menu1)
        data.add(menu2)
        data.add(menu3)
        data.add(menu4)
        data.add(menu5)


        return data
    }


}