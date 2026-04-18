package com.example.errandcue.reminders.engine
import com.example.errandcue.domain.model.ResolvedReminder
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class ReminderResolver @Inject constructor() {
    fun resolve(placeId: Long, placeName: String, tasks: List<Pair<Long,String>>): ResolvedReminder {
        val titles = tasks.map { it.second }
        val body = when { titles.isEmpty() -> "You're near $placeName."; titles.size == 1 -> titles.first(); else -> titles.take(2).joinToString(" • ") + if (titles.size > 2) " • +${titles.size-2} more" else "" }
        val speech = "You're near $placeName. " + if (titles.isEmpty()) "" else titles.joinToString(". ")
        return ResolvedReminder(placeId, placeName, tasks.map{it.first}, placeName, body, speech.trim())
    }
}