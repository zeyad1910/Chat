package com.route.chatc37.ui.register

import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import com.route.chatc37.UserProvider
import com.route.chatc37.base.BaseViewModel
import com.route.chatc37.database.FireStoreUtils
import com.route.chatc37.database.models.User
import com.route.chatc37.isValidEmail

class RegisterViewModel :BaseViewModel<RegisterNavigator> (){

    val userName = ObservableField<String>()
    val userNameError = ObservableField<String?>()
    val email = ObservableField<String>()
    val emailError = ObservableField<String?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String?>()
    val passwordConfirm = ObservableField<String>()
    val passwordConfirmError = ObservableField<String?>()

    val auth = FirebaseAuth.getInstance()
    fun register(){
        if(!validForm())return;
        navigator?.showLoading("loading...")
        auth.createUserWithEmailAndPassword(
            email.get()!!,
            password.get()!!)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    insertUserToDataBase(task.result.user?.uid!!);
                }else {
                    navigator?.hideDialoge()
                    navigator?.showMessage(task.exception?.localizedMessage?:"")
                }
            }

    }
    fun insertUserToDataBase(uid:String){
        val user = User(
            id = uid,
            userName = userName.get(),
            email = email.get()
        )
        FireStoreUtils()
            .insertUserToDatabase(user)
            .addOnCompleteListener{task->
                navigator?.hideDialoge()
                if(task.isSuccessful){
                   // navigator?.showMessage("successful registration")
                    UserProvider.user = user
                    navigator?.gotoHome()
                }else {
                    navigator?.showMessage(task.exception?.localizedMessage?:"")
                }
            }
    }
    fun validForm():Boolean{
        var isValid = true;
        if(userName.get().isNullOrBlank()){
            userNameError.set("Please enter your name")
            isValid =false
        }else {
            isValid = true;
            userNameError.set(null)
        }
        if(email.get().isNullOrBlank()) {
            emailError.set("Please enter your email")
            isValid = false;
        }else if(email.get()?.isValidEmail() == false){
            emailError.set("Please enter a valid email")
            isValid = false;

        }else {
            isValid=true;
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            isValid = false
            passwordError.set("please enter password")
        }else {
            isValid = true
            passwordError.set(null)
        }
        if(passwordConfirm.get().isNullOrBlank()){
            isValid =false;
            passwordConfirmError.set("please re-enter password")
        }else if (password.get()?.equals(passwordConfirm.get())==false){
            passwordConfirmError.set("doesn't match")

        }else {
            isValid = true
            passwordConfirmError.set(null)
        }
         return isValid;

    }
    fun gotoLogin(){
        navigator?.gotoLogin()
    }
}