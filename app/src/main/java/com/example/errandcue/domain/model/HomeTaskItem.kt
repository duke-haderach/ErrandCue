package com.example.errandcue.domain.model
data class HomeTaskItem(val id: Long, val title: String, val placeName: String?, val placeId: Long?, val triggerType: String, val travelMode: String)