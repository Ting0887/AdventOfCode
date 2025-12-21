import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day6_part2 {
    public static void main(String[] args) throws Exception {
        List<String> rawLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input6.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                rawLines.add(line);
            }
        }

        if (rawLines.isEmpty()) {
            System.out.println("0");
            return;
        }

        int maxw = 0;
        for (String s : rawLines) if (s.length() > maxw) maxw = s.length();

        int rowsN = rawLines.size();
        String[] rows = new String[rowsN];
        for (int i = 0; i < rowsN; i++) rows[i] = padRight(rawLines.get(i), maxw);

        int opRow = -1;
        for (int r = rowsN - 1; r >= 0; r--) {
            for (int c = 0; c < maxw; c++) {
                char ch = rows[r].charAt(c);
                if (ch == '+' || ch == '*') {
                    opRow = r;
                    break;
                }
            }
            if (opRow != -1) break;
        }

        if (opRow == -1) throw new RuntimeException("No operator row found!");

        List<int[]> groups = new ArrayList<>();
        int c = 0;
        while (c < maxw) {
            if (colIsAllSpace(rows, c)) {
                c++;
                continue;
            }
            int start = c;
            while (c < maxw && !colIsAllSpace(rows, c)) c++;
            int end = c - 1;
            groups.add(new int[]{start, end});
        }

        long grandTotal = 0;

        for (int[] g : groups) {
            int start = g[0], end = g[1];

            char op = '?';
            int opCol = -1;
            for (int col = start; col <= end; col++) {
                char ch = rows[opRow].charAt(col);
                if (ch == '+' || ch == '*') {
                    op = ch;
                    opCol = col;
                    break;
                }
            }
            if (opCol == -1) throw new RuntimeException("No operator in group!");

            String[] block = new String[opRow]; 
            for (int r = 0; r < opRow; r++) block[r] = rows[r].substring(start, end + 1);

            List<Long> nums = new ArrayList<>();
            int width = end - start + 1;
            for (int colIdx = width - 1; colIdx >= 0; colIdx--) {
                StringBuilder sb = new StringBuilder();
                for (int r = 0; r < block.length; r++) {
                    char ch = block[r].charAt(colIdx);
                    if (Character.isDigit(ch)) sb.append(ch);
                }
                if (sb.length() > 0) nums.add(Long.parseLong(sb.toString()));
            }

            if (nums.isEmpty()) continue;

            long result = (op == '+') ? 0 : 1;
            for (long v : nums) {
                if (op == '+') result += v;
                else result *= v;
            }

            grandTotal += result;
        }

        System.out.println(grandTotal);
    }

    private static boolean colIsAllSpace(String[] rows, int col) {
        for (String s : rows) if (s.charAt(col) != ' ') return false;
        return true;
    }

    private static String padRight(String s, int width) {
        if (s.length() >= width) return s;
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < width) sb.append(' ');
        return sb.toString();
    }
}
