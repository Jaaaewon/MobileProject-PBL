package com.example.pbl2shoppingmall

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var productList = arrayListOf<Product>()
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mProductRef : DatabaseReference
    private var adapter: RecyclerAdapter = RecyclerAdapter()
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val keyword = spinner.selectedItem.toString()
                val newAdapter= RecyclerAdapter()
                if(keyword.equals("all")){
                    for (searchProduct: Product in productList) {
                        newAdapter.addProduct(searchProduct)
                    }
                    adapter = newAdapter
                    adapter.setContext(context)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                }else {
                    for (searchProduct: Product in productList) {
                        if (searchProduct.category.contains(keyword, true)) {
                            newAdapter.addProduct(searchProduct)
                        }
                    }
                    if (newAdapter.itemCount != 0) {
                        adapter = newAdapter
                        adapter.setContext(context)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter
                    }
                }
            }
        }
        searchButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View){
                val keyword = searchText!!.text.toString()
                val newAdapter= RecyclerAdapter()
                for(searchProduct : Product in productList){
                    if(searchProduct.name.contains(keyword, true)){
                        newAdapter.addProduct(searchProduct)
                    }
                }
                if(newAdapter.itemCount != 0){
                    adapter = newAdapter
                    adapter.setContext(context)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                }
                searchText.setText(null)
            }
        })

        adapter.setContext(context)
        productFromFirebase();
    }

    private fun productFromFirebase(){
        mDatabase = FirebaseDatabase.getInstance()
        mProductRef = mDatabase.getReference("Shoppingmall")

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        mProductRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(products: DataSnapshot in dataSnapshot.getChildren()){
                    val category = products.child("category").value.toString()
                    val name = products.child("name").value.toString()
                    val path = products.child("path").value.toString()
                    val price = products.child("price").value.toString()
                    val context = products.child("context").value.toString()

                    productList.add(Product(category, name, path, price, context))
                    adapter.addProduct(Product(category, name, path, price, context))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed", "Failed to read value.", error.toException())
            }
        })
        recyclerView.adapter = adapter
    }
}
