import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class day5_part2{
    public static void main(String[] args) throws Exception{
        File file = new File("input5.txt");
        Scanner sc = new Scanner(file);
        List<BigInteger[]> rangeList = new ArrayList<>();
        List<BigInteger> InputNums = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            if(line.contains("-")){
                BigInteger[] nums = new BigInteger[2];
                BigInteger a = new BigInteger(line.split("-")[0]);
                BigInteger b = new BigInteger(line.split("-")[1]);
                nums[0] = a; 
                nums[1] = b;
                rangeList.add(nums);
            } else if(!line.contains("-") && !line.equals("")){
                InputNums.add(new BigInteger(line));
            }
        }
        sc.close();
        BigInteger[][] intervals = typeTransform(rangeList);
        sortArr(intervals);

        List<BigInteger[]> merged = new ArrayList<>();

        // merge intervals
        for (BigInteger[] cur : intervals) {
            if (merged.isEmpty() ||
                merged.get(merged.size() - 1)[1].compareTo(cur[0]) < 0) {
                merged.add(new BigInteger[]{cur[0], cur[1]});
            } 
            else {
                BigInteger[] last = merged.get(merged.size() - 1);
                last[1] = last[1].max(cur[1]);
            }
        }
        // record range is used
        HashMap<BigInteger[], Boolean> record = new HashMap<>();
        for(BigInteger[] interval : merged){
            record.put(interval, false);
        }

        BigInteger count = BigInteger.ZERO;
            for(BigInteger[] interval : merged){
                BigInteger s = interval[0];
                BigInteger e = interval[1];
                    count = count.add(e.subtract(s).add(BigInteger.ONE));
            }
        System.out.println(count);
    }
    private static BigInteger[][] typeTransform(List<BigInteger[]> intervals){
        BigInteger[][] res = new BigInteger[intervals.size()][2];
        for(int i = 0; i < intervals.size(); i++){
            res[i][0] = intervals.get(i)[0];
            res[i][1] = intervals.get(i)[1];
        }
        return res;
    }
    private static void sortArr(BigInteger[][] intervals){
        Arrays.sort(intervals, (a, b) -> a[0].compareTo(b[0]));
    }
}