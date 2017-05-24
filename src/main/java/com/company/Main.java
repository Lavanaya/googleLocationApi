package com.company;

import java.util.*;

public class Main {

    int V, E;    // V-> no. of vertices & E->no.of edges
    private Edge edge[];
    final int[] result=new int[7];
    final Map<Integer,LinkedList<Integer>> map=new HashMap<Integer, LinkedList<Integer>>();
    final List<Integer> preOderWalk=new ArrayList<Integer>();

    class Edge implements Comparable<Edge>
    {
        int src, dest, weight;

        // Comparator function used for sorting edges based on
        // their weight
        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    }

    public Main(){}
    public Main (int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }

    public static void main(String[] args) throws Exception{
	// write your code here
        Main main=new Main();
      //  main.findShortestPath(p);
        main.findShortestPath(new String[]{
                "Vancouver,BC",
                "SanFrancisco",
                "Seattle, WA, USA",
                "Victoria, BC, Canada",
                "Toronto, Canada"

        });
      //  main.testFunction();

    }

    public void findShortestPath(String[] p) throws Exception{

        V = p.length;  // Number of vertices in graph
        E = V*(V-1)/2;  // Number of edges in a fully connected graph is n(n-1)/2

        //initialize edges
        Main graph = new Main(V, E);

        //undirected graph so get distance of one point to another only from one direction and initialize weights=distances
        int k=0;
        for(int i=0;i<p.length ; i++){
            for(int j=i+1;j<p.length;j++)
            {
                float dist=new DistanceCalculator().distancePointInput(new String[]{p[i],p[j]});

               graph.edge[k].src = i;
               graph.edge[k].dest = j;
               graph.edge[k].weight = Math.round(dist);
               k++;
            }
        }
        prims(graph);
     }


    /**
     *calculate minimum spanning tree using prims algo
     * @param graph with initialised vertex and edges
     */
    public void prims(Main graph){
        ArrayList<Boolean> mstSet=new ArrayList<Boolean>();
        int[] edgeMinWeight=new int[V];

        //initialize edgeMinWeight=Infinite and mstSet(vertex in mst) as false
        for(int i=0;i<V;i++){
            mstSet.add(i,false);
            edgeMinWeight[i]=Integer.MAX_VALUE;
        }

        //assign edgeMinWeight of source point as 0 to pick it first
        edgeMinWeight[0]=0;

        int k=0;
        System.out.println("Prims path calculated:");
        while(k!=V-2){
           int v= findMinWeightVertex(mstSet,edgeMinWeight);
         //   System.out.println("v="+v);
           mstSet.set(v,true);
           //assuming fully connected graph update minWeight value of all connected edges

            for(int i=0;i<E;i++){
                    if((graph.edge[i].src==v ) && !mstSet.get(graph.edge[i].dest) &&  graph.edge[i].weight < edgeMinWeight[graph.edge[i].dest])
                    {
                        //update parent of destination node
                        System.out.println(result[graph.edge[i].dest]);
                        if(map.get(result[graph.edge[i].dest])!=null)
                        {
                            boolean value=  map.get(result[graph.edge[i].dest]).remove((Object)graph.edge[i].dest);
                            System.out.println(value);
                        }

                        System.out.println("result "+ v + "--> "+graph.edge[i].dest + ",   "+graph.edge[i].weight );
                        if(map.containsKey(v))
                            map.get(v).add(graph.edge[i].dest);
                        else
                        {
                            map.put(v,new LinkedList<Integer>(Arrays.asList(graph.edge[i].dest)));
                        }

                        //adding parent of destination node
                        result[graph.edge[i].dest]= v;
                        edgeMinWeight[graph.edge[i].dest]=graph.edge[i].weight;
                    }
                }
            k++;
        }
        DFS(0);
    }

    /**
     *find vertex with minimum weight and which is not yet visited
     * @param mstSet
     * @param edWt
     * @return
     */
    public int findMinWeightVertex(List<Boolean> mstSet,int[] edWt){
        int minValue=Integer.MAX_VALUE,minValueIndex=-1;
        for(int i=0;i< V;i++){
            if( (!mstSet.get(i)) && minValue>edWt[i])
            {
                minValue=edWt[i];
                minValueIndex=i;
            }
        }
        return minValueIndex;
    }

    /**
     *
     * @param v
     * @param visited
     */
    void DFSUtil(int v,boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;
        preOderWalk.add(v);
      //  System.out.print(v+" ");
        if(!map.containsKey(v))
            return;
        // Recur for all the vertices adjacent to this vertex
        for (Integer n : map.get(v)) {
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    /**
     * The function to do DFS traversal. It uses recursive DFSUtil()
     * @param v
     */

    void DFS(int v)
    {
        // Mark all the vertices as not visited(set as false by default in java)
        boolean visited[] = new boolean[V];
        // Call the recursive helper function to print DFS traversal
        DFSUtil(v, visited);
        preOderWalk.add(0);
        System.out.println("PreOrder Walk: "+preOderWalk.toString());
    }

    //unit test function
    public void testFunction(){
        V=5;E=7;
        Main graph = new Main(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 2;

        // add edge 0-3
        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 6;

        // add edge 1-3
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 8;

        //add edge 1-2
        graph.edge[6].src = 1;
        graph.edge[6].dest = 2;
        graph.edge[6].weight = 3;

        //add edge 2-4
        graph.edge[5].src = 2;
        graph.edge[5].dest = 4;
        graph.edge[5].weight = 7;

        //add edge 1-4
        graph.edge[1].src = 1;
        graph.edge[1].dest = 4;
        graph.edge[1].weight = 5;

        //add edge 3-4
        graph.edge[4].src = 3;
        graph.edge[4].dest = 4;
        graph.edge[4].weight = 9;

        prims(graph);

    }


}
