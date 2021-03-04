package com.tagava.ui.transaction

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tagava.R
import com.tagava.databinding.TransactionDialogFragmentBinding
import com.tagava.ui.customer_dashboard.CustomerDashboardViewModel
import com.tagava.util.CustomeProgressDialog

class TransactionDialogFragment : DialogFragment() {


    private var transactionViewLayout: ConstraintLayout? = null
    private var otpViewLayout: ConstraintLayout? = null
    var otpTextView: OtpTextView? = null
    private lateinit var transactionDialogViewModel: TransactionDialogViewModel

    private var binding: TransactionDialogFragmentBinding? = null
    var custId: String? = null
    var type: String? = null
    var custName: String? = null
    var customeProgressDialog: CustomeProgressDialog? = null
    override fun onResume() {
        super.onResume()
        this.transactionDialogViewModel.CustomerPaymentStatus.value = false
        this.transactionDialogViewModel.RatingStatus.value = false
        this.transactionDialogViewModel.CustomerPaymentOTPGotStatus.value = false

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.transaction_dialog_fragment, container, false
        )
        transactionViewLayout = binding?.root?.findViewById(R.id.transaction_view_layout)
        otpViewLayout = binding?.root?.findViewById(R.id.otp_view_layout)
        customeProgressDialog = CustomeProgressDialog(requireContext())

        custId = arguments?.getString("custid")
        type = arguments?.getString("type")
        custName = arguments?.getString("custName")

        otpTextView = binding?.root?.findViewById(R.id.otp_view)


        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                otpTextView?.resetState()
            }

            override fun onOTPComplete(otp: String) {
                //  Toast.makeText(this@OTPActivity, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
                transactionDialogViewModel.otpText?.set(otp)
            }
        }

        initViewModel()
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    fun initViewModel() {

        var transactionDialogViewModelFactory = TransactionDialogViewModelFactory()
        transactionDialogViewModel =
            activity?.let {
                ViewModelProviders.of(it, transactionDialogViewModelFactory)
                    .get(TransactionDialogViewModel::class.java)
            }!!
        binding?.viewmodelTransaction = transactionDialogViewModel

        transactionDialogViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        transactionDialogViewModel.customerId?.set(custId)
        transactionDialogViewModel.giveorgot?.set(type)

        transactionDialogViewModel.amount?.set("")


        transactionDialogViewModel.CustomerPaymentStatus.observe(viewLifecycleOwner, Observer {
            if (it) {
                    val bundle = bundleOf(
                            Pair("custid", custId),
                            Pair("custName", custName)
                    )


                    showDialog()
                }

        })

        transactionDialogViewModel.RatingStatus.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.transactionDialogViewModel.CustomerPaymentStatus.value = false
                this.transactionDialogViewModel.RatingStatus.value = false
                this.transactionDialogViewModel.CustomerPaymentOTPGotStatus.value = false
                CustomerDashboardViewModel?.isTransactionPopupCalled?.value = false

                findNavController().popBackStack()

            }
        })

        this.transactionDialogViewModel.errorData.observe(viewLifecycleOwner, Observer {

            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

        })

        this.transactionDialogViewModel.CustomerPaymentOTPGotStatus.observe(viewLifecycleOwner,
                Observer {
                    if (it) {
                        transactionViewLayout?.visibility = View.GONE
                        otpViewLayout?.visibility = View.VISIBLE
                        otpTextView?.requestFocusOTP()
                    }
                })
    }


    private fun showDialog() {
        val dialog = Dialog(activity)
        dialog.setTitle("Rating")
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.rating_dialog_logout)

        var submitBtn: Button = dialog.findViewById(R.id.submit_button)
        var ratingString: RatingBar = dialog.findViewById(R.id.rating_transaction_view)
        submitBtn.setOnClickListener {
            var value = ratingString.rating.toInt()
            transactionDialogViewModel.CustomerPaymentStatus.value = false
            transactionDialogViewModel.giveRatings(value.toString())
            dialog.dismiss()
            // findNavController().popBackStack()
        }
        dialog.show()

    }


}