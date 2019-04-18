package co.heri.monzo.utils.sms;

import java.util.*

data class MpesaMessage(
    val amount: Double,
    val datetime: Date,
    val code: String,
    val cost: Double,
    val balance: Double,
    val type: String,
    val participant: String){


    override fun toString(): String {
        return "MpesaMessage(amount=$amount, datetime=$datetime, code='$code', cost=$cost, balance=$balance, type='$type', participant='$participant')"
    }
}
