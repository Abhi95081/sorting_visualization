package com.example.sortingvisualization.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sortingvisualization.algorithms.CodeSnippets
import com.example.sortingvisualization.algorithms.ProgrammingLanguage
import com.example.sortingvisualization.algorithms.SortingAlgorithm
import com.example.sortingvisualization.viewmodel.SortingViewModel
import kotlin.math.max
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
            viewModel.array.maxOrNull() ?: 1.0 
        }
    }

    val minValue by remember(viewModel.array) { 
        derivedStateOf { 
            viewModel.array.minOrNull() ?: 0.0 
        }
    }

    val normalizedArray by remember(viewModel.array, maxValue, minValue) {
        derivedStateOf {
            val range = maxValue - minValue
            viewModel.array.map { 
                if (range == 0.0) 1.0 else (it - minValue) / range 
            }
        }
    }

    @Composable
    fun CodeSnippetDisplay() {
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

    @Composable
    fun SortingStepsDisplay() {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = "Sorting Steps",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Indices Display
                Text(
                    text = "Indices: ${
                        if (viewModel.currentCompareIndices.first != -1) 
                            "${viewModel.currentCompareIndices.first},${viewModel.currentCompareIndices.second}" 
                        else 
                            "-"
                    }",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Sorting Steps List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp, max = 150.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    items(viewModel.sortingSteps) { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ArraySizeDialog() {
        if (viewModel.showArraySizeDialog.value) {
            var tempArraySize by remember { mutableStateOf(viewModel.arraySize.toString()) }

            AlertDialog(
                onDismissRequest = { 
                    viewModel.dismissArraySizeDialog() 
                },
                title = { 
                    Text(
                        "Set Array Size", 
                        style = MaterialTheme.typography.titleMedium
                    ) 
                },
                text = {
                    Column {
                        Text(
                            "Choose array size between 10 and 100",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = tempArraySize,
                            onValueChange = { 
                                // Allow only numeric input
                                tempArraySize = it.filter { char -> char.isDigit() }
                            },
                            label = { Text("Array Size") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val size = tempArraySize.toIntOrNull()?.coerceIn(10, 100) ?: viewModel.arraySize
                            viewModel.updateArraySize(size)
                        }
                    ) {
                        Text("Apply")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { 
                            viewModel.dismissArraySizeDialog() 
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    // Main UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()  // Ensure content doesn't overlap system bars
    ) {
        // Scrollable content with bottom padding to prevent overlap with bottom bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
                .padding(bottom = 56.dp),  // Add extra bottom padding for bottom bar
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1. Graph Visualization (First)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 250.dp),  // Ensure minimum height
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        itemsIndexed(viewModel.array) { index, value ->
                            val barHeight = normalizedArray[index] * 180.0
                            val isComparing = index == viewModel.currentCompareIndices.first || 
                                              index == viewModel.currentCompareIndices.second

                            // Dynamic color generation based on value
                            val hue = ((sin(value * 0.5) + 1.0) * 0.5 * 360.0).toFloat()
                            val baseColor = Color.hsv(hue, 0.8f, 0.9f)
                            val gradientColor = Color.hsv(
                                (hue + 30f) % 360f, 
                                min(0.8f * 1.2f, 1f), 
                                min(0.9f * 1.1f, 1f)
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(barWidth + 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(barWidth)
                                        .height(max(barHeight, 20.0).dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = if (isComparing) 
                                                    listOf(Color.Red, Color.DarkGray) 
                                                else 
                                                    listOf(baseColor, gradientColor),
                                                start = Offset(0f, 0f),
                                                end = Offset(50f, 50f)
                                            ),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .border(
                                            width = 1.dp, 
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                )
                                
                                // Display value with formatted decimal
                                Text(
                                    text = "%.2f".format(value),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 2. Enter Array Input
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Enter Array",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    TextField(
                        value = manualInputText,
                        onValueChange = { 
                            manualInputText = it 
                        },
                        label = { Text("Enter numbers") },
                        placeholder = { Text("e.g. 5.5, 3.2, 7.1, 1.9") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            Button(
                                onClick = {
                                    viewModel.parseInputArray(manualInputText)
                                }
                            ) {
                                Text("Apply")
                            }
                        }
                    )
                }
            }

            // 3. Speed and Granularity Controls
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Speed and Visualization",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Sorting Speed Slider with Label
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Speed", modifier = Modifier.weight(1f))
                        Text(
                            text = when {
                                viewModel.sortingSpeed <= 100 -> "1x"
                                viewModel.sortingSpeed <= 200 -> "2x"
                                viewModel.sortingSpeed <= 300 -> "3x"
                                viewModel.sortingSpeed <= 400 -> "4x"
                                else -> "5x"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Slider(
                            value = viewModel.sortingSpeed.toFloat(),
                            onValueChange = { 
                                viewModel.updateSortingSpeed(it.toLong()) 
                            },
                            valueRange = 1f..500f,
                            modifier = Modifier.weight(3f)
                        )
                    }
                }
            }

            // 4. Programming Language Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Programming Language",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Language Selection Dropdown
                    ExposedDropdownMenuBox(
                        expanded = languageExpanded,
                        onExpandedChange = { 
                            languageExpanded = !languageExpanded 
                        }
                    ) {
                        TextField(
                            value = selectedLanguageState.value.name,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Language") },
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

                        DropdownMenu(
                            expanded = languageExpanded,
                            onDismissRequest = { 
                                languageExpanded = false 
                            }
                        ) {
                            ProgrammingLanguage.values().forEach { language ->
                                DropdownMenuItem(
                                    text = { Text(language.name) },
                                    onClick = {
                                        selectedLanguageState.value = language
                                        languageExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Add Code Snippet Display
                    Spacer(modifier = Modifier.height(8.dp))
                    CodeSnippetDisplay()
                }
            }

            // 5. Algorithm Complexity
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Algorithm Complexity",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = algorithmComplexity,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // 6. Sorting Steps and Controls
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Sorting Steps",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Indices Display
                    Text(
                        text = "Indices: ${
                            if (viewModel.currentCompareIndices.first != -1) 
                                "${viewModel.currentCompareIndices.first},${viewModel.currentCompareIndices.second}" 
                            else 
                                "-"
                        }",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Sorting Steps List
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp, max = 150.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(viewModel.sortingSteps) { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 9.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // Sticky Bottom Bar for Controls
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))  // Add shadow for depth
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Array Size Button
                Button(
                    onClick = { viewModel.showArraySizeDialog() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Size: ${viewModel.arraySize}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 10.sp
                    )
                }

                // Random Array Button
                Button(
                    onClick = { viewModel.generateRandomArray() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Random",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 10.sp
                    )
                }

                // Sort Button
                Button(
                    onClick = { viewModel.startSorting() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Sort",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 10.sp
                    )
                }

                // Reset Button
                Button(
                    onClick = { viewModel.resetArray() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Reset",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onError,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // Array Size Dialog
        ArraySizeDialog()
    }
}
