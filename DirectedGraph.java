/*
 * Name: Corey Glover
 * Date: July10, 2023
 * Course: CMSC 350
 * Description: Class DirectedGraph that will take the input and separate the values
 * into individual vertices then prints them depending on their dependents
 * Source: Similar code from textbook provided by UMGS course, Youtube: https://www.youtube.com/watch?v=wMq9c_sKnyE
 * Youtube: https://www.youtube.com/watch?v=wJSTWTu4RHU, https://www.youtube.com/watch?v=q6xXq6Doj00 and 
 * https://www.youtube.com/watch?v=j5flXM4CmM4&list=PLLhXOOcUg89CBvj7ntb8DWFCkTVKrn-U1&index=18
 */
import java.io.*;
import java.util.*;

public class DirectedGraph {
	// performed similar to project on linklist
    static Vertex head;
    static Vertex tail;

    static class Vertex {
        boolean found = false;
        boolean complete = false;
        String name;
        Edge firstEdge;
        Edge lastEdge;
        Vertex next;

        public Vertex(String name) {
            this.name = name;
        }
    }

    public static class Edge {
        Vertex vertex;
        Edge next;

        public Edge(Vertex vertex) {
            this.vertex = vertex;
        }
    }

    public static void addEdge(String source, String dest) {
        Vertex sourceVer = findAddVertex(source);
        Vertex destVer = findAddVertex(dest);

        Edge newEdge = new Edge(destVer);

        if (sourceVer.lastEdge != null) {
        	sourceVer.lastEdge.next = newEdge;
        } else {
        	sourceVer.firstEdge = newEdge;
        }
        sourceVer.lastEdge = newEdge;
    }

    public static Vertex find(Vertex current, String name) {
        if (current == null) {
            return null; // Vertex not found
        }

        if (current.name.equals(name)) {
            current.found = true;
            return current; // Vertex found
        }

        return find(current.next, name); // Recursively search for the vertex
    }

    public static Vertex findAddVertex(String name) {
        Vertex vertex = find(head, name);

        if (vertex != null) {
            return vertex; // Vertex found
        }

        vertex = new Vertex(name);

        if (tail != null) {
            tail.next = vertex;
        } else {
            head = vertex;
        }
        tail = vertex;

        return vertex; // New vertex added
    }

    static void print() {
        Vertex curVer = head;
        while (curVer != null) {
            System.out.print("Vertex: " + curVer.name + " Edges: ");
            Edge curEdge = curVer.firstEdge;
            while (curEdge != null) {
                System.out.print(curEdge.vertex.name + " ");
                curEdge = curEdge.next;
            }
            System.out.println();
            
            curVer = curVer.next;
        }
    }

    public static void removeVertex() {
        Vertex curVer = head;
        Vertex lastVer = null;
        
        while (curVer != null) {
            boolean hasNext = false;
            Edge edge = curVer.firstEdge;

            while (edge != null) {
                if (edge.vertex != null) {
                	hasNext = true;
                    break;
                }
                edge = edge.next;
            }

            if (!hasNext) {
                if (lastVer == null) {
                    // Remove the head vertex
                    head = curVer.next;
                } else {
                    // Remove the current vertex
                	lastVer.next = curVer.next;
                }

                // Update the tail if the current vertex is the tail
                if (curVer == tail) {
                    tail = lastVer;
                }

                // Move to the next vertex
                curVer = curVer.next;
            } else {
                // Move to the next vertex
            	lastVer = curVer;
                curVer = curVer.next;
            }
        }
    }

    public static void notReached() {
        Vertex curVer = head;
        depthFirst(curVer);
        while (curVer != null) {
            if (!curVer.found) {
                System.out.print("\n-----------------------\n");
                System.out.println(curVer.name + " is unreachable");
            }
            curVer = curVer.next;
        }
        
    }
    
    public static void cycleDetected(Vertex check) {
    	
    	System.out.print("Cycle detected at: " + check.name);
    }
    
    public static void depthFirst(Vertex start) {
    	 if (start == null) {
             return;
         }

         start.found = true;
//         actions.processVertex(start);

         Edge newEdge = start.firstEdge;
         while (newEdge != null) {
             Vertex nextEdge = newEdge.vertex;
             if (!nextEdge.found) {
//                 actions.descend();
                 depthFirst(nextEdge);
//                 actions.ascend();
             } else if (!nextEdge.complete) {
//                 actions.cycleDetected(nextCase);
            	 System.out.println("-----------------------");
            	 cycleDetected(nextEdge);
             }
             newEdge = newEdge.next;
         }
         start.complete = true;
    }

    public static void buildGraph() throws IOException {
        String string = "ClassA ClassC ClassE ClassJ\r\n" + "ClassB ClassD ClassG\r\n" + "ClassC ClassA\n"
                + "ClassE ClassB ClassF ClassH\r\n" + "ClassJ ClassB\r\n" + "ClassI ClassC";

        BufferedReader read = new BufferedReader(new StringReader(string));

        List<String[]> array = new ArrayList<String[]>();

        for (String line = read.readLine(); line != null; line = read.readLine()) {
            String[] fltStr = line.split(" ");
            String[] str = new String[fltStr.length];
            for (int i = 0; i < str.length; i++) {
                str[i] = fltStr[i];
            }
            array.add(str);
        }
        int V = array.size();

        ArrayList<ArrayList<String>> adj = new ArrayList<ArrayList<String>>(V);
        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<String>());
        for (int a = 0; a < array.size(); a++) {
            String[] word = array.get(a);

            for (int i = 1; i < word.length; i++) {
                DirectedGraph.addEdge(word[0], word[i]);
            }
        }

        // Printing the graph
        DirectedGraph.removeVertex();

        // Printing the updated graph
        DirectedGraph.print();

        // Display unreachable classes
        notReached();
    }

    // Driver Code
    public static void main(String[] args) throws IOException {
        buildGraph();
    }
}
