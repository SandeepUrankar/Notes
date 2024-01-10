package com.sandeepurankar.notes.data.model

import java.time.LocalDateTime
import java.util.UUID


data class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
