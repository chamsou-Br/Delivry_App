package com.example.food_delivery.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.food_delivery.*
import com.example.food_delivery.Utils.DataType.reviewRest
import com.example.food_delivery.Utils.DataType.tokenData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.ViewModal.MenuModal
import com.example.food_delivery.databinding.FragmentDetailsBinding
import com.example.food_delivery.databinding.FragmentReviewBinding
import com.example.movieexample.viewmodel.RestaurantModal
import org.w3c.dom.Text


class reviewFragment : Fragment() {

    lateinit var binding: FragmentReviewBinding
    lateinit var restModal : RestaurantModal
    lateinit var clientModal : ClientModal
    var rating = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(layoutInflater)
        val root = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        val ratingCard: Array<CardView> = arrayOf(binding.rating1,binding.rating2,binding.rating3,binding.rating4,binding.rating5)
        val textRating: Array<TextView> = arrayOf(binding.textRating1,binding.textRating2,binding.textRating3,binding.textRating4,binding.textRating5)
        val ConstraintLayoutrating: Array<ConstraintLayout> = arrayOf(binding.ConstraintLayoutRating1,binding.ConstraintLayoutRating2,binding.ConstraintLayoutRating3,binding.ConstraintLayoutRating4,binding.ConstraintLayoutRating5)

        restModal = ViewModelProvider(requireActivity()).get(RestaurantModal::class.java)
        clientModal = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        val args = arguments;
        restModal.loadRest(args?.getString("id")!!)
        restModal.getReviewRest(args?.getString("id")!!, tokenData(clientModal.client.value?.token.toString()))

        restModal.review.observe(requireActivity()){rev ->
            binding.apply{
                if (rev != null  && rev.rest == args?.getString("id")!!) {
                    reviewInput.setText(rev.review);
                    val dark = ContextCompat.getColor(requireActivity(), R.color.dark)
                    val white = ContextCompat.getColor(requireActivity(), R.color.white)
                    ConstraintLayoutrating.forEach {
                        it.setBackgroundResource(R.drawable.rating_border_card_view)
                    }
                    textRating.forEach {
                        it.setTextColor(dark)
                    }
                    this@reviewFragment.rating = rev.rating;
                    textRating[rev.rating - 1].setTextColor(white)
                    ConstraintLayoutrating[rev.rating - 1].setBackgroundResource(R.drawable.rating_border_card_view_focus)

                }
}
        }

        restModal.restaurant.observe(requireActivity()) { rest ->
            binding.apply {
                if (isAdded) {
                    Glide.with(requireActivity())
                        .load(rest.logoUrl)
                        .into(imgRest)
                }
                nameRest.text = rest.name
            }
        }
        restModal.reviewLoading.observe(requireActivity()) { loading ->
            if (loading == true) {
                binding.progressBarReview.visibility = View.VISIBLE
                binding.sendReviewText.visibility = View.GONE
                binding.finishIcon.visibility = View.GONE
            } else {
                binding.progressBarReview.visibility = View.GONE
                binding.sendReviewText.visibility = View.VISIBLE
                binding.finishIcon.visibility = View.VISIBLE
            }
        }


        ratingCard.forEachIndexed { index, cardView ->
            cardView.setOnClickListener {
                val dark = ContextCompat.getColor(requireActivity(), R.color.dark)
                val white = ContextCompat.getColor(requireActivity(), R.color.white)
                ConstraintLayoutrating.forEach {
                    it.setBackgroundResource(R.drawable.rating_border_card_view)
                }
                textRating.forEach {
                    it.setTextColor(dark)
                }
                rating = index+1;
                textRating[index].setTextColor(white)
                ConstraintLayoutrating[index].setBackgroundResource(R.drawable.rating_border_card_view_focus)
            }
        }

        binding.sendReview.setOnClickListener {
            val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
            if (pref.contains("connected")) {
                val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
                val rev = reviewRest(pref.getString("token_food_delivry","")!!, restModal.restaurant.value?._id!!,rating,binding.reviewInput.text.toString())
                restModal.ReviewRest(rev,requireContext());
                it.findNavController().navigateUp()
            }else {
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
            }

        }

        binding.apply {
            back.setOnClickListener {
                it.findNavController().navigateUp()
            }
            fb.setOnClickListener {
                openFb(requireActivity(), restModal.restaurant.value!!.fbUrl, restModal.restaurant.value!!.fbUrl!!)
            }
            map.setOnClickListener {
                openMap(requireActivity(), restModal.restaurant.value!!.mapX!!, restModal.restaurant.value!!.mapY!!)
            }
            insta.setOnClickListener {
                openInsta(requireActivity(), restModal.restaurant.value!!.instApp!!,restModal.restaurant.value!!.instUrl!!)
            }
            phone.setOnClickListener {
                call(requireActivity(), restModal.restaurant.value!!.phone!!)
            }
            mail.setOnClickListener {
                mailTo(requireActivity(),restModal.restaurant.value!!.email!!)
            }

        }

    }
}