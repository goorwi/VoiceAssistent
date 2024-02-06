package com.example.voiceassistent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val sendButton: Button by lazy { findViewById<Button>(R.id.sendButton) }
    private val questionField: EditText by lazy { findViewById<EditText>(R.id.questionField) }
    private val chatWindow: TextView by lazy { findViewById<TextView>(R.id.chatWindow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendButton.setOnClickListener {
            onSend()
        }
    }

    private fun onSend() {
        val text = questionField.text.toString()
        val answer = AI().getAnswer(text)
        chatWindow.append(">> $text\n")
        chatWindow.append("<< $answer\n")

        dismissKeyboard()
    }

    private fun dismissKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}