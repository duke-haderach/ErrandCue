package com.example.errandcue.core.time
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class CooldownPolicy @Inject constructor() {
    private val cooldownMs = 15 * 60 * 1000L
    fun canTrigger(now: Long, last: Long?): Boolean = last == null || now - last >= cooldownMs
}