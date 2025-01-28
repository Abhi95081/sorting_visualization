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
        )
        // Add more sorting algorithms here
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
