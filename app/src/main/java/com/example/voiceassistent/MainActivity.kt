package com.example.voiceassistent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val sendButton: Button by lazy { findViewById<Button>(R.id.sendButton) }
    private val questionField: EditText by lazy { findViewById<EditText>(R.id.questionField) }
    private val chatWindow: TextView by lazy { findViewById<TextView>(R.id.chatWindow) }

    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun onSend() {
        val text = questionField.text.toString()
        val answer = AI().getAnswer(text)
        chatWindow.append(">> $text\n")
        chatWindow.append("<< $answer\n")

        questionField.text.clear()

        //textToSpeech.speak(answer, TextToSpeech.QUEUE_FLUSH, null, null)

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