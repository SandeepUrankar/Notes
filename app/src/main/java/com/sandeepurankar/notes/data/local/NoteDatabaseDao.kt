package com.sandeepurankar.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sandeepurankar.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {
    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from notes WHERE id=:id")
    suspend fun getNoteByID(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE from notes")
    suspend fun deleteAllNotes()

    @Delete
    suspend fun deleteNote(note: Note)

}