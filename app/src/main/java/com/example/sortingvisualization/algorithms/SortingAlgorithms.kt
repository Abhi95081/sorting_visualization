package com.example.sortingvisualization.algorithms

import kotlinx.coroutines.delay

class SortingAlgorithms {
    // Bubble Sort: Repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order
    suspend fun bubbleSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                onCompare(j, j + 1)
                delay(delay)
                
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                    
                    onSwap(j, j + 1)
                    delay(delay)
                }
            }
        }
    }

    // Selection Sort: Divides input list into two parts - sorted and unsorted
    suspend fun selectionSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        for (i in 0 until n - 1) {
            var minIndex = i
            for (j in i + 1 until n) {
                onCompare(minIndex, j)
                delay(delay)
                
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            
            if (minIndex != i) {
                // Swap elements
                val temp = arr[i]
                arr[i] = arr[minIndex]
                arr[minIndex] = temp
                
                onSwap(i, minIndex)
                delay(delay)
            }
        }
    }

    // Insertion Sort: Builds the final sorted array one item at a time
    suspend fun insertionSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            
            while (j >= 0) {
                onCompare(j, j + 1)
                delay(delay)
                
                if (arr[j] > key) {
                    arr[j + 1] = arr[j]
                    onSwap(j, j + 1)
                    delay(delay)
                    j--
                } else {
                    break
                }
            }
            arr[j + 1] = key
        }
    }

    // Merge Sort: Divides the array into two halves, recursively sorts them, and then merges
    suspend fun mergeSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        mergeSortHelper(arr, 0, arr.size - 1, onCompare, onSwap, delay)
    }

    private suspend fun mergeSortHelper(
        arr: MutableList<Int>,
        left: Int,
        right: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long
    ) {
        if (left < right) {
            val mid = left + (right - left) / 2
            
            mergeSortHelper(arr, left, mid, onCompare, onSwap, delay)
            mergeSortHelper(arr, mid + 1, right, onCompare, onSwap, delay)
            
            merge(arr, left, mid, right, onCompare, onSwap, delay)
        }
    }

    private suspend fun merge(
        arr: MutableList<Int>,
        left: Int,
        mid: Int,
        right: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long
    ) {
        val leftArr = arr.subList(left, mid + 1).toMutableList()
        val rightArr = arr.subList(mid + 1, right + 1).toMutableList()
        
        var i = 0
        var j = 0
        var k = left
        
        while (i < leftArr.size && j < rightArr.size) {
            onCompare(left + i, mid + 1 + j)
            delay(delay)
            
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i]
                onSwap(k, left + i)
                delay(delay)
                i++
            } else {
                arr[k] = rightArr[j]
                onSwap(k, mid + 1 + j)
                delay(delay)
                j++
            }
            k++
        }
        
        while (i < leftArr.size) {
            arr[k] = leftArr[i]
            onSwap(k, left + i)
            delay(delay)
            i++
            k++
        }
        
        while (j < rightArr.size) {
            arr[k] = rightArr[j]
            onSwap(k, mid + 1 + j)
            delay(delay)
            j++
            k++
        }
    }

    // Quick Sort: Uses divide and conquer strategy with a pivot
    suspend fun quickSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        quickSortHelper(arr, 0, arr.size - 1, onCompare, onSwap, delay)
    }

    private suspend fun quickSortHelper(
        arr: MutableList<Int>,
        low: Int,
        high: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long
    ) {
        if (low < high) {
            val pivotIndex = partition(arr, low, high, onCompare, onSwap, delay)
            quickSortHelper(arr, low, pivotIndex - 1, onCompare, onSwap, delay)
            quickSortHelper(arr, pivotIndex + 1, high, onCompare, onSwap, delay)
        }
    }

    private suspend fun partition(
        arr: MutableList<Int>,
        low: Int,
        high: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long
    ): Int {
        val pivot = arr[high]
        var i = low - 1
        
        for (j in low until high) {
            onCompare(j, high)
            delay(delay)
            
            if (arr[j] < pivot) {
                i++
                
                // Swap elements
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
                
                onSwap(i, j)
                delay(delay)
            }
        }
        
        // Place pivot in correct position
        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp
        
        onSwap(i + 1, high)
        delay(delay)
        
        return i + 1
    }

    // Heap Sort: Converts array into a heap data structure
    suspend fun heapSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        
        // Build max heap
        for (i in n / 2 - 1 downTo 0) {
            heapify(arr, n, i, onCompare, onSwap, delay)
        }
        
        // Extract elements from heap one by one
        for (i in n - 1 downTo 1) {
            // Move current root to end
            val temp = arr[0]
            arr[0] = arr[i]
            arr[i] = temp
            
            onSwap(0, i)
            delay(delay)
            
            // Call max heapify on the reduced heap
            heapify(arr, i, 0, onCompare, onSwap, delay)
        }
    }

    private suspend fun heapify(
        arr: MutableList<Int>,
        n: Int,
        i: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        delay: Long
    ) {
        var largest = i
        val left = 2 * i + 1
        val right = 2 * i + 2
        
        // Check if left child is larger than root
        if (left < n) {
            onCompare(largest, left)
            delay(delay)
            
            if (arr[left] > arr[largest]) {
                largest = left
            }
        }
        
        // Check if right child is larger than largest so far
        if (right < n) {
            onCompare(largest, right)
            delay(delay)
            
            if (arr[right] > arr[largest]) {
                largest = right
            }
        }
        
        // If largest is not root
        if (largest != i) {
            // Swap
            val swap = arr[i]
            arr[i] = arr[largest]
            arr[largest] = swap
            
            onSwap(i, largest)
            delay(delay)
            
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest, onCompare, onSwap, delay)
        }
    }
}
