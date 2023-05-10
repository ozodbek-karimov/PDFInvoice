package pl.ozodbek.pdfinvoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.ozodbek.pdfinvoice.databinding.FragmentCardAmountBinding

class CardAmountFragment : Fragment() {

    private var _binding: FragmentCardAmountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardAmountBinding.inflate(inflater, container, false)


        binding.receiverSelectionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cardAmountFragment_to_detailFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}