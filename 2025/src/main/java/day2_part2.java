import java.io.File;
import java.util.Scanner;

public class day2_part2 {
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
                    if(checkIsInvalid(numStr) && numStr.length() > 1) ans += num;
                }
            }
        }
        System.out.println(ans);
        scanner.close();
    }
    private static boolean checkIsInvalid(String num){
      int start = 1;
      int end = num.length() / 2;
      boolean isInvalid = true;
      while(start <= end){
          String baseNum = num.substring(0, start);
          if(num.length() % baseNum.length() != 0){
             start++;
             continue;
          }
            for(int i = start; i + start <= num.length(); i+=start){
                String n1 = num.substring(i, i + start);
                if(!baseNum.equals(n1)){
                    isInvalid = false;
                    break;
                } else isInvalid = true;
            }
            if(isInvalid) break;
            start += 1;
      }
      return isInvalid;
   }
}
