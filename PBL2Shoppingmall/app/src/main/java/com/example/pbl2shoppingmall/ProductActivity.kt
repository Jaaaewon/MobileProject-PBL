package com.example.pbl2shoppingmall

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        if (intent.hasExtra("product")) {
            val product = intent.getParcelableExtra<Product>("product")

            productName.text = product.name
            productPrice.text = product.price
            productContext.text = product.context
            imageTake(product.path)
        } else {
            Toast.makeText(this, "데이터 없음", Toast.LENGTH_SHORT).show()
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }


    }
    fun imageTake(path: String) {
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference.child(path)

        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Glide 이용하여 이미지뷰에 로딩
                Glide.with(imageView.context)
                    .load(task.result)
                    .override(1024, 980)
                    .into(imageView)
            } else {
            }
        }
    }
}