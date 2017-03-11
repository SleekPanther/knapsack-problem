# 0-1 Knapsack Problem
Implementation of the 0-1 (binary) Knapsack Problem

##Notes
- `solveKnapsack()` populated the memoization table & finds the maximum weight possible
- `findOptimalKnapsackContents()` is called after `solveKnapsack()` to see what items were actually added
- **The ID or name of an Item is its array index**
- Must be **non-negative** integer weights
- Arrays for `values` and `weights` are hard coded into Constructor. No error checking  
- View output with **fixed-width font** for columns to line up (configure your IDE or copy to new document)

##Code Details
- Arrays are indexed from 1 to make human readable
- Put a 0 as the 1st element of `values` and `weights`
- `memoizationTable` and `chosenItems` are both initialized to dimensions `[numberOfItems+1][knapsackWeightCapacity+1]` to keep it indexed from 1
- Pseudocode has step to set all values in 0th row to 0. **Not needed since Java initialized the entire 2D array to 0's**
- `findWidestNumberInTable()`, `findWidestElementInList()` and `leftPad()` are all helper methods used by `printTable()`
- `printTable()` is pretty fancy & prints padding spaces to the left of numbers so the columns line up. It looks nicer, but doesn't have to be that complicated
