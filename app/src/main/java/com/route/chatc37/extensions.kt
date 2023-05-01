package com.route.chatc37

fun String.isValidEmail():Boolean{
    if(isNullOrBlank())return false
   return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches();
}