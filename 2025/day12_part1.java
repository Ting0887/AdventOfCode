import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class day12_part1 {

    // ========= 主程式 =========
    public static void main(String[] args) throws Exception {
        File file = new File("testInput/input12.txt");
        Scanner sc = new Scanner(file);

        List<List<int[]>> baseShapes = new ArrayList<>();
        List<String> shapeLines = new ArrayList<>();

        String line;
        boolean readingShapes = true;

        List<String> regionLines = new ArrayList<>();

        while (sc.hasNext()) {
            line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            // region 開始
            if (line.contains("x") && line.contains(":")) {
                readingShapes = false;
            }

            if (readingShapes) {
                if (line.endsWith(":")) {
                    if (!shapeLines.isEmpty()) {
                        baseShapes.add(parseShape(shapeLines));
                        shapeLines.clear();
                    }
                } else {
                    shapeLines.add(line);
                }
            } else {
                regionLines.add(line);
            }
        }
        sc.close();
        if (!shapeLines.isEmpty()) {
            baseShapes.add(parseShape(shapeLines));
        }

        // 產生所有旋轉/翻轉形態
        List<List<List<int[]>>> variants = new ArrayList<>();
        int[] areas = new int[baseShapes.size()];
        for (int i = 0; i < baseShapes.size(); i++) {
            variants.add(generateVariants(baseShapes.get(i)));
            areas[i] = baseShapes.get(i).size();
        }

        int answer = 0;

        for (String r : regionLines) {
            if (canFitRegion(r, variants, areas)) {
                answer++;
            }
        }

        System.out.println(answer);
    }

    // ========= Shape Parsing =========
    static List<int[]> parseShape(List<String> lines) {
        List<int[]> cells = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == '#') {
                    cells.add(new int[]{x, y});
                }
            }
        }
        return cells;
    }

    // ========= 旋轉 + 翻轉 =========
    static List<List<int[]>> generateVariants(List<int[]> shape) {
        Set<String> seen = new HashSet<>();
        List<List<int[]>> res = new ArrayList<>();

        for (int fx = 0; fx < 2; fx++) {
            for (int fy = 0; fy < 2; fy++) {
                for (int rot = 0; rot < 4; rot++) {
                    List<int[]> cur = new ArrayList<>();
                    for (int[] p : shape) {
                        int x = p[0], y = p[1];
                        if (fx == 1) x = -x;
                        if (fy == 1) y = -y;
                        for (int i = 0; i < rot; i++) {
                            int t = x;
                            x = y;
                            y = -t;
                        }
                        cur.add(new int[]{x, y});
                    }
                    normalize(cur);
                    String key = encode(cur);
                    if (seen.add(key)) {
                        res.add(cur);
                    }
                }
            }
        }
        return res;
    }

    static void normalize(List<int[]> shape) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (int[] p : shape) {
            minX = Math.min(minX, p[0]);
            minY = Math.min(minY, p[1]);
        }
        for (int[] p : shape) {
            p[0] -= minX;
            p[1] -= minY;
        }
    }

    static String encode(List<int[]> shape) {
        shape.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        StringBuilder sb = new StringBuilder();
        for (int[] p : shape) {
            sb.append(p[0]).append(',').append(p[1]).append(';');
        }
        return sb.toString();
    }

    // ========= Region 判斷 =========
    static boolean canFitRegion(
            String line,
            List<List<List<int[]>>> variants,
            int[] areas
    ) {
        String[] parts = line.split(":");
        String[] wh = parts[0].split("x");
        int W = Integer.parseInt(wh[0]);
        int H = Integer.parseInt(wh[1]);

        String[] nums = parts[1].trim().split("\\s+");
        int[] cnt = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            cnt[i] = Integer.parseInt(nums[i]);
        }

        // 面積剪枝
        int need = 0;
        for (int i = 0; i < cnt.length; i++) {
            need += cnt[i] * areas[i];
        }
        if (need > W * H) return false;

        // 展開禮物
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            for (int k = 0; k < cnt[i]; k++) {
                list.add(i);
            }
        }

        // 先放大的
        list.sort((a, b) -> areas[b] - areas[a]);

        int[] order = list.stream().mapToInt(i -> i).toArray();

        Board board = new Board(W, H);
        return dfs(board, order, 0, variants);
    }

    // ========= 回溯 =========
    static boolean dfs(
            Board board,
            int[] order,
            int idx,
            List<List<List<int[]>>> variants
    ) {
        if (idx == order.length) return true;

        int id = order[idx];

        for (int y = 0; y < board.H; y++) {
            for (int x = 0; x < board.W; x++) {
                for (List<int[]> v : variants.get(id)) {
                    if (board.canPlace(v, x, y)) {
                        board.place(v, x, y, true);
                        if (dfs(board, order, idx + 1, variants))
                            return true;
                        board.place(v, x, y, false);
                    }
                }
            }
        }
        return false;
    }

    // ========= Board =========
    static class Board {
        int W, H;
        boolean[][] grid;

        Board(int w, int h) {
            W = w;
            H = h;
            grid = new boolean[h][w];
        }

        boolean canPlace(List<int[]> shape, int x0, int y0) {
            for (int[] p : shape) {
                int x = x0 + p[0];
                int y = y0 + p[1];
                if (x < 0 || y < 0 || x >= W || y >= H || grid[y][x])
                    return false;
            }
            return true;
        }

        void place(List<int[]> shape, int x0, int y0, boolean val) {
            for (int[] p : shape) {
                grid[y0 + p[1]][x0 + p[0]] = val;
            }
        }
    }
}
