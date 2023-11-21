package com.pdm.weatherapp.db

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.model.User

object FirebaseDB {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private var citiesListReg : ListenerRegistration? = null
    var onUserLogin : ((User) -> Unit)? = null
    var onUserLogout : (() -> Unit)? = null
    var onCityAdded : ((FavoriteCity) -> Unit)? = null
    var onCityRemoved : ((FavoriteCity) -> Unit)? = null

    init { auth.addAuthStateListener { auth ->
        if (auth.currentUser != null) {
            val refCurrUser = db.collection("users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get()
                .addOnSuccessListener {
                    it.toObject(User::class.java)?.let {
                        onUserLogin?.invoke(it) } }
            citiesListReg = refCurrUser.collection("cities")
                .addSnapshotListener { snapshots, ex ->
                    ex?.let { return@addSnapshotListener }
                    snapshots?.documentChanges?.forEach {change ->
                        val city = change.document.
                        toObject(FavoriteCity::class.java)
                        if (change.type == DocumentChange.Type.ADDED) {
                            onCityAdded?.invoke(city)
                        } else if (change.type == DocumentChange.Type.REMOVED) {
                            onCityRemoved?.invoke(city)
                        }
                    }
                }
        } else {
            citiesListReg?.remove()
            onUserLogout?.invoke()
        }
    }}
    fun register(userName: String, email: String) {
        val tmpUser = User(userName, email)
        db.collection("users").
        document(auth.currentUser?.uid + "").set(tmpUser);
    }
    fun add(city:FavoriteCity) {
        auth.currentUser?.let { currUser ->
            city.name?.let { name ->
                db.collection("users").document(currUser.uid)
                    .collection("cities").document(name)
                    .set(city)
            }
        }
    }
    fun remove(city: FavoriteCity) {
        auth.currentUser?.let { currUser ->
            val citiesRef = db.collection("users")
                .document(currUser.uid).collection("cities")
            citiesRef.document(city.name!!).delete()
        }
    }
}