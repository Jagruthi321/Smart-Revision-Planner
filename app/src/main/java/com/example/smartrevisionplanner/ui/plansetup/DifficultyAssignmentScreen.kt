package com.example.smartrevisionplanner.ui.plansetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.ChapterSelection
import com.example.smartrevisionplanner.domain.model.Difficulty

@Composable
fun DifficultyAssignmentScreen(
    selectedChapters: List<ChapterSelection>,
    chapterDifficulties: Map<ChapterMetadata, Difficulty>,
    onDifficultyChange: (ChapterMetadata, Difficulty) -> Unit,
    onGenerate: () -> Unit,
    onBack: () -> Unit,
    isGenerating: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(24.dp)) {
        Text("Step 4: Assign Difficulty", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Hard chapters get more study time",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(selectedChapters) { selection ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            selection.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            selection.subject,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Difficulty.entries.forEach { difficulty ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    RadioButton(
                                        selected = (chapterDifficulties[selection.metadata] ?: Difficulty.Medium) == difficulty,
                                        onClick = { onDifficultyChange(selection.metadata, difficulty) }
                                    )
                                    Text(
                                        difficulty.name,
                                        modifier = Modifier.padding(start = 4.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                enabled = !isGenerating
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = onGenerate,
                modifier = Modifier.weight(1f),
                enabled = !isGenerating
            ) {
                Text(if (isGenerating) "Generating..." else "Generate Plan")
            }
        }
    }
}
