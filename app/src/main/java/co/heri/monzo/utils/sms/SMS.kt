package co.heri.monzo.utils.sms

import android.content.Context
import android.provider.Telephony
import java.util.*

object SMS {

    fun getSmsConversation(context: Context, number: String? = null, completion: (conversations: List<Conversation>?) -> Unit) {
        val cursor = context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)

        val numbers = ArrayList<String>()
        val messages = ArrayList<Message>()
        var results = ArrayList<Conversation>()

        while (cursor != null && cursor.moveToNext()) {
            val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))

            numbers.add(number)
            messages.add(Message(number, body, Date(smsDate.toLong())))
        }

        cursor?.close()

        numbers.forEach { number ->
            if (results.find { it.number == number } == null) {
                val msg = messages.filter { it.number == number }
                results.add(Conversation(number = number, message = msg))
            }
        }

        if (number != null) {
            results = results.filter { it.number == number } as ArrayList<Conversation>
        }

        completion(results)
    }
}