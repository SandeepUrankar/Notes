package com.sandeepurankar.notes.ui.screens.noteList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeepurankar.notes.data.local.getNotes
import com.sandeepurankar.notes.data.model.Note
import com.sandeepurankar.notes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {
    //    private val notes = mutableStateListOf<Note>()
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.getAllNotes().distinctUntilChanged().collect{ listOfNotes ->
                if(listOfNotes.isEmpty()){
                    Log.d("NotesApp", "The list is empty.")
                }else{
                    _notes.value = listOfNotes
                }
            }
        }
    }

    fun getAllNotes() = notes

    fun addNote(note: Note) = viewModelScope.launch { notesRepository.insertNote(note) }

    fun deleteNote(note: Note) = viewModelScope.launch { notesRepository.deleteNote(note) }
}