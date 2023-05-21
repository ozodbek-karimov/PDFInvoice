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
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pl.ozodbek.pdfinvoice.databinding.FragmentNewReceiverBinding

class NewReceiverFragment : Fragment() {

    private var _binding: FragmentNewReceiverBinding? = null
    private val binding get() = _binding!!

    private var lastFocusedIndex = 0

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewReceiverBinding.inflate(inflater, container, false)

        setupEditTextListeners()
        checkBoxSetup()

        binding.phoneNumberPicker.setOnClickListener {
            findNavController().navigate(R.id.action_newReceiverFragment_to_countriesFragment)
        }

        binding.savedContacts.setOnClickListener {
            findNavController().navigate(R.id.action_newReceiverFragment_to_savedContactsFragment)
        }

        binding.receiverCard.setOnClickListener {
            findNavController().navigate(R.id.action_newReceiverFragment_to_receiverCardPeakerFragment)
        }

        return binding.root
    }

    private fun checkBoxSetup() {
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
                setupValidationForOtherInputs(
                    binding.receiverFamilynameEt,
                    binding.receiverFamilynameLayoutEt,
                    "Maydonni to'ldiring",
                    2
                )
                binding.receiverFamilynameLayoutEt.hint =
                    getString(R.string.otasining_ismi_lotin_harflari_bilan)
                binding.receiverFamilynameLayoutEt.isEnabled = true
            }
        }
    }

    private fun setupEditTextListeners() {

        // Set focus on the TextInputLayout when the fragment is shown

        setupValidationForPhoneNumberInput(
            binding.receiverPhonenumberEt,
            binding.receiverPhonenumberLayoutEt,
            "Telefon raqamida raqamlarning hammasi ham ko'rsatilmagan",
            0
        )
        setupValidationForOtherInputs(
            binding.maskedEditTextCardPeaker,
            binding.receiverNameLayoutEt,
            "Maydonni to'ldiring",
            1
        )
        setupValidationForOtherInputs(
            binding.receiverSurenameEt,
            binding.receiverSurenameLayoutEt,
            "Maydonni to'ldiring",
            2
        )
        setupValidationForOtherInputs(
            binding.receiverFamilynameEt,
            binding.receiverFamilynameLayoutEt,
            "Maydonni to'ldiring",
            3
        )
    }

    private fun showErrorAnimation(textInputLayout: TextInputLayout) {
        YoYo.with(Techniques.Shake)
            .duration(400)
            .repeat(0)
            .playOn(textInputLayout)
    }

    private fun setupValidationForOtherInputs(
        textInputEditText: TextInputEditText,
        textInputLayout: TextInputLayout,
        errorDescription: String,
        focusIndex: Int

    ) {
        var showError = false

        textInputEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                saveLastFocusedIndex(focusIndex)
                val text = textInputEditText.text?.toString() ?: ""
                if (text.isEmpty()) {
                    textInputLayout.error = errorDescription
                    showErrorAnimation(textInputLayout)
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
                    showErrorAnimation(textInputLayout)
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
        errorDescription: String,
        focusIndex: Int
    ) {
        var showError = false
        textInputEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                saveLastFocusedIndex(focusIndex)
                val text = textInputEditText.text?.toString() ?: ""
                if (text.isEmpty() && text.length < 9) {
                    textInputLayout.error = errorDescription
                    showError = true
                    showErrorAnimation(textInputLayout)
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
                } else {
                    showError = false
                    textInputLayout.error = null
                }
            }
        }
    }

    private fun setFocusOnEditText(lastFocusedIndex: Int) {
        when (lastFocusedIndex) {
            0 -> binding.receiverPhonenumberEt.requestFocus()
            1 -> binding.maskedEditTextCardPeaker.requestFocus()
            2 -> binding.receiverSurenameEt.requestFocus()
            3 -> binding.receiverFamilynameEt.requestFocus()
        }
    }
    private fun saveLastFocusedIndex(index: Int) {
        lastFocusedIndex = index
    }

    override fun onResume() {
        super.onResume()
        setFocusOnEditText(lastFocusedIndex)
    }
}