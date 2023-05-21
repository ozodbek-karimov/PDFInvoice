package pl.ozodbek.pdfinvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.ozodbek.pdfinvoice.databinding.ListOfCountriesRowItemBinding

class CountryAdapter(private val countryList: List<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListOfCountriesRowItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    inner class ViewHolder(private val binding: ListOfCountriesRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.countryNameTextView.text = country.name
            binding.countryCodeTextView .text = country.phoneCode
            binding.countryFlagImageView.setImageResource(country.flagResId)
        }
    }
}


