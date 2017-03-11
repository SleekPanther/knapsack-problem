# 0-1 Knapsack Problem
Implementation of the 0-1 (binary) Knapsack Problem

##Optimal Substructure
w is the current max weight of the knapsack (goes from 0 to W, the actual max weight of the knapsack)  
- If wi>W, the item it too heavy to fit in the knapsack so we copy the value from row above: `OPT(i-1,w)`
- If the item CAN fit there are 2 cases
  1. The current item is not included, just copy from row above: `OPT(i-1,w)`
  2. Current iten IS included, add it's value & find OPT for a new weight: `vi + OPT(i-1,w-wi)`
![](https://raw.githubusercontent.com/SleekPanther/knapsack-problem/master/picturres/optimal-substructure.png)

##Pseudocode
![](https://raw.githubusercontent.com/SleekPanther/knapsack-problem/master/picturres/pseudocode.png)

##Finding the Knapsack's Contents
A slightly different version of the algorithm, but the point is the final loop that recovers the items from the keep array
![](https://raw.githubusercontent.com/SleekPanther/knapsack-problem/master/picturres/knapsack-pseudocode-backtrack.png)

##Example Problem
![](https://raw.githubusercontent.com/SleekPanther/knapsack-problem/master/picturres/items.png)
This program runs on 5 items with 2 differnt weight capacites: 11 and 10

###Capacity=11 Solution
![](https://raw.githubusercontent.com/SleekPanther/knapsack-problem/master/picturres/w%3D11-table.png)
###Optimal Solution is total value=40 from item 3 & 4


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
- A `true` in `chosenItems` means that an item was added to get that max value when calculating `memoizationTable`, `false` means it just **copied the value from the row above**
