package com.example.sortingvisualization.algorithms

import com.example.sortingvisualization.algorithms.SortingAlgorithm
import com.example.sortingvisualization.algorithms.ProgrammingLanguage

object CodeSnippets {
    private val algorithmCodeSnippets = mapOf(
        SortingAlgorithm.BUBBLE_SORT to """
            void bubbleSort(std::vector<double>& arr) {
                int n = arr.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        if (arr[j] > arr[j + 1]) {
                            // Swap elements
                            double temp = arr[j];
                            arr[j] = arr[j + 1];
                            arr[j + 1] = temp;
                        }
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.SELECTION_SORT to """
            void selectionSort(std::vector<double>& arr) {
                int n = arr.size();
                for (int i = 0; i < n - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j] < arr[minIndex]) {
                            minIndex = j;
                        }
                    }
                    // Swap elements
                    double temp = arr[minIndex];
                    arr[minIndex] = arr[i];
                    arr[i] = temp;
                }
            }
        """.trimIndent(),
        SortingAlgorithm.INSERTION_SORT to """
            void insertionSort(std::vector<double>& arr) {
                int n = arr.size();
                for (int i = 1; i < n; i++) {
                    double key = arr[i];
                    int j = i - 1;
                    
                    while (j >= 0 && arr[j] > key) {
                        arr[j + 1] = arr[j];
                        j--;
                    }
                    arr[j + 1] = key;
                }
            }
        """.trimIndent(),
        SortingAlgorithm.MERGE_SORT to """
            void merge(std::vector<double>& arr, int left, int mid, int right) {
                int n1 = mid - left + 1;
                int n2 = right - mid;
                
                std::vector<double> leftArr(n1);
                std::vector<double> rightArr(n2);
                
                for (int i = 0; i < n1; i++)
                    leftArr[i] = arr[left + i];
                for (int j = 0; j < n2; j++)
                    rightArr[j] = arr[mid + 1 + j];
                
                int i = 0, j = 0, k = left;
                
                while (i < n1 && j < n2) {
                    if (leftArr[i] <= rightArr[j]) {
                        arr[k] = leftArr[i];
                        i++;
                    } else {
                        arr[k] = rightArr[j];
                        j++;
                    }
                    k++;
                }
                
                while (i < n1) {
                    arr[k] = leftArr[i];
                    i++;
                    k++;
                }
                
                while (j < n2) {
                    arr[k] = rightArr[j];
                    j++;
                    k++;
                }
            }
            
            void mergeSort(std::vector<double>& arr, int left, int right) {
                if (left >= right) return;
                
                int mid = left + (right - left) / 2;
                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);
                merge(arr, left, mid, right);
            }
            
            // Wrapper function to call mergeSort
            void mergeSortWrapper(std::vector<double>& arr) {
                mergeSort(arr, 0, arr.size() - 1);
            }
        """.trimIndent(),
        SortingAlgorithm.QUICK_SORT to """
            int partition(std::vector<double>& arr, int low, int high) {
                double pivot = arr[high];
                int i = low - 1;
                
                for (int j = low; j < high; j++) {
                    if (arr[j] < pivot) {
                        i++;
                        std::swap(arr[i], arr[j]);
                    }
                }
                std::swap(arr[i + 1], arr[high]);
                return i + 1;
            }
            
            void quickSort(std::vector<double>& arr, int low, int high) {
                if (low < high) {
                    int pi = partition(arr, low, high);
                    quickSort(arr, low, pi - 1);
                    quickSort(arr, pi + 1, high);
                }
            }
            
            // Wrapper function to call quickSort
            void quickSortWrapper(std::vector<double>& arr) {
                quickSort(arr, 0, arr.size() - 1);
            }
        """.trimIndent(),
        SortingAlgorithm.HEAP_SORT to """
            void heapify(std::vector<double>& arr, int n, int i) {
                int largest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < n && arr[left] > arr[largest])
                    largest = left;
                
                if (right < n && arr[right] > arr[largest])
                    largest = right;
                
                if (largest != i) {
                    std::swap(arr[i], arr[largest]);
                    heapify(arr, n, largest);
                }
            }
            
            void heapSort(std::vector<double>& arr) {
                int n = arr.size();
                
                // Build max heap
                for (int i = n / 2 - 1; i >= 0; i--)
                    heapify(arr, n, i);
                
                // Extract elements from heap one by one
                for (int i = n - 1; i > 0; i--) {
                    std::swap(arr[0], arr[i]);
                    heapify(arr, i, 0);
                }
            }
        """.trimIndent(),
        SortingAlgorithm.SHELL_SORT to """
            void shellSort(std::vector<double>& arr) {
                int n = arr.size();
                int gap = n / 2;

                while (gap > 0) {
                    for (int i = gap; i < n; i++) {
                        double temp = arr[i];
                        int j = i;

                        while (j >= gap && arr[j - gap] > temp) {
                            arr[j] = arr[j - gap];
                            j -= gap;
                        }

                        arr[j] = temp;
                    }
                    gap /= 2;
                }
            }
        """.trimIndent(),
        SortingAlgorithm.COCKTAIL_SHAKER_SORT to """
            void cocktailShakerSort(std::vector<double>& arr) {
                bool swapped;
                int start = 0;
                int end = arr.size();

                do {
                    swapped = false;

                    // Forward pass (like bubble sort)
                    for (int i = start; i < end - 1; i++) {
                        if (arr[i] > arr[i + 1]) {
                            // Swap elements
                            double temp = arr[i];
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
                            // Swap elements
                            double temp = arr[i];
                            arr[i] = arr[i - 1];
                            arr[i - 1] = temp;
                            swapped = true;
                        }
                    }

                    start++;
                } while (swapped);
            }
        """.trimIndent(),
        SortingAlgorithm.COMB_SORT to """
            void combSort(std::vector<double>& arr) {
                double shrinkFactor = 1.3;
                int gap = arr.size();
                bool swapped = true;

                while (gap > 1 || swapped) {
                    // Reduce gap
                    if (gap > 1) {
                        gap = (gap / shrinkFactor);
                    }

                    swapped = false;

                    // Compare elements with current gap
                    for (int i = 0; i < arr.size() - gap; i++) {
                        if (arr[i] > arr[i + gap]) {
                            // Swap elements
                            double temp = arr[i];
                            arr[i] = arr[i + gap];
                            arr[i + gap] = temp;
                            swapped = true;
                        }
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.COUNTING_SORT to """
            void countingSort(std::vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *std::min_element(arr.begin(), arr.end());
                double maxVal = *std::max_element(arr.begin(), arr.end());
                
                int range = maxVal - minVal + 1;
                std::vector<int> count(range, 0);
                
                // Count occurrences of each unique object
                for (double num : arr)
                    count[static_cast<int>(num - minVal)]++;
                
                // Reconstruct the sorted array
                int index = 0;
                for (int i = 0; i < range; i++) {
                    while (count[i] > 0) {
                        arr[index] = i + minVal;
                        index++;
                        count[i]--;
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.RADIX_SORT to """
            void countingSort(std::vector<double>& arr, int exp) {
                int n = arr.size();
                std::vector<double> output(n);
                std::vector<int> count(10, 0);
                
                // Store count of occurrences in count[]
                for (int i = 0; i < n; i++)
                    count[static_cast<int>((arr[i] / exp)) % 10]++;
                
                // Change count[i] so that count[i] now contains actual
                // position of this digit in output[]
                for (int i = 1; i < 10; i++)
                    count[i] += count[i - 1];
                
                // Build the output array
                for (int i = n - 1; i >= 0; i--) {
                    output[count[static_cast<int>((arr[i] / exp)) % 10] - 1] = arr[i];
                    count[static_cast<int>((arr[i] / exp)) % 10]--;
                }
                
                // Copy the output array to arr[]
                arr = output;
            }
            
            void radixSort(std::vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find the maximum number to know number of digits
                double maxVal = *std::max_element(arr.begin(), arr.end());
                
                // Do counting sort for every digit
                for (int exp = 1; maxVal / exp > 0; exp *= 10)
                    countingSort(arr, exp);
            }
        """.trimIndent(),
        SortingAlgorithm.BUCKET_SORT to """
            void bucketSort(std::vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *std::min_element(arr.begin(), arr.end());
                double maxVal = *std::max_element(arr.begin(), arr.end());
                
                // Number of buckets
                int bucketCount = 10;
                
                // Create empty buckets
                std::vector<std::vector<double>> buckets(bucketCount);
                
                // Distribute input array elements into buckets
                for (double num : arr) {
                    int bucketIndex = static_cast<int>((num - minVal) / (maxVal - minVal + 1) * bucketCount);
                    buckets[bucketIndex].push_back(num);
                }
                
                // Sort individual buckets
                for (auto& bucket : buckets) {
                    std::sort(bucket.begin(), bucket.end());
                }
                
                // Concatenate all buckets into arr
                int index = 0;
                for (const auto& bucket : buckets) {
                    for (double num : bucket) {
                        arr[index++] = num;
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.PIGEONHOLE_SORT to """
            void pigeonholeSort(std::vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *std::min_element(arr.begin(), arr.end());
                double maxVal = *std::max_element(arr.begin(), arr.end());
                
                int range = maxVal - minVal + 1;
                std::vector<int> pigeonholes(range, 0);
                
                // Count occurrences of each unique object
                for (double num : arr)
                    pigeonholes[static_cast<int>(num - minVal)]++;
                
                // Reconstruct the sorted array
                int index = 0;
                for (int i = 0; i < range; i++) {
                    while (pigeonholes[i] > 0) {
                        arr[index] = i + minVal;
                        index++;
                        pigeonholes[i]--;
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.TIM_SORT to """
            void insertionSort(std::vector<double>& arr, int left, int right) {
                for (int i = left + 1; i <= right; i++) {
                    double key = arr[i];
                    int j = i - 1;
                    
                    while (j >= left && arr[j] > key) {
                        arr[j + 1] = arr[j];
                        j--;
                    }
                    arr[j + 1] = key;
                }
            }
            
            void merge(std::vector<double>& arr, int left, int mid, int right) {
                int n1 = mid - left + 1;
                int n2 = right - mid;
                
                std::vector<double> leftArr(n1);
                std::vector<double> rightArr(n2);
                
                for (int i = 0; i < n1; i++)
                    leftArr[i] = arr[left + i];
                for (int j = 0; j < n2; j++)
                    rightArr[j] = arr[mid + 1 + j];
                
                int i = 0, j = 0, k = left;
                
                while (i < n1 && j < n2) {
                    if (leftArr[i] <= rightArr[j]) {
                        arr[k] = leftArr[i];
                        i++;
                    } else {
                        arr[k] = rightArr[j];
                        j++;
                    }
                    k++;
                }
                
                while (i < n1) {
                    arr[k] = leftArr[i];
                    i++;
                    k++;
                }
                
                while (j < n2) {
                    arr[k] = rightArr[j];
                    j++;
                    k++;
                }
            }
            
            void timSort(std::vector<double>& arr) {
                const int RUN = 32;
                int n = arr.size();
                
                // Sort individual subarrays of size RUN
                for (int i = 0; i < n; i += RUN)
                    insertionSort(arr, i, std::min(i + RUN - 1, n - 1));
                
                // Start merging from size RUN (or 32)
                for (int size = RUN; size < n; size *= 2) {
                    for (int left = 0; left < n; left += 2 * size) {
                        int mid = left + size - 1;
                        int right = std::min(left + 2 * size - 1, n - 1);
                        
                        if (mid < right)
                            merge(arr, left, mid, right);
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.GNOME_SORT to """
            void gnomeSort(std::vector<double>& arr) {
                int index = 0;
                while (index < arr.size()) {
                    if (index == 0 || arr[index] >= arr[index - 1]) {
                        index++;
                    } else {
                        // Swap elements
                        double temp = arr[index];
                        arr[index] = arr[index - 1];
                        arr[index - 1] = temp;
                        index--;
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.CYCLE_SORT to """
            void cycleSort(std::vector<double>& arr) {
                int n = arr.size();
                
                for (int cycleStart = 0; cycleStart < n - 1; cycleStart++) {
                    double item = arr[cycleStart];
                    
                    // Find where to put the item
                    int pos = cycleStart;
                    for (int i = cycleStart + 1; i < n; i++)
                        if (arr[i] < item)
                            pos++;
                    
                    // If the item is already in correct position
                    if (pos == cycleStart)
                        continue;
                    
                    // Otherwise put the item there or right after any duplicates
                    while (item == arr[pos])
                        pos++;
                    
                    // Put the item to its right position
                    if (pos != cycleStart) {
                        std::swap(item, arr[pos]);
                        cycleStart--;  // Restart the cycle
                    }
                }
            }
        """.trimIndent(),
        SortingAlgorithm.PANCAKE_SORT to """
            void flip(std::vector<double>& arr, int k) {
                int start = 0;
                while (start < k) {
                    std::swap(arr[start], arr[k]);
                    start++;
                    k--;
                }
            }
            
            void pancakeSort(std::vector<double>& arr) {
                int n = arr.size();
                
                for (int currSize = n; currSize > 1; currSize--) {
                    // Find index of the maximum element in arr[0..currSize-1]
                    int maxIndex = 0;
                    for (int i = 0; i < currSize; i++)
                        if (arr[i] > arr[maxIndex])
                            maxIndex = i;
                    
                    // Move the maximum element to the end of the current subarray
                    if (maxIndex != currSize - 1) {
                        // Flip the maximum element to the beginning if it's not already there
                        if (maxIndex != 0)
                            flip(arr, maxIndex);
                        
                        // Flip the entire current subarray
                        flip(arr, currSize - 1);
                    }
                }
            }
        """.trimIndent()
    )

    private val languageCodeSnippets = mapOf(
        SortingAlgorithm.BUBBLE_SORT to mapOf(
            ProgrammingLanguage.CPP to """
                void bubbleSort(std::vector<double>& arr) {
                    int n = arr.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                double temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public static void bubbleSort(List<Double> arr) {
                    int n = arr.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr.get(j) > arr.get(j + 1)) {
                                Double temp = arr.get(j);
                                arr.set(j, arr.get(j + 1));
                                arr.set(j + 1, temp);
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
                                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                    return arr
            """.trimIndent()
        ),
        SortingAlgorithm.MERGE_SORT to mapOf(
            ProgrammingLanguage.CPP to """
                void merge(std::vector<double>& arr, int left, int mid, int right) {
                    int n1 = mid - left + 1;
                    int n2 = right - mid;
                    
                    std::vector<double> leftArr(n1);
                    std::vector<double> rightArr(n2);
                    
                    for (int i = 0; i < n1; i++)
                        leftArr[i] = arr[left + i];
                    for (int j = 0; j < n2; j++)
                        rightArr[j] = arr[mid + 1 + j];
                    
                    int i = 0, j = 0, k = left;
                    
                    while (i < n1 && j < n2) {
                        if (leftArr[i] <= rightArr[j]) {
                            arr[k] = leftArr[i];
                            i++;
                        } else {
                            arr[k] = rightArr[j];
                            j++;
                        }
                        k++;
                    }
                    
                    while (i < n1) {
                        arr[k] = leftArr[i];
                        i++;
                        k++;
                    }
                    
                    while (j < n2) {
                        arr[k] = rightArr[j];
                        j++;
                        k++;
                    }
                }
                
                void mergeSort(std::vector<double>& arr, int left, int right) {
                    if (left >= right) return;
                    
                    int mid = left + (right - left) / 2;
                    mergeSort(arr, left, mid);
                    mergeSort(arr, mid + 1, right);
                    merge(arr, left, mid, right);
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public static void mergeSort(List<Double> arr, int left, int right) {
                    if (left < right) {
                        int mid = (left + right) / 2;
                        mergeSort(arr, left, mid);
                        mergeSort(arr, mid + 1, right);
                        merge(arr, left, mid, right);
                    }
                }
                
                private static void merge(List<Double> arr, int left, int mid, int right) {
                    int n1 = mid - left + 1;
                    int n2 = right - mid;
                    
                    List<Double> leftArr = new ArrayList<>(arr.subList(left, mid + 1));
                    List<Double> rightArr = new ArrayList<>(arr.subList(mid + 1, right + 1));
                    
                    int i = 0, j = 0, k = left;
                    
                    while (i < n1 && j < n2) {
                        if (leftArr.get(i) <= rightArr.get(j)) {
                            arr.set(k++, leftArr.get(i++));
                        } else {
                            arr.set(k++, rightArr.get(j++));
                        }
                    }
                    
                    while (i < n1) {
                        arr.set(k++, leftArr.get(i++));
                    }
                    
                    while (j < n2) {
                        arr.set(k++, rightArr.get(j++));
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.PYTHON to """
                def merge_sort(arr):
                    if len(arr) <= 1:
                        return arr
                    
                    mid = len(arr) // 2
                    left = merge_sort(arr[:mid])
                    right = merge_sort(arr[mid:])
                    
                    return merge(left, right)
                
                def merge(left, right):
                    result = []
                    i, j = 0, 0
                    
                    while i < len(left) and j < len(right):
                        if left[i] <= right[j]:
                            result.append(left[i])
                            i += 1
                        else:
                            result.append(right[j])
                            j += 1
                    
                    result.extend(left[i:])
                    result.extend(right[j:])
                    
                    return result
            """.trimIndent()
        ),
        SortingAlgorithm.QUICK_SORT to mapOf(
            ProgrammingLanguage.CPP to """
                int partition(std::vector<double>& arr, int low, int high) {
                    double pivot = arr[high];
                    int i = low - 1;
                    
                    for (int j = low; j < high; j++) {
                        if (arr[j] < pivot) {
                            i++;
                            std::swap(arr[i], arr[j]);
                        }
                    }
                    std::swap(arr[i + 1], arr[high]);
                    return i + 1;
                }
                
                void quickSort(std::vector<double>& arr, int low, int high) {
                    if (low < high) {
                        int pi = partition(arr, low, high);
                        quickSort(arr, low, pi - 1);
                        quickSort(arr, pi + 1, high);
                    }
                }
            """.trimIndent(),
            ProgrammingLanguage.JAVA to """
                public static void quickSort(List<Double> arr, int low, int high) {
                    if (low < high) {
                        int pi = partition(arr, low, high);
                        quickSort(arr, low, pi - 1);
                        quickSort(arr, pi + 1, high);
                    }
                }
                
                private static int partition(List<Double> arr, int low, int high) {
                    Double pivot = arr.get(high);
                    int i = low - 1;
                    
                    for (int j = low; j < high; j++) {
                        if (arr.get(j) < pivot) {
                            i++;
                            Double temp = arr.get(i);
                            arr.set(i, arr.get(j));
                            arr.set(j, temp);
                        }
                    }
                    
                    Double temp = arr.get(i + 1);
                    arr.set(i + 1, arr.get(high));
                    arr.set(high, temp);
                    
                    return i + 1;
                }
            """.trimIndent(),
            ProgrammingLanguage.PYTHON to """
                def quick_sort(arr):
                    if len(arr) <= 1:
                        return arr
                    
                    pivot = arr[len(arr) // 2]
                    left = [x for x in arr if x < pivot]
                    middle = [x for x in arr if x == pivot]
                    right = [x for x in arr if x > pivot]
                    
                    return quick_sort(left) + middle + quick_sort(right)
            """.trimIndent()
        )
    )

    // Function to get code snippet for a specific algorithm and language
    fun getCodeSnippet(
        algorithm: SortingAlgorithm,
        language: ProgrammingLanguage
    ): String {
        return languageCodeSnippets[algorithm]?.get(language) 
            ?: "// Code snippet not available for ${algorithm.displayName} in ${language.displayName}"
    }
}
