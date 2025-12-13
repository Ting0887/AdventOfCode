import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class day8_part2 {
    static class Edge {
        int u, v;
        long dist;
        Edge(int u, int v, long dist) {
            this.u = u;
            this.v = v;
            this.dist = dist;
        }
    }

    static class UnionFind {
        int[] parent;
        int[] size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;

            if (size[ra] < size[rb]) {
                parent[ra] = rb;
                size[rb] += size[ra];
            } else {
                parent[rb] = ra;
                size[ra] += size[rb];
            }
            return true;
        }
    }

    public static void main(String[] args) throws Exception{
        List<long[]> points = new ArrayList<>();
        File file = new File("testInput/input8.txt");
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            String line = sc.nextLine();
            String[] part = line.split(",");
            long x = Integer.parseInt(part[0]);
            long y = Integer.parseInt(part[1]);
            long z = Integer.parseInt(part[2]);
            long[] point = new long[3];
            point[0] = x;
            point[1] = y;
            point[2] = z;
            points.add(point);
        }
        sc.close();

        int n = points.size();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long dx = points.get(i)[0] - points.get(j)[0];
                long dy = points.get(i)[1] - points.get(j)[1];
                long dz = points.get(i)[2] - points.get(j)[2];
                long dist = dx * dx + dy * dy + dz * dz;
                edges.add(new Edge(i, j, dist));
            }
        }

        edges.sort(Comparator.comparingLong(e -> e.dist));

        UnionFind uf = new UnionFind(n);
        int circuitsLeft = n;
        long lastXProduct = 0;

        for (Edge e : edges) {
            if (uf.union(e.u, e.v)) {
                circuitsLeft--;
                if (circuitsLeft == 1) {
                    lastXProduct = points.get(e.u)[0] * points.get(e.v)[0];
                    break;
                }
            }
        }

        System.out.println(lastXProduct);
    }
}
