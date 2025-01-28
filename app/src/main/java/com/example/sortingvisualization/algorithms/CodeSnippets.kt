package com.example.sortingvisualization.algorithms

object CodeSnippets {
    private val algorithmCodeSnippets = mapOf(
        SortingAlgorithm.BUBBLE_SORT to """
            fun bubbleSort(arr: MutableList<Int>) {
                val n = arr.size
                for (i in 0 until n - 1) {
                    for (j in 0 until n - i - 1) {
                        if (arr[j] > arr[j + 1]) {
                            // Swap elements
                            val temp = arr[j]
                            arr[j] = arr[j + 1]
                            arr[j + 1] = temp
                        }
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.SELECTION_SORT to """
            fun selectionSort(arr: MutableList<Int>) {
                val n = arr.size
                for (i in 0 until n - 1) {
                    var minIndex = i
                    for (j in i + 1 until n) {
                        if (arr[j] < arr[minIndex]) {
                            minIndex = j
                        }
                    }
                    // Swap the found minimum element
                    val temp = arr[minIndex]
                    arr[minIndex] = arr[i]
                    arr[i] = temp
                }
            }
        """.trimIndent(),
        SortingAlgorithm.INSERTION_SORT to """
            fun insertionSort(arr: MutableList<Int>) {
                for (i in 1 until arr.size) {
                    val key = arr[i]
                    var j = i - 1
                    while (j >= 0 && arr[j] > key) {
                        arr[j + 1] = arr[j]
                        j--
                    }
                    arr[j + 1] = key
                }
            }
        """.trimIndent(),
        SortingAlgorithm.MERGE_SORT to """
            fun mergeSort(arr: MutableList<Int>, left: Int, right: Int) {
                if (left < right) {
                    val mid = (left + right) / 2
                    mergeSort(arr, left, mid)
                    mergeSort(arr, mid + 1, right)
                    merge(arr, left, mid, right)
                }
            }

            fun merge(arr: MutableList<Int>, left: Int, mid: Int, right: Int) {
                val leftArr = arr.subList(left, mid + 1).toMutableList()
                val rightArr = arr.subList(mid + 1, right + 1).toMutableList()

                var i = 0
                var j = 0
                var k = left

                while (i < leftArr.size && j < rightArr.size) {
                    if (leftArr[i] <= rightArr[j]) {
                        arr[k++] = leftArr[i++]
                    } else {
                        arr[k++] = rightArr[j++]
                    }
                }

                while (i < leftArr.size) arr[k++] = leftArr[i++]
                while (j < rightArr.size) arr[k++] = rightArr[j++]
            }
        """.trimIndent(),
        SortingAlgorithm.QUICK_SORT to """
            fun quickSort(arr: MutableList<Int>, low: Int, high: Int) {
                if (low < high) {
                    val partitionIndex = partition(arr, low, high)
                    quickSort(arr, low, partitionIndex - 1)
                    quickSort(arr, partitionIndex + 1, high)
                }
            }

            fun partition(arr: MutableList<Int>, low: Int, high: Int): Int {
                val pivot = arr[high]
                var i = low - 1

                for (j in low until high) {
                    if (arr[j] < pivot) {
                        i++
                        // Swap elements
                        val temp = arr[i]
                        arr[i] = arr[j]
                        arr[j] = temp
                    }
                }

                // Place pivot in correct position
                val temp = arr[i + 1]
                arr[i + 1] = arr[high]
                arr[high] = temp

                return i + 1
            }
        """.trimIndent(),
        SortingAlgorithm.HEAP_SORT to """
            fun heapSort(arr: MutableList<Int>) {
                val n = arr.size

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

                    // Call max heapify on the reduced heap
                    heapify(arr, i, 0)
                }
            }

            fun heapify(arr: MutableList<Int>, n: Int, i: Int) {
                var largest = i
                val left = 2 * i + 1
                val right = 2 * i + 2

                if (left < n && arr[left] > arr[largest]) {
                    largest = left
                }

                if (right < n && arr[right] > arr[largest]) {
                    largest = right
                }

                if (largest != i) {
                    // Swap elements
                    val swap = arr[i]
                    arr[i] = arr[largest]
                    arr[largest] = swap

                    // Recursively heapify the affected sub-tree
                    heapify(arr, n, largest)
                }
            }
        """.trimIndent(),
        SortingAlgorithm.SHELL_SORT to """
            fun shellSort(arr: MutableList<Int>) {
                val n = arr.size
                var gap = n / 2

                while (gap > 0) {
                    for (i in gap until n) {
                        val temp = arr[i]
                        var j = i

                        while (j >= gap && arr[j - gap] > temp) {
                            arr[j] = arr[j - gap]
                            j -= gap
                        }

                        arr[j] = temp
                    }
                    gap /= 2
                }
            }
        """.trimIndent(),
        SortingAlgorithm.COCKTAIL_SHAKER_SORT to """
            fun cocktailShakerSort(arr: MutableList<Int>) {
                var swapped: Boolean
                var start = 0
                var end = arr.size

                do {
                    swapped = false

                    // Forward pass (like bubble sort)
                    for (i in start until end - 1) {
                        if (arr[i] > arr[i + 1]) {
                            val temp = arr[i]
                            arr[i] = arr[i + 1]
                            arr[i + 1] = temp
                            swapped = true
                        }
                    }

                    if (!swapped) break

                    swapped = false
                    end--

                    // Backward pass
                    for (i in end - 1 downTo start + 1) {
                        if (arr[i] < arr[i - 1]) {
                            val temp = arr[i]
                            arr[i] = arr[i - 1]
                            arr[i - 1] = temp
                            swapped = true
                        }
                    }

                    start++
                } while (swapped)
            }
        """.trimIndent(),
        SortingAlgorithm.COMB_SORT to """
            fun combSort(arr: MutableList<Int>) {
                val shrinkFactor = 1.3
                var gap = arr.size
                var swapped = true

                while (gap > 1 || swapped) {
                    // Update gap
                    gap = maxOf(1, (gap / shrinkFactor).toInt())

                    swapped = false

                    for (i in 0 until arr.size - gap) {
                        if (arr[i] > arr[i + gap]) {
                            // Swap elements
                            val temp = arr[i]
                            arr[i] = arr[i + gap]
                            arr[i + gap] = temp
                            swapped = true
                        }
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.COUNTING_SORT to """
            fun countingSort(arr: MutableList<Int>) {
                if (arr.isEmpty()) return

                // Find range of input
                val max = arr.maxOrNull()!!
                val min = arr.minOrNull()!!
                val range = max - min + 1

                // Create counting array
                val count = MutableList(range) { 0 }

                // Count occurrences of each unique object
                for (num in arr) {
                    count[num - min]++
                }

                // Modify count array to store actual position of each object
                for (i in 1 until count.size) {
                    count[i] += count[i - 1]
                }

                // Build output array
                val output = MutableList(arr.size) { 0 }
                for (i in arr.size - 1 downTo 0) {
                    output[count[arr[i] - min] - 1] = arr[i]
                    count[arr[i] - min]--
                }

                // Copy output to arr
                for (i in arr.indices) {
                    arr[i] = output[i]
                }
            }
        """.trimIndent(),
        SortingAlgorithm.RADIX_SORT to """
            fun radixSort(arr: MutableList<Int>) {
                if (arr.isEmpty()) return

                // Find the maximum number to know number of digits
                val max = arr.maxOrNull()!!

                // Do counting sort for every digit
                var exp = 1
                while (max / exp > 0) {
                    countingSortByDigit(arr, exp)
                    exp *= 10
                }
            }

            private fun countingSortByDigit(arr: MutableList<Int>, exp: Int) {
                val n = arr.size
                val output = MutableList(n) { 0 }
                val count = MutableList(10) { 0 }

                // Store count of occurrences in count[]
                for (i in 0 until n) {
                    val index = (arr[i] / exp) % 10
                    count[index]++
                }

                // Change count[i] so that count[i] now contains actual
                // position of this digit in output[]
                for (i in 1 until 10) {
                    count[i] += count[i - 1]
                }

                // Build the output array
                for (i in n - 1 downTo 0) {
                    val index = (arr[i] / exp) % 10
                    output[count[index] - 1] = arr[i]
                    count[index]--
                }

                // Copy the output array to arr[], so that arr[] now
                // contains sorted numbers according to current digit
                for (i in 0 until n) {
                    arr[i] = output[i]
                }
            }
        """.trimIndent(),
        SortingAlgorithm.BUCKET_SORT to """
            fun bucketSort(arr: MutableList<Int>) {
                if (arr.isEmpty()) return

                // Find range of input
                val max = arr.maxOrNull()!!
                val min = arr.minOrNull()!!

                // Number of buckets
                val bucketCount = 10

                // Create empty buckets
                val buckets = List(bucketCount) { mutableListOf<Int>() }

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
                        arr[index++] = num
                    }
                }
            }
        """.trimIndent()
    )

    private val languageCodeSnippets = mapOf(
        SortingAlgorithm.BUBBLE_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun bubbleSort(arr: MutableList<Int>) {
                    val n = arr.size
                    for (i in 0 until n - 1) {
                        for (j in 0 until n - i - 1) {
                            if (arr[j] > arr[j + 1]) {
                                // Swap elements
                                val temp = arr[j]
                                arr[j] = arr[j + 1]
                                arr[j + 1] = temp
                            }
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public void bubbleSort(int[] arr) {
                    int n = arr.length;
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                // Swap elements
                                int temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.PYTHON to """
                def bubble_sort(arr):
                    n = len(arr)
                    for i in range(n - 1):
                        for j in range(n - i - 1):
                            if arr[j] > arr[j + 1]:
                                # Swap elements
                                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                    return arr
            """.trimIndent(),
            ProgrammingLanguage.CPP to """
                void bubbleSort(vector<int>& arr) {
                    int n = arr.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                // Swap elements
                                int temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVASCRIPT to """
                function bubbleSort(arr) {
                    const n = arr.length;
                    for (let i = 0; i < n - 1; i++) {
                        for (let j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                // Swap elements
                                let temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                    return arr;
                }
            """.trimIndent()
        ),
        SortingAlgorithm.SHELL_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun shellSort(arr: MutableList<Int>) {
                    val n = arr.size
                    var gap = n / 2
                    
                    while (gap > 0) {
                        for (i in gap until n) {
                            val temp = arr[i]
                            var j = i
                            
                            while (j >= gap && arr[j - gap] > temp) {
                                arr[j] = arr[j - gap]
                                j -= gap
                            }
                            
                            arr[j] = temp
                        }
                        gap /= 2
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public void shellSort(int[] arr) {
                    int n = arr.length;
                    for (int gap = n / 2; gap > 0; gap /= 2) {
                        for (int i = gap; i < n; i++) {
                            int temp = arr[i];
                            int j;
                            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                                arr[j] = arr[j - gap];
                            }
                            arr[j] = temp;
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.PYTHON to """
                def shell_sort(arr):
                    n = len(arr)
                    gap = n // 2
                    
                    while gap > 0:
                        for i in range(gap, n):
                            temp = arr[i]
                            j = i
                            
                            while j >= gap and arr[j - gap] > temp:
                                arr[j] = arr[j - gap]
                                j -= gap
                            
                            arr[j] = temp
                        
                        gap //= 2
                    
                    return arr
            """.trimIndent(),
            ProgrammingLanguage.CPP to """
                void shellSort(vector<int>& arr) {
                    int n = arr.size();
                    for (int gap = n / 2; gap > 0; gap /= 2) {
                        for (int i = gap; i < n; i++) {
                            int temp = arr[i];
                            int j;
                            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                                arr[j] = arr[j - gap];
                            }
                            arr[j] = temp;
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVASCRIPT to """
                function shellSort(arr) {
                    const n = arr.length;
                    for (let gap = Math.floor(n / 2); gap > 0; gap = Math.floor(gap / 2)) {
                        for (let i = gap; i < n; i++) {
                            let temp = arr[i];
                            let j;
                            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                                arr[j] = arr[j - gap];
                            }
                            arr[j] = temp;
                        }
                    }
                    return arr;
                }
            """.trimIndent()
        ),
        SortingAlgorithm.COCKTAIL_SHAKER_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun cocktailShakerSort(arr: MutableList<Int>) {
                    var swapped: Boolean
                    var start = 0
                    var end = arr.size
                    
                    do {
                        swapped = false
                        
                        // Forward pass (like bubble sort)
                        for (i in start until end - 1) {
                            if (arr[i] > arr[i + 1]) {
                                val temp = arr[i]
                                arr[i] = arr[i + 1]
                                arr[i + 1] = temp
                                swapped = true
                            }
                        }
                        
                        if (!swapped) break
                        
                        swapped = false
                        end--
                        
                        // Backward pass
                        for (i in end - 1 downTo start + 1) {
                            if (arr[i] < arr[i - 1]) {
                                val temp = arr[i]
                                arr[i] = arr[i - 1]
                                arr[i - 1] = temp
                                swapped = true
                            }
                        }
                        
                        start++
                    } while (swapped)
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public void cocktailShakerSort(int[] arr) {
                    boolean swapped;
                    int start = 0;
                    int end = arr.length;
                    
                    do {
                        swapped = false;
                        
                        // Forward pass
                        for (int i = start; i < end - 1; i++) {
                            if (arr[i] > arr[i + 1]) {
                                int temp = arr[i];
                                arr[i] = arr[i + 1];
                                arr[i + 1] = temp;
                                swapped = true;
                            }
                        }
                        
                        if (!swapped) break;
                        
                        swapped = false;
                        end--;
                        
                        // Backward pass
                        for (int i = end - 1; i >= start + 1; i--) {
                            if (arr[i] < arr[i - 1]) {
                                int temp = arr[i];
                                arr[i] = arr[i - 1];
                                arr[i - 1] = temp;
                                swapped = true;
                            }
                        }
                        
                        start++;
                    } while (swapped);
                }
            """.trimIndent()
        ),
        SortingAlgorithm.COMB_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun combSort(arr: MutableList<Int>) {
                    val shrinkFactor = 1.3
                    var gap = arr.size
                    var swapped = true
                    
                    while (gap > 1 || swapped) {
                        // Update gap
                        gap = maxOf(1, (gap / shrinkFactor).toInt())
                        
                        swapped = false
                        
                        for (i in 0 until arr.size - gap) {
                            if (arr[i] > arr[i + gap]) {
                                // Swap elements
                                val temp = arr[i]
                                arr[i] = arr[i + gap]
                                arr[i + gap] = temp
                                swapped = true
                            }
                        }
                    }
                }
            """.trimIndent()
        ),
        SortingAlgorithm.COUNTING_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun countingSort(arr: MutableList<Int>) {
                    if (arr.isEmpty()) return
                    
                    // Find range of input
                    val max = arr.maxOrNull()!!
                    val min = arr.minOrNull()!!
                    val range = max - min + 1
                    
                    // Create counting array
                    val count = MutableList(range) { 0 }
                    
                    // Count occurrences of each unique object
                    for (num in arr) {
                        count[num - min]++
                    }
                    
                    // Modify count array to store actual position of each object
                    for (i in 1 until count.size) {
                        count[i] += count[i - 1]
                    }
                    
                    // Build output array
                    val output = MutableList(arr.size) { 0 }
                    for (i in arr.size - 1 downTo 0) {
                        output[count[arr[i] - min] - 1] = arr[i]
                        count[arr[i] - min]--
                    }
                    
                    // Copy output to arr
                    for (i in arr.indices) {
                        arr[i] = output[i]
                    }
                }
            """.trimIndent()
        ),
        SortingAlgorithm.RADIX_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun radixSort(arr: MutableList<Int>) {
                    if (arr.isEmpty()) return
                    
                    // Find the maximum number to know number of digits
                    val max = arr.maxOrNull()!!
                    
                    // Do counting sort for every digit
                    var exp = 1
                    while (max / exp > 0) {
                        countingSortByDigit(arr, exp)
                        exp *= 10
                    }
                }
                
                private fun countingSortByDigit(arr: MutableList<Int>, exp: Int) {
                    val n = arr.size
                    val output = MutableList(n) { 0 }
                    val count = MutableList(10) { 0 }
                    
                    // Store count of occurrences in count[]
                    for (i in 0 until n) {
                        val index = (arr[i] / exp) % 10
                        count[index]++
                    }
                    
                    // Change count[i] so that count[i] now contains actual
                    // position of this digit in output[]
                    for (i in 1 until 10) {
                        count[i] += count[i - 1]
                    }
                    
                    // Build the output array
                    for (i in n - 1 downTo 0) {
                        val index = (arr[i] / exp) % 10
                        output[count[index] - 1] = arr[i]
                        count[index]--
                    }
                    
                    // Copy the output array to arr[], so that arr[] now
                    // contains sorted numbers according to current digit
                    for (i in 0 until n) {
                        arr[i] = output[i]
                    }
                }
            """.trimIndent()
        ),
        SortingAlgorithm.BUCKET_SORT to mapOf(
            ProgrammingLanguage.KOTLIN to """
                fun bucketSort(arr: MutableList<Int>) {
                    if (arr.isEmpty()) return
                    
                    // Find range of input
                    val max = arr.maxOrNull()!!
                    val min = arr.minOrNull()!!
                    
                    // Number of buckets
                    val bucketCount = 10
                    
                    // Create empty buckets
                    val buckets = List(bucketCount) { mutableListOf<Int>() }
                    
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
                            arr[index++] = num
                        }
                    }
                }
            """.trimIndent()
        )
    )

    fun getCodeSnippet(
        algorithm: SortingAlgorithm,
        language: ProgrammingLanguage
    ): String {
        return languageCodeSnippets[algorithm]?.get(language)
            ?: algorithmCodeSnippets[algorithm]
            ?: "Code snippet not available"
    }
}
