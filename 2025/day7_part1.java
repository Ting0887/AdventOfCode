import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class day7_part1 {

    static class Beam {
        int r, c;
        Beam(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("input7.txt");
        Scanner sc = new Scanner(file);

        List<char[]> grid = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            char[] row = new char[line.length()];
            for(int i = 0; i < line.length(); i++){
                row[i] = line.charAt(i);
            }
            grid.add(row);
        }
        sc.close();
        System.out.println(countSplits(grid));
    }
    private static int countSplits(List<char[]> grid) {
        int R = grid.size();
        int C = grid.get(0).length;

        char[][] map = new char[R][C];
        int sr = -1, sc = -1;

        for (int i = 0; i < R; i++) {
            map[i] = grid.get(i);
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 'S') {
                    sr = i;
                    sc = j;
                }
            }
        }

        Queue<Beam> q = new LinkedList<>();
        q.offer(new Beam(sr, sc));

        boolean[][] visitedSplit = new boolean[R][C];
        int splitCount = 0;

        while (!q.isEmpty()) {
            Beam b = q.poll();
            int nr = b.r + 1;
            int nc = b.c;

            if (nr >= R) continue;

            char cell = map[nr][nc];

            if (cell == '.') {
                q.offer(new Beam(nr, nc));
            } else if (cell == '^') {
                if (!visitedSplit[nr][nc]) {
                    visitedSplit[nr][nc] = true;
                    splitCount++;

                    if (nc - 1 >= 0) q.offer(new Beam(nr, nc - 1));
                    if (nc + 1 < C) q.offer(new Beam(nr, nc + 1));
                }
            }
        }

        return splitCount;
    }
}
