package com.example.sortingvisualization.algorithms

import com.example.sortingvisualization.algorithms.CodeSnippets.languageCodeSnippets
import com.example.sortingvisualization.algorithms.CodeSnippets.languageCodeSnippetsExtended

object CodeSnippets {
        private val algorithmCodeSnippets: Map<SortingAlgorithm, String> = mapOf(
                SortingAlgorithm.BUBBLE_SORT to """
            void bubbleSort(vector<double>& arr) {
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
            void selectionSort(vector<double>& arr) {
                int n = arr.size();
                for (int i = 0; i < n - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j] < arr[minIndex]) {
                            minIndex = j;
                        }
                    }
                    double temp = arr[minIndex];
                    arr[minIndex] = arr[i];
                    arr[i] = temp;
                }
            }
        """.trimIndent(),
                SortingAlgorithm.INSERTION_SORT to """
            void insertionSort(vector<double>& arr) {
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
            void merge(vector<double>& arr, int left, int mid, int right) {
                int n1 = mid - left + 1;
                int n2 = right - mid;
                
                vector<double> leftArr(n1);
                vector<double> rightArr(n2);
                
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
            
            void mergeSort(vector<double>& arr, int left, int right) {
                if (left >= right) return;
                
                int mid = left + (right - left) / 2;
                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);
                merge(arr, left, mid, right);
            }
            void mergeSortWrapper(vector<double>& arr) {
                mergeSort(arr, 0, arr.size() - 1);
            }
        """.trimIndent(),
                SortingAlgorithm.QUICK_SORT to """
            int partition(vector<double>& arr, int low, int high) {
                double pivot = arr[high];
                int i = low - 1;
                
                for (int j = low; j < high; j++) {
                    if (arr[j] < pivot) {
                        i++;
                        swap(arr[i], arr[j]);
                    }
                }
                swap(arr[i + 1], arr[high]);
                return i + 1;
            }
            
            void quickSort(vector<double>& arr, int low, int high) {
                if (low < high) {
                    int pi = partition(arr, low, high);
                    quickSort(arr, low, pi - 1);
                    quickSort(arr, pi + 1, high);
                }
            }
            void quickSortWrapper(vector<double>& arr) {
                quickSort(arr, 0, arr.size() - 1);
            }
        """.trimIndent(),
                SortingAlgorithm.HEAP_SORT to """
            void heapify(vector<double>& arr, int n, int i) {
                int largest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < n && arr[left] > arr[largest])
                    largest = left;
                
                if (right < n && arr[right] > arr[largest])
                    largest = right;
                
                if (largest != i) {
                    swap(arr[i], arr[largest]);
                    heapify(arr, n, largest);
                }
            }
            
            void heapSort(vector<double>& arr) {
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
            void shellSort(vector<double>& arr) {
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
            void cocktailShakerSort(vector<double>& arr) {
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
            void combSort(vector<double>& arr) {
                double shrinkFactor = 1.3;
                int gap = arr.size();
                bool swapped = true;

                while (gap > 1 || swapped) {
                    // Reduce gap
                    if (gap > 1) {
                        gap = (gap / shrinkFactor);
                    }

                    swapped = false;

                    for (int i = 0; i < arr.size() - gap; i++) {
                        if (arr[i] > arr[i + gap]) {
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
            void countingSort(vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *min_element(arr.begin(), arr.end());
                double maxVal = *max_element(arr.begin(), arr.end());
                
                int range = maxVal - minVal + 1;
                vector<int> count(range, 0);
                
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
            void countingSort(vector<double>& arr, int exp) {
                int n = arr.size();
                vector<double> output(n);
                vector<int> count(10, 0);
                
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
            
            void radixSort(vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find the maximum number to know number of digits
                double maxVal = *max_element(arr.begin(), arr.end());
                
                // Do counting sort for every digit
                for (int exp = 1; maxVal / exp > 0; exp *= 10)
                    countingSort(arr, exp);
            }
        """.trimIndent(),
                SortingAlgorithm.BUCKET_SORT to """
            void bucketSort(vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *min_element(arr.begin(), arr.end());
                double maxVal = *max_element(arr.begin(), arr.end());
                
                // Number of buckets
                int bucketCount = 10;
                
                // Create empty buckets
                vector<vector<double>> buckets(bucketCount);
                
                // Distribute input array elements into buckets
                for (double num : arr) {
                    int bucketIndex = static_cast<int>((num - minVal) / (maxVal - minVal + 1) * bucketCount);
                    buckets[bucketIndex].push_back(num);
                }
                
                // Sort individual buckets
                for (auto& bucket : buckets) {
                    sort(bucket.begin(), bucket.end());
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
            void pigeonholeSort(vector<double>& arr) {
                if (arr.empty()) return;
                
                // Find range of input
                double minVal = *min_element(arr.begin(), arr.end());
                double maxVal = *max_element(arr.begin(), arr.end());
                
                int range = maxVal - minVal + 1;
                vector<int> pigeonholes(range, 0);
                
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
                SortingAlgorithm.CYCLE_SORT to """
            void cycleSort(vector<double>& arr) {
                int n = arr.size();
                
                for (int cycleStart = 0; cycleStart < n - 1; cycleStart++) {
                    double item = arr[cycleStart];
                    
                    int pos = cycleStart;
                    for (int i = cycleStart + 1; i < n; i++)
                        if (arr[i] < item)
                            pos++;
                    
                    // If the item is already in correct position
                    if (pos == cycleStart)
                        continue;
                    
                    // Put the item to its right position
                    while (item != arr[pos]) {
                        swap(item, arr[pos]);
                    }
                }
            }
        """.trimIndent()
        )

        val languageCodeSnippets: Map<SortingAlgorithm, Map<ProgrammingLanguage, String>> =
                mapOf(
                        SortingAlgorithm.BUBBLE_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def bubble_sort(arr):
                    n = len(arr)
                    for i in range(n):
                        for j in range(n - i - 1):
                            if arr[j] > arr[j + 1]:
                                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                    return arr
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void bubbleSort(int[] arr) {
                    int n = arr.length;
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                int temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                void bubbleSort(int arr[], int n) {
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (arr[j] > arr[j + 1]) {
                                swap(arr[j], arr[j + 1]);
                            }
                        }
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.SELECTION_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def selection_sort(arr):
                    n = len(arr)
                    for i in range(n):
                        min_idx = i
                        for j in range(i + 1, n):
                            if arr[j] < arr[min_idx]:
                                min_idx = j
                        arr[i], arr[min_idx] = arr[min_idx], arr[i]
                    return arr
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void selectionSort(int[] arr) {
                    int n = arr.length;
                    for (int i = 0; i < n - 1; i++) {
                        int minIdx = i;
                        for (int j = i + 1; j < n; j++) {
                            if (arr[j] < arr[minIdx]) {
                                minIdx = j;
                            }
                        }
                        int temp = arr[minIdx];
                        arr[minIdx] = arr[i];
                        arr[i] = temp;
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                void selectionSort(int arr[], int n) {
                    for (int i = 0; i < n - 1; i++) {
                        int minIdx = i;
                        for (int j = i + 1; j < n; j++) {
                            if (arr[j] < arr[minIdx]) {
                                minIdx = j;
                            }
                        }
                        swap(arr[i], arr[minIdx]);
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.INSERTION_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def insertion_sort(arr):
                    for i in range(1, len(arr)):
                        key = arr[i]
                        j = i - 1
                        while j >= 0 and arr[j] > key:
                            arr[j + 1] = arr[j]
                            j -= 1
                        arr[j + 1] = key
                    return arr
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void insertionSort(int[] arr) {
                    int n = arr.length;
                    for (int i = 1; i < n; i++) {
                        int key = arr[i];
                        int j = i - 1;
                        while (j >= 0 && arr[j] > key) {
                            arr[j + 1] = arr[j];
                            j--;
                        }
                        arr[j + 1] = key;
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                void insertionSort(int arr[], int n) {
                    for (int i = 1; i < n; i++) {
                        int key = arr[i];
                        int j = i - 1;
                        while (j >= 0 && arr[j] > key) {
                            arr[j + 1] = arr[j];
                            j--;
                        }
                        arr[j + 1] = key;
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.MERGE_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def merge_sort(arr):
                    if len(arr) <= 1:
                        return arr
                    mid = len(arr) // 2
                    left_half = merge_sort(arr[:mid])
                    right_half = merge_sort(arr[mid:])
                    return merge(left_half, right_half)

                def merge(left, right):
                    merged = []
                    left_index = 0
                    right_index = 0

                    while left_index < len(left) and right_index < len(right):
                        if left[left_index] <= right[right_index]:
                            merged.append(left[left_index])
                            left_index += 1
                        else:
                            merged.append(right[right_index])
                            right_index += 1

                    merged.extend(left[left_index:])
                    merged.extend(right[right_index:])
                    return merged
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void mergeSort(int[] arr) {
                    if (arr.length <= 1) return;
                    int mid = arr.length / 2;
                    int[] left = Arrays.copyOfRange(arr, 0, mid);
                    int[] right = Arrays.copyOfRange(arr, mid, arr.length);
                    mergeSort(left);
                    mergeSort(right);
                    merge(arr, left, right);
                }

                public static void merge(int[] arr, int[] left, int[] right) {
                    int i = 0, j = 0, k = 0;
                    while (i < left.length && j < right.length) {
                        if (left[i] <= right[j]) {
                            arr[k++] = left[i++];
                        } else {
                            arr[k++] = right[j++];
                        }
                    }
                    while (i < left.length) arr[k++] = left[i++];
                    while (j < right.length) arr[k++] = right[j++];
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                void merge(int arr[], int left, int mid, int right) {
                    int n1 = mid - left + 1;
                    int n2 = right - mid;
                    
                    vector<int> leftArr(n1);
                    vector<int> rightArr(n2);
                    
                    for (int i = 0; i < n1; i++)
                        leftArr[i] = arr[left + i];
                    for (int j = 0; j < n2; j++)
                        rightArr[j] = arr[mid + 1 + j];
                    
                    int i = 0, j = 0, k = left;
                    
                    while (i < n1 && j < n2) {
                        if (leftArr[i] <= rightArr[j]) {
                            arr[k++] = leftArr[i++];
                        } else {
                            arr[k++] = rightArr[j++];
                        }
                    }
                    
                    while (i < n1) arr[k++] = leftArr[i++];
                    while (j < n2) arr[k++] = rightArr[j++];
                }
                
                void mergeSort(int arr[], int left, int right) {
                    if (left < right) {
                        int mid = left + (right - left) / 2;
                        mergeSort(arr, left, mid);
                        mergeSort(arr, mid + 1, right);
                        merge(arr, left, mid, right);
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.QUICK_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def quick_sort(arr):
                    if len(arr) <= 1:
                        return arr
                    pivot = arr[len(arr) // 2]
                    left = [x for x in arr if x < pivot]
                    middle = [x for x in arr if x == pivot]
                    right = [x for x in arr if x > pivot]
                    return quick_sort(left) + middle + quick_sort(right)
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void quickSort(int[] arr) {
                    quickSort(arr, 0, arr.length - 1);
                }

                public static void quickSort(int[] arr, int low, int high) {
                    if (low < high) {
                        int pi = partition(arr, low, high);
                        quickSort(arr, low, pi - 1);
                        quickSort(arr, pi + 1, high);
                    }
                }

                public static int partition(int[] arr, int low, int high) {
                    int pivot = arr[high];
                    int i = low - 1;
                    for (int j = low; j < high; j++) {
                        if (arr[j] < pivot) {
                            i++;
                            int temp = arr[i];
                            arr[i] = arr[j];
                            arr[j] = temp;
                        }
                    }
                    int temp = arr[i + 1];
                    arr[i + 1] = arr[high];
                    arr[high] = temp;
                    return i + 1;
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                int partition(int arr[], int low, int high) {
                    int pivot = arr[high];
                    int i = low - 1;
                    
                    for (int j = low; j < high; j++) {
                        if (arr[j] < pivot) {
                            i++;
                            swap(arr[i], arr[j]);
                        }
                    }
                    swap(arr[i + 1], arr[high]);
                    return i + 1;
                }
                
                void quickSort(int arr[], int low, int high) {
                    if (low < high) {
                        int pi = partition(arr, low, high);
                        quickSort(arr, low, pi - 1);
                        quickSort(arr, pi + 1, high);
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.HEAP_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
                def heapify(arr, n, i):
                    largest = i
                    left = 2 * i + 1
                    right = 2 * i + 2

                    if left < n and arr[left] > arr[largest]:
                        largest = left

                    if right < n and arr[right] > arr[largest]:
                        largest = right

                    if largest != i:
                        arr[i], arr[largest] = arr[largest], arr[i]
                        heapify(arr, n, largest)

                def heap_sort(arr):
                    n = len(arr)

                    for i in range(n // 2 - 1, -1, -1):
                        heapify(arr, n, i)

                    for i in range(n - 1, 0, -1):
                        arr[0], arr[i] = arr[i], arr[0]
                        heapify(arr, i, 0)
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void heapSort(int[] arr) {
                    int n = arr.length;
                    for (int i = n / 2 - 1; i >= 0; i--) {
                        heapify(arr, n, i);
                    }
                    for (int i = n - 1; i > 0; i--) {
                        int temp = arr[0];
                        arr[0] = arr[i];
                        arr[i] = temp;
                        heapify(arr, i, 0);
                    }
                }

                public static void heapify(int[] arr, int n, int i) {
                    int largest = i;
                    int left = 2 * i + 1;
                    int right = 2 * i + 2;

                    if (left < n && arr[left] > arr[largest]) {
                        largest = left;
                    }

                    if (right < n && arr[right] > arr[largest]) {
                        largest = right;
                    }

                    if (largest != i) {
                        int temp = arr[i];
                        arr[i] = arr[largest];
                        arr[largest] = temp;
                        heapify(arr, n, largest);
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.CPP to """
                void heapify(vector<int>& arr, int n, int i) {
                    int largest = i;
                    int left = 2 * i + 1;
                    int right = 2 * i + 2;
                    
                    if (left < n && arr[left] > arr[largest])
                        largest = left;
                    
                    if (right < n && arr[right] > arr[largest])
                        largest = right;
                    
                    if (largest != i) {
                        swap(arr[i], arr[largest]);
                        heapify(arr, n, largest);
                    }
                }
                
                void heapSort(vector<int>& arr) {
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
            """.trimIndent()
                        ),
                        SortingAlgorithm.SHELL_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void shellSort(int arr[], int n) {
                    for (int gap = n/2; gap > 0; gap /= 2) {
                        for (int i = gap; i < n; i++) {
                            int temp = arr[i];
                            int j;
                            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                                arr[j] = arr[j - gap];
                            arr[j] = temp;
                        }
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void shellSort(int[] arr) {
                    int n = arr.length;
                    
                    // Start with a big gap, then reduce gap
                    for (int gap = n / 2; gap > 0; gap /= 2) {
                        // Do gapped insertion sort for this gap size
                        for (int i = gap; i < n; i++) {
                            int temp = arr[i];
                            int j;
                            
                            // Shift earlier gap-sorted elements up until the correct location for arr[i] is found
                            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                                arr[j] = arr[j - gap];
                            }
                            
                            // Place temp (the original arr[i]) in its correct location
                            arr[j] = temp;
                        }
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.COCKTAIL_SHAKER_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void cocktailShakerSort(int arr[], int n) {
                    bool swapped = true;
                    int start = 0;
                    int end = n - 1;
                    
                    while (swapped) {
                        swapped = false;
                        
                        // Forward pass
                        for (int i = start; i < end; i++) {
                            if (arr[i] > arr[i + 1]) {
                                swap(arr[i], arr[i + 1]);
                                swapped = true;
                            }
                        }
                        
                        if (!swapped) break;
                        
                        swapped = false;
                        end--;
                        
                        // Backward pass
                        for (int i = end - 1; i >= start; i--) {
                            if (arr[i] > arr[i + 1]) {
                                swap(arr[i], arr[i + 1]);
                                swapped = true;
                            }
                        }
                        
                        start++;
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void cocktailShakerSort(int[] arr) {
                    boolean swapped;
                    int start = 0;
                    int end = arr.length;
                    
                    while (start < end) {
                        // Forward pass
                        swapped = false;
                        for (int i = start; i < end - 1; i++) {
                            if (arr[i] > arr[i + 1]) {
                                // Swap elements
                                int temp = arr[i];
                                arr[i] = arr[i + 1];
                                arr[i + 1] = temp;
                                swapped = true;
                            }
                        }
                        
                        if (!swapped) {
                            // If nothing moved, array is sorted
                            break;
                        }
                        
                        // Reduce end because the last element is now in place
                        end--;
                        
                        // Backward pass
                        swapped = false;
                        for (int i = end - 1; i >= start; i--) {
                            if (arr[i] > arr[i + 1]) {
                                // Swap elements
                                int temp = arr[i];
                                arr[i] = arr[i + 1];
                                arr[i + 1] = temp;
                                swapped = true;
                            }
                        }
                        
                        // Increase start because the first element is now in place
                        start++;
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.COMB_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void combSort(int arr[], int n) {
                    int gap = n;
                    bool swapped = true;
                    
                    while (gap > 1 || swapped) {
                        gap = max(1, gap * 10 / 13);
                        swapped = false;
                        
                        for (int i = 0; i < n - gap; i++) {
                            if (arr[i] > arr[i + gap]) {
                                swap(arr[i], arr[i + gap]);
                                swapped = true;
                            }
                        }
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void combSort(int[] arr) {
                    int n = arr.length;
                    int gap = n;
                    boolean swapped = true;
                    
                    while (gap != 1 || swapped) {
                        // Reduce gap
                        gap = getNextGap(gap);
                        
                        // Compare all elements with current gap
                        swapped = false;
                        
                        for (int i = 0; i < n - gap; i++) {
                            if (arr[i] > arr[i + gap]) {
                                // Swap elements
                                int temp = arr[i];
                                arr[i] = arr[i + gap];
                                arr[i + gap] = temp;
                                swapped = true;
                            }
                        }
                    }
                }
                
                // Function to find next gap
                private static int getNextGap(int gap) {
                    // Shrink gap by a factor of 1.3
                    gap = (gap * 10) / 13;
                    return (gap < 1) ? 1 : gap;
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.COUNTING_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void countingSort(int arr[], int n) {
                    int max = *max_element(arr, arr + n);
                    int min = *min_element(arr, arr + n);
                    int range = max - min + 1;
                    
                    vector<int> count(range, 0);
                    vector<int> output(n);
                    
                    // Count occurrences of each unique object
                    for (int i = 0; i < n; i++)
                        count[arr[i] - min]++;
                    
                    // Change count[i] so that count[i] now contains 
                    // actual position of this element in output array
                    for (int i = 1; i < range; i++)
                        count[i] += count[i - 1];
                    
                    // Build the output array
                    for (int i = n - 1; i >= 0; i--) {
                        output[count[arr[i] - min] - 1] = arr[i];
                        count[arr[i] - min]--;
                    }
                    
                    // Copy the output array to arr
                    for (int i = 0; i < n; i++)
                        arr[i] = output[i];
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void countingSort(int[] arr) {
                    if (arr == null || arr.length <= 1) return;
                    
                    // Find the range of the input
                    int max = Arrays.stream(arr).max().getAsInt();
                    int min = Arrays.stream(arr).min().getAsInt();
                    int range = max - min + 1;
                    
                    // Create count array to store count of individual elements
                    int[] count = new int[range];
                    
                    // Store count of each character
                    for (int num : arr)
                        count[num - min]++;
                    
                    // Change count[i] so that count[i] now contains actual
                    // position of this digit in output array
                    for (int i = 1; i < count.length; i++)
                        count[i] += count[i - 1];
                    
                    // Build the output array
                    int[] output = new int[arr.length];
                    for (int i = arr.length - 1; i >= 0; i--) {
                        output[count[arr[i] - min] - 1] = arr[i];
                        count[arr[i] - min]--;
                    }
                    
                    // Copy the output array to arr
                    System.arraycopy(output, 0, arr, 0, arr.length);
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.RADIX_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void countingSort(int arr[], int n, int exp) {
                    vector<int> output(n);
                    vector<int> count(10, 0);
                    
                    // Store count of occurrences in count[]
                    for (int i = 0; i < n; i++)
                        count[(arr[i] / exp) % 10]++;
                    
                    // Change count[i] so that count[i] now contains actual
                    // position of this digit in output[]
                    for (int i = 1; i < 10; i++)
                        count[i] += count[i - 1];
                    
                    // Build the output array
                    for (int i = n - 1; i >= 0; i--) {
                        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
                        count[(arr[i] / exp) % 10]--;
                    }
                    
                    // Copy the output array to arr[]
                    for (int i = 0; i < n; i++)
                        arr[i] = output[i];
                }
                
                void radixSort(int arr[], int n) {
                    int max = *max_element(arr, arr + n);
                    
                    // Do counting sort for every digit
                    for (int exp = 1; max / exp > 0; exp *= 10)
                        countingSort(arr, n, exp);
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void radixSort(int[] arr) {
                    if (arr == null || arr.length <= 1) return;
                    
                    // Find the maximum number to know the number of digits
                    int max = Arrays.stream(arr).max().getAsInt();
                    
                    // Do counting sort for every digit
                    for (int exp = 1; max / exp > 0; exp *= 10) {
                        countingSort(arr, exp);
                    }
                }
                
                private static void countingSort(int[] arr, int exp) {
                    int n = arr.length;
                    int[] output = new int[n];
                    int[] count = new int[10];
                    
                    // Store count of occurrences in count[]
                    for (int i = 0; i < n; i++)
                        count[(arr[i] / exp) % 10]++;
                    
                    // Change count[i] so that count[i] now contains actual
                    // position of this digit in output[]
                    for (int i = 1; i < 10; i++)
                        count[i] += count[i - 1];
                    
                    // Build the output array
                    for (int i = n - 1; i >= 0; i--) {
                        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
                        count[(arr[i] / exp) % 10]--;
                    }
                    
                    // Copy the output array to arr[]
                    System.arraycopy(output, 0, arr, 0, n);
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.BUCKET_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void bucketSort(int arr[], int n) {
                    // Find maximum and minimum elements
                    int max = *max_element(arr, arr + n);
                    int min = *min_element(arr, arr + n);
                    
                    // Number of buckets
                    int bucketCount = max - min + 1;
                    vector<vector<int>> buckets(bucketCount);
                    
                    // Distribute input array elements into buckets
                    for (int i = 0; i < n; i++) {
                        int bucketIndex = arr[i] - min;
                        buckets[bucketIndex].push_back(arr[i]);
                    }
                    
                    // Sort individual buckets
                    for (auto& bucket : buckets) {
                        sort(bucket.begin(), bucket.end());
                    }
                    
                    // Concatenate all buckets back into arr
                    int index = 0;
                    for (const auto& bucket : buckets) {
                        for (int val : bucket) {
                            arr[index++] = val;
                        }
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void bucketSort(int[] arr) {
                    if (arr == null || arr.length <= 1) return;
                    
                    // Find minimum and maximum values
                    int minValue = Arrays.stream(arr).min().getAsInt();
                    int maxValue = Arrays.stream(arr).max().getAsInt();
                    
                    // Number of buckets
                    int bucketCount = maxValue - minValue + 1;
                    List<List<Integer>> buckets = new ArrayList<>(bucketCount);
                    
                    // Initialize empty buckets
                    for (int i = 0; i < bucketCount; i++) {
                        buckets.add(new ArrayList<>());
                    }
                    
                    // Distribute input array elements into buckets
                    for (int num : arr) {
                        int bucketIndex = num - minValue;
                        buckets.get(bucketIndex).add(num);
                    }
                    
                    // Sort individual buckets
                    for (List<Integer> bucket : buckets) {
                        Collections.sort(bucket);
                    }
                    
                    // Concatenate all buckets back into arr
                    int index = 0;
                    for (List<Integer> bucket : buckets) {
                        for (int num : bucket) {
                            arr[index++] = num;
                        }
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.PIGEONHOLE_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void pigeonholeSort(int arr[], int n) {
                    // Find minimum and maximum elements
                    int min = *min_element(arr, arr + n);
                    int max = *max_element(arr, arr + n);
                    int range = max - min + 1;
                    
                    // Create pigeonholes
                    vector<int> pigeonholes(range, 0);
                    
                    // Populate pigeonholes
                    for (int i = 0; i < n; i++)
                        pigeonholes[arr[i] - min]++;
                    
                    // Put the elements back into the array in sorted order
                    int index = 0;
                    for (int j = 0; j < range; j++) {
                        while (pigeonholes[j]-- > 0)
                            arr[index++] = j + min;
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void pigeonholeSort(int[] arr) {
                    if (arr == null || arr.length <= 1) return;
                    
                    // Find minimum and maximum elements
                    int min = Arrays.stream(arr).min().getAsInt();
                    int max = Arrays.stream(arr).max().getAsInt();
                    int range = max - min + 1;
                    
                    // Create pigeonholes
                    int[] pigeonholes = new int[range];
                    
                    // Populate pigeonholes
                    for (int num : arr) {
                        pigeonholes[num - min]++;
                    }
                    
                    // Put the elements back into the array in sorted order
                    int index = 0;
                    for (int j = 0; j < range; j++) {
                        while (pigeonholes[j]-- > 0) {
                            arr[index++] = j + min;
                        }
                    }
                }
            """.trimIndent()
                        ),
                        SortingAlgorithm.CYCLE_SORT to mapOf(
                                ProgrammingLanguage.CPP to """
                void cycleSort(int arr[], int n) {
                    for (int cycle_start = 0; cycle_start < n - 1; cycle_start++) {
                        int item = arr[cycle_start];
                        
                        // Find where to put the item
                        int pos = cycle_start;
                        for (int i = cycle_start + 1; i < n; i++)
                            if (arr[i] < item)
                                pos++;
                        
                        // If the item is already in correct position
                        if (pos == cycle_start)
                            continue;
                        
                        // Put the item to its right position
                        while (item != arr[pos]) {
                            swap(item, arr[pos]);
                        }
                    }
                }
            """.trimIndent(),
                                ProgrammingLanguage.JAVA to """
                public static void cycleSort(int[] arr) {
                    for (int cycleStart = 0; cycleStart < arr.length - 1; cycleStart++) {
                        int item = arr[cycleStart];
                        
                        // Find the position where we put the item
                        int pos = cycleStart;
                        for (int i = cycleStart + 1; i < arr.length; i++) {
                            if (arr[i] < item) {
                                pos++;
                            }
                        }
                        
                        // If the item is already in the correct position
                        if (pos == cycleStart) {
                            continue;
                        }
                        
                        // Put the item to its right position
                        while (item != arr[pos]) {
                            int temp = item;
                            item = arr[pos];
                            arr[pos] = temp;
                        }
                    }
                }
            """.trimIndent()
                        )
                )

        // Extend the languageCodeSnippets map with Python implementations
        val languageCodeSnippetsExtended: Map<SortingAlgorithm, Map<ProgrammingLanguage, String>> =
                (languageCodeSnippets ?: emptyMap()) + mapOf(
                        SortingAlgorithm.MERGE_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def merge_sort(arr):
    if len(arr) <= 1:
        return arr
    
    # Divide the array into two halves
    mid = len(arr) // 2
    left = arr[:mid]
    right = arr[mid:]
    
    # Recursively sort both halves
    left = merge_sort(left)
    right = merge_sort(right)
    
    # Merge the sorted halves
    return merge(left, right)

def merge(left, right):
    result = []
    i, j = 0, 0
    
    # Compare and merge elements from both lists
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    
    # Add remaining elements from left list
    result.extend(left[i:])
    
    # Add remaining elements from right list
    result.extend(right[j:])
    
    return result
            """.trimIndent()
                        ),
                        SortingAlgorithm.QUICK_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    
    # Choose the pivot (last element)
    pivot = arr[-1]
    
    # Partition the array
    left = [x for x in arr[:-1] if x < pivot]
    right = [x for x in arr[:-1] if x >= pivot]
    
    # Recursively sort left and right partitions
    return quick_sort(left) + [pivot] + quick_sort(right)
            """.trimIndent()
                        ),
                        SortingAlgorithm.HEAP_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def heapify(arr, n, i):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2
    
    # Check if left child exists and is larger than root
    if left < n and arr[left] > arr[largest]:
        largest = left
    
    # Check if right child exists and is larger than largest so far
    if right < n and arr[right] > arr[largest]:
        largest = right
    
    # If largest is not root, swap and continue heapifying
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]
        heapify(arr, n, largest)

def heap_sort(arr):
    n = len(arr)
    
    # Build max heap
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)
    
    # Extract elements from heap one by one
    for i in range(n - 1, 0, -1):
        # Move current root to end
        arr[0], arr[i] = arr[i], arr[0]
        
        # Call max heapify on the reduced heap
        heapify(arr, i, 0)
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.SHELL_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def shell_sort(arr):
    n = len(arr)
    
    # Start with a big gap, then reduce gap
    gap = n // 2
    
    while gap > 0:
        # Do gapped insertion sort for this gap size
        for i in range(gap, n):
            temp = arr[i]
            j = i
            
            # Shift earlier gap-sorted elements up until the correct location for arr[i] is found
            while j >= gap and arr[j - gap] > temp:
                arr[j] = arr[j - gap]
                j -= gap
            
            # Place temp (the original arr[i]) in its correct location
            arr[j] = temp
        
        # Reduce gap
        gap //= 2
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.COCKTAIL_SHAKER_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def cocktail_shaker_sort(arr):
    n = len(arr)
    swapped = True
    start = 0
    end = n
    
    while swapped:
        # Reset the swapped flag on entering the loop
        swapped = False
        
        # Forward pass
        for i in range(start, end - 1):
            if arr[i] > arr[i + 1]:
                arr[i], arr[i + 1] = arr[i + 1], arr[i]
                swapped = True
        
        if not swapped:
            # If nothing moved, array is sorted
            break
        
        # Reduce end because the last element is now in place
        end -= 1
        
        # Backward pass
        for i in range(end - 1, start - 1, -1):
            if arr[i] < arr[i + 1]:
                arr[i], arr[i + 1] = arr[i + 1], arr[i]
                swapped = True
        
        # Increase start because the first element is now in place
        start += 1
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.COMB_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def get_next_gap(gap):
    # Shrink gap by a factor of 1.3
    gap = (gap * 10) // 13
    return max(gap, 1)

def comb_sort(arr):
    n = len(arr)
    gap = n
    swapped = True
    
    while gap != 1 or swapped:
        # Reduce gap
        gap = get_next_gap(gap)
        
        # Compare all elements with current gap
        swapped = False
        
        for i in range(0, n - gap):
            if arr[i] > arr[i + gap]:
                arr[i], arr[i + gap] = arr[i + gap], arr[i]
                swapped = True
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.COUNTING_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def counting_sort(arr):
    if not arr:
        return arr
    
    # Find the range of the input
    min_val = min(arr)
    max_val = max(arr)
    range_val = max_val - min_val + 1
    
    # Create count array to store count of individual elements
    count = [0] * range_val
    
    # Store count of each character
    for num in arr:
        count[num - min_val] += 1
    
    # Change count[i] so that count[i] now contains actual
    # position of this element in output array
    for i in range(1, len(count)):
        count[i] += count[i - 1]
    
    # Build the output array
    output = [0] * len(arr)
    for i in range(len(arr) - 1, -1, -1):
        output[count[arr[i] - min_val] - 1] = arr[i]
        count[arr[i] - min_val] -= 1
    
    return output
            """.trimIndent()
                        ),
                        SortingAlgorithm.RADIX_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def counting_sort_radix(arr, exp):
    n = len(arr)
    output = [0] * n
    count = [0] * 10
    
    # Store count of occurrences in count[]
    for i in range(n):
        index = arr[i] // exp
        count[index % 10] += 1
    
    # Change count[i] so that count[i] now contains actual
    # position of this digit in output[]
    for i in range(1, 10):
        count[i] += count[i - 1]
    
    # Build the output array
    i = n - 1
    while i >= 0:
        index = arr[i] // exp
        output[count[index % 10] - 1] = arr[i]
        count[index % 10] -= 1
        i -= 1
    
    # Copy the output array to arr[]
    for i in range(n):
        arr[i] = output[i]

def radix_sort(arr):
    if not arr:
        return arr
    
    # Find the maximum number to know the number of digits
    max_val = max(arr)
    
    # Do counting sort for every digit
    exp = 1
    while max_val // exp > 0:
        counting_sort_radix(arr, exp)
        exp *= 10
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.BUCKET_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def bucket_sort(arr):
    if not arr:
        return arr
    
    # Find minimum and maximum values
    min_val = min(arr)
    max_val = max(arr)
    
    # Number of buckets
    bucket_count = max_val - min_val + 1
    
    # Initialize empty buckets
    buckets = [[] for _ in range(bucket_count)]
    
    # Distribute input array elements into buckets
    for num in arr:
        bucket_index = num - min_val
        buckets[bucket_index].append(num)
    
    # Sort individual buckets
    for bucket in buckets:
        bucket.sort()
    
    # Concatenate all buckets back into arr
    result = []
    for bucket in buckets:
        result.extend(bucket)
    
    return result
            """.trimIndent()
                        ),
                        SortingAlgorithm.PIGEONHOLE_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def pigeonhole_sort(arr):
    if not arr:
        return arr
    
    # Find minimum and maximum elements
    min_val = min(arr)
    max_val = max(arr)
    range_val = max_val - min_val + 1
    
    # Create pigeonholes
    pigeonholes = [0] * range_val
    
    # Populate pigeonholes
    for num in arr:
        pigeonholes[num - min_val] += 1
    
    # Put the elements back into the array in sorted order
    index = 0
    for j in range(range_val):
        while pigeonholes[j] > 0:
            arr[index] = j + min_val
            index += 1
            pigeonholes[j] -= 1
    
    return arr
            """.trimIndent()
                        ),
                        SortingAlgorithm.CYCLE_SORT to mapOf(
                                ProgrammingLanguage.PYTHON to """
def cycle_sort(arr):
    for cycle_start in range(len(arr) - 1):
        item = arr[cycle_start]
        
        # Find the position where we put the item
        pos = cycle_start
        for i in range(cycle_start + 1, len(arr)):
            if arr[i] < item:
                pos += 1
        
        # If the item is already in the correct position
        if pos == cycle_start:
            continue
        
        # Put the item to its right position
        while item != arr[pos]:
            arr[pos], item = item, arr[pos]
        
        # Rotate the rest of the cycle
        while pos != cycle_start:
            pos = cycle_start
            
            # Find the position where we put the item
            for i in range(cycle_start + 1, len(arr)):
                if arr[i] < item:
                    pos += 1
            
            # Put the item to its right position
            while item != arr[pos]:
                arr[pos], item = item, arr[pos]
    
    return arr
            """.trimIndent()
                        )
                )
}


fun getCodeSnippet(algorithm: SortingAlgorithm, language: ProgrammingLanguage): String {
    return when (language) {
        in ProgrammingLanguage.values() -> {
            languageCodeSnippetsExtended[algorithm]?.get(language)
                ?: languageCodeSnippets[algorithm]?.get(language)
                ?: ""
        }
        else -> ""
    }
}
