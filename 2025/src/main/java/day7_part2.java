import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day7_part2 {
    public static void main(String[] args) throws Exception{
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
        System.out.println(countTimeLine(grid));
    }
    private static long countTimeLine(List<char[]> grid){
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

        long[][] ways = new long[R][C];
        ways[sr][sc] = 1;

        long result = 0;

        for (int r = sr; r < R - 1; r++) {
            for (int c = 0; c < C; c++) {
                if (ways[r][c] == 0) continue;

                char next = map[r + 1][c];

                if (next == '.') {
                    ways[r + 1][c] += ways[r][c];
                } else if (next == '^') {
                    if (c - 1 >= 0)
                        ways[r + 1][c - 1] += ways[r][c];
                    if (c + 1 < C)
                        ways[r + 1][c + 1] += ways[r][c];
                }
            }
        }

        // 出界的時間線
        for (int c = 0; c < C; c++) {
            result += ways[R - 1][c];
        }

        return result;
    }
}
