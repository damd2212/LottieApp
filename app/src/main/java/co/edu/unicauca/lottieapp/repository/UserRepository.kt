package co.edu.unicauca.lottieapp.repository

import co.edu.unicauca.lottieapp.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    suspend fun createUser(userName: String, userEmailAddress: String, userType: String, userLoginPassword: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword).await()
                val userId = registrationResult.user?.uid!!
                val newUser = User(userName, userEmailAddress, userType)
                //databaseReference.child(userId).setValue(newUser).await()
                Resource.Success(registrationResult)
            }
        }
    }

    suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()

    fun sendResetPassword(email : String) = firebaseAuth.sendPasswordResetEmail(email)

}