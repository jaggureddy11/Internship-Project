package com.hallisanthe.hallisanthe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hallisanthe.hallisanthe.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        val categories = listOf(
            Category("Pottery", "🏺"),
            Category("Textiles", "🧵"),
            Category("Woodwork", "🪵"),
            Category("Organic", "🌿"),
            Category("Metalwork", "⚒️"),
            Category("Jewelry", "📿"),
            Category("Paintings", "🎨"),
            Category("Basketry", "🧺")
        )

        binding.rvCategories.adapter = CategoryAdapter(categories) { category ->
            val bundle = Bundle().apply {
                putString("selected_category", category.name)
            }
            val homeFragment = HomeFragment().apply {
                arguments = bundle
            }
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, homeFragment)
                .addToBackStack(null)
                .commit()
            
            // Sync bottom nav selection (Home is index 0 usually)
            (activity as? MainActivity)?.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)?.selectedItemId = R.id.nav_home
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}