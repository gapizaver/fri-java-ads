## Data structures

### List: Seznam.java

List of elements Elt.java

### Binary search tree: BST.java

### Hashtable: HTB.java

### Graph with weights: GRPH.java

Weighted graph with implemented Bellman-Ford algorithm. Graph is initialized with number of vertices `int verticesCount`. Edges are added with method `void addEdge(int from, int to, int cost);` . Method `void printShortestDistsFrom(int from);` uses Bellman-Ford algorithm to find the cheapest paths from vertex *from* to all other vertices. The prices are displayed for every route.

*Negative weights are allowed.*

### Binary maze: LBR.java

Given a MxN matrix of cells where each element can either be 0 or 1, the shortest path from cell *from* to cell *to* is found with method `void printPath(int from, int to);`

## Algorithms

### Sorting algorithms: Sort.java

- 8 different sorting algorithms

- ascending or descending sorting

- **UI instructions**

  - instructions are read through standard input

  - instruction consists of 2 lines:

    1. <trace/count> <.algorithm> <up/down>
       - trace outputs a trace of algorithm's sorting
       - algorithms:
         - `insert` - snsertion sort
         - `select` - selection sort
         - `bubble` - bubble sort
         - `heap` - heap sort
         - `merge` - merge sort
         - `quick` - quick sort (p = first element)
         - `radix` - radix sort
         - `bucket` - bucket sort ( k = n/2) 
       - count counts number of moves and comparisons
    2. line of numbers separated with space

  - example

    1. `trace insert up `
    2. `42 17 27 51 39`

  - output:

    ```
    42 17 27 51 39 
    17 42 | 27 51 39 
    17 27 42 | 51 39 
    17 27 42 51 | 39 
    17 27 39 42 51 |
    ```

