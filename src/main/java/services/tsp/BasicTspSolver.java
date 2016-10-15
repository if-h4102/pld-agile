package services.tsp;

import models.DeliveryGraph;
import models.Planning;

import java.util.ArrayList;

public class BasicTspSolver extends AbstractTspSolver {
    /**
     * The constructor for a basic TSP solver.
     * It doesn't need anything for now.
     */
    BasicTspSolver() {
        // Nothing to do
    }

    /**
     * Solve the TSP problem from a DeliveryGraph.
     * @param graph The (complete) graph representing all delivery points and the warehouse.
     * @return The delivery plan (Planning) associated to the given DeliveryGraph.
     */
    @Override
    public Planning solve(DeliveryGraph graph) {
        // TODO
        return null;
    }

    public void showPermutations(int nodes) {
        // Create nodes lists
        ArrayList<Integer> notSeen = new ArrayList<Integer>(nodes);
        ArrayList<Integer> seen = new ArrayList<Integer>();
        // Let's say that the first seen node is 0...
        seen.add(0);
        // ...so the others aren't seen yet
        for(int i = 1; i < nodes; i++) {
            notSeen.add(i);
        }
        // Let's compute permutations
        getPermutations(0, notSeen, seen);
    }

    private void getPermutations(int node, ArrayList<Integer> notSeen, ArrayList<Integer> seen) {
        if(notSeen.size() == 0) {
            // Stop it
            // "seen" contains a new nodes permutation
        } else {
            for(int nextNode: notSeen) {
                seen.add(nextNode);
                notSeen.remove(nextNode);
                getPermutations(nextNode, notSeen, seen);
                // Show it
                this.showIntegerArray(seen);
                // Let's put everything like it was before
                notSeen.add(nextNode);
                seen.remove(nextNode);
            }
        }
    }

    private void showIntegerArray(ArrayList<Integer> l) {
        System.out.print("Seen: ");
        for(int i: l) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
