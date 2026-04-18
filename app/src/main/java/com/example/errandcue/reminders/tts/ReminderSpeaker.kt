package com.example.errandcue.reminders.tts
import android.content.Context; import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext; import java.util.Locale
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class ReminderSpeaker @Inject constructor(@ApplicationContext ctx: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(ctx, this)
    private var ready = false
    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) { tts?.language = Locale.US; ready = true } }
    fun speak(text: String) { if (ready) tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ec") }
}