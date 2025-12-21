import java.io.File;
import java.util.Scanner;

public class day1_part1 {
    public static void main(String[] args) throws Exception {

        int start = 50;
        int countZero = 0;

        File file = new File("input1.txt");
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()) {
            String rotateLR = scanner.next();

            char dir = rotateLR.charAt(0);
            int val = Integer.parseInt(rotateLR.substring(1));

            if(dir == 'L'){
                start -= val;
            } else if(dir == 'R'){
                start += val;
            }

            while(start < 0) start += 100;
            while(start >= 100) start -= 100;

            if(start == 0){
                countZero++;
            }
        }

        scanner.close();
        System.out.println(countZero);
    }
}
