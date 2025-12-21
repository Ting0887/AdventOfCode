import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day5_part1 {
    public static void main(String[] args) throws Exception{
        File file = new File("input5.txt");
        Scanner sc = new Scanner(file);
        List<String> rangeList = new ArrayList<>();
        List<BigInteger> InputNums = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            if(line.contains("-")){
                rangeList.add(line);
            } else if(!line.contains("-") && !line.equals("")){
                InputNums.add(new BigInteger(line));
            }
        }
        sc.close();
        int ans = 0;
        for(BigInteger number : InputNums){
            if(checkIDValid(rangeList, number))
                ans += 1;
        }
        System.out.println(ans);
    }
    private static boolean checkIDValid(List<String> rangeList, BigInteger number){
        for(String range : rangeList){
            BigInteger a = new BigInteger(range.split("-")[0]);
            BigInteger b = new BigInteger(range.split("-")[1]);
            if(number.compareTo(a) >= 0 && number.compareTo(b) <= 0) return true;
        }
        return false;
    }
}
