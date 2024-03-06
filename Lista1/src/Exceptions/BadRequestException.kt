package Exceptions

class BadRequestException  (
    message:String, cause: Throwable? = null
): IllegalArgumentException(message, cause)