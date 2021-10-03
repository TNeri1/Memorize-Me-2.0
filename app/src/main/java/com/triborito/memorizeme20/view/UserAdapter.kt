package com.triborito.memorizeme20.view

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.triborito.memorizeme20.R
import com.triborito.memorizeme20.model.UserData
import org.w3c.dom.Text

class UserAdapter(val c: Context, val userList: ArrayList<UserData>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v) {
        var topic:TextView
        var question:TextView
        var mMenus:ImageView
        var answer:TextView

        init {
            topic = v.findViewById<TextView>(R.id.mTopic)
            question = v.findViewById<TextView>(R.id.mQuestion)
            answer = v.findViewById<TextView>(R.id.mAnswer)
            v.setOnClickListener { showHide(answer) }
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun showHide(answer: TextView) {
            answer.visibility = if (answer.visibility == TextView.VISIBLE) {
                TextView.INVISIBLE
            } else {
                TextView.VISIBLE
            }
        }

        private fun popupMenus(v: View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                        val topic = v.findViewById<EditText>(R.id.userTopic)
                        val question = v.findViewById<EditText>(R.id.userQuestion)
                        val answer = v.findViewById<EditText>(R.id.userAnswer)
                                AlertDialog.Builder(c)
                                        .setView(v)
                                        .setPositiveButton("Ok") {
                                            dialog,_->
                                            position.userTopic = topic.text.toString()
                                            position.userQuestion = question.text.toString()
                                            position.userAnswer = answer.text.toString()
                                            notifyDataSetChanged()
                                            Toast.makeText(c, "Topic is Edited", Toast.LENGTH_SHORT).show()
                                            dialog.dismiss()
                                        }
                                        .setNegativeButton("Cancel") {
                                            dialog,_->
                                            dialog.dismiss()
                                        }
                                    .create()
                                    .show()

                        true
                    }
                    R.id.delete -> {
                        // Set delete
                        AlertDialog.Builder(c)
                                .setTitle("Delete")
                                .setIcon(R.drawable.ic_warning)
                                .setMessage("Are you sure you want to delete this?")
                                .setPositiveButton("Yes") {
                                    dialog,_->
                                    userList.removeAt(adapterPosition)
                                    notifyDataSetChanged()
                                    Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                                .setNegativeButton("No") {
                                    dialog,_->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.topic.text = newList.userTopic
        holder.question.text = newList.userQuestion
        holder.answer.text = newList.userAnswer
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}