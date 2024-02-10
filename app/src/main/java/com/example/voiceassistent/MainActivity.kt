package com.example.voiceassistent

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voiceassistent.message.Message
import com.example.voiceassistent.message.MessageListAdapter
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val sendButton: Button by lazy { findViewById<Button>(R.id.sendButton) }
    private val questionField: EditText by lazy { findViewById<EditText>(R.id.questionField) }
    private lateinit var chatMessgeList: RecyclerView
    protected var messageListAdapter: MessageListAdapter = MessageListAdapter()
    private lateinit var textToSpeech: TextToSpeech

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatMessgeList = findViewById(R.id.chatMessageList)
        chatMessgeList.layoutManager = LinearLayoutManager(this)
        chatMessgeList.adapter = messageListAdapter

        sendButton.setOnClickListener {
            onSend()
        }

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            it ->
            if (it != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun onSend() {
        val text = questionField.text.toString()
        messageListAdapter.messageList.add(Message(text, isSend = true))

        AI().getAnswer(text) {
            messageListAdapter.messageList.add(Message(it, isSend = false))
            messageListAdapter.notifyDataSetChanged()
            //textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        questionField.text.clear()


        chatMessgeList.scrollToPosition(messageListAdapter.messageList.size - 1)

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