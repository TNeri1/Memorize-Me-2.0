package com.triborito.memorizeme20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.triborito.memorizeme20.model.UserData
import com.triborito.memorizeme20.view.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set list
        userList = ArrayList()
        // Set find ID
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        // Set adapter
        userAdapter = UserAdapter(this, userList)
        // Set Recycler View Adapter
        recv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recv.adapter = userAdapter
        // Set dialog
        addsBtn.setOnClickListener { addInfo() }



}

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item, null)
        // Set View
        val userTopic = v.findViewById<EditText>(R.id.userTopic)
        val userQuestion = v.findViewById<EditText>(R.id.userQuestion)
        val userAnswer = v.findViewById<EditText>(R.id.userAnswer)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") {
            dialog,_->
            val topics = userTopic.text.toString()
            val questions = userQuestion.text.toString()
            val answers = userAnswer.text.toString()
            userList.add(UserData("${topics}", "${questions}", "${answers}"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") {
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

}