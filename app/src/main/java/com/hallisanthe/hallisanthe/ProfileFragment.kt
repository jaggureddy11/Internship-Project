package com.hallisanthe.hallisanthe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hallisanthe.hallisanthe.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.menuMyListings.setOnClickListener { showToast("My Listings clicked") }
        binding.menuWishlist.setOnClickListener { showToast("Wishlist clicked") }
        binding.menuMessages.setOnClickListener { showToast("Messages clicked") }
        binding.menuSettings.setOnClickListener { showToast("Settings clicked") }
        binding.menuLogout.setOnClickListener { showToast("Logging out...") }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}