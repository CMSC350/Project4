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
            return null; // not found
        }

        if (current.name.equals(name)) {
            current.found = true;
            return current; // found
        }

        return find(current.next, name); // search for the vertex
    }

    public static Vertex findAddVertex(String name) {
        Vertex vertex = find(head, name);

        if (vertex != null) {
            return vertex; // found
        }

        vertex = new Vertex(name);

        if (tail != null) {
            tail.next = vertex;
        } else {
            head = vertex;
        }
        tail = vertex;

        return vertex; // New vertex
    }

    static void print() {
    	System.out.print("Standard View: \n");
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
        System.out.print("------------------------");
    }
    static void hierarchyPrint() {
    	System.out.print("\nHierarchy View: \n");
        Vertex curVer = head;
        while (curVer != null) {
            System.out.print(curVer.name);
            Edge curEdge = curVer.firstEdge;
            while (curEdge != null) {
                System.out.print("\n   " + curEdge.vertex.name);
                curEdge = curEdge.next;
            }
            System.out.println();            
            curVer = curVer.next;
        }
        System.out.print("----------------------");
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

    public static boolean notReached() {
        Vertex curVer = head;
        depthFirst(curVer);
        while (curVer != null) {
            if (!curVer.found) {
                System.out.print("\n-----------------------\n");
                System.out.println(curVer.name + " is unreachable");
                return true;
            }
            curVer = curVer.next;
        }
		return false;
        
    }
    
    public static void cycleDetected(Vertex check) {
    	
    	System.out.print("Cycle detected at: " + check.name);
    }
    
    public static void depthFirst(Vertex start) {
    	 if (start == null) {
             return;
         }

         start.found = true;

         Edge newEdge = start.firstEdge;
         while (newEdge != null) {
             Vertex nextEdge = newEdge.vertex;
             if (!nextEdge.found) {
                 depthFirst(nextEdge);
             } else if (!nextEdge.complete) {
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
        DirectedGraph.hierarchyPrint();

        // Display unreachable classes
        notReached();
    }

    // Driver Code
    public static void main(String[] args) throws IOException {
        buildGraph();
    }
}

