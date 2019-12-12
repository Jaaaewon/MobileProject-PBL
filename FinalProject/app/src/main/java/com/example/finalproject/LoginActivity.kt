package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity  : AppCompatActivity() {
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mFriendRef : DatabaseReference
    private var pwArray = mutableListOf<String>();
    private var i =0;
    private lateinit var inputPw : String

    private var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        productFromFirebase()

        button.setOnClickListener {
            inputPw = editText.text.toString()
            Log.d("Success", "성공했따!! ${pwArray.size}")
            var checkEx = false;
            var checkIndex = 0;
            for (a in pwArray) {
                if (a == inputPw) {
                    checkEx=true;
                    checkIndex = pwArray.indexOf(a)
                    Log.d("$$$$$$$$$$",a);
                }
            }
            if(checkEx==false) {

                //mFriendRef.child("ex").push().child("password").push().setValue(inputPw)
                mFriendRef.child("ex" + i).child("password").setValue(inputPw)
                mFriendRef.child("ex" + i).child("second").child("lat").setValue(37.56);
                mFriendRef.child("ex" + i).child("second").child("lng").setValue(126.97);
                val nextIntent = Intent(this,PracticeJSONActivity::class.java)
                nextIntent.putExtra("exName","ex${i}")
                nextIntent.putExtra("isFirst",1)
                startActivity(nextIntent)
                i++;
            }else {
                val nextIntent = Intent(this,PracticeJSONActivity::class.java)
                nextIntent.putExtra("exName","ex${checkIndex}")
                nextIntent.putExtra("isFirst",0)
                startActivity(nextIntent)
            }
        }
    }

    private fun productFromFirebase(){
        mDatabase = FirebaseDatabase.getInstance()
        mFriendRef = mDatabase.getReference("friend")

        lateinit var connectFriend : DatabaseReference


        mFriendRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pwArray =  mutableListOf<String>();
                for (friend: DataSnapshot in dataSnapshot.getChildren()) {
                    pwArray.add(friend.child("password").value.toString());
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed", "Failed to read value.", error.toException())
            }
        })
    }
}