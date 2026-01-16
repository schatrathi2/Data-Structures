import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Sruthi Chatrat
 * @version 1.0
 * @userid schatrathi6
 * @GTID 903558326
 *
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least one of the inputs is null.");
        }
        boolean s = false;
        for (Vertex<T> v : graph.getVertices()) {
            if (start.equals(v)) {
                s = true;
            }
        }
        if (!s) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        List<Vertex<T>> l = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> al = graph.getAdjList();
        dfsrecursive(start, graph, l, vs, al);
        return l;
    }

    /**
     *
     * Recursive method to help with DFS.
     *
     * @param <T>   the generic typing of the data
     * @param curr the  current vertex
     * @param graph the graph to search through
     * @param l the list to be returned in main method
     * @param vs the list of visited vertices
     * @param al the adjacency list of the graph
     */
    public static <T> void dfsrecursive(Vertex<T> curr, Graph<T> graph, List<Vertex<T>> l, Set<Vertex<T>> vs,
                                        Map<Vertex<T>, List<VertexDistance<T>>> al) {
        vs.add(curr);
        if (!l.contains(curr)) {
            l.add(curr);
        }
        List<VertexDistance<T>> neighbors = al.get(curr);
        for (int i = 0; i < neighbors.size(); i++) {
            if (!vs.contains(neighbors.get(i).getVertex())) {
                dfsrecursive(neighbors.get(i).getVertex(), graph, l, vs, al);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least one of the inputs is null.");
        }
        boolean s = false;
        for (Vertex<T> v : graph.getVertices()) {
            if (start.equals(v)) {
                s = true;
            }
        }
        if (!s) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> vs = new HashSet<>();
        Map<Vertex<T>, Integer> dm = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> al = graph.getAdjList();
        for (Vertex<T> v : graph.getVertices()) {
            dm.put(v, Integer.MAX_VALUE);
        }
        int d = 0;
        VertexDistance<T> vd = new VertexDistance<>(start, d);
        pq.add(vd);
        while (!pq.isEmpty() && vs.size() < graph.getVertices().size()) {
            vd = pq.remove();
            if (!vs.contains(vd.getVertex())) {
                vs.add(vd.getVertex());
                dm.replace(vd.getVertex(), vd.getDistance());
                d = vd.getDistance();
                List<VertexDistance<T>> neighbors = al.get(vd.getVertex());
                for (int i = 0; i < neighbors.size(); i++) {
                    if (!vs.contains(neighbors.get(i).getVertex())) {
                        vd = new VertexDistance<>(neighbors.get(i).getVertex(), d + neighbors.get(i).getDistance());
                        pq.add(vd);
                    }
                }
            }
        }
        return dm;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least one of the inputs is null.");
        }
        boolean s = false;
        for (Vertex<T> v : graph.getVertices()) {
            if (start.equals(v)) {
                s = true;
            }
        }
        if (!s) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> vs = new HashSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> al = graph.getAdjList();
        for (Edge<T> e : graph.getEdges()) {
            pq.add(e);
        }
        vs.add(start);
        while (!pq.isEmpty() && vs.size() < graph.getVertices().size()) {
            Edge<T> e = pq.remove();
            if (!vs.contains(e.getV())) {
                vs.add(e.getV());
                mst.add(e);
                mst.add(new Edge<>(e.getV(), e.getU(), e.getWeight()));
                List<VertexDistance<T>> neighbors = al.get(e.getV());
                for (int i = 0; i < neighbors.size(); i++) {
                    if (!vs.contains(neighbors.get(i).getVertex())) {
                        pq.add(new Edge<>(e.getV(), neighbors.get(i).getVertex(), neighbors.get(i).getDistance()));
                    }
                }
            }
        }
        return mst;
    }
}