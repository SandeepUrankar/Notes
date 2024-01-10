package com.sandeepurankar.notes.data.local

import com.sandeepurankar.notes.data.model.Note

fun getNotes(): List<Note> {
    return listOf(
        Note(title = "Name", description = "My name is sandeep"),
        Note(
            title = "Pokemon",
            description = "Favourite pokemon is pikachu. Favourite pokemon is pikachu. Favourite pokemon is pikachu. Favourite pokemon is pikachu."
        ),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "Pokemon", description = "Favourite pokemon is pikachu."),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "Pokemon", description = "Favourite pokemon is pikachu."),
        Note(title = "Name", description = "My name is sandeep"),
        Note(title = "Pokemon", description = "Favourite pokemon is pikachu.")
    )
}