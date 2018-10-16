package com.example.mayank.kwizzapp.wallet

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mayank.kwizzapp.KwizzApp

import com.example.mayank.kwizzapp.R
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectFragmentComponent
import com.example.mayank.kwizzapp.helpers.processRequest
import com.example.mayank.kwizzapp.network.ITransaction
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.wallet_menu_layout.*
import net.rmitsolutions.mfexpert.lms.helpers.SharedPrefKeys
import net.rmitsolutions.mfexpert.lms.helpers.getPref
import net.rmitsolutions.mfexpert.lms.helpers.showDialog
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragment
import org.jetbrains.anko.find
import javax.inject.Inject

class WalletMenuFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var transactionService: ITransaction
    private lateinit var compositeDisposable: CompositeDisposable
    private var listener: OnFragmentInteractionListener? = null
    private val CLICKABLES = intArrayOf(R.id.buttonAddPoints, R.id.buttonWithdrawalPoints, R.id.buttonTransferPoints, R.id.buttonTransactions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        val depComponent = DaggerInjectFragmentComponent.builder()
                .applicationComponent(KwizzApp.applicationComponent)
                .build()
        depComponent.injectWalletMenuFragment(this)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wallet_menu, container, false)
        // Getting balance from server
        checkBalance()

        for (id in CLICKABLES){
            view.find<Button>(id).setOnClickListener(this)
        }
        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonAddPoints ->{
                val addPointsFragment = AddPointsFragment()
                switchToFragment(addPointsFragment)
            }

            R.id.buttonWithdrawalPoints ->{
                val withdrawalPointsFragment = WithdrawalPointsFragment()
                switchToFragment(withdrawalPointsFragment)
            }

            R.id.buttonTransferPoints ->{
                val transferPointsFragment = TransferPointsFragment()
                switchToFragment(transferPointsFragment)
            }

            R.id.buttonTransactions ->{
                showDialog(activity!!, "Transactions", "Coming soon !")
            }
        }
    }

    private fun checkBalance(){
        val mobileNumber = activity?.getPref(SharedPrefKeys.MOBILE_NUMBER, "")
        if (mobileNumber!=""){
            compositeDisposable.add(transactionService.checkBalance(mobileNumber!!)
                    .processRequest(
                            { response ->
                                if (response.isSuccess){
                                    balanceTextView.text ="${activity?.getString(R.string.rupeeSymbol)} - ${response.balance}"
                                }
                            },
                            { err->
                                showDialog(activity!!, "Error", err.toString())
                            }
                    ))
        }else {
            balanceTextView.visibility = View.GONE
        }

    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is OnFragmentInteractionListener -> listener = context
            else -> throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
    }
}