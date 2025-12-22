import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day2 {
    public static void main(String[] args) throws Exception {
        File file = new File("src/main/java/testInput/input2.txt");
        Scanner scanner = new Scanner(file);
        List<int[]> nums = new ArrayList<>();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int[] row = new int[parts.length];
            for(int i = 0; i < parts.length; i++){
                row[i] = Integer.parseInt(parts[i]);
            }
            nums.add(row);
        }
        scanner.close();
        part1(nums);
        part2(nums);
    }
    private static void part1(List<int[]> numsList) {
        int ans = 0;
        for(int[] nums : numsList){
            // check array is increasing or decreasing
            if(isIncreasing(nums) || isDecreasing(nums)){
                ans++;
            }
        }
        System.out.println("part1 = " + ans);
    }
    private static void part2(List<int[]> numsList) {
        int ans = 0;
        // if only remove one number to make the array increasing or decreasing
        // or no remove number to make the array increasing or decreasing
        for (int[] nums : numsList) {
            if (isSafe(nums)) {
                ans++;
                continue;
            }

            boolean ok = false;

            for (int i = 0; i < nums.length; i++) {

                if (isSafeRemovingIndex(nums, i)) {
                    ok = true;
                    break;
                }
            }

            if (ok) ans++;
        }
        System.out.println("part2 = " + ans);
    }
    
    private static boolean isSafe(int[] nums) {
        return isIncreasing(nums) || isDecreasing(nums);
    }

    private static boolean isIncreasing(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (!(nums[i] > nums[i - 1])) return false;
            if (nums[i] - nums[i - 1] > 3) return false;
            if (nums[i] - nums[i - 1] < 1) return false;
        }
        return true;
    }
    private static boolean isDecreasing(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (!(nums[i] < nums[i - 1])) return false;
            if (nums[i - 1] - nums[i] > 3) return false;
            if (nums[i - 1] - nums[i] < 1) return false;
        }
        return true;
    }
    private static boolean isSafeRemovingIndex(int[] nums, int k) {
        int[] tmp = new int[nums.length - 1];

        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == k) continue;
            tmp[idx++] = nums[i];
        }

        return isSafe(tmp);
    }
}
