package com.example.sortingvisualization.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
        const val MIN_SPEED = 10L
        const val MAX_SPEED = 2000L
    }

    // State properties
    private val _arraySize = mutableStateOf(50)
    val arraySize: Int by _arraySize

    private val _array = mutableStateListOf<Int>()
    val array: List<Int> = _array

    private val _isSorting = mutableStateOf(false)
    val isSorting: Boolean by _isSorting

    private val _sortingSpeed = mutableStateOf(100L)
    var sortingSpeed: Long by _sortingSpeed

    private val _currentCompareIndices = mutableStateOf(Pair(-1, -1))
    val currentCompareIndices: Pair<Int, Int> by _currentCompareIndices

    private var sortingJob: Job? = null

    private val _selectedAlgorithm = mutableStateOf(SortingAlgorithm.BUBBLE_SORT)
    var selectedAlgorithm: SortingAlgorithm by _selectedAlgorithm

    // Generate a random array with the specified size
    private fun createRandomArray(size: Int): List<Int> {
        return List(size) { (1..size).random() }
    }

    // Generate a new random array
    fun generateRandomArray() {
        cancelSorting()
        _array.clear()
        _array.addAll(createRandomArray(_arraySize.value))
    }

    // Update array size with validation
    fun updateArraySize(newSize: Int) {
        cancelSorting()
        _arraySize.value = newSize.coerceIn(MIN_ARRAY_SIZE, MAX_ARRAY_SIZE)
        generateRandomArray()
    }

    // Update sorting speed with validation
    fun updateSortingSpeed(newSpeed: Long) {
        _sortingSpeed.value = newSpeed.coerceIn(MIN_SPEED, MAX_SPEED)
    }

    // Start sorting the array with the selected algorithm
    fun startSorting() {
        if (_isSorting.value) return
        _isSorting.value = true
        _currentCompareIndices.value = Pair(-1, -1)

        sortingJob = viewModelScope.launch {
            when (selectedAlgorithm) {
                SortingAlgorithm.BUBBLE_SORT -> bubbleSort()
                SortingAlgorithm.SELECTION_SORT -> selectionSort()
                SortingAlgorithm.INSERTION_SORT -> insertionSort()
                SortingAlgorithm.MERGE_SORT -> mergeSortWrapper()
                SortingAlgorithm.QUICK_SORT -> quickSortWrapper()
                SortingAlgorithm.HEAP_SORT -> heapSort()
            }
            _isSorting.value = false
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

    // Sorting algorithm implementations
    private suspend fun bubbleSort() {
        val arr = _array
        val n = arr.size
        for (i in 0 until n - 1) {
            var swapped = false
            for (j in 0 until n - i - 1) {
                _currentCompareIndices.value = Pair(j, j + 1)
                delay(sortingSpeed)

                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j + 1].also { arr[j + 1] = arr[j] }
                    swapped = true
                }
            }
            if (!swapped) break
        }
    }

    private suspend fun selectionSort() {
        val arr = _array
        val n = arr.size
        for (i in 0 until n - 1) {
            var minIndex = i
            for (j in i + 1 until n) {
                _currentCompareIndices.value = Pair(minIndex, j)
                delay(sortingSpeed)

                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            if (minIndex != i) {
                arr[i] = arr[minIndex].also { arr[minIndex] = arr[i] }
            }
        }
    }

    private suspend fun insertionSort() {
        val arr = _array
        val n = arr.size
        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                _currentCompareIndices.value = Pair(j, j + 1)
                delay(sortingSpeed)

                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
        }
    }

    private suspend fun mergeSortWrapper() {
        val arr = _array.toMutableList()
        mergeSort(arr, 0, arr.size - 1)
        _array.clear()
        _array.addAll(arr)
    }

    private suspend fun mergeSort(arr: MutableList<Int>, left: Int, right: Int) {
        if (left < right) {
            val mid = (left + right) / 2
            mergeSort(arr, left, mid)
            mergeSort(arr, mid + 1, right)
            merge(arr, left, mid, right)
        }
    }

    private suspend fun merge(arr: MutableList<Int>, left: Int, mid: Int, right: Int) {
        val leftArr = arr.subList(left, mid + 1).toMutableList()
        val rightArr = arr.subList(mid + 1, right + 1).toMutableList()

        var i = 0
        var j = 0
        var k = left

        while (i < leftArr.size && j < rightArr.size) {
            _currentCompareIndices.value = Pair(left + i, mid + 1 + j)
            delay(sortingSpeed)

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
        quickSort(arr, 0, arr.size - 1)
        _array.clear()
        _array.addAll(arr)
    }

    private suspend fun quickSort(arr: MutableList<Int>, low: Int, high: Int) {
        if (low < high) {
            val pivotIndex = partition(arr, low, high)
            quickSort(arr, low, pivotIndex - 1)
            quickSort(arr, pivotIndex + 1, high)
        }
    }

    private suspend fun partition(arr: MutableList<Int>, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1

        for (j in low until high) {
            _currentCompareIndices.value = Pair(j, high)
            delay(sortingSpeed)

            if (arr[j] < pivot) {
                i++
                arr[i] = arr[j].also { arr[j] = arr[i] }
            }
        }

        arr[i + 1] = arr[high].also { arr[high] = arr[i + 1] }
        return i + 1
    }

    private suspend fun heapSort() {
        val arr = _array
        val n = arr.size

        for (i in n / 2 - 1 downTo 0) heapify(arr, n, i)
        for (i in n - 1 downTo 1) {
            arr[0] = arr[i].also { arr[i] = arr[0] }
            heapify(arr, i, 0)
        }
    }

    private suspend fun heapify(arr: MutableList<Int>, n: Int, i: Int) {
        var largest = i
        val left = 2 * i + 1
        val right = 2 * i + 2

        if (left < n && arr[left] > arr[largest]) largest = left
        if (right < n && arr[right] > arr[largest]) largest = right

        if (largest != i) {
            arr[i] = arr[largest].also { arr[largest] = arr[i] }
            heapify(arr, n, largest)
        }
    }

    // Update array manually based on input string
    fun updateArrayManually(input: String) {
        try {
            val parsedArray = input.split(Regex("[,\\s]+"))
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toList()

            _array.clear()
            _array.addAll(parsedArray)
        } catch (e: Exception) {
            // Handle invalid input gracefully
        }
    }
}
