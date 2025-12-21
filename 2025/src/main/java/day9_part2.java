import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class day9_part2 {
    static class P {
        int x, y;
        P(int x, int y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("testInput/input9.txt"));
        List<P> red = new ArrayList<>();
        while (sc.hasNext()) {
            String[] s = sc.nextLine().split(",");
            red.add(new P(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
        }
        sc.close();

        int n = red.size();

        // --- 1. 計算兩點矩形面積 ---
        class Rect {
            long area;
            P a, b;
            Rect(P a, P b) {
                this.a = a; this.b = b;
                this.area = (long)(Math.abs(a.x - b.x) + 1) * (Math.abs(a.y - b.y) + 1);
            }
        }

        List<Rect> rects = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                rects.add(new Rect(red.get(i), red.get(j)));
            }
        }
        // 依照面積降序排序
        rects.sort((r1, r2) -> Long.compare(r2.area, r1.area));

        // --- 2. 建立水平線 & 垂直線 ---
        Map<Integer, List<int[]>> horizontalLines = new HashMap<>();
        Map<Integer, List<int[]>> verticalLines = new HashMap<>();

        for (int i = 0; i < n; i++) {
            P a = red.get(i);
            P b = red.get((i+1)%n);

            if (a.x == b.x) {
                verticalLines.computeIfAbsent(a.x, k -> new ArrayList<>())
                             .add(new int[]{Math.min(a.y, b.y), Math.max(a.y, b.y)});
            } else {
                horizontalLines.computeIfAbsent(a.y, k -> new ArrayList<>())
                               .add(new int[]{Math.min(a.x, b.x), Math.max(a.x, b.x)});
            }
        }

        Map<Integer, Set<Integer>> hx = new HashMap<>();
        for (int y : horizontalLines.keySet()) {
            Set<Integer> s = new HashSet<>();
            for (int[] seg : horizontalLines.get(y)) {
                for (int x = seg[0]; x <= seg[1]; x++) s.add(x);
            }
            hx.put(y, s);
        }

        Map<Integer, Set<Integer>> vy = new HashMap<>();
        for (int x : verticalLines.keySet()) {
            Set<Integer> s = new HashSet<>();
            for (int[] seg : verticalLines.get(x)) {
                for (int y = seg[0]; y <= seg[1]; y++) s.add(y);
            }
            vy.put(x, s);
        }

        // --- 4. 判斷點是否在邊界 ---
        java.util.function.BiPredicate<Integer,Integer> isPointOnBoundary = (x,y) -> {
            return (hx.containsKey(y) && hx.get(y).contains(x)) ||
                   (vy.containsKey(x) && vy.get(x).contains(y));
        };

        // --- 5. 判斷矩形內部是否有線段交集 ---
        java.util.function.BiFunction<P,P,Boolean> hasIntersection = (a,b) -> {
            int x1 = Math.min(a.x, b.x) + 1, x2 = Math.max(a.x, b.x);
            int y1 = Math.min(a.y, b.y) + 1, y2 = Math.max(a.y, b.y);

            for (int y : horizontalLines.keySet()) {
                if (y >= y1 && y < y2) {
                    for (int[] seg : horizontalLines.get(y)) {
                        for (int x = seg[0]+1; x < seg[1]; x++) {
                            if (x >= x1 && x < x2) return false;
                        }
                    }
                }
            }
            for (int x : verticalLines.keySet()) {
                if (x >= x1 && x < x2) {
                    for (int[] seg : verticalLines.get(x)) {
                        for (int y : range(seg[0]+1, seg[1])) {
                            if (y >= y1 && y < y2) return false;
                        }
                    }
                }
            }
            return true;
        };

        // --- 6. 找最大矩形 ---
        Long p2 = null;
        for (Rect r : rects) {
            if (isPointOnBoundary.test(r.a.x, r.b.y) || isPointOnBoundary.test(r.b.x, r.a.y)) {
                if (hasIntersection.apply(r.a, r.b)) {
                    p2 = r.area;
                    break;
                }
            }
        }
        System.out.println(p2);
    }

    // range(a,b) 不包含 b
    static List<Integer> range(int a, int b) {
        List<Integer> l = new ArrayList<>();
        for (int i = a; i < b; i++) l.add(i);
        return l;
    }
}
