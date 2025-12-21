import java.io.File;
import java.util.Scanner;

public class day3_part1 {
    public static void main(String[] args) throws Exception {
        File file = new File("input3.txt");
        int ans = 0;
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String num = scanner.nextLine();
            ans += findMax(num);
        }
        System.out.println(ans);
        scanner.close();
    }
    private static int findMax(String num){
        int maxNum = 0;
        for(int i = 0; i < num.length(); i++){
            for(int j = i + 1; j < num.length(); j++){
                String d1 = num.substring(i, i+1);
                String d2 = num.substring(j, j+1);
                int d = Integer.parseInt(d1 + d2);
                if(maxNum < d){
                    maxNum = d;
                }
            }
        }
        return maxNum;
    }
}
