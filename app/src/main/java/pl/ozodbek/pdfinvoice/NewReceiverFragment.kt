package pl.ozodbek.pdfinvoice

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pl.ozodbek.pdfinvoice.databinding.FragmentNewReceiverBinding

class NewReceiverFragment : Fragment() {

    private var _binding: FragmentNewReceiverBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewReceiverBinding.inflate(inflater, container, false)

        setupEditTextListeners()

        binding.receiverFamilynameCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val colorRed = ContextCompat.getColor(requireContext(), R.color.red)

                val checkBoxDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_changer)!!
                        .mutate()
                checkBoxDrawable.setTintList(ColorStateList.valueOf(colorRed))

                binding.receiverFamilynameCheckBox.buttonDrawable = checkBoxDrawable



                binding.receiverFamilynameLayoutEt.boxBackgroundColor =
                    ContextCompat.getColor(requireContext(), R.color.emptyBoxColor)
                binding.receiverFamilynameLayoutEt.setBoxStrokeColorStateList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.color_state_selector
                    )!!
                )
                binding.receiverFamilynameLayoutEt.hintTextColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.lightMediumGray)
                binding.receiverFamilynameLayoutEt.isEnabled = false
                binding.receiverFamilynameLayoutEt.error = null
                binding.receiverFamilynameEt.text = null
            } else {
                binding.receiverFamilynameLayoutEt.boxBackgroundColor =
                    ContextCompat.getColor(requireContext(), R.color.cardViewColor)
                binding.receiverFamilynameLayoutEt.setBoxStrokeColorStateList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.color_state_selector_original
                    )!!
                )
                binding.receiverFamilynameLayoutEt.hintTextColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.blue)
                setupValidationForNonPhoneNumberInputs(
                    binding.receiverFamilynameEt,
                    binding.receiverFamilynameLayoutEt,
                    "Maydonni to'ldiring"
                )
                binding.receiverFamilynameLayoutEt.hint =
                    getString(R.string.otasining_ismi_lotin_harflari_bilan)
                binding.receiverFamilynameLayoutEt.isEnabled = true
            }
        }



        return binding.root
    }

    private fun setupEditTextListeners() {
        setupValidationForPhoneNumberInput(
            binding.receiverPhonenumberEt,
            binding.receiverPhonenumberLayoutEt,
            "Telefon raqamida raqamlarning hammasi ham ko'rsatilmagan"
        )
        setupValidationForNonPhoneNumberInputs(
            binding.receiverNameEt,
            binding.receiverNameLayoutEt,
            "Maydonni to'ldiring"
        )
        setupValidationForNonPhoneNumberInputs(
            binding.receiverSurenameEt,
            binding.receiverSurenameLayoutEt,
            "Maydonni to'ldiring"
        )
        setupValidationForNonPhoneNumberInputs(
            binding.receiverFamilynameEt,
            binding.receiverFamilynameLayoutEt,
            "Maydonni to'ldiring"
        )
    }

    private fun setupValidationForNonPhoneNumberInputs(
        textInputEditText: TextInputEditText,
        textInputLayout: TextInputLayout,
        errorDescription: String
    ) {
        var showError = false

        textInputEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = textInputEditText.text?.toString() ?: ""
                if (text.isEmpty()) {
                    textInputLayout.error = errorDescription
                    textInputLayout.errorIconDrawable = null
                    showError = true
                } else {
                    textInputLayout.error = null
                    showError = false
                }
            }
        }

        textInputEditText.doOnTextChanged { text, _, _, _ ->
            if (textInputEditText.isFocused && showError) {
                if (text!!.isEmpty()) {
                    textInputLayout.error = errorDescription
                    textInputLayout.errorIconDrawable = null
                } else {
                    showError = false
                    textInputLayout.error = null
                }
            }
        }
    }

    private fun setupValidationForPhoneNumberInput(
        textInputEditText: TextInputEditText,
        textInputLayout: TextInputLayout,
        errorDescription: String
    ) {
        var showError = false

        textInputEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = textInputEditText.text?.toString() ?: ""
                if (text.length < 9) {
                    textInputLayout.error = errorDescription
                    textInputLayout.errorIconDrawable = null
                    showError = true
                } else {
                    textInputLayout.error = null
                    showError = false
                }
            } else {
                textInputLayout.error = null
                showError = false
            }
        }

        textInputEditText.doOnTextChanged { text, _, _, _ ->
            if (textInputEditText.isFocused && showError) {
                if (text!!.length < 9) {
                    textInputLayout.error = errorDescription
                    textInputLayout.errorIconDrawable = null
                } else {
                    showError = false
                    textInputLayout.error = null
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
