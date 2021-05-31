import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LBR {
    private int cells[][];

    public LBR(int[][] cells) {
        this.cells = cells;
    }

    // struktura za vrsto celic za BFS
    static class queueNode {
        int pt;     // ID celice (velikost matrike MxN; ID celice ∈ {1..M*N})
        int dist;   // dolžina poti do trenutno celice
        queueNode prevPt; // vozlišče prejšnje celice

        public queueNode(int pt, queueNode prevPt, int dist) {
            this.pt = pt;
            this.prevPt = prevPt;
            this.dist = dist;
        }
    };

    public void printPath(int from, int to) {
        boolean[] visited = new boolean[cells.length*cells[0].length +1];
        visited[from] = true;

        // vrsta celic za BFS
        Queue<queueNode> q = new LinkedList<>();
        // dodaj celico from v vrsto
        queueNode s = new queueNode(from, null, 0);
        q.add(s);

        // BFS
        while (!q.isEmpty()) {
            queueNode curr = q.peek();
            int pt = curr.pt;

            // če smo prišli do celice to => konec
            if (pt == to) {
                System.out.printf("Length %d:\n",q.peek().dist);

                // sprehodi se nazaj po poti in točke dodajaj v sklad
                queueNode node = q.remove();
                Stack<Integer> route = new Stack<Integer>();
                while (node.prevPt != null){
                    route.push(node.pt);
                    node = node.prevPt;
                }
                route.push(node.pt);

                // izprazni sklad - izpiši pot
                while (!route.isEmpty()) {
                    System.out.println(route.pop());
                }

                // končaj algoritem
                return;
            }

            // sicer dequeue prvo celico in enqueue sosednje celice
            queueNode prevPT = q.remove();
            // v vrsto dodaj vsako veljavno sosednjo celico
            for (int i = 0; i < 4; i++) {
                int new_pt = pt - 1;

                if (i == 1) {
                    // če na desnem robu preskoči
                    if (pt % cells[0].length == 0) continue;
                    new_pt = pt +1;
                } else if (i == 2) {
                    new_pt = pt - cells[0].length;
                } else if (i == 3) {
                    new_pt = pt + cells[0].length;
                }
                // če na levem robu preskoči
                else if (pt % cells[0].length == 1) continue;


                // če celice veljavna in še ne obiskana, jo dodaj v vrsto
                int y = (new_pt-1) / cells.length;
                int x = (new_pt-1) % cells.length;
                if (new_pt > 0 && new_pt <= cells.length*cells[0].length
                        && cells[y][x] == 0 && !visited[new_pt]) {
                    // označi celico za obiskano in jo dodaj v vrsto
                    visited[new_pt] = true;
                    queueNode Adjcell = new queueNode(new_pt, prevPT,curr.dist + 1);
                    q.add(Adjcell);
                }
            }
        }
        System.out.println("None");
    }
}
