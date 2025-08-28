# TSPSolver
Solver for Traveling Salesman Problem (TSP) with two central algorithms: 

  * **Simulated Annealing**
  * **Ant-Colony system**

These algorithms use other algorithms for their operation. In particular *Nearest-Neighbour* is used as constructive algorithm for generate an initial solution, *Two-Opt* is used as local optimization algorithm and *Prim* is used when algorithms need to generate minimum spanning tree.

## Run instructions
Executing the algorithm requires some parameters:

  * Tsp file name
  * Which algorithm to use (A Ant Colony, S Simulated Annealing)
  * Random numbers feed (optional)
  * Maximum time of execution (optional, default is three minutes)
