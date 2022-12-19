package co.edu.unicauca.lottieapp.data.repository

import android.graphics.Bitmap
import co.edu.unicauca.lottieapp.utils.Constants
import co.edu.unicauca.lottieapp.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class UserRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    private val storage: FirebaseStorage = Firebase.storage("gs://appmovillog.appspot.com")

    suspend fun createUser( userEmailAddress: String, userLoginPassword: String,nombre:String,codigo:String,identificacion : String){

        try {

            firebaseAuth.createUserWithEmailAndPassword(userEmailAddress,userLoginPassword).await()
            val user  = firebaseAuth.currentUser!!
            val profileUpdate = userProfileChangeRequest {
                displayName = nombre
            }
            user.updateProfile(profileUpdate).await()
            val userInfo = hashMapOf(
                "codigo" to codigo,
                "identificacion" to identificacion
            )
            db.collection(Constants.USER).document(user.uid).set(userInfo).await()

        }catch (e:FirebaseAuthException){
            throw Exception(e.message)
        }


//        return withContext(Dispatchers.IO) {
//            safeCall {
//                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword).await()
//                val userId = registrationResult.user?.uid!!
//                val newUser = User(userName, userEmailAddress, userType)
//
//                Resource.Success(registrationResult)
//            }
//        }
    }

    suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }
    }

    suspend fun getCurrentUser(): User?{
        val user = firebaseAuth.currentUser
        if (user != null){
            var foto:String? = null
            if (user.photoUrl != null){
                foto = user.photoUrl.toString()
            }

            val info = db.collection(Constants.USER).document(user.uid).get().await()
            return User(user.displayName!!,user.email!!,info.get("identificacion").toString(),info.get("codigo").toString(),foto)
            //return User(user.displayName!!,user.email!!,"","",foto)
        }
        return null
    }

    fun getUsuario() = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()

    fun sendResetPassword(email : String) = firebaseAuth.sendPasswordResetEmail(email)

    suspend fun uploadImage(image: Bitmap): User?{
        val user = firebaseAuth.currentUser

        if (user != null){
            //val prueba = Firebase.storage("gs://appmovillog.appspot.com")
            val ref = storage.reference

            println("---------------")
            println(ref)
            println("---------------")
            val imageRef = ref.child("${user.uid}.jpg")
            println("---------------")
            println(imageRef)
            println("---------------")
            val baos: ByteArrayOutputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG,100,baos)
            imageRef.putBytes(baos.toByteArray()).await()

            val url = imageRef.downloadUrl.await()

            println("------------")
            println(url)
            println("------------")

            val profileUpdate = userProfileChangeRequest {
                photoUri = url
            }
            user.updateProfile(profileUpdate).await()
        }

        return this.getCurrentUser()
    }

}