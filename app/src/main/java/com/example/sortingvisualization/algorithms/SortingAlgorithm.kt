package com.example.sortingvisualization.algorithms

enum class SortingAlgorithm(val displayName: String) {
    // Comparison-based sorting algorithms
    BUBBLE_SORT("Bubble Sort"), 
    SELECTION_SORT("Selection Sort"), 
    INSERTION_SORT("Insertion Sort"), 
    MERGE_SORT("Merge Sort"), 
    QUICK_SORT("Quick Sort"), 
    HEAP_SORT("Heap Sort"),

    // Advanced comparison-based sorting
    SHELL_SORT("Shell Sort"),
    COCKTAIL_SHAKER_SORT("Cocktail Shaker Sort"),
    COMB_SORT("Comb Sort"),

    // Non-comparison based sorting
    COUNTING_SORT("Counting Sort"),
    RADIX_SORT("Radix Sort"),
    BUCKET_SORT("Bucket Sort"),

    // Additional sorting algorithms
    PIGEONHOLE_SORT("Pigeonhole Sort"),
    TIM_SORT("Tim Sort"),
    GNOME_SORT("Gnome Sort"),
    CYCLE_SORT("Cycle Sort"),
    PANCAKE_SORT("Pancake Sort")
}
