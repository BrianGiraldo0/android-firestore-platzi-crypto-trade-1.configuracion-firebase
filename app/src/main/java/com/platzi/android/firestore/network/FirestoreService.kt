package com.platzi.android.firestore.network

import com.google.firebase.firestore.FirebaseFirestore
import com.platzi.android.firestore.model.Crypto
import com.platzi.android.firestore.model.User
import java.lang.Exception

const val CRYPTO_COLLECTION_NAME = "cryptos"
const val USERS_COLLECTION_NAME = "users"

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {
    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>){
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception ->  callback.onFailed(exception)}
    }

    fun updateUser(user: User, callback: Callback<User>?){
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(user.username)
            .update("crytosList", user.cryptosList)
            .addOnSuccessListener { result ->
                if (result != null){
                    callback!!.onSuccess(user)
                }
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception)}
    }

    fun updateCrypto(crypto: Crypto){
        firebaseFirestore.collection(CRYPTO_COLLECTION_NAME).document(crypto.getDocumentId())
            .update("available", crypto.available)
    }

    fun getCryptos(callback: Callback<List<Crypto>>?){
        firebaseFirestore.collection(CRYPTO_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val cryptoList = result.toObjects(Crypto::class.java)
                    callback!!.onSuccess(cryptoList)
                    break
                }
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception)  }
    }

    fun findUserById(id: String, callback: Callback<User>){
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(id)
            .get()
            .addOnSuccessListener { result ->
                if(result.data != null){
                    callback.onSuccess(result.toObject(User::class.java))
                }else{
                    callback.onSuccess(null)
                }
            }
            .addOnFailureListener { exception -> callback.onFailed(exception)  }
    }
}
