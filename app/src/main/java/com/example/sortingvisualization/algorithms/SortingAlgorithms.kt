package com.example.sortingvisualization.algorithms

import kotlinx.coroutines.delay

class SortingAlgorithms {

    enum class SortingStep {
        INITIALIZATION,
        COMPARISON,
        SWAP,
        ITERATION,
        COMPLETION
    }


    data class AlgorithmBox(
        val algorithmName: String,
        val currentStep: SortingStep,
        val stepDescription: String,
        val iterationCount: Int,
        val comparisonCount: Int,
        val swapCount: Int
    )


    suspend fun bubbleSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        var iterationCount = 0
        var comparisonCount = 0
        var swapCount = 0


        onStepUpdate(AlgorithmBox(
            algorithmName = "Bubble Sort",
            currentStep = SortingStep.INITIALIZATION,
            stepDescription = "Starting Bubble Sort with array of size $n",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)

        for (i in 0 until n - 1) {
            iterationCount++
            

            onStepUpdate(AlgorithmBox(
                algorithmName = "Bubble Sort",
                currentStep = SortingStep.ITERATION,
                stepDescription = "Iteration ${i + 1} of ${n - 1}",
                iterationCount = iterationCount,
                comparisonCount = comparisonCount,
                swapCount = swapCount
            ))
            delay(delay)

            for (j in 0 until n - i - 1) {
                comparisonCount++
                

                onStepUpdate(AlgorithmBox(
                    algorithmName = "Bubble Sort",
                    currentStep = SortingStep.COMPARISON,
                    stepDescription = "Comparing elements at indices $j and ${j + 1}",
                    iterationCount = iterationCount,
                    comparisonCount = comparisonCount,
                    swapCount = swapCount
                ))
                onCompare(j, j + 1)
                delay(delay)
                
                if (arr[j] > arr[j + 1]) {
                    swapCount++
                    

                    onStepUpdate(AlgorithmBox(
                        algorithmName = "Bubble Sort",
                        currentStep = SortingStep.SWAP,
                        stepDescription = "Swapping elements at indices $j and ${j + 1}",
                        iterationCount = iterationCount,
                        comparisonCount = comparisonCount,
                        swapCount = swapCount
                    ))
                    
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                    
                    onSwap(j, j + 1)
                    delay(delay)
                }
            }
        }

        // Completion Step
        onStepUpdate(AlgorithmBox(
            algorithmName = "Bubble Sort",
            currentStep = SortingStep.COMPLETION,
            stepDescription = "Sorting complete",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)
    }


    suspend fun selectionSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        var iterationCount = 0
        var comparisonCount = 0
        var swapCount = 0

        // Initialization Step
        onStepUpdate(AlgorithmBox(
            algorithmName = "Selection Sort",
            currentStep = SortingStep.INITIALIZATION,
            stepDescription = "Starting Selection Sort with array of size $n",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)

        for (i in 0 until n - 1) {
            iterationCount++
            
            // Iteration Start Step
            onStepUpdate(AlgorithmBox(
                algorithmName = "Selection Sort",
                currentStep = SortingStep.ITERATION,
                stepDescription = "Iteration ${i + 1} of ${n - 1}",
                iterationCount = iterationCount,
                comparisonCount = comparisonCount,
                swapCount = swapCount
            ))
            delay(delay)

            var minIndex = i
            for (j in i + 1 until n) {
                comparisonCount++
                

                onStepUpdate(AlgorithmBox(
                    algorithmName = "Selection Sort",
                    currentStep = SortingStep.COMPARISON,
                    stepDescription = "Comparing elements at indices $minIndex and $j",
                    iterationCount = iterationCount,
                    comparisonCount = comparisonCount,
                    swapCount = swapCount
                ))
                onCompare(minIndex, j)
                delay(delay)
                
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            
            if (minIndex != i) {
                swapCount++
                

                onStepUpdate(AlgorithmBox(
                    algorithmName = "Selection Sort",
                    currentStep = SortingStep.SWAP,
                    stepDescription = "Swapping elements at indices $i and $minIndex",
                    iterationCount = iterationCount,
                    comparisonCount = comparisonCount,
                    swapCount = swapCount
                ))
                
                val temp = arr[i]
                arr[i] = arr[minIndex]
                arr[minIndex] = temp
                
                onSwap(i, minIndex)
                delay(delay)
            }
        }


        onStepUpdate(AlgorithmBox(
            algorithmName = "Selection Sort",
            currentStep = SortingStep.COMPLETION,
            stepDescription = "Sorting complete",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)
    }


    suspend fun insertionSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        var iterationCount = 0
        var comparisonCount = 0
        var swapCount = 0


        onStepUpdate(AlgorithmBox(
            algorithmName = "Insertion Sort",
            currentStep = SortingStep.INITIALIZATION,
            stepDescription = "Starting Insertion Sort with array of size $n",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)

        for (i in 1 until n) {
            iterationCount++
            

            onStepUpdate(AlgorithmBox(
                algorithmName = "Insertion Sort",
                currentStep = SortingStep.ITERATION,
                stepDescription = "Iteration ${i} of ${n - 1}",
                iterationCount = iterationCount,
                comparisonCount = comparisonCount,
                swapCount = swapCount
            ))
            delay(delay)

            val key = arr[i]
            var j = i - 1
            
            while (j >= 0) {
                comparisonCount++
                
                // Comparison Step
                onStepUpdate(AlgorithmBox(
                    algorithmName = "Insertion Sort",
                    currentStep = SortingStep.COMPARISON,
                    stepDescription = "Comparing elements at indices $j and ${j + 1}",
                    iterationCount = iterationCount,
                    comparisonCount = comparisonCount,
                    swapCount = swapCount
                ))
                onCompare(j, j + 1)
                delay(delay)
                
                if (arr[j] > key) {
                    swapCount++
                    

                    onStepUpdate(AlgorithmBox(
                        algorithmName = "Insertion Sort",
                        currentStep = SortingStep.SWAP,
                        stepDescription = "Shifting element at index $j to the right",
                        iterationCount = iterationCount,
                        comparisonCount = comparisonCount,
                        swapCount = swapCount
                    ))
                    
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


        onStepUpdate(AlgorithmBox(
            algorithmName = "Insertion Sort",
            currentStep = SortingStep.COMPLETION,
            stepDescription = "Sorting complete",
            iterationCount = iterationCount,
            comparisonCount = comparisonCount,
            swapCount = swapCount
        ))
        delay(delay)
    }

    // Merge Sort: Divides the array into two halves, recursively sorts them, and then merges
    suspend fun mergeSort(
        arr: MutableList<Int>,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        mergeSortHelper(arr, 0, arr.size - 1, onCompare, onSwap, onStepUpdate, delay)
    }

    private suspend fun mergeSortHelper(
        arr: MutableList<Int>,
        left: Int,
        right: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long
    ) {
        if (left < right) {
            val mid = left + (right - left) / 2
            
            // Split Step
            onStepUpdate(AlgorithmBox(
                algorithmName = "Merge Sort",
                currentStep = SortingStep.ITERATION,
                stepDescription = "Splitting array into two halves at index $mid",
                iterationCount = 0,
                comparisonCount = 0,
                swapCount = 0
            ))
            delay(delay)

            mergeSortHelper(arr, left, mid, onCompare, onSwap, onStepUpdate, delay)
            mergeSortHelper(arr, mid + 1, right, onCompare, onSwap, onStepUpdate, delay)
            
            merge(arr, left, mid, right, onCompare, onSwap, onStepUpdate, delay)
        }
    }

    private suspend fun merge(
        arr: MutableList<Int>,
        left: Int,
        mid: Int,
        right: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long
    ) {
        val leftArr = arr.subList(left, mid + 1).toMutableList()
        val rightArr = arr.subList(mid + 1, right + 1).toMutableList()
        
        var i = 0
        var j = 0
        var k = left
        
        // Merge Step
        onStepUpdate(AlgorithmBox(
            algorithmName = "Merge Sort",
            currentStep = SortingStep.ITERATION,
            stepDescription = "Merging two halves of the array",
            iterationCount = 0,
            comparisonCount = 0,
            swapCount = 0
        ))
        delay(delay)

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
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        quickSortHelper(arr, 0, arr.size - 1, onCompare, onSwap, onStepUpdate, delay)
    }

    private suspend fun quickSortHelper(
        arr: MutableList<Int>,
        low: Int,
        high: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long
    ) {
        if (low < high) {
            val pivotIndex = partition(arr, low, high, onCompare, onSwap, onStepUpdate, delay)
            quickSortHelper(arr, low, pivotIndex - 1, onCompare, onSwap, onStepUpdate, delay)
            quickSortHelper(arr, pivotIndex + 1, high, onCompare, onSwap, onStepUpdate, delay)
        }
    }

    private suspend fun partition(
        arr: MutableList<Int>,
        low: Int,
        high: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long
    ): Int {
        val pivot = arr[high]
        var i = low - 1
        
        // Partition Step
        onStepUpdate(AlgorithmBox(
            algorithmName = "Quick Sort",
            currentStep = SortingStep.ITERATION,
            stepDescription = "Partitioning array around pivot $pivot",
            iterationCount = 0,
            comparisonCount = 0,
            swapCount = 0
        ))
        delay(delay)

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
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long = 50
    ) {
        val n = arr.size
        
        // Build max heap
        for (i in n / 2 - 1 downTo 0) {
            heapify(arr, n, i, onCompare, onSwap, onStepUpdate, delay)
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
            heapify(arr, i, 0, onCompare, onSwap, onStepUpdate, delay)
        }
    }

    private suspend fun heapify(
        arr: MutableList<Int>,
        n: Int,
        i: Int,
        onCompare: (Int, Int) -> Unit,
        onSwap: (Int, Int) -> Unit,
        onStepUpdate: (AlgorithmBox) -> Unit,
        delay: Long
    ) {
        var largest = i
        val left = 2 * i + 1
        val right = 2 * i + 2
        
        // Heapify Step
        onStepUpdate(AlgorithmBox(
            algorithmName = "Heap Sort",
            currentStep = SortingStep.ITERATION,
            stepDescription = "Heapifying subtree rooted at index $i",
            iterationCount = 0,
            comparisonCount = 0,
            swapCount = 0
        ))
        delay(delay)

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
            heapify(arr, n, largest, onCompare, onSwap, onStepUpdate, delay)
        }
    }
}
