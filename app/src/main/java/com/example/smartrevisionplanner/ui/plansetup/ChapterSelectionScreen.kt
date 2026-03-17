package com.example.smartrevisionplanner.ui.plansetup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrevisionplanner.domain.model.ChapterMetadata

@Composable
fun ChapterSelectionScreen(
    chapters: List<ChapterMetadata>,
    selectedChapters: Set<ChapterMetadata>,
    onToggleChapter: (ChapterMetadata) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(24.dp)) {
        Text("Step 3: Select Chapters", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(chapters) { chapter ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleChapter(chapter) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = chapter in selectedChapters,
                        onCheckedChange = { onToggleChapter(chapter) }
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(chapter.name)
                        Text(
                            chapter.subject,
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Back")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f),
                enabled = selectedChapters.isNotEmpty()
            ) {
                Text("Next")
            }
        }
    }
}
