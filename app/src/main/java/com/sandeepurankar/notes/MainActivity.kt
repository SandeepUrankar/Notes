package com.sandeepurankar.notes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.sandeepurankar.notes.ui.screens.noteList.NotesListScreen
import com.sandeepurankar.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                NotesListScreen()
            }
        }
    }
}
