package co.heri.monzo.utils.sms

import android.annotation.SuppressLint
import java.util.regex.Pattern
import java.text.SimpleDateFormat
import java.util.*



/**
 * 24 Jan, 2018
 * Parses MPESA SMS String through simple String manipulation. And a tiny bit of regex :-D
 */
class MpesaParser {

    fun parse(message: String): MpesaMessage {
        val exploded = message.toLowerCase().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()



        //Get Transaction code
        val code = exploded[0].trim { it <= ' ' }.toUpperCase().take(10)

        //Get transaction type
        val transactionType = getTransactionType(message.toLowerCase())

        //Get sender/recipient
        val participant = getParticipant(transactionType, message.toLowerCase())

        //Get mpesa amount
        var amount = 0.0
        var balance = 0.0
        var transactionCost = 0.0

        var moneyCount = 0
        for (str in exploded) {
            if (str.startsWith("ksh")) {
                val money = str.replace("ksh", "")
                if (moneyCount == 0) {
                    amount = money.trim { it <= ' ' }.toDouble()
                } else if (moneyCount == 1) {
                    balance = money.substring(0, money.length - 1).trim { it <= ' ' }.toDouble()
                } else if (moneyCount == 2) {
                    transactionCost = money.substring(0, money.length - 1).trim { it <= ' ' }.toDouble()
                }
                moneyCount++
            }
        }

        val parsed = MpesaMessage(
            type = transactionType,
            code = code,
            amount = amount,
            balance = balance,
            cost = transactionCost,
            participant = participant.trim { it <= ' ' },
            datetime = getDate(exploded)
        )


        return parsed
    }

    private fun getTransactionType(message: String): String {
        var transactionType = "unknown"
        if (message.toLowerCase().contains("you have received")) {
            transactionType = "received"
        } else if (message.toLowerCase().contains("sent to")) {
            transactionType = "sent"
        } else if (message.toLowerCase().contains("withdraw")) {
            transactionType = "withdraw"
        } else if (message.toLowerCase().contains("paid to")) {
            transactionType = "paybill"
        } else if (message.toLowerCase().contains("you bought")) {
            transactionType = "airtime"
        }
        return transactionType.trim { it <= ' ' }
    }

    private fun getParticipant(transactionType: String, msg: String): String {
        if (transactionType == "received") {
            val findSubject = Pattern.compile("from(.*)on [1-9]")
            val matcher = findSubject.matcher(msg)
            while (matcher.find()) {
                return matcher.group(1)
            }
        } else if (transactionType == "withdraw") {
            val findSubject = Pattern.compile("from(.*)new m-pesa")
            val matcher = findSubject.matcher(msg)
            while (matcher.find()) {
                return matcher.group(1)
            }
        } else if (transactionType == "sent" || transactionType == "paybill") {
            val findSubject = Pattern.compile("to(.*)on [1-9]")
            val matcher = findSubject.matcher(msg)
            while (matcher.find()) {
                return matcher.group(1)
            }
        }
        return ""
    }



    @SuppressLint("SimpleDateFormat")
    fun getDate(exploded: Array<String>): Date {



        val date_raw = exploded[exploded.indexOf("on") + 1]//.replace('/', '-')


        val position = exploded.indexOf("at")

        val time = exploded[position + 1]//.split("/")


        val period = exploded[position + 2].take(2)

        val str_date = "$date_raw $time $period"

        val dateFormat = SimpleDateFormat("dd/MM/YY hh:mm aa")
        val ts = dateFormat.parse(str_date);

        return ts
    }
}