package pl.ozodbek.pdfinvoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.ozodbek.pdfinvoice.databinding.FragmentMainBinding
import pl.ozodbek.pdfinvoice.databinding.FragmentReceiverdataBinding

class ReceiverDataFragment : Fragment() {

    private var _binding: FragmentReceiverdataBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiverdataBinding.inflate(inflater, container, false)


        binding.newReceiverButton.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_newReceiverFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}