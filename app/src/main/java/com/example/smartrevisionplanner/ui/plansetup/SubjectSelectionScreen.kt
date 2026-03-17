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
import com.example.smartrevisionplanner.domain.model.Subject

@Composable
fun SubjectSelectionScreen(
    subjects: List<Subject>,
    selectedSubjects: Set<Subject>,
    onToggleSubject: (Subject) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(24.dp)) {
        Text("Step 2: Select Subjects", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(subjects) { subject ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleSubject(subject) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = subject in selectedSubjects,
                        onCheckedChange = { onToggleSubject(subject) }
                    )
                    Text(subject.name, modifier = Modifier.padding(start = 8.dp))
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
                enabled = selectedSubjects.isNotEmpty()
            ) {
                Text("Next")
            }
        }
    }
}
