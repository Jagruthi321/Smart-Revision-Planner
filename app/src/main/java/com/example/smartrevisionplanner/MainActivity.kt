package com.example.smartrevisionplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartrevisionplanner.ui.plansetup.PlanSetupScreen
import com.example.smartrevisionplanner.ui.plansetup.PlanSetupViewModel
import com.example.smartrevisionplanner.ui.plansetup.PlanSetupViewModelFactory
import com.example.smartrevisionplanner.ui.theme.SmartRevisionPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as SmartRevisionApp
        setContent {
            SmartRevisionPlannerTheme {
                var showPlanSetup by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showPlanSetup) {
                        val viewModel: PlanSetupViewModel = viewModel(
                            factory = PlanSetupViewModelFactory(
                                syllabusRepository = app.syllabusRepository,
                                chapterRepository = app.chapterRepository,
                                onPlanSaved = { showPlanSetup = false }
                            )
                        )
                        PlanSetupScreen(
                            viewModel = viewModel,
                            onPlanSaved = { showPlanSetup = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        HomeScreen(
                            app = app,
                            onCreatePlanClick = { showPlanSetup = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    app: SmartRevisionApp,
    onCreatePlanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Smart Revision Planner")
        Button(onClick = onCreatePlanClick) {
            Text("Create Study Plan")
        }
    }
}