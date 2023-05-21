package pl.ozodbek.pdfinvoice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import pl.ozodbek.pdfinvoice.databinding.FragmentCardAmountBinding
import pl.ozodbek.pdfinvoice.utils.Constants
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MAXIMUM_RECEIVING_AMOUNT
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MAXIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MAXIMUM_SENDING_AMOUNT
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MAXIMUM_SENDING_AMOUNT_ERROR_MESSAGE
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MINIMUM_RECEIVING_AMOUNT
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MINIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MINIMUM_SENDING_AMOUNT
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.MINIMUM_SENDING_AMOUNT_ERROR_MESSAGE
import pl.ozodbek.pdfinvoice.utils.Constants.Companion.NO_AMOUNT_ERROR_MESSAGE

class CardAmountFragment : Fragment() {

    private var _binding: FragmentCardAmountBinding? = null
    private val binding get() = _binding!!

    private var sendingAmount: Double? = MINIMUM_SENDING_AMOUNT
    private var receivingAmount: Double? = MINIMUM_RECEIVING_AMOUNT

    private var isSendingAmountFocused = false
    private var isReceivingAmountFocused = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardAmountBinding.inflate(inflater, container, false)
        checkAmountSetup()

        binding.receiverSelectionBtn.setOnClickListener {
            navigateToDetailFragment()
        }


        return binding.root
    }

    private fun navigateToDetailFragment() {
        val sendingAmountErrorEnabled = binding.sendingAmountTextInputLayout.isErrorEnabled
        val receivingAmountErrorEnabled = binding.receivingAmountTextInputLayput.isErrorEnabled

        if (sendingAmount == null || sendingAmountErrorEnabled || receivingAmountErrorEnabled) {
            showErrorAnimation(binding.sendingAmountTextInputLayout)
            binding.sendingAmountTextInputLayout.isErrorEnabled = true
            binding.receivingAmountTextInputLayput.isErrorEnabled = true
            binding.sendingAmountTextInputLayout.error = NO_AMOUNT_ERROR_MESSAGE
            binding.receivingAmountTextInputLayput.error = null

        } else {
            findNavController().navigate(R.id.action_cardAmountFragment_to_detailFragment)

        }
    }

    private fun checkAmountSetup() {
        val groupingSeparator = " "
        val groupingSize = 3
        val decimalPattern = "(?<=\\d)(?=(\\d{${groupingSize}})+\\b)"

        binding.sendingAmountTextInputEdittext.setOnFocusChangeListener { _, hasFocus ->
            isSendingAmountFocused = hasFocus
        }

        binding.receivingAmountTextInputEdittext.setOnFocusChangeListener { _, hasFocus ->
            isReceivingAmountFocused = hasFocus
        }

        binding.sendingAmountTextInputEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val editText1 = binding.sendingAmountTextInputEdittext
                val editText2 = binding.receivingAmountTextInputEdittext
                val text = s?.toString()
                val cleanText = text?.replace(groupingSeparator, "") ?: ""
                val formattedText =
                    cleanText.replace(decimalPattern.toRegex(), groupingSeparator)

                if (text.toString() != formattedText) {
                    val selectionStart = editText1.selectionStart
                    val selectionEnd = editText1.selectionEnd

                    editText1.setText(formattedText)

                    val cleanTextLength = cleanText.length
                    val formattedTextLength = formattedText.length
                    val cursorOffset = formattedTextLength - cleanTextLength
                    val newSelectionStart =
                        (selectionStart + cursorOffset).coerceIn(0, formattedTextLength)
                    val newSelectionEnd =
                        (selectionEnd + cursorOffset).coerceIn(0, formattedTextLength)

                    editText1.setSelection(newSelectionStart, newSelectionEnd)

                    val textSize = if (formattedText.length > 8) {
                        resources.getDimension(R.dimen.small_text_size)
                    } else {
                        resources.getDimension(R.dimen.big_text_size)
                    }

                    editText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    editText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                }

                sendingAmount = formattedText.toDoubleOrNull()

                if (isSendingAmountFocused) {
                    binding.receivingAmountTextInputEdittext.text = null
                    binding.receivingAmountTextInputLayput.error = null
                    resetTextSize(binding.sendingAmountTextInputEdittext)
                    updateTextSize(binding.receivingAmountTextInputEdittext)
                    updateTextSize(binding.sendingAmountTextInputEdittext)
                }

                // Continue with the rest of the code for sending amount
                sendingAmount?.let { amount ->
                    when {
                        amount < MINIMUM_SENDING_AMOUNT -> {
                            showErrorAndSetErrorMessages(
                                binding.sendingAmountTextInputLayout,
                                binding.receivingAmountTextInputLayput,
                                MINIMUM_SENDING_AMOUNT_ERROR_MESSAGE,
                                null
                            )
                        }

                        amount > MAXIMUM_SENDING_AMOUNT -> {
                            showErrorAndSetErrorMessages(
                                binding.sendingAmountTextInputLayout,
                                binding.receivingAmountTextInputLayput,
                                MAXIMUM_SENDING_AMOUNT_ERROR_MESSAGE,
                                null
                            )
                        }

                        else -> {
                            clearErrors(
                                binding.sendingAmountTextInputLayout,
                                binding.receivingAmountTextInputLayput
                            )
                        }
                    }
                }
            }
        })

        binding.receivingAmountTextInputEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val editText1 = binding.sendingAmountTextInputEdittext
                val editText2 = binding.receivingAmountTextInputEdittext
                val text = s?.toString()
                val cleanText = text?.replace(groupingSeparator, "") ?: ""
                val formattedText =
                    cleanText.replace(decimalPattern.toRegex(), groupingSeparator)

                if (text.toString() != formattedText) {
                    val selectionStart = editText2.selectionStart
                    val selectionEnd = editText2.selectionEnd

                    editText2.setText(formattedText)

                    val cleanTextLength = cleanText.length
                    val formattedTextLength = formattedText.length
                    val cursorOffset = formattedTextLength - cleanTextLength
                    val newSelectionStart =
                        (selectionStart + cursorOffset).coerceIn(0, formattedTextLength)
                    val newSelectionEnd =
                        (selectionEnd + cursorOffset).coerceIn(0, formattedTextLength)

                    editText2.setSelection(newSelectionStart, newSelectionEnd)

                    val textSize = if (formattedText.length > 8) {
                        resources.getDimension(R.dimen.small_text_size)
                    } else {
                        resources.getDimension(R.dimen.big_text_size)
                    }

                    editText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    editText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                }

                receivingAmount = formattedText.toDoubleOrNull()

                if (isReceivingAmountFocused) {
                    binding.sendingAmountTextInputEdittext.text = null
                    binding.sendingAmountTextInputLayout.error = null
                    resetTextSize(binding.receivingAmountTextInputEdittext)
                    updateTextSize(binding.sendingAmountTextInputEdittext)
                    updateTextSize(binding.receivingAmountTextInputEdittext)
                }

                // Continue with the rest of the code for receiving amount
                receivingAmount?.let { amount ->
                    when {
                        amount < MINIMUM_RECEIVING_AMOUNT -> {
                            showErrorAndSetErrorMessages(
                                binding.receivingAmountTextInputLayput,
                                binding.sendingAmountTextInputLayout,
                                MINIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE,
                                null
                            )
                        }

                        amount > MAXIMUM_RECEIVING_AMOUNT -> {
                            showErrorAndSetErrorMessages(
                                binding.receivingAmountTextInputLayput,
                                binding.sendingAmountTextInputLayout,
                                MAXIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE,
                                null
                            )
                        }

                        else -> {
                            clearErrors(
                                binding.receivingAmountTextInputLayput,
                                binding.sendingAmountTextInputLayout
                            )
                        }
                    }
                }
            }
        })
    }

    private fun updateTextSize(editText: EditText) {
        val textSize = if ((editText.text?.length ?: 0) > 8) {
            resources.getDimension(R.dimen.small_text_size)
        } else {
            resources.getDimension(R.dimen.big_text_size)
        }
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    private fun resetTextSize(editText: EditText) {
        val textSize = resources.getDimension(R.dimen.big_text_size)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }


    private fun showErrorAndSetErrorMessages(
        errorLayoutToShow: TextInputLayout,
        errorLayoutToHide: TextInputLayout,
        errorMessageToShow: String,
        errorMessageToHide: String?
    ) {
        errorLayoutToShow.isErrorEnabled = true
        errorLayoutToHide.isErrorEnabled = true
        errorLayoutToShow.error = errorMessageToShow
        errorLayoutToHide.error = errorMessageToHide
    }

    private fun clearErrors(errorLayout1: TextInputLayout, errorLayout2: TextInputLayout) {
        errorLayout1.isErrorEnabled = false
        errorLayout2.isErrorEnabled = false
        errorLayout1.error = null
        errorLayout2.error = null
    }


    private fun showErrorAnimation(textInputLayout: TextInputLayout) {
        val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
        textInputLayout.startAnimation(shake)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}