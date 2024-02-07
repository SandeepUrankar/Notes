package com.sandeepurankar.notes.ui.screens.noteList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sandeepurankar.notes.data.local.getNotes
import com.sandeepurankar.notes.data.model.Note
import com.sandeepurankar.notes.ui.components.NoteCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen() {

    val noteListViewModel: NoteListViewModel = viewModel()
    val notes = noteListViewModel.notes.collectAsState().value
    var dialogState by remember {
        mutableStateOf(false)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Notes")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { dialogState = true }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add note FAB."
                )
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            val title = remember { mutableStateOf(TextFieldValue()) }
            val body = remember { mutableStateOf(TextFieldValue()) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 12.dp,
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = notes, key = { it.id }) { note ->
                        NoteCard(note = note, onDeleteClicked = {
                            noteListViewModel.deleteNote(it)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Your note is deleted. 🗒",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        })
                    }
                }
            }
            if (dialogState) {
                AddNoteDialog(onSubmit = {
                    if (title.value.text.isNotEmpty() && body.value.text.isNotEmpty()) {
                        noteListViewModel.addNote(
                            Note(
                                title = title.value.text,
                                description = body.value.text
                            )
                        )
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Your note has been added. 🗒",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }

                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Please fill the fields while adding a note.",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }, title, body, onDismissRequest = {
                    dialogState = false
                    title.value = TextFieldValue("")
                    body.value = TextFieldValue("")
                })
            }
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