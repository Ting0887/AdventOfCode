import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class day11_part1 {
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
        ans += dfs("you", map);
        System.out.println("Ans = " + ans);
    }
    
    static Map<String, Long> memo = new HashMap<>();

    private static long dfs(String sit, Map<String, List<String>> map) {
        long count = 0;
        if(map.get(sit).get(0).equals("out")){
            return 1;
        } else {
            for (String next : map.get(sit)) {
                count += dfs(next, map);
            }
        }
        return count;
    }
}

