package com.example.mayank.kwizzapp.network

import com.example.mayank.googleplaygame.network.wallet.Transactions
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import io.reactivex.Observable
import retrofit2.Call

interface ITransaction {

    @FormUrlEncoded
    @POST("payu/checkBalance.php")
    fun checkBalance(@Field("mobileNumber") mobileNumber: String): Observable<Transactions.CheckBalance>

    @FormUrlEncoded
    @POST("payu/subtractBalance.php")
    fun subtractResultBalance(
            @Field("displayName") displayName: String,
            @Field("amount") amount: Double,
            @Field("timeStamp") timeStamp: String): Observable<Transactions.GetBalance>

    @FormUrlEncoded
    @POST("payu/updateResultBalance.php")
    fun updateResultBalance(
            @Field("displayName") displayName: String,
            @Field("amount") amount: Double,
            @Field("timeStamp") timeStamp: String): Observable<Transactions.GetBalance>

    @FormUrlEncoded
    @POST("payu/addPayment.php")
    fun addTransactionDetails(
            @Field("firstName") firstName: String,
            @Field("lastName") lastName: String,
            @Field("playGameName") playGameName: String,
            @Field("mobileNumber") mobileNumber: String,
            @Field("transferTo") transferTo: String,
            @Field("receivedFrom") receivedFrom: String,
            @Field("email") email: String,
            @Field("productInfo") productInfo: String,
            @Field("amount") amount: String,
            @Field("txnId") txnId: String,
            @Field("paymentId") paymentId: String,
            @Field("addedOn") addedOn: String,
            @Field("createdOn") createdOn: String,
            @Field("bankRefNumber") bankRefNumber: String,
            @Field("bankCode") bankCode: String,
            @Field("transactionType") transactionType: String,
            @Field("status") status: String): Observable<Transactions.GetBalance>
}