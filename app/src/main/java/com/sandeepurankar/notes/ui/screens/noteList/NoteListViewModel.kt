package com.sandeepurankar.notes.ui.screens.noteList

import androidx.lifecycle.ViewModel
import com.sandeepurankar.notes.data.local.getNotes
import com.sandeepurankar.notes.data.model.Note

class NoteListViewModel : ViewModel(){
    private val notes = mutableListOf<Note>()

    init {
        notes.addAll(getNotes())
    }
    fun getAllNotes() =  notes

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun removeNote(note: Note){
        notes.remove(note)
    }
}