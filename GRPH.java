import java.util.*;

public class GRPH {
    private Integer[][] edges;

    public GRPH(int verticesCount) {
        this.edges = new Integer[verticesCount][verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                edges[i][j] = null;
            }
        }
    }

    public void addEdge(int from, int to, int cost) {
        this.edges[from][to] = cost;
    }

    // Bellman-Fordov algoritem
    public void printShortestDistsFrom(int from) {
        // inicializacija - dolžine do vseh ostalih vozlišč neskončno
        int dist[] = new int[this.edges.length];
        for (int i = 0; i < this.edges.length; i++) {
                dist[i] = Integer.MAX_VALUE;
        }
        dist[from] = 0;

        // izračunaj najkrajše poti od src do ostalih vozlišč
        for (int i = 0; i < this.edges.length - 1; i++) {
            for (int u = 0; u < this.edges.length; u++) {
                for (int v = 0; v < this.edges.length; v++) {
                    // obstaja pot med vozliščema
                    if (edges[u][v] != null) {
                        if (dist[u] != Integer.MAX_VALUE && dist[u] + edges[u][v] < dist [v]) {
                            dist[v] = dist[u] + edges[u][v];
                        }
                    }
                }
            }
        }

        // izpiši rezultat
        System.out.println("V .. Cena");
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] != Integer.MAX_VALUE)
                System.out.printf("%d .. %d\n", i, dist[i]);
            else {
                System.out.printf("%d .. None\n", i);
            }

        }
    }

    public static void main(String[] args) {
        GRPH g = new GRPH(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 3, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(3, 2, 1);
        g.printShortestDistsFrom(0);
    }
}