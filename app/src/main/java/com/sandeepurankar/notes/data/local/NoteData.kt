package com.sandeepurankar.notes.data.local

import com.sandeepurankar.notes.data.model.Note

fun getNotes(): List<Note> {
    return listOf(
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "College", description = "I am studying in KLETECH"),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "College", description = "I am studying in KLETECH"),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "College", description = "I am studying in KLETECH"),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "College", description = "I am studying in KLETECH")
    )
}