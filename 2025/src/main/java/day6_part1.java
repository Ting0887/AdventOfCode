import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
public class day6_part1 {
    public static void main(String[] args) throws Exception{
        File file = new File("input6.txt");
        Scanner sc = new Scanner(file);
        List<List<Long>> totalNum = new ArrayList<>();
        List<String> operatorList = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            // divide into number and operator
            if (line.contains("*") || line.contains("+")){
                String[] operators = line.trim().split(" ");
                for(String operator : operators){
                    if(operator.equals("+") || operator.equals("*"))
                        operatorList.add(operator);
                }
            } else {
                List<Long> numList = new ArrayList<>();
                String[] nums = line.trim().split(" ");
                System.out.println(Arrays.toString(nums));
                for(String num : nums){
                    if(!num.equals("")){
                        long numInt = Long.parseLong(num);
                        numList.add(numInt);
                    }
                }
                totalNum.add(numList);
            }
        }
        sc.close();
        long ans = 0;
        for(int i = 0; i < operatorList.size(); i++){
            if(operatorList.get(i).equals("+")){
                long initAdd = totalNum.get(0).get(i);
                for(int j = 1; j < totalNum.size(); j++){
                    initAdd += totalNum.get(j).get(i);
                }
                ans += initAdd;
            } else {
                long initMultiply = totalNum.get(0).get(i);
                for(int j = 1; j < totalNum.size(); j++){
                    initMultiply *= totalNum.get(j).get(i);
                }
                ans += initMultiply;
            }
        }
        System.out.println(ans);
    }
}
