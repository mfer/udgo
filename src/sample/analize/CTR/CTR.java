
/******************************************************************************
 *  Compilation:  javac CTR.java
 *  Execution:    java CTR filename.txt
 *  Dependencies: PrimMST.java EdgeWeightedGraph.java Edge.java
 *                IndexMinPQ.java In.java StdOut.java
 *
 */

/*
*
*  input 
*      node_id-x,y,z
*      node_id(rx)-node_id(tx)
*  produce
*      a graph in which the nodes are connected if there is a pair rx-tx
*      submit the graph to PRIM algorithm 
*          to produce the MST : minimum spanning tree
*  output
*      CTR : critical range transmission
*          that is the maximum distance between nodes in the MST
*  @author sudolshw@gmail.com
*/

public class CTR {

    public static void main(String[] args) {
        Integer elements=0;
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
            elements++;
        }
        StdOut.printf("%d/", elements);
        StdOut.printf("%d\n", G.V()-1);
        StdOut.printf("%.5f\n", mst.weight());
        StdOut.printf("%.5f\n", mst.biggest());
    }


}
