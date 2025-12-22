import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class day1 {
    public static void main(String[] args) throws Exception {
        File file = new File("src/main/java/testInput/input1.txt");
        Scanner scanner = new Scanner(file);
        List<Integer> leftNums = new ArrayList<>();
        List<Integer> rightNums = new ArrayList<>();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] parts = line.split("   ");
            leftNums.add(Integer.parseInt(parts[0]));
            rightNums.add(Integer.parseInt(parts[1]));
        }
        scanner.close();
        part1(leftNums, rightNums);
        part2(leftNums, rightNums);
    }
    private static void part1(List<Integer> leftNums, List<Integer> rightNums){
        long ans = 0;
        // sort leftNums and rightNums
        leftNums.sort((a, b) -> b - a);
        rightNums.sort((a, b) -> b - a);
        for(int i = 0; i < leftNums.size(); i++){
            long diff = leftNums.get(i) - rightNums.get(i);
            ans += Math.abs(diff);
        }
        System.out.println("part1 = " + ans);
    }
    private static void part2(List<Integer> leftNums, List<Integer> rightNums){
        long ans = 0;
        for(int i = 0; i < leftNums.size(); i++){
            long freq = Collections.frequency(rightNums, leftNums.get(i));
            long values = leftNums.get(i) * freq;
            ans += values;
        }
        System.out.println("part2 = " + ans);
    }
}
