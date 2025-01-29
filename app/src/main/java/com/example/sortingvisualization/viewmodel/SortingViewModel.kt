package com.example.sortingvisualization.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sortingvisualization.algorithms.SortingAlgorithm
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SortingViewModel : ViewModel() {

    private companion object {
        const val MIN_ARRAY_SIZE = 10
        const val MAX_ARRAY_SIZE = 100
        const val MIN_SPEED = 1L
        const val MAX_SPEED = 500L
        const val DEFAULT_SPEED = 50L
    }

    // State properties
    private val _arraySize = mutableStateOf(50)
    val arraySize: Int by _arraySize

    private val _array = mutableStateListOf<Double>()
    val array: List<Double> = _array

    private val _isSorting = mutableStateOf(false)
    val isSorting: Boolean by _isSorting

    // Improved speed control with exponential scaling
    private val _sortingSpeed = mutableStateOf(DEFAULT_SPEED)
    var sortingSpeed: Long by _sortingSpeed

    // New property to control visualization granularity
    private val _visualizationGranularity = mutableStateOf(1)
    val visualizationGranularity: Int by _visualizationGranularity

    private val _currentCompareIndices = mutableStateOf(Pair(-1, -1))
    val currentCompareIndices: Pair<Int, Int> by _currentCompareIndices

    private var sortingJob: Job? = null

    private val _selectedAlgorithm = mutableStateOf(SortingAlgorithm.BUBBLE_SORT)
    var selectedAlgorithm: SortingAlgorithm by _selectedAlgorithm

    // Array Size Dialog State
    private val _showArraySizeDialog = mutableStateOf(false)
    val showArraySizeDialog: State<Boolean> = _showArraySizeDialog

    // Sorting steps tracking
    private val _sortingSteps = mutableStateListOf<String>()
    val sortingSteps: List<String> = _sortingSteps

    // Generate a random array with the specified size
    private fun createRandomArray(size: Int): List<Double> {
        return List(size) { (1..size).random().toDouble() }
    }

    // Generate a new random array
    fun generateRandomArray() {
        cancelSorting()
        _array.clear()
        _array.addAll(createRandomArray(_arraySize.value))
    }

    // Method to show array size dialog
    fun showArraySizeDialog() {
        _showArraySizeDialog.value = true
    }

    // Method to dismiss array size dialog
    fun dismissArraySizeDialog() {
        _showArraySizeDialog.value = false
    }

    // Update array size with validation
    fun updateArraySize(newSize: Int) {
        cancelSorting()
        
        // Ensure the size is within reasonable bounds
        val constrainedSize = newSize.coerceIn(10, 100)
        
        // Update array size
        _arraySize.value = constrainedSize
        
        // Regenerate array with new size
        generateRandomArray()
        
        // Dismiss dialog
        dismissArraySizeDialog()
    }

    // Update sorting speed with exponential scaling
    fun updateSortingSpeed(newSpeed: Long) {
        // Exponential scaling: lower values mean faster sorting
        _sortingSpeed.value = newSpeed.coerceIn(MIN_SPEED, MAX_SPEED)
    }

    // New method to control visualization granularity
    fun updateVisualizationGranularity(newGranularity: Int) {
        _visualizationGranularity.value = newGranularity.coerceIn(1, 10)
    }

    // Clear sorting steps before starting a new sort
    private fun clearSortingSteps() {
        _sortingSteps.clear()
    }

    // Add a sorting step
    private fun addSortingStep(step: String) {
        _sortingSteps.add(step)
        // Limit the number of steps to prevent excessive memory usage
        if (_sortingSteps.size > 20) {
            _sortingSteps.removeAt(0)
        }
    }

    // Start sorting the array with the selected algorithm
    fun startSorting() {
        if (_isSorting.value) return
        
        // Ensure we have an array to sort
        if (_array.isEmpty()) {
            generateRandomArray()
        }

        _isSorting.value = true
        _currentCompareIndices.value = Pair(-1, -1)

        sortingJob = viewModelScope.launch {
            try {
                when (selectedAlgorithm) {
                    SortingAlgorithm.BUBBLE_SORT -> bubbleSort()
                    SortingAlgorithm.SELECTION_SORT -> selectionSort()
                    SortingAlgorithm.INSERTION_SORT -> insertionSort()
                    SortingAlgorithm.MERGE_SORT -> mergeSortWrapper()
                    SortingAlgorithm.QUICK_SORT -> quickSortWrapper()
                    SortingAlgorithm.HEAP_SORT -> heapSort()
                    
                    // Advanced comparison-based sorting
                    SortingAlgorithm.SHELL_SORT -> shellSort()
                    SortingAlgorithm.COCKTAIL_SHAKER_SORT -> cocktailShakerSort()
                    SortingAlgorithm.COMB_SORT -> combSort()
                    
                    // Non-comparison based sorting
                    SortingAlgorithm.COUNTING_SORT -> countingSort()
                    SortingAlgorithm.RADIX_SORT -> radixSortWrapper()
                    SortingAlgorithm.BUCKET_SORT -> bucketSort()
                }
            } catch (e: Exception) {
                // Log the error or handle it appropriately
                e.printStackTrace()
            } finally {
                _isSorting.value = false
                _currentCompareIndices.value = Pair(-1, -1)
            }
        }
    }

    // Reset the array to a new random one
    fun resetArray() {
        generateRandomArray()
    }

    // Cancel any ongoing sorting process
    private fun cancelSorting() {
        sortingJob?.cancel()
        _isSorting.value = false
        _currentCompareIndices.value = Pair(-1, -1)
    }

    init {
        generateRandomArray()
    }

    // Optimized delay mechanism
    private suspend fun smartDelay() {
        // Exponential delay with granularity control
        val baseDelay = (MAX_SPEED - sortingSpeed + 1)
        val granularityMultiplier = visualizationGranularity
        delay(baseDelay * granularityMultiplier)
    }

    // Sorting algorithm implementations
    private suspend fun bubbleSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("Starting Bubble Sort")

        for (i in 0 until n - 1) {
            var swapped = false
            for (j in 0 until n - i - 1) {
                _currentCompareIndices.value = Pair(j, j + 1)
                smartDelay()

                if (arr[j] > arr[j + 1]) {
                    // Add step for swap
                    addSortingStep("Swapping ${arr[j]} and ${arr[j + 1]} at indices $j and ${j+1}")
                    
                    arr[j] = arr[j + 1].also { arr[j + 1] = arr[j] }
                    swapped = true
                }
            }
            if (!swapped) {
                addSortingStep("Array is sorted. Stopping early.")
                break
            }
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun selectionSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("Starting Selection Sort")

        for (i in 0 until n - 1) {
            var minIndex = i
            for (j in i + 1 until n) {
                _currentCompareIndices.value = Pair(minIndex, j)
                smartDelay()

                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            
            if (minIndex != i) {
                addSortingStep("Moving minimum ${arr[minIndex]} to index $i")
                arr[i] = arr[minIndex].also { arr[minIndex] = arr[i] }
            }
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun insertionSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("Starting Insertion Sort")

        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                _currentCompareIndices.value = Pair(j, j + 1)
                smartDelay()

                arr[j + 1] = arr[j]
                j--
            }
            addSortingStep("Inserting ${key} at index ${j+1}")
            arr[j + 1] = key
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun mergeSortWrapper() {
        val arr = _array.toMutableList()
        clearSortingSteps()
        addSortingStep("Starting Merge Sort")
        mergeSort(arr, 0, arr.size - 1)
        _array.clear()
        _array.addAll(arr)
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun mergeSort(arr: MutableList<Double>, left: Int, right: Int) {
        if (left < right) {
            val mid = (left + right) / 2
            mergeSort(arr, left, mid)
            mergeSort(arr, mid + 1, right)
            merge(arr, left, mid, right)
        }
    }

    private suspend fun merge(arr: MutableList<Double>, left: Int, mid: Int, right: Int) {
        val leftArr = arr.subList(left, mid + 1).toMutableList()
        val rightArr = arr.subList(mid + 1, right + 1).toMutableList()

        var i = 0
        var j = 0
        var k = left

        while (i < leftArr.size && j < rightArr.size) {
            _currentCompareIndices.value = Pair(left + i, mid + 1 + j)
            smartDelay()

            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++]
            } else {
                arr[k++] = rightArr[j++]
            }
        }

        while (i < leftArr.size) arr[k++] = leftArr[i++]
        while (j < rightArr.size) arr[k++] = rightArr[j++]
    }

    private suspend fun quickSortWrapper() {
        val arr = _array.toMutableList()
        clearSortingSteps()
        addSortingStep("Starting Quick Sort")
        quickSort(arr, 0, arr.size - 1)
        _array.clear()
        _array.addAll(arr)
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun quickSort(arr: MutableList<Double>, low: Int, high: Int) {
        if (low < high) {
            val partitionIndex = partition(arr, low, high)
            quickSort(arr, low, partitionIndex - 1)
            quickSort(arr, partitionIndex + 1, high)
        }
    }

    private suspend fun partition(arr: MutableList<Double>, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1

        for (j in low until high) {
            _currentCompareIndices.value = Pair(j, high)
            smartDelay()

            if (arr[j] < pivot) {
                i++
                // Swap elements
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
                addSortingStep("Swapping ${arr[i]} and ${arr[j]} at indices $i and $j")
            }
        }

        // Place pivot in correct position
        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp
        addSortingStep("Placing pivot ${arr[i+1]} at index ${i+1}")

        return i + 1
    }

    private suspend fun heapSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("Starting Heap Sort")

        // Build max heap
        for (i in n / 2 - 1 downTo 0) {
            heapify(arr, n, i)
        }

        // Extract elements from heap one by one
        for (i in n - 1 downTo 1) {
            // Move current root to end
            val temp = arr[0]
            arr[0] = arr[i]
            arr[i] = temp
            addSortingStep("Swapping ${arr[0]} and ${arr[i]} at indices 0 and $i")

            // Call max heapify on the reduced heap
            heapify(arr, i, 0)
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun heapify(arr: MutableList<Double>, n: Int, i: Int) {
        var largest = i
        val left = 2 * i + 1
        val right = 2 * i + 2

        _currentCompareIndices.value = Pair(largest, left)
        smartDelay()

        if (left < n && arr[left] > arr[largest]) {
            largest = left
        }

        _currentCompareIndices.value = Pair(largest, right)
        smartDelay()

        if (right < n && arr[right] > arr[largest]) {
            largest = right
        }

        if (largest != i) {
            // Swap elements
            val swap = arr[i]
            arr[i] = arr[largest]
            arr[largest] = swap
            addSortingStep("Swapping ${arr[i]} and ${arr[largest]} at indices $i and $largest")

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest)
        }
    }

    // Advanced comparison-based sorting algorithms
    private suspend fun shellSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("Starting Shell Sort")

        var gap = n / 2
        
        while (gap > 0) {
            for (i in gap until n) {
                val temp = arr[i]
                var j = i
                
                while (j >= gap && arr[j - gap] > temp) {
                    _currentCompareIndices.value = Pair(j - gap, j)
                    smartDelay()
                    
                    arr[j] = arr[j - gap]
                    j -= gap
                }
                
                addSortingStep("Inserting ${temp} at index $j")
                arr[j] = temp
            }
            gap /= 2
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun cocktailShakerSort() {
        val arr = _array
        var swapped: Boolean
        var start = 0
        var end = arr.size
        clearSortingSteps()
        addSortingStep("Starting Cocktail Shaker Sort")

        do {
            swapped = false
            
            // Forward pass (like bubble sort)
            for (i in start until end - 1) {
                _currentCompareIndices.value = Pair(i, i + 1)
                smartDelay()
                
                if (arr[i] > arr[i + 1]) {
                    val temp = arr[i]
                    arr[i] = arr[i + 1]
                    arr[i + 1] = temp
                    addSortingStep("Swapping ${arr[i]} and ${arr[i + 1]} at indices $i and ${i+1}")
                    swapped = true
                }
            }
            
            if (!swapped) break
            
            swapped = false
            end--
            
            // Backward pass
            for (i in end - 1 downTo start + 1) {
                _currentCompareIndices.value = Pair(i - 1, i)
                smartDelay()
                
                if (arr[i] < arr[i - 1]) {
                    val temp = arr[i]
                    arr[i] = arr[i - 1]
                    arr[i - 1] = temp
                    addSortingStep("Swapping ${arr[i]} and ${arr[i - 1]} at indices $i and ${i-1}")
                    swapped = true
                }
            }
            
            start++
        } while (swapped)
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun combSort() {
        val arr = _array
        val shrinkFactor = 1.3
        var gap = arr.size
        var swapped = true
        clearSortingSteps()
        addSortingStep("Starting Comb Sort")

        while (gap > 1 || swapped) {
            // Update gap
            gap = maxOf(1, (gap / shrinkFactor).toInt())
            
            swapped = false
            
            for (i in 0 until arr.size - gap) {
                _currentCompareIndices.value = Pair(i, i + gap)
                smartDelay()
                
                if (arr[i] > arr[i + gap]) {
                    // Swap elements
                    val temp = arr[i]
                    arr[i] = arr[i + gap]
                    arr[i + gap] = temp
                    addSortingStep("Swapping ${arr[i]} and ${arr[i + gap]} at indices $i and ${i+gap}")
                    swapped = true
                }
            }
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    // Non-comparison based sorting algorithms
    private suspend fun countingSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        // Find range of input
        val max = arr.maxOrNull()!!
        val min = arr.minOrNull()!!
        val range = max - min + 1
        
        // Create counting array
        val count = MutableList(range.toInt()) { 0 }
        
        // Count occurrences of each unique object
        for (num in arr) {
            count[(num - min).toInt()]++
        }
        
        // Modify count array to store actual position of each object
        for (i in 1 until count.size) {
            count[i] += count[i - 1]
        }
        
        // Build output array
        val output = MutableList(arr.size) { 0.0 }
        for (i in arr.size - 1 downTo 0) {
            _currentCompareIndices.value = Pair(i, count[(arr[i] - min).toInt()] - 1)
            smartDelay()
            
            output[count[(arr[i] - min).toInt()] - 1] = arr[i]
            count[(arr[i] - min).toInt()]--
        }
        
        // Copy output to arr
        for (i in arr.indices) {
            arr[i] = output[i]
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun radixSortWrapper() {
        val arr = _array
        if (arr.isEmpty()) return
        
        // Find the maximum number to know number of digits
        val max = arr.maxOrNull()!!
        
        // Do counting sort for every digit
        var exp = 1
        clearSortingSteps()
        addSortingStep("Starting Radix Sort")

        while (max * exp > 0) {
            countingSortByDigit(arr, exp)
            exp *= 10
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    private suspend fun countingSortByDigit(arr: MutableList<Double>, exp: Int) {
        val n = arr.size
        val output = MutableList(n) { 0.0 }
        val count = MutableList(10) { 0 }
        
        // Store count of occurrences in count[]
        for (i in 0 until n) {
            val index = ((arr[i] * exp).toInt() % 10)
            count[index]++
        }
        
        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (i in 1 until 10) {
            count[i] += count[i - 1]
        }
        
        // Build the output array
        for (i in n - 1 downTo 0) {
            val index = ((arr[i] * exp).toInt() % 10)
            _currentCompareIndices.value = Pair(i, count[index] - 1)
            smartDelay()
            
            output[count[index] - 1] = arr[i]
            count[index]--
        }
        
        // Copy the output array to arr[]
        for (i in 0 until n) {
            arr[i] = output[i]
        }
    }

    private suspend fun bucketSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        // Find range of input
        val max = arr.maxOrNull()!!
        val min = arr.minOrNull()!!
        
        // Number of buckets
        val bucketCount = 10
        
        // Create empty buckets
        val buckets = List(bucketCount) { mutableListOf<Double>() }
        
        // Distribute input array elements into buckets
        for (num in arr) {
            val bucketIndex = ((num - min) * (bucketCount - 1) / (max - min)).toInt()
            buckets[bucketIndex].add(num)
        }
        
        // Sort individual buckets
        for (bucket in buckets) {
            bucket.sort()
        }
        
        // Concatenate all buckets into arr
        var index = 0
        for (bucket in buckets) {
            for (num in bucket) {
                _currentCompareIndices.value = Pair(index, index)
                smartDelay()
                
                arr[index++] = num
            }
        }
        addSortingStep("Sorting Complete")
        _currentCompareIndices.value = Pair(-1, -1)
    }

    // Update array manually based on input string
    fun parseInputArray(input: String) {
        try {
            val parsedArray = input.split(Regex("[,\\s]+"))
                .filter { it.isNotBlank() }
                .map { 
                    it.trim().toDoubleOrNull() ?: throw NumberFormatException("Invalid number: $it") 
                }
                .toList()

            if (parsedArray.isEmpty()) {
                throw IllegalArgumentException("Input array cannot be empty")
            }

            _array.clear()
            _array.addAll(parsedArray)
        } catch (e: NumberFormatException) {
            // Handle invalid input, perhaps show an error to the user
            println("Error parsing input: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Invalid input: ${e.message}")
        }
    }
}
