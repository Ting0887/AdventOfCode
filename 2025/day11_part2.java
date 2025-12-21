import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class day11_part2 {
    public static void main(String[] args) throws Exception {
        File file = new File("testInput/input11.txt");
        Scanner sc = new Scanner(file);
        Map<String, List<String>> map = new HashMap<>();
        while(sc.hasNext()){
            List<String> positions = new ArrayList<>();
            String line = sc.nextLine();
            String[] part = line.split(":");

            String[] candidates = part[part.length - 1].split(" ");
            for(String candidate : candidates){
                if(!candidate.isEmpty()) positions.add(candidate);
            }
            map.put(part[0], positions);
        }
        sc.close();
        long ans = 0;
        // start from you
        boolean seendac = false;
        boolean seenfft = false;
        ans += dfs("svr", map, seendac, seenfft);
        System.out.println("Ans = " + ans);
    }
    
    static Map<String, Long> memo = new HashMap<>();

    private static long dfs(String sit, Map<String, List<String>> map, boolean seendac, boolean seenfft) {
        String key = sit + "|" + seendac + "|" + seenfft;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        if (map.get(sit).size() == 1 && map.get(sit).get(0).equals("out")) {
            long res = (seendac && seenfft) ? 1 : 0;
            memo.put(key, res);
            return res;
        }
        long count = 0;
        for (String next : map.get(sit)) {
            boolean nextSeenDac = seendac || next.equals("dac");
            boolean nextSeenFft = seenfft || next.equals("fft");
            count += dfs(next, map, nextSeenDac, nextSeenFft);
        }
        memo.put(key, count);
        return count;
    }
}

