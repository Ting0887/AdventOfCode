import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day4_part2 {
    public static void main(String[] args) throws Exception{
        File file = new File("input4.txt");
        Scanner sc = new Scanner(file);
        List<String[]> arrLst = new ArrayList<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            String[] row = new String[line.length()];
            for(int i = 0; i < line.length(); i++){
                row[i] = line.substring(i, i + 1);
            }
            arrLst.add(row);
       }
       sc.close();
       int ans = 0;
       String[][] arr = ListToArr(arrLst);

       while(true){
            int take = 0;
            for(int i = 0; i < arr.length; i++){
                for(int j = 0; j < arr[0].length; j++){
                    int fewerthan4 = 0;
                    // check top, down, left, right, corners totally 8 directions
                    if(arr[i][j].equals("@")){
                        if(i > 0 && arr[i - 1][j].equals("@")) fewerthan4++;
                        if(i + 1 < arr.length && arr[i + 1][j].equals("@")) fewerthan4++;
                        if(j > 0 && arr[i][j - 1].equals("@")) fewerthan4++;
                        if(j + 1 < arr[0].length && arr[i][j + 1].equals("@")) fewerthan4++;
                        if(i > 0 && j > 0 && arr[i - 1][j - 1].equals("@")) fewerthan4++;
                        if(i > 0 && j + 1 < arr[0].length && arr[i - 1][j + 1].equals("@")) fewerthan4++;
                        if(i + 1 < arr.length && j > 0 && arr[i +  1][j - 1].equals("@")) fewerthan4++;
                        if(i + 1 < arr.length && j + 1 < arr[0].length && arr[i + 1][j + 1].equals("@")) fewerthan4++;
                        if(fewerthan4 < 4){
                            take++;
                            arr[i][j] = "x";
                        } 
                    } else {
                        continue;
                    }
                }
            }
            if(take == 0) break;
            ans += take;
       }
       System.out.println(ans);
    }
    private static String[][] ListToArr(List<String[]> lst){
        String[][] arr = new String[lst.size()][lst.get(0).length];
        for(int i = 0; i < lst.size(); i++){
            arr[i] = lst.get(i);
        }
        return arr;
    }
}
