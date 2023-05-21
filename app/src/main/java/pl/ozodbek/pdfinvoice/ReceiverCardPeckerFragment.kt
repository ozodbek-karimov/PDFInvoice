package pl.ozodbek.pdfinvoice

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import pl.ozodbek.pdfinvoice.databinding.FragmentReceiverCardPeakerBinding

class ReceiverCardPeckerFragment : Fragment() {

    private var _binding: FragmentReceiverCardPeakerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiverCardPeakerBinding.inflate(inflater, container, false)

        binding.readyButton.setOnClickListener {
            performCardValidation()
        }

        return binding.root
    }


    private fun performCardValidation() {


        val cardNumber = binding.maskedEditTextCardPeaker.unMaskedText
        val enoughNumberError = "Karta raqami 16 ta raqamdan kam bo'lmasligi \nkerak"
        val noNumberError = "Maydonni to'ldirish shart"
        val notFoundNumberError = "Karta raqami noto'g'ri"
        val expectedCard = "0000000000000000"

        // Clear the error before performing validation
        binding.textInputCardPeakerLayout.error = null

        when {
            cardNumber!!.isEmpty() -> {
                showErrorAndRequestFocus(
                    binding.textInputCardPeakerLayout,
                    noNumberError
                )
                showErrorViewToUser()
            }

            cardNumber.length < 16 -> {
                showErrorAndRequestFocus(
                    binding.textInputCardPeakerLayout,
                    enoughNumberError
                )

                binding.centerReadyText.visibility = View.GONE
                binding.centerProgressBar.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.centerReadyText.visibility = View.VISIBLE
                    binding.centerProgressBar.visibility = View.GONE
                }, 500)

            }

            cardNumber == expectedCard -> {
                performExpectedCardAction()
                binding.centerReadyText.visibility = View.GONE
                binding.leftProgressBar.visibility = View.VISIBLE
                binding.rightReadyTextview.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.leftProgressBar.visibility = View.GONE
                    binding.rightReadyTextview.visibility = View.GONE
                    binding.centerReadyText.visibility = View.VISIBLE
                }, 500)

            }


            else -> showErrorAndRequestFocus(binding.textInputCardPeakerLayout, notFoundNumberError)
        }
    }

    private fun showErrorViewToUser() {

        binding.centerReadyText.visibility = View.GONE
        binding.centerProgressBar.visibility = View.VISIBLE

        binding.maskedEditTextCardPeaker.doOnTextChanged { _, _, _, _ ->
            binding.textInputCardPeakerLayout.error = null
        }

        binding.textInputCardPeakerLayout.isEnabled = false

        val grayColor = ContextCompat.getColor(requireContext(), R.color.emptyBoxColor)
        binding.cardImageView.imageTintList = ColorStateList.valueOf(grayColor)


        binding.textInputCardPeakerLayout.boxBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.emptyBoxColor)
        binding.textInputCardPeakerLayout.setBoxStrokeColorStateList(
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.color_state_selector
            )!!
        )
        binding.textInputCardPeakerLayout.hintTextColor =
            ContextCompat.getColorStateList(requireContext(), R.color.lightMediumGray)

        binding.textInputCardPeakerLayout.setErrorTextColor(
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.light_red
            )!!
        )

        Handler(Looper.getMainLooper()).postDelayed({

            binding.centerReadyText.visibility = View.VISIBLE
            binding.centerProgressBar.visibility = View.GONE

            binding.textInputCardPeakerLayout.isEnabled = true

            val white = ContextCompat.getColor(requireContext(), R.color.white)
            binding.cardImageView.imageTintList = ColorStateList.valueOf(white)

            binding.textInputCardPeakerLayout.boxBackgroundColor =
                ContextCompat.getColor(requireContext(), R.color.cardViewColor)

            binding.textInputCardPeakerLayout.setBoxStrokeColorStateList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.color_state_selector_original
                )!!
            )

            binding.textInputCardPeakerLayout.hintTextColor =
                ContextCompat.getColorStateList(requireContext(), R.color.blue)

            binding.textInputCardPeakerLayout.requestFocus()


        }, 500)

    }


    private fun showErrorAndRequestFocus(
        textInputLayout: TextInputLayout,
        errorDescription: String
    ) {
        textInputLayout.requestFocus()
        textInputLayout.error = errorDescription
        showErrorAnimation(textInputLayout)
    }

    private fun performExpectedCardAction() {
//        Snackbar.make(
//            requireView(),
//            binding.maskedEditTextCardPeaker.text.toString(),
//            Snackbar.LENGTH_LONG
//        ).show()
    }

    private fun showErrorAnimation(textInputLayout: TextInputLayout) {
        YoYo.with(Techniques.Shake)
            .duration(400)
            .repeat(0)
            .playOn(textInputLayout)
    }

    override fun onResume() {
        super.onResume()
        binding.textInputCardPeakerLayout.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
