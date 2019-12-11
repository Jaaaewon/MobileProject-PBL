package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity  : AppCompatActivity() {
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mFriendRef : DatabaseReference

    private lateinit var inputPw : String

    private var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener {
            inputPw = editText.text.toString()
            productFromFirebase()
        }
    }

    private fun productFromFirebase(){
        mDatabase = FirebaseDatabase.getInstance()
        mFriendRef = mDatabase.getReference("friend")

        lateinit var connectFriend : DatabaseReference


        mFriendRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (friend: DataSnapshot in dataSnapshot.getChildren()) {
                    if (friend.child("password").value.toString() == inputPw) {
                        var lat = friend.child("first").child("lat").value.toString()
                        var lng = friend.child("first").child("lng").value.toString()
                        Log.d("Success", "성공했따!! $lat $lng")
                        Toast.makeText(context, "이거 성공인가? : $lat $lng ", Toast.LENGTH_LONG)
                            .show()
                    } else
                        Log.d("Else", "에러잖아!!!!")
                }

                /*for(friend: DataSnapshot in dataSnapshot.getChildren()){
                    val category = friend.child("ex").value.toString()
                    val name = friend.child("name").value.toString()
                    val path = friend.child("path").value.toString()
                    val price = friend.child("price").value.toString()
                    val context = friend.child("context").value.toString()

                    //productList.add(Product(category, name, path, price, context))
                    //adapter.addProduct(Product(category, name, path, price, context))
                }*/
                //adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed", "Failed to read value.", error.toException())
            }
        })
    }
}
