package com.sandeepurankar.notes.repository

import com.sandeepurankar.notes.data.local.NoteDatabaseDao
import com.sandeepurankar.notes.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDatabaseDao: NoteDatabaseDao) {

    fun getAllNotes(): Flow<List<Note>> =
        notesDatabaseDao.getAllNotes().flowOn(Dispatchers.IO).conflate();

    suspend fun insertNote(note: Note) = notesDatabaseDao.insertNote(note)
    suspend fun updateNote(note: Note) = notesDatabaseDao.updateNote(note)
    suspend fun deleteNote(note: Note) = notesDatabaseDao.deleteNote(note)
    suspend fun getNoteByID(id: String) = notesDatabaseDao.getNoteByID(id)
    suspend fun deleteAllNotes() = notesDatabaseDao.deleteAllNotes()


}