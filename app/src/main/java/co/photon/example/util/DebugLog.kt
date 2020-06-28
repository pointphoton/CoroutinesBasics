package co.photon.example.util

import android.util.Log

object DebugLog {
    private var test: String? = null
    private const val MAX_INDEX = 4000
    private const val MIN_INDEX = 3000
    private var isDebuggable = false

    /**
     * Writes debugging log.
     */
    fun write() {
        //   Log.d("*** DEBUG MODE *** ", String.valueOf(isDebuggable));
        if (isDebuggable) {
            val sb = StringBuilder()
            val stackTrace = Exception().stackTrace[1]
            var fileName = stackTrace.fileName
            if (fileName == null) {
                fileName = "" // It is necessary if you want to use proguard obfuscation.
            }
            sb.append(stackTrace.methodName).append("(").append(fileName).append(":").append(stackTrace.lineNumber).append(")")
            Log.d("***", sb.toString())
        }
    }

    fun write(message: Any) {
        if (isDebuggable) {
            val sb = StringBuilder()
            val stackTrace = Exception().stackTrace[1]
            var fileName = stackTrace.fileName
            if (fileName == null) {
                fileName = "" // It is necessary if you want to use proguard obfuscation.
            }
            sb.append(stackTrace.methodName).append("(").append(fileName).append(":").append(stackTrace.lineNumber).append(")")
            val fullMessage = StringBuilder(message.toString())
            if (fullMessage.length > MAX_INDEX) {
                var theSubstring = fullMessage.substring(0, MAX_INDEX)
                var theIndex = MAX_INDEX

                // Try to find a substring break at a line end.
                // theIndex = theSubstring.lastIndexOf('\n');
                if (theIndex >= MIN_INDEX) {
                    theSubstring = theSubstring.substring(0, theIndex)
                } else {
                    theIndex = MAX_INDEX
                }
                //log the substring
                sb.append(":").append(theSubstring)
                Log.d("***", sb.toString())

                // Recursively log the remainder.
                write(fullMessage.substring(theIndex))
            } else {
                sb.append(":").append(message.toString())
                Log.d("***", sb.toString())
            }
        }
    }

    fun write(tag: String, message: Any) {
        if (isDebuggable) {
            val sb = StringBuilder()
            val stackTrace = Exception().stackTrace[1]
            var fileName = stackTrace.fileName
            if (fileName == null) {
                fileName = "" // It is necessary if you want to use proguard obfuscation.
            }
            val info = stackTrace.methodName + " (" + fileName + ":" + stackTrace.lineNumber + ")"
            sb.append(stackTrace.methodName).append("(").append(fileName).append(":").append(stackTrace.lineNumber).append(")")
                    .append(" *** ").append(message.toString())
            Log.d("_$tag", sb.toString())
        }
    }

    init {
       isDebuggable = true



    }
}