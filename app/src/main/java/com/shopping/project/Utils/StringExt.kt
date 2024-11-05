package com.shopping.project.Utils

import android.content.res.ColorStateList
import android.net.Uri
import android.text.Spanned
import android.text.TextUtils
import android.util.Base64
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern

fun String.addCountryCode(): String {
    if (this.startsWith("0")) this.removeRange(0,1)
    return "+62$this"
}

fun String.addZero(): String {

    if (this.length == 1) return "0$this"

    return this
}

fun String.getNameFromUrl(): String {
    val split = this.split("/")
    if (split.isNotEmpty()) return split[split.size-1]
    return this
}

fun String.toBoolean() = this.lowercase() in arrayOf("true", "1")

fun String.isNumeric() = this.all { c -> c.isDigit() }

fun String.isEmailValid(): Boolean =!TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isAlphabet(includeSpace: Boolean): Boolean {
    val regex = if (includeSpace)
        "^[a-zA-Z ]+"
    else
        "^[a-zA-Z]+"

    return if (this.isNotEmpty())
        Pattern.compile(regex).matcher(this).matches()
    else
        false
}

fun String.isNumeric(includeSpace: Boolean = false): Boolean {
    val regex = if (includeSpace)
        "^[0-9 ]+"
    else
        "^[0-9]+"

    return if (this.isNotEmpty())
        Pattern.compile(regex).matcher(this).matches()
    else
        false
}

fun String?.orNull(defaultNull: String = "", functionIfNotNull: (s: String) -> String? = { null }): String = if (this != null) {
    if (!functionIfNotNull(this).isNullOrEmpty()) functionIfNotNull(this)
        ?: defaultNull
    else this
} else defaultNull

fun String.isAlphanumeric(includeSpace: Boolean): Boolean {
    val regex = if (includeSpace)
        "^[a-zA-Z0-9 ]+"
    else
        "^[a-zA-Z0-9]+"

    return if (this.isNotEmpty())
        Pattern.compile(regex).matcher(this).matches()
    else
        false
}

fun <T> String.json2Object(objectClass: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(this, objectClass)
}

fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()

fun randomString(length: Int = 10): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
fun TextView.setTextColorRes(res: Int) {
    setTextColor(ContextCompat.getColor(context, res))
}

fun TextView.setBackgroundColorRes(res: Int) {
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, res))
}

fun String.toHtml(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

fun String.isUrl(): Boolean {
    return this.startsWith("http") || this.startsWith("https")
}

fun String.capitalize(locale: Locale = Locale.getDefault()): String {
    if (isNotEmpty()) {
        val firstChar = this[0]
        if (firstChar.isLowerCase()) {
            return buildString {
                val titleChar = firstChar.titlecaseChar()
                if (titleChar != firstChar.uppercaseChar()) {
                    append(titleChar)
                } else {
                    append(this@capitalize.substring(0, 1).uppercase(locale))
                }
                append(this@capitalize.substring(1))
            }
        }
    }
    return this
}

fun String.isValidVehicleNumber(): Boolean {
    val regex = "^[A-Za-z]{1,2}\\s?[0-9]{1,4}\\s?[A-Za-z]{1,3}\$".toRegex()
    return regex.matches(this)
}

fun String?.normalizePhoneNumber(): String? {
    val numberOnly = this?.replace(Regex("[^\\d]"), "")
    return when (numberOnly?.firstOrNull()) {
        '0' -> "62${numberOnly.drop(1)}"
        '8' -> "62${numberOnly}"
        else -> numberOnly
    }
}

fun String?.normalizePhoneNumberInternationalFormat(): String? {
    val numberOnly = this?.replace(Regex("[^\\d]"), "")
    return when (numberOnly?.firstOrNull()) {
        '0' -> "+62${numberOnly.drop(1)}"
        '8' -> "+62${numberOnly}"
        else -> numberOnly
    }
}

fun String.getExtension(): String {
    return substring(lastIndexOf(".") + 1)
}
fun String?.convertUTCDateToStringFormat(
    input: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    output: String = "dd MMM yyyy . HH.mm",
    locale: Locale = Locale.getDefault(),
    isPaymentDate: Boolean = false,
): String {
    val inputFormat = SimpleDateFormat(input, locale)
    val outputFormat = SimpleDateFormat(output, locale)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = try {
        inputFormat.parse(this ?: "")
    } catch (e: ParseException) {
        if (isPaymentDate) {
            Date().toEndPaymentExpiryDate()
        } else {
            Date()
        }
    }
    return outputFormat.format(date)
}

fun String.isDateExpired( pattern: String = "yyyy-MM-dd"): Boolean {
    // Define a date formatter based on the expected pattern
    val formatter = DateTimeFormatter.ofPattern(pattern)

    // Parse the input expiry date string
    val expiryDate = LocalDate.parse(this, formatter)

    // Get the current date
    val currentDate = LocalDate.now()

    // Check if the expiry date is before the current date
    return expiryDate.isBefore(currentDate)
}

fun String.getRupiahNumberAsLong(defaultValue: Long = 0): Long {
    return this.replace("Rp", "")
        .replace(" ", "")
        .replace(".", "")
        .toLongOrNull() ?: defaultValue
}

fun String.removeFormatCurrency(): String {
    return this.replace("Rp", "")
        .replace(" ", "")
        .replace(".", "")
}

infix fun String?.equalsIgnoreCase(anotherString: String?) = equals(anotherString, true)

infix fun String?.notEqualsIgnoreCase(anotherString: String?) = !equals(anotherString, true)

infix fun String?.isEither(others: Collection<String>) = others.any { it equalsIgnoreCase this }

fun String.formatDate(
    inputFormat: String? = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
    outputFormat: String? = "yyyy-MM-dd'T'HH:mm:ss",
): String {
    val inputDateFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputDateFormatter = SimpleDateFormat(outputFormat, Locale("id", "ID"))
    return try {
        val parsedDate = inputDateFormatter.parse(this)
        if (parsedDate != null) {
            outputDateFormatter.format(parsedDate)
        } else {
            ""
        }
    } catch (e: ParseException) {
        ""
    }
}


fun String?.convertStringDateAnotherFormat(
    defaultDate: String? = "yyyy-MM-dd",
    targetDate: String? = "dd MMMM yyyy"
) : String {
    val locale = Locale("id","ID")
    val defaultDateFormatter = SimpleDateFormat(defaultDate)
    val targetDateFormatter = SimpleDateFormat(targetDate, locale)

    return try {
        targetDateFormatter.format(defaultDateFormatter.parse(this))
    } catch (e: ParseException){
        ""
    }
}

fun String.toCalendar(
    defaultFormat: String = "dd MMMM yyyy"
): Calendar {
    return try {
        val stringFormat = SimpleDateFormat(defaultFormat, Locale.getDefault())
        val date = stringFormat.parse(this)
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.time = it
        } ?: run {
            calendar.time = Date()
        }
        return calendar
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}



fun String.formatPhoneNumber(): String {
    /* Make sure the input is correct
       expected input : 8123456789
       expected output : +62812-3456-789
     */
    try {
        val numberOnly = this.replace(Regex("[^\\d]"), "")
        val phoneNumber = when (numberOnly.firstOrNull()) {
            '0' -> "+62${numberOnly.drop(1)}"
            '8' -> "+62${numberOnly}"
            else -> numberOnly
        }
        val formattedNumber = StringBuilder()

        if (phoneNumber.startsWith("+62")) {
            formattedNumber.append(phoneNumber.substring(0, 6))
            formattedNumber.append("-")
            formattedNumber.append(phoneNumber.substring(6, 10))
            formattedNumber.append("-")
            formattedNumber.append(phoneNumber.substring(10))
        } else {
            formattedNumber.append(phoneNumber)
        }

        return formattedNumber.toString()
    } catch (e: Exception) {
        return this
    }
}

fun getExtentionUrl(url: String): String {
    val filenameArray = url.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return filenameArray[filenameArray.size - 1]
}

fun String.toIntForce(default: Int = 0) = try {
    if (this.isEmpty()) default else this.filter { isDigitsOnly() }.toInt()
} catch (e: Exception) {
    e.printStackTrace()
    default
}

fun String.ifEmptyOrZero(defaultValue: () -> String) =
    if (isEmpty() || this == "0") defaultValue() else this

fun String.getFirstNumberIndex(): Int {
    var res = -1
    run searchDigit@ {
        this.forEachIndexed  { index, c ->
            if (c.isDigit()) {
                res = index
                return@searchDigit
            }
        }
    }
    return res
}

fun String.getLastNumberIndex(): Int {
    var res = -1
    this.forEachIndexed { index, c ->
        if (c.isDigit()) {
            res = index
        }
    }
    return res
}

fun String.encodeBase64() = Base64.encode(this.toByteArray(), Base64.DEFAULT).toString(Charsets.UTF_8)

fun String.decodeBase64() = Base64.decode(this.toByteArray(), Base64.DEFAULT).toString(Charsets.UTF_8)

fun String.getQueryParametersFromUrl(): Map<String, String> {
    val uri = Uri.parse(this)
    val queryParams = mutableMapOf<String, String>()

    uri.queryParameterNames.forEach { paramName ->
        uri.getQueryParameter(paramName)?.let { paramValue ->
            queryParams[paramName] = paramValue
        }
    }

    return queryParams
}

fun String.formatLicensePlate():String {
    val result = StringBuilder()
    var prevChar: Char? = null

    for (char in this) {
        if (prevChar != null) {
            if ((prevChar.isDigit() && char.isLetter()) || (prevChar.isLetter() && char.isDigit())) {
                result.append(' ')
            }
        }
        result.append(char)
        prevChar = char
    }

    return result.toString()
}

fun Date.toEndPaymentExpiryDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    return calendar.time
}
