package pl.ozodbek.pdfinvoice

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.ozodbek.pdfinvoice.databinding.FragmentSavedContactsBinding

class SavedContactsFragment : Fragment() {
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

    private var _binding: FragmentSavedContactsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedContactsBinding.inflate(inflater, container, false)

        val contacts = contactList()

        val adapter = ContactAdapter(contacts)
        binding.recyclerview.adapter = adapter

        return binding.root
    }

    private fun contactList(): List<Contacts> {
        val countryList: MutableList<Contacts> = mutableListOf()

        countryList.apply {
            add(Contacts("+342466482361", "+342466482361"))
            add(Contacts("United Kingdom", "+3424664823644"))
            add(Contacts("Canada", "+342466482361"))
            add(Contacts("Australia", "+3424664823661"))
            add(Contacts("Germany", "+3424664823649"))
            add(Contacts("France", "+3424664823633"))
            add(Contacts("Spain", "+3424664823634"))
            add(Contacts("Italy", "+3424664823639"))
            add(Contacts("Brazil", "+3424664823655"))
            add(Contacts("India", "+3424664823691"))
            add(Contacts("China", "+3424664823686"))
            add(Contacts("Japan", "+3424664823681"))
            add(Contacts("Russia", "+342466482367"))
            add(Contacts("Mexico", "+3424664823652"))
            add(Contacts("South Korea", "+3424664823682"))
            add(Contacts("Saudi Arabia", "+34246648236966"))
            add(Contacts("Turkey", "+3424664823690"))
            add(Contacts("Netherlands", "+3424664823631"))
            add(Contacts("Sweden", "+3424664823646"))
            add(Contacts("Switzerland", "+3424664823641"))

        }
        return countryList
    }



//    private fun requestContactsPermission() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.READ_CONTACTS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.READ_CONTACTS),
//                PERMISSIONS_REQUEST_READ_CONTACTS
//            )
//        } else {
//            // Permission already granted
//            loadContacts()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                loadContacts()
//            } else {
//                // Permission denied
//                // Handle the scenario when permission is denied
//            }
//        }
//    }
//
//    @SuppressLint("Range")
//    private fun loadContacts() {
//        val contactsList = ArrayList<Contacts>()
//
//        val cursor: Cursor? = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null,
//            null,
//            null,
//            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
//        )
//
//        cursor?.let {
//            while (cursor.moveToNext()) {
//                val name =
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                val phoneNumber =
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//
//                contactsList.add(Contacts(name, phoneNumber))
//            }
//            cursor.close()
//        }
//
//        val adapter = ContactAdapter(contactsList)
//        binding.recyclerview.adapter = adapter
//    }
//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}