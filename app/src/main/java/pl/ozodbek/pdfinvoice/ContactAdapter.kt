package pl.ozodbek.pdfinvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.ozodbek.pdfinvoice.databinding.ListOfContactsRowItemBinding
import pl.ozodbek.pdfinvoice.databinding.ListOfCountriesRowItemBinding

class ContactAdapter(private val contactsList: List<Contacts>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListOfContactsRowItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = contactsList[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ViewHolder(private val binding: ListOfContactsRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Contacts) {
            binding.contactName.text = country.name
            binding.contactNumber.text = country.phoneNumber
            binding.shortText.text = country.name.first().uppercaseChar().toString()
        }
    }
}


