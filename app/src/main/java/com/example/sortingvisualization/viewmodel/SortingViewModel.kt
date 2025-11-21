package com.example.sortingvisualization.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sortingvisualization.algorithms.SortingAlgorithm
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow

class SortingViewModel : ViewModel() {


    var showSplash = mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            showSplash.value = false
        }
    }

    private companion object {
        const val MIN_SPEED = 1L
        const val MAX_SPEED = 500L
        const val DEFAULT_SPEED = 50L
    }

    // State properties
    private val _arraySize = mutableIntStateOf(50)
    val arraySize: Int by _arraySize

    private val _array = mutableStateListOf<Double>()
    val array: List<Double> = _array

    private val _isSorting = mutableStateOf(false)

    // Improved speed control with exponential scaling
    private val _sortingSpeed = mutableLongStateOf(DEFAULT_SPEED)
    var sortingSpeed: Long by _sortingSpeed

    // New property to control visualization granularity
    private val _visualizationGranularity = mutableIntStateOf(1)
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
        _array.addAll(createRandomArray(_arraySize.intValue))
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
        _arraySize.intValue = constrainedSize
        
        // Regenerate array with new size
        generateRandomArray()
        
        // Dismiss dialog
        dismissArraySizeDialog()
    }

    // Update sorting speed with exponential scaling
    fun updateSortingSpeed(newSpeed: Long) {
        // Exponential scaling: lower values mean faster sorting
        _sortingSpeed.longValue = newSpeed.coerceIn(MIN_SPEED, MAX_SPEED)
    }

    // New method to control visualization granularity
    fun updateVisualizationGranularity(newGranularity: Int) {
        _visualizationGranularity.intValue = newGranularity.coerceIn(1, 10)
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
                    
                    // Additional sorting algorithms
                    SortingAlgorithm.PIGEONHOLE_SORT -> pigeonholeSort()
                    SortingAlgorithm.GNOME_SORT -> gnomeSort()
                    SortingAlgorithm.CYCLE_SORT -> cycleSort()

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

    // Bubble Sort with detailed steps
    private suspend fun bubbleSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Bubble Sort")
        addSortingStep("Total elements: $n")

        for (i in 0 until n - 1) {
            var swapped = false
            addSortingStep("Pass ${i + 1}: Comparing adjacent elements")

            for (j in 0 until n - i - 1) {
                _currentCompareIndices.value = Pair(j, j + 1)
                smartDelay()

                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                    swapped = true

                    addSortingStep("Swap: ${arr[j]} ↔ ${arr[j + 1]} at indices $j and ${j + 1}")
                } else {
                    addSortingStep("No swap needed: ${arr[j]} ≤ ${arr[j + 1]}")
                }
            }

            if (!swapped) {
                addSortingStep("🏁 Array is already sorted. Terminating early.")
                break
            }
        }

        addSortingStep("✅ Bubble Sort completed")
    }

    // Selection Sort with detailed steps
    private suspend fun selectionSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Selection Sort")
        addSortingStep("Total elements: $n")

        for (i in 0 until n - 1) {
            var minIndex = i
            addSortingStep("Pass ${i + 1}: Finding minimum element")

            for (j in i + 1 until n) {
                _currentCompareIndices.value = Pair(minIndex, j)
                smartDelay()

                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                    addSortingStep("New minimum found: ${arr[minIndex]} at index $minIndex")
                }
            }

            if (minIndex != i) {
                // Swap elements
                val temp = arr[minIndex]
                arr[minIndex] = arr[i]
                arr[i] = temp

                addSortingStep("Swap: ${arr[i]} ↔ ${arr[minIndex]} at indices $i and $minIndex")
            } else {
                addSortingStep("No swap needed for index $i")
            }
        }

        addSortingStep("✅ Selection Sort completed")
    }

    // Insertion Sort with detailed steps
    private suspend fun insertionSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Insertion Sort")
        addSortingStep("Total elements: $n")

        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1

            addSortingStep("Pass ${i}: Inserting $key into sorted portion")

            while (j >= 0 && arr[j] > key) {
                _currentCompareIndices.value = Pair(j, j + 1)
                smartDelay()

                arr[j + 1] = arr[j]
                addSortingStep("Shifting ${arr[j]} one position right")
                j--
            }

            arr[j + 1] = key
            addSortingStep("Inserted $key at index ${j + 1}")
        }

        addSortingStep("✅ Insertion Sort completed")
    }

    // Merge Sort with detailed steps
    private suspend fun mergeSortWrapper() {
        val arr = _array
        clearSortingSteps()
        addSortingStep("🚀 Starting Merge Sort")
        addSortingStep("Total elements: ${arr.size}")

        mergeSort(arr, 0, arr.size - 1)

        addSortingStep("✅ Merge Sort completed")
    }

    private suspend fun mergeSort(arr: MutableList<Double>, left: Int, right: Int) {
        if (left < right) {
            val mid = (left + right) / 2
            addSortingStep("Dividing array: Left = $left, Right = $right, Mid = $mid")

            // Recursively sort left and right halves
            mergeSort(arr, left, mid)
            mergeSort(arr, mid + 1, right)

            // Merge the sorted halves
            merge(arr, left, mid, right)
        }
    }

    private suspend fun merge(arr: MutableList<Double>, left: Int, mid: Int, right: Int) {
        val leftArr = arr.subList(left, mid + 1).toMutableList()
        val rightArr = arr.subList(mid + 1, right + 1).toMutableList()

        addSortingStep("Merging subarrays: Left = $leftArr, Right = $rightArr")

        var i = 0
        var j = 0
        var k = left

        while (i < leftArr.size && j < rightArr.size) {
            _currentCompareIndices.value = Pair(left + i, mid + 1 + j)
            smartDelay()

            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i]
                addSortingStep("Placing ${leftArr[i]} from left subarray")
                i++
            } else {
                arr[k] = rightArr[j]
                addSortingStep("Placing ${rightArr[j]} from right subarray")
                j++
            }
            k++
        }

        // Handle remaining elements
        while (i < leftArr.size) {
            arr[k] = leftArr[i]
            addSortingStep("Placing remaining ${leftArr[i]} from left subarray")
            i++
            k++
        }

        while (j < rightArr.size) {
            arr[k] = rightArr[j]
            addSortingStep("Placing remaining ${rightArr[j]} from right subarray")
            j++
            k++
        }

        addSortingStep("Merge complete: ${arr.subList(left, right + 1)}")
    }

    // Quick Sort with detailed steps
    private suspend fun quickSortWrapper() {
        val arr = _array
        clearSortingSteps()
        addSortingStep("🚀 Starting Quick Sort")
        addSortingStep("Total elements: ${arr.size}")

        quickSort(arr, 0, arr.size - 1)

        addSortingStep("✅ Quick Sort completed")
    }

    private suspend fun quickSort(arr: MutableList<Double>, low: Int, high: Int) {
        if (low < high) {
            addSortingStep("Partitioning range: Low = $low, High = $high")
            val partitionIndex = partition(arr, low, high)
            
            addSortingStep("Partition index: $partitionIndex")
            
            // Recursively sort left and right partitions
            quickSort(arr, low, partitionIndex - 1)
            quickSort(arr, partitionIndex + 1, high)
        }
    }

    private suspend fun partition(arr: MutableList<Double>, low: Int, high: Int): Int {
        val pivot = arr[high]
        addSortingStep("Pivot selected: $pivot at index $high")
        
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

                addSortingStep("Swap: ${arr[i]} ↔ ${arr[j]} (smaller than pivot)")
            }
        }

        // Place pivot in correct position
        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp

        addSortingStep("Final pivot placement: ${arr[i + 1]} at index ${i + 1}")
        return i + 1
    }

    // Heap Sort with detailed steps
    private suspend fun heapSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Heap Sort")
        addSortingStep("Total elements: $n")

        // Build max heap
        for (i in n / 2 - 1 downTo 0) {
            addSortingStep("Building max heap: Heapifying subtree rooted at index $i")
            heapify(arr, n, i)
        }

        // Extract elements from heap one by one
        for (i in n - 1 downTo 1) {
            // Move current root to end
            val temp = arr[0]
            arr[0] = arr[i]
            arr[i] = temp

            addSortingStep("Swap root ${arr[0]} with last element ${arr[i]}")

            // Call max heapify on the reduced heap
            heapify(arr, i, 0)
        }

        addSortingStep("✅ Heap Sort completed")
    }

    private suspend fun heapify(arr: MutableList<Double>, n: Int, i: Int) {
        var largest = i
        val left = 2 * i + 1
        val right = 2 * i + 2

        _currentCompareIndices.value = Pair(i, largest)
        smartDelay()

        if (left < n && arr[left] > arr[largest]) {
            largest = left
            addSortingStep("Left child ${arr[left]} is larger than root ${arr[i]}")
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right
            addSortingStep("Right child ${arr[right]} is larger than current largest")
        }

        if (largest != i) {
            // Swap elements
            val swap = arr[i]
            arr[i] = arr[largest]
            arr[largest] = swap

            addSortingStep("Swap: ${arr[i]} ↔ ${arr[largest]} to maintain heap property")

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest)
        }
    }

    // Radix Sort with decimal number support
    private suspend fun radixSortWrapper() {
        val arr = _array
        if (arr.isEmpty()) return

        clearSortingSteps()
        addSortingStep("🚀 Starting Radix Sort")
        addSortingStep("Total elements: ${arr.size}")

        // Step 1: Normalize - Find maximum decimal places
        val maxDecimalPlaces = findMaxDecimalPlaces(arr)
        addSortingStep("Maximum decimal places detected: $maxDecimalPlaces")

        // Step 2: Normalize numbers to integers
        val multiplier = 10.0.pow(maxDecimalPlaces)
        val intArr = arr.map { (it * multiplier).toLong() }.toMutableList()
        addSortingStep("Normalized array: $intArr")

        // Step 3: Find the maximum number to know number of digits
        val max = intArr.maxOrNull() ?: return
        addSortingStep("Maximum normalized value: $max")

        // Step 4: Do counting sort for every digit
        var exp = 1L
        while (max / exp > 0) {
            countingSortByDigit(intArr, exp)
            exp *= 10
            addSortingStep("Sorting by digit place: $exp")
        }

        // Step 5: Convert back to original decimal form
        val sortedArr = intArr.map { it / multiplier }.toMutableList()
        
        // Update the original array
        for (i in arr.indices) {
            arr[i] = sortedArr[i]
        }

        addSortingStep("✅ Radix Sort completed")
    }

    // Helper function to find maximum decimal places
    private fun findMaxDecimalPlaces(arr: List<Double>): Int {
        return arr.maxOfOrNull { 
            val decimalPart = abs(it - it.toLong())
            var decimalPlaces = 0
            var current = decimalPart
            
            while (current > 0 && decimalPlaces < 10) {
                current *= 10
                current -= current.toLong()
                decimalPlaces++
            }
            
            decimalPlaces
        } ?: 0
    }

    // Modified counting sort for long integers
    private fun countingSortByDigit(arr: MutableList<Long>, exp: Long) {
        val n = arr.size
        val output = MutableList(n) { 0L }
        val count = MutableList(10) { 0 }

        // Store count of occurrences in count[]
        for (i in 0 until n) {
            val index = ((arr[i] / exp) % 10).toInt()
            count[index]++
        }

        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (i in 1 until 10) {
            count[i] += count[i - 1]
        }

        // Build the output array
        for (i in n - 1 downTo 0) {
            val index = ((arr[i] / exp) % 10).toInt()
            output[count[index] - 1] = arr[i]
            count[index]--
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current digit
        for (i in 0 until n) {
            arr[i] = output[i]
        }
    }

    // Shell Sort with detailed steps
    private suspend fun shellSort() {
        val arr = _array
        val n = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Shell Sort")
        addSortingStep("Total elements: $n")

        var gap = n / 2
        
        while (gap > 0) {
            for (i in gap until n) {
                val temp = arr[i]
                var j = i
                
                while (j >= gap && arr[j - gap] > temp) {
                    _currentCompareIndices.value = Pair(j - gap, j)
                    smartDelay()
                    
                    arr[j] = arr[j - gap]
                    addSortingStep("Shifting ${arr[j - gap]} one position right")
                    j -= gap
                }
                
                addSortingStep("Inserting $temp at index $j")
                arr[j] = temp
            }
            gap /= 2
        }
        addSortingStep("✅ Shell Sort completed")
    }

    // Cocktail Shaker Sort with detailed steps
    private suspend fun cocktailShakerSort() {
        val arr = _array
        var swapped: Boolean
        var start = 0
        var end = arr.size
        clearSortingSteps()
        addSortingStep("🚀 Starting Cocktail Shaker Sort")
        addSortingStep("Total elements: ${arr.size}")

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
                    addSortingStep("Swap: ${arr[i]} ↔ ${arr[i + 1]} at indices $i and ${i+1}")
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
                    addSortingStep("Swap: ${arr[i]} ↔ ${arr[i - 1]} at indices $i and ${i-1}")
                    swapped = true
                }
            }
            
            start++
        } while (swapped)
        addSortingStep("✅ Cocktail Shaker Sort completed")
    }

    // Comb Sort with detailed steps
    private suspend fun combSort() {
        val arr = _array
        val shrinkFactor = 1.3
        var gap = arr.size
        var swapped = true
        clearSortingSteps()
        addSortingStep("🚀 Starting Comb Sort")
        addSortingStep("Total elements: ${arr.size}")

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
                    addSortingStep("Swap: ${arr[i]} ↔ ${arr[i + gap]} at indices $i and ${i+gap}")
                    swapped = true
                }
            }
        }
        addSortingStep("✅ Comb Sort completed")
    }

    // Counting Sort with detailed steps
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
        addSortingStep("✅ Counting Sort completed")
    }

    // Bucket Sort with detailed steps
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
        addSortingStep("✅ Bucket Sort completed")
    }

    // Pigeonhole Sort
    private suspend fun pigeonholeSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        // Find range of input
        val min = arr.minOrNull()!!
        val max = arr.maxOrNull()!!
        val range = max - min + 1
        
        // Create pigeonhole array
        val pigeonhole = MutableList(range.toInt()) { mutableListOf<Double>() }
        
        // Distribute input array elements into pigeonholes
        for (num in arr) {
            pigeonhole[(num - min).toInt()].add(num)
        }
        
        // Sort individual pigeonholes
        for (hole in pigeonhole) {
            hole.sort()
        }
        
        // Concatenate all pigeonholes into arr
        var index = 0
        for (hole in pigeonhole) {
            for (num in hole) {
                _currentCompareIndices.value = Pair(index, index)
                smartDelay()
                
                arr[index++] = num
            }
        }
        addSortingStep("✅ Pigeonhole Sort completed")
    }

    private suspend fun insertionSort(arr: MutableList<Double>, start: Int, end: Int) {
        for (i in start + 1..end) {
            val key = arr[i]
            var j = i - 1
            
            while (j >= start && arr[j] > key) {
                _currentCompareIndices.value = Pair(j, j + 1)
                smartDelay()
                
                arr[j + 1] = arr[j]
                j--
            }
            
            arr[j + 1] = key
        }
    }

    // Gnome Sort
    private suspend fun gnomeSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        var pos = 0
        while (pos < arr.size) {
            if (pos == 0 || arr[pos] >= arr[pos - 1]) {
                pos++
            } else {
                // Swap elements
                val temp = arr[pos]
                arr[pos] = arr[pos - 1]
                arr[pos - 1] = temp
                
                addSortingStep("Swap: ${arr[pos]} ↔ ${arr[pos - 1]} at indices $pos and ${pos - 1}")
                pos--
            }
            _currentCompareIndices.value = Pair(pos, pos)
            smartDelay()
        }
        addSortingStep("✅ Gnome Sort completed")
    }

    // Cycle Sort
    private suspend fun cycleSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        var writes = 0
        for (cycleStart in 0 until arr.size - 1) {
            var item = arr[cycleStart]
            
            var pos = cycleStart
            for (i in cycleStart + 1 until arr.size) {
                if (arr[i] < item) {
                    pos++
                }
            }
            
            if (pos == cycleStart) {
                continue
            }
            
            while (item == arr[pos]) {
                pos++
            }
            
            val temp = arr[pos]
            arr[pos] = item
            item = temp
            
            writes++
            
            while (pos != cycleStart) {
                pos = cycleStart
                for (i in cycleStart + 1 until arr.size) {
                    if (arr[i] < item) {
                        pos++
                    }
                }
                
                while (item == arr[pos]) {
                    pos++
                }
                
                val temp2 = arr[pos]
                arr[pos] = item
                item = temp2
                
                writes++
            }
            
            if (writes > 0) {
                addSortingStep("Cycle sort: ${arr[cycleStart]} at index $cycleStart")
            }
            _currentCompareIndices.value = Pair(cycleStart, cycleStart)
            smartDelay()
        }
        addSortingStep("✅ Cycle Sort completed")
    }

    // Pancake Sort
    private suspend fun pancakeSort() {
        val arr = _array
        if (arr.isEmpty()) return
        
        var curSize = arr.size
        while (curSize > 1) {
            var mi = 0
            for (i in 0 until curSize) {
                if (arr[i] > arr[mi]) {
                    mi = i
                }
            }
            
            if (mi == curSize - 1) {
                break
            }
            
            if (mi == 0) {
                // Swap elements
                val temp = arr[curSize - 1]
                arr[curSize - 1] = arr[mi]
                arr[mi] = temp
                
                addSortingStep("Swap: ${arr[curSize - 1]} ↔ ${arr[mi]} at indices ${curSize - 1} and $mi")
            } else {
                // Swap elements
                val temp = arr[mi]
                arr[mi] = arr[0]
                arr[0] = temp
                
                addSortingStep("Swap: ${arr[mi]} ↔ ${arr[0]} at indices $mi and 0")
                
                // Swap elements
                val temp2 = arr[curSize - 1]
                arr[curSize - 1] = arr[mi]
                arr[mi] = temp2
                
                addSortingStep("Swap: ${arr[curSize - 1]} ↔ ${arr[mi]} at indices ${curSize - 1} and $mi")
            }
            
            curSize--
            _currentCompareIndices.value = Pair(curSize, curSize)
            smartDelay()
        }
        addSortingStep("✅ Pancake Sort completed")
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

