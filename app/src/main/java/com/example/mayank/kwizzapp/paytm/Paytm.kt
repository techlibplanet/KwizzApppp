package com.example.mayank.kwizzapp.paytm

class Paytm {

    // Transfer points to Users Body params
    class TransferPointsToUser{
        var request : List<RequestTransferPointsToUser>? = null
        var metadata : String? = null
        var ipAddress : String? = null
        var platformName :String? = null
        var operationType: String? = null
    }

    class RequestTransferPointsToUser{
        var requestType : String? = null
        var merchantGuid : String? = null
        var merchantOrderId : String? =null
        var salesWalletName : String? = null
        var salesWalletGuid: String? = null
        var payeeEmailId: String? = null
        var payeePhoneNumber: String? = null
        var payeeSsold:  String? = null
        var appliedToNewUsers : String? = null
        var amount : String? = null
        var currencyCode: String? = null
    }

    // Response from transfer points
    class TransferPointsResponse(val type : String,val requestGuid : String? = null, val orderId : String,val status : String,
    val statusCode: String, val statusMessage:String,val metaData : String, val response : List<ResponseTransfer>)

    class ResponseTransfer(val walletSysTransactionId : String)

    // Check status of paytm merchant to user transaction body params
    class CheckStatus{
        var request : List<RequestCheckStatus>? = null
        var platformName : String? = null
        var operationType: String? = null

    }

    class RequestCheckStatus{
        var requestType : String? = null
        var txnType : String? = null
        var txnId: Int? = null
        var merchantGuid: String? = null
    }

    class ResponseCheckStatus(val type : String, val requestGuid : String,val orderId : String,val status : String, val statusCode: String,
                              val statusMessage : String,val metadata : String, val response : List<ResponseStatus>)

    class ResponseStatus(val txnList : ArrayList<PaytmTransactions>)

    class PaytmTransactions(val txnGuid : String,val txnAmount : Double, val status : Int, val message : String,val txnErrorCode : String,
                            val ssold : String, val txnType : String, val merchantOrderId : String, val pgTxnId : String,
                            val pgRefundId : String, val cashbackTxnId : String, val isLimitPending : Boolean)


}