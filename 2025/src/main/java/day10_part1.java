import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class day10_part1 {
    public static void main(String[] args) throws Exception {
        File file = new File("testInput/input10.txt");
        Scanner sc = new Scanner(file);
        List<Integer> totalLightsList = new ArrayList<>();
        List<long[]> buttonsList = new ArrayList<>();
        List<Long> targetsList = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] part = line.split(" ");

            /* ===== target ===== */
            // [.##.#..##.]
            String diagram = part[0];
            int totalLights = diagram.length() - 2;
            totalLightsList.add(totalLights);

            long target = 0;
            for (int i = 1; i <= totalLights; i++) {
                if (diagram.charAt(i) == '#') {
                    target |= (1L << (i - 1));
                }
            }
            targetsList.add(target);

            /* ===== buttons ===== */
            List<Long> btns = new ArrayList<>();

            for (int i = 1; i < part.length; i++) {
                if (!part[i].startsWith("(")) break;

                String btnStr = part[i].substring(1, part[i].length() - 1);
                String[] nums = btnStr.split(",");

                long mask = 0;
                for (String s : nums) {
                    int pos = Integer.parseInt(s.trim());
                    mask |= (1L << pos);
                }
                btns.add(mask);
            }

            long[] buttons = new long[btns.size()];
            for (int i = 0; i < btns.size(); i++) {
                buttons[i] = btns.get(i);
            }
            buttonsList.add(buttons);
        }
        sc.close();

        int ans = 0;
        for (int i = 0; i < totalLightsList.size(); i++) {
            int totalLights = totalLightsList.get(i);
            long[] buttons = buttonsList.get(i);
            long target = targetsList.get(i);

            int result = solve(totalLights, buttons, target);
            ans += result;
        }

        System.out.println("Total: " + ans);
    }
    static int solve(int lights, long[] buttons, long target) {
        int m = buttons.length;
        long[] a = new long[lights];
        
        for (int i = 0; i < lights; i++) {
            long row = 0;
            for (int j = 0; j < m; j++) {
                if (((buttons[j] >> i) & 1) == 1) {
                    row |= (1L << j);
                }
            }
            if (((target >> i) & 1) == 1) {
                row |= (1L << m);
            }
            a[i] = row;
        }

        int rank = 0;
        int[] where = new int[m];
        Arrays.fill(where, -1);

        // XOR Gaussian Elimination
        for (int col = 0; col < m && rank < lights; col++) {
            int sel = -1;
            for (int i = rank; i < lights; i++) {
                if (((a[i] >> col) & 1) == 1) {
                    sel = i;
                    break;
                }
            }
            if (sel == -1) continue;

            long tmp = a[rank];
            a[rank] = a[sel];
            a[sel] = tmp;

            where[col] = rank;

            for (int i = 0; i < lights; i++) {
                if (i != rank && ((a[i] >> col) & 1) == 1) {
                    a[i] ^= a[rank];
                }
            }
            rank++;
        }

        // 檢查無解
        for (int i = rank; i < lights; i++) {
            if ((a[i] & ((1L << m) - 1)) == 0 &&
                ((a[i] >> m) & 1) == 1) {
                return -1;
            }
        }

        // 自由變數
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (where[i] == -1) free.add(i);
        }

        int ans = Integer.MAX_VALUE;
        int f = free.size();

        // 枚舉自由變數
        for (int mask = 0; mask < (1 << f); mask++) {
            long x = 0;
            for (int i = 0; i < f; i++) {
                if (((mask >> i) & 1) == 1) {
                    x |= (1L << free.get(i));
                }
            }

            // 回推主變數
            for (int i = 0; i < m; i++) {
                if (where[i] != -1) {
                    int row = where[i];
                    long rhs = (a[row] >> m) & 1;
                    long sum = Long.bitCount(a[row] & x) & 1;
                    if ((rhs ^ sum) == 1) {
                        x |= (1L << i);
                    }
                }
            }

            ans = Math.min(ans, Long.bitCount(x));
        }

        return ans;
    }
}
