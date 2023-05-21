package pl.ozodbek.pdfinvoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.ozodbek.pdfinvoice.databinding.FragmentCountriesBinding


class CountriesFragment : Fragment() {

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)

        val countries = fetchCountryList()

        val adapter = CountryAdapter(countries)
        binding.countryRecyclerView.adapter = adapter


        return binding.root
    }

    private fun fetchCountryList(): List<Country> {
        val countryList: MutableList<Country> = mutableListOf()

       countryList.apply {
           add(Country("United States", "+1", R.drawable.uzbekistan))
           add(Country("United Kingdom", "+44", R.drawable.uzbekistan))
           add(Country("Canada", "+1", R.drawable.uzbekistan))
           add(Country("Australia", "+61", R.drawable.uzbekistan))
           add(Country("Germany", "+49", R.drawable.uzbekistan))
           add(Country("France", "+33", R.drawable.uzbekistan))
           add(Country("Spain", "+34", R.drawable.uzbekistan))
           add(Country("Italy", "+39", R.drawable.uzbekistan))
           add(Country("Brazil", "+55", R.drawable.uzbekistan))
           add(Country("India", "+91", R.drawable.uzbekistan))
           add(Country("China", "+86", R.drawable.uzbekistan))
           add(Country("Japan", "+81", R.drawable.uzbekistan))
           add(Country("Russia", "+7", R.drawable.uzbekistan))
           add(Country("Mexico", "+52", R.drawable.uzbekistan))
           add(Country("South Korea", "+82", R.drawable.uzbekistan))
           add(Country("Saudi Arabia", "+966", R.drawable.uzbekistan))
           add(Country("Turkey", "+90", R.drawable.uzbekistan))
           add(Country("Netherlands", "+31", R.drawable.uzbekistan))
           add(Country("Sweden", "+46", R.drawable.uzbekistan))
           add(Country("Switzerland", "+41", R.drawable.uzbekistan))

       }
        return countryList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}