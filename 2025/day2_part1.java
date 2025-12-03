import java.io.File;
import java.util.Scanner;

public class day2_part1 {
    public static void main(String[] args) throws Exception {
        File file = new File("input2.txt");
        Scanner scanner = new Scanner(file);
        long ans = 0;
        while(scanner.hasNext()){
            String[] line = scanner.nextLine().split(",");
            for(int i = 0; i < line.length; i++){
                long start = Long.parseLong(line[i].split("-")[0]);
                long end = Long.parseLong(line[i].split("-")[1]);
                for(long num = start; num <= end; num++){
                    String numStr = Long.toString(num);
                    if(numStr.length() % 2 != 0) continue;
                    else{
                        if(isInvalid(numStr)) ans += num;
                    }
                }
            }
        }
        System.out.println(ans);
        scanner.close();
    }
    private static boolean isInvalid(String num){
        int half = num.length() / 2;
        String d1 = num.substring(0, half);
        String d2 = num.substring(half, num.length());
        if(d1.equals(d2)) return true;
        else return false;
    }
}
