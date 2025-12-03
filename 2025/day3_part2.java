import java.io.File;
import java.util.Scanner;

public class day3_part2 {
    public static void main(String[] args) throws Exception {
        /* find maxNumber and length=12 
        ex. 811111111111119 -> 811111111119
            234234234234278 -> 434234234278
        */
       File file = new File("input3.txt");
       Scanner scanner = new Scanner(file);
       int targetLength = 12;
       long ans = 0;
       while(scanner.hasNext()){
           String numString = scanner.nextLine();
           String result = findMaxNumber(numString, targetLength);
           ans += Long.parseLong(result);
       }
       System.out.println(ans);
       scanner.close();
    }
    private static String findMaxNumber(String numString, int targetLength){
        StringBuilder stack = new StringBuilder();
        for(int i = 0; i < numString.length(); i++){
            char currentChar = numString.charAt(i);
            while(stack.length() > 0 && currentChar > stack.charAt(stack.length() - 1) 
                  && (numString.length() - i + stack.length() > targetLength)){
                stack.deleteCharAt(stack.length() - 1);
            }
            if(stack.length() < targetLength){
                stack.append(currentChar);
            }
        }
        return stack.toString();
    }
}
