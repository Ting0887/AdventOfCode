import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class day9_part1 {
    public static void main(String[] args) throws Exception {
        File file = new File("testInput/input9.txt");
        Scanner sc = new Scanner(file);
        List<long[]> points = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            String[] part = line.split(",");
            long x = Long.parseLong(part[0]);
            long y = Long.parseLong(part[1]);
            points.add(new long[]{x, y});
        }
        long maxArea = 0;
        for(int i = 0; i < points.size(); i++){
            for(int j = i + 1; j < points.size(); j++){
                long x1 = points.get(i)[0];
                long y1 = points.get(i)[1];
                long x2 = points.get(j)[0];
                long y2 = points.get(j)[1];
                long area = Math.abs(x1 - x2 + 1) * Math.abs(y1 - y2 + 1);
                maxArea = Math.max(maxArea, area);
            }
        }
        System.out.println(maxArea);
    }
    
}
