package com.sandeepurankar.notes.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sandeepurankar.notes.data.model.Note
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NoteCard(
    note: Note = Note(
        title = "My name",
        description = "I am Sandeep, I am 21 years old."
    ),
    onNoteClicked: () -> Unit = {},
    onDeleteClicked: (note: Note) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable {
                onNoteClicked.invoke()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(.9f, false)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = note.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = note.createdAt.format(
                        DateTimeFormatter.ofPattern(
                            "EEE, d MMM hh:mm",
                            Locale.getDefault()
                        )
                    ),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            IconButton(onClick = { onDeleteClicked(note) }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Delete note icon.",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

        }
    }
}