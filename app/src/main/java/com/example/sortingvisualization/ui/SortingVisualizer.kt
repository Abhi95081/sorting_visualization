package com.example.sortingvisualization.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.ui.unit.sp
import com.example.sortingvisualization.algorithms.CodeSnippets
import com.example.sortingvisualization.algorithms.ProgrammingLanguage
import com.example.sortingvisualization.algorithms.SortingAlgorithm
import com.example.sortingvisualization.viewmodel.SortingViewModel
import kotlin.math.min
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingVisualizer(
    viewModel: SortingViewModel,
    isLandscape: Boolean = false
) {
    // Language selection state with preservation across orientation
    val selectedLanguageState = rememberSaveable { 
        mutableStateOf(ProgrammingLanguage.CPP)
    }
    
    // Algorithm expanded state
    var algorithmExpanded by remember { 
        mutableStateOf(false) 
    }
    
    // Language expanded state
    var languageExpanded by remember { 
        mutableStateOf(false) 
    }

    // State for manual array input
    var manualInputText by remember { mutableStateOf("") }

    // Complexity information for current algorithm
    val algorithmComplexity = remember(viewModel.selectedAlgorithm) {
        when (viewModel.selectedAlgorithm) {
            // Basic comparison-based sorting
            SortingAlgorithm.BUBBLE_SORT ->
                "Time: O(n²), Space: O(1)\nStable: Yes, In-place: Yes"
            SortingAlgorithm.SELECTION_SORT -> 
                "Time: O(n²), Space: O(1)\nStable: No, In-place: Yes"
            SortingAlgorithm.INSERTION_SORT -> 
                "Time: O(n²), Space: O(1)\nStable: Yes, In-place: Yes"
            SortingAlgorithm.MERGE_SORT -> 
                "Time: O(n log n), Space: O(n)\nStable: Yes, In-place: No"
            SortingAlgorithm.QUICK_SORT -> 
                "Time: O(n log n), Space: O(log n)\nStable: No, In-place: Yes"
            SortingAlgorithm.HEAP_SORT -> 
                "Time: O(n log n), Space: O(1)\nStable: No, In-place: Yes"
            
            // Advanced comparison-based sorting
            SortingAlgorithm.SHELL_SORT ->
                "Time: O(n log n), Space: O(1)\nStable: No, In-place: Yes"
            SortingAlgorithm.COCKTAIL_SHAKER_SORT ->
                "Time: O(n²), Space: O(1)\nStable: Yes, In-place: Yes"
            SortingAlgorithm.COMB_SORT ->
                "Time: O(n log n), Space: O(1)\nStable: No, In-place: Yes"
            
            // Non-comparison based sorting
            SortingAlgorithm.COUNTING_SORT ->
                "Time: O(n + k), Space: O(k)\nStable: Yes, In-place: No\nRequires known range"
            SortingAlgorithm.RADIX_SORT ->
                "Time: O(d(n + k)), Space: O(n + k)\nStable: Yes, In-place: No\nRequires fixed-width integers"
            SortingAlgorithm.BUCKET_SORT ->
                "Time: O(n + k), Space: O(n + k)\nStable: Yes, In-place: No\nRequires uniform distribution"
        }
    }

    val barWidth by remember(viewModel.array.size) { 
        derivedStateOf { 
            when {
                viewModel.array.size <= 10 -> 30.dp
                viewModel.array.size <= 20 -> 25.dp
                viewModel.array.size <= 50 -> 15.dp
                else -> 10.dp
            }
        }
    }

    val maxValue by remember(viewModel.array) { 
        derivedStateOf { 
            viewModel.array.maxOrNull() ?: 1 
        }
    }

    // Sorting Controls Composable
    @Composable
    fun SortingControls() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Responsive layout for algorithm selection and controls
            if (isLandscape) {
                // Horizontal layout for landscape mode
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Algorithm Selection Dropdown with ScrollView
                    ExposedDropdownMenuBox(
                        expanded = algorithmExpanded,
                        onExpandedChange = { 
                            algorithmExpanded = !algorithmExpanded 
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Selected Algorithm TextField
                        TextField(
                            value = viewModel.selectedAlgorithm.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Sorting Algorithm") },
                            trailingIcon = { 
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = algorithmExpanded
                                ) 
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        // Dropdown Menu with Scrollable List
                        DropdownMenu(
                            expanded = algorithmExpanded,
                            onDismissRequest = { 
                                algorithmExpanded = false 
                            },
                            modifier = Modifier
                                .heightIn(max = 250.dp)
                        ) {
                            // Scrollable list of algorithms
                            SortingAlgorithm.values().forEach { algorithm ->
                                DropdownMenuItem(
                                    text = { Text(algorithm.displayName) },
                                    onClick = {
                                        // Update selected algorithm in ViewModel
                                        viewModel.selectedAlgorithm = algorithm
                                        algorithmExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Speed and Control Buttons
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Speed Slider
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Speed")
                            Slider(
                                modifier = Modifier.weight(1f),
                                value = viewModel.sortingSpeed.toFloat(),
                                onValueChange = { viewModel.updateSortingSpeed(it.toLong()) },
                                valueRange = 1f..500f,
                                steps = 49
                            )
                        }

                        // Visualization Granularity Slider
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Granularity")
                            Slider(
                                modifier = Modifier.weight(1f),
                                value = viewModel.visualizationGranularity.toFloat(),
                                onValueChange = { viewModel.updateVisualizationGranularity(it.toInt()) },
                                valueRange = 1f..10f,
                                steps = 8
                            )
                        }

                        // Control Buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Start Visualization Button
                            Button(
                                onClick = { 
                                    viewModel.startSorting() 
                                },
                                enabled = !viewModel.isSorting
                            ) {
                                Text("Start")
                            }

                            // Reset Button
                            Button(
                                onClick = { 
                                    viewModel.resetArray() 
                                },
                                enabled = !viewModel.isSorting
                            ) {
                                Text("Reset")
                            }
                        }
                    }
                }
            } else {
                // Vertical layout for portrait mode
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Algorithm Selection Dropdown with ScrollView
                    ExposedDropdownMenuBox(
                        expanded = algorithmExpanded,
                        onExpandedChange = { 
                            algorithmExpanded = !algorithmExpanded 
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Selected Algorithm TextField
                        TextField(
                            value = viewModel.selectedAlgorithm.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Sorting Algorithm") },
                            trailingIcon = { 
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = algorithmExpanded
                                ) 
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        // Dropdown Menu with Scrollable List
                        DropdownMenu(
                            expanded = algorithmExpanded,
                            onDismissRequest = { 
                                algorithmExpanded = false 
                            },
                            modifier = Modifier
                                .heightIn(max = 250.dp)
                        ) {
                            // Scrollable list of algorithms
                            SortingAlgorithm.values().forEach { algorithm ->
                                DropdownMenuItem(
                                    text = { Text(algorithm.displayName) },
                                    onClick = {
                                        // Update selected algorithm in ViewModel
                                        viewModel.selectedAlgorithm = algorithm
                                        algorithmExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Speed Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Speed")
                        Slider(
                            modifier = Modifier.weight(1f),
                            value = viewModel.sortingSpeed.toFloat(),
                            onValueChange = { viewModel.updateSortingSpeed(it.toLong()) },
                            valueRange = 1f..500f,
                            steps = 49
                        )
                    }

                    // Visualization Granularity Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Granularity")
                        Slider(
                            modifier = Modifier.weight(1f),
                            value = viewModel.visualizationGranularity.toFloat(),
                            onValueChange = { viewModel.updateVisualizationGranularity(it.toInt()) },
                            valueRange = 1f..10f,
                            steps = 8
                        )
                    }

                    // Control Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Start Visualization Button
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                viewModel.startSorting() 
                            },
                            enabled = !viewModel.isSorting
                        ) {
                            Text("Start Visualization")
                        }

                        // Reset Button
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                viewModel.resetArray() 
                            },
                            enabled = !viewModel.isSorting
                        ) {
                            Text("Reset")
                        }
                    }
                }
            }

            // Manual Array Input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = manualInputText,
                    onValueChange = { manualInputText = it },
                    label = { Text("Enter array (comma or space-separated)") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { 
                        viewModel.updateArrayManually(manualInputText)
                        manualInputText = "" 
                    },
                    enabled = !viewModel.isSorting
                ) {
                    Text("Update")
                }
            }

            // Array Size Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Array Size: ${viewModel.arraySize}")
                Slider(
                    value = viewModel.arraySize.toFloat(),
                    onValueChange = { 
                        viewModel.updateArraySize(it.toInt()) 
                    },
                    valueRange = 10f..100f,
                    steps = 9
                )
            }
        }
    }

    // Sorting Steps Display Composable
    @Composable
    fun SortingStepsDisplay() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Current Step Information
                Text(
                    text = "Current Step: ${
                        when {
                            !viewModel.isSorting -> "Not Sorting"
                            viewModel.currentCompareIndices.first != -1 -> 
                                "Comparing indices ${viewModel.currentCompareIndices.first} and ${viewModel.currentCompareIndices.second}"
                            else -> "Initializing Sort"
                        }
                    }",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Code Snippet Display
                Text(
                    text = CodeSnippets.getCodeSnippet(
                        viewModel.selectedAlgorithm, 
                        selectedLanguageState.value
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                )
            }
        }
    }

    // Main scrollable layout
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Sorting Visualization at the top (full width)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(viewModel.array) { index, value ->
                    val height = (value.toFloat() / maxValue) * 250f

                    val barColor = remember(value) {
                        val hue = ((sin(value.toFloat() * 0.5f) + 1f) * 0.5f * 360f)
                        val saturation = 0.8f
                        val brightness = 0.9f
                        
                        val baseColor = Color.hsv(hue, saturation, brightness)
                        val gradientColor = Color.hsv(
                            (hue + 30f) % 360f, 
                            min(saturation * 1.2f, 1f), 
                            min(brightness * 1.1f, 1f)
                        )
                        
                        Brush.linearGradient(
                            colors = listOf(baseColor, gradientColor),
                            start = Offset(0f, 0f),
                            end = Offset(50f, 50f)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(barWidth + 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(barWidth)
                                .height(height.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    brush = when {
                                        index == viewModel.currentCompareIndices.first || 
                                        index == viewModel.currentCompareIndices.second -> 
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    MaterialTheme.colorScheme.errorContainer, 
                                                    MaterialTheme.colorScheme.errorContainer
                                                )
                                            )
                                        else -> barColor
                                    },
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        
                        // Display block size
                        Text(
                            text = value.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // Sorting Controls at the bottom
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Algorithm Complexity Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Algorithm Complexity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        algorithmComplexity,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Language Selection and Code Snippet
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Language Selection Dropdown
                ExposedDropdownMenuBox(
                    expanded = languageExpanded,
                    onExpandedChange = { 
                        languageExpanded = !languageExpanded 
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Selected Language TextField
                    TextField(
                        value = selectedLanguageState.value.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Programming Language") },
                        trailingIcon = { 
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = languageExpanded
                            ) 
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    // Dropdown Menu with Scrollable List
                    DropdownMenu(
                        expanded = languageExpanded,
                        onDismissRequest = { 
                            languageExpanded = false 
                        },
                        modifier = Modifier
                            .heightIn(max = 250.dp)
                    ) {
                        // List of programming languages
                        ProgrammingLanguage.values().forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language.displayName) },
                                onClick = {
                                    // Update selected language
                                    selectedLanguageState.value = language
                                    languageExpanded = false
                                }
                            )
                        }
                    }
                }

                // Code Snippet with Copy Button
                val context = LocalContext.current
                val codeSnippet = CodeSnippets.getCodeSnippet(
                    viewModel.selectedAlgorithm, 
                    selectedLanguageState.value
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 250.dp)
                        .background(Color.LightGray.copy(alpha = 0.1f))
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    // Scrollable Code Snippet
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(4.dp)
                    ) {
                        Text(
                            text = codeSnippet,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            lineHeight = 12.sp
                        )
                    }

                    // Copy Button
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Sorting Algorithm Code", codeSnippet)
                            clipboard.setPrimaryClip(clip)
                            
                            Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Text("Copy Code", fontSize = 12.sp)
                    }
                }
            }

            // Sorting Controls
            SortingControls()

            // Sorting Steps Display
            SortingStepsDisplay()
        }
    }
}
