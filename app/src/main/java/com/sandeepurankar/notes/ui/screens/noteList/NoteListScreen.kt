package com.sandeepurankar.notes.ui.screens.noteList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sandeepurankar.notes.data.model.Note
import com.sandeepurankar.notes.ui.components.NoteCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesList() {

    val noteListViewModel: NoteListViewModel = viewModel()
    val notes = remember {
        noteListViewModel.getAllNotes()
    }
    Surface {
        var dialogState by remember {
            mutableStateOf(false)
        }
        val title = remember { mutableStateOf(TextFieldValue()) }
        val body = remember { mutableStateOf(TextFieldValue()) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Button(onClick = {
                dialogState = true
            }) {
                Text(text = "Add a Note")
            }

            LazyColumn {
                items(items = notes, key = { it.id }) { note ->
                    NoteCard(note = note, onDeleteClicked = {
                        noteListViewModel.removeNote(it)
                    })
                }
            }
        }
        if (dialogState) {
            AddNoteDialog(onSubmit = {
                if (title.value.text.isNotEmpty() && body.value.text.isNotEmpty())
                    noteListViewModel.addNote(Note(title = title.value.text, description = body.value.text))
            }, title, body, onDismissRequest = {
                dialogState = false
                title.value = TextFieldValue("")
                body.value = TextFieldValue("")
            })
        }
    }
}

@Composable
fun AddNoteDialog(
    onSubmit: () -> Unit,
    title: MutableState<TextFieldValue>,
    body: MutableState<TextFieldValue>,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Note", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = body.value,
                    onValueChange = { body.value = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.End) {
                    Button(onClick = { onDismissRequest.invoke() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        // Handle add note action here
                        onSubmit.invoke()
                        onDismissRequest.invoke()
                    }) {
                        Text("Add Note")
                    }
                }
            }
        }
    }
}