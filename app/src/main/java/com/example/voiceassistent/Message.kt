package com.example.voiceassistent

import java.util.Date

data class Message(var text: String, var date: Date = Date(), var isSend: Boolean)