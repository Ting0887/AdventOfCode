import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;;

public class day10_part2 {

    public static long solveOneMachine(
        int M,
        int N,
        int[][] K,
        int[] J
    ) {

        Loader.loadNativeLibraries();
        MPSolver solver =
            MPSolver.createSolver("SCIP");

        MPVariable[] x = new MPVariable[N];

        for (int j = 0; j < N; j++) {
            x[j] = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "x" + j);
        }

        // M constraints
        for (int i = 0; i < M; i++) {
            MPConstraint constraint = solver.makeConstraint(J[i], J[i]);
            for (int j = 0; j < N; j++) {
                if (K[j][i] == 1) {
                    constraint.setCoefficient(x[j], 1);
                }
            }
        }

        // minimize sum x[j]
        MPObjective objective = solver.objective();
        for (int j = 0; j < N; j++) {
            objective.setCoefficient(x[j], 1);
        }
        objective.setMinimization();

        MPSolver.ResultStatus result = solver.solve();

        if (result == MPSolver.ResultStatus.OPTIMAL) {
            long ans = 0;
            for (int j = 0; j < N; j++) {
                ans += (long) x[j].solutionValue();
            }
            return ans;
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {

        File file = new File("src/main/java/testInput/input10.txt");
        Scanner sc = new Scanner(file);
        List<Integer> counters = new ArrayList<>();
        List<Integer> buttons = new ArrayList<>();
        List<int[][]> K_list = new ArrayList<>();
        List<int[]> J_list = new ArrayList<>();
        while (sc.hasNext()) {
            String line = sc.nextLine().trim();
            String[] parts = line.split(" ");

            /** counters = characters inside [...] */
            int M = parts[0].substring(1, parts[0].length() - 1).length();
            counters.add(M);

            /** number of buttons */
            int N = parts.length - 2;
            buttons.add(N);

            /** allocate full K correctly */
            int[][] K = new int[N][M];

            /** parse each button */
            for (int j = 0; j < N; j++) {

                String btn = parts[j + 1]; // "(3,1)"
                btn = btn.substring(1, btn.length() - 1); // "3,1"

                if (!btn.isEmpty()) {
                    String[] indices = btn.split(",");
                    for (String s : indices) {
                        if (s.isEmpty()) continue;
                        int counterIndex = Integer.parseInt(s);
                        K[j][counterIndex] = 1;
                    }
                }
            }

            K_list.add(K);

            /** parse J */
            String last = parts[parts.length - 1]; // "{3,5,4,7}"
            last = last.substring(1, last.length() - 1); // "3,5,4,7"
            String[] jValues = last.split(",");

            int[] J = new int[jValues.length];
            for (int i = 0; i < jValues.length; i++) {
                J[i] = Integer.parseInt(jValues[i]);
            }
            J_list.add(J);
        }
        sc.close();
        /* 
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}

        int M = 4;  // counters
        int N = 6;  // 按鈕數量

        int[][] K = {
                {0,0,0,1}, // button0: (3)
                {0,1,0,1}, // button1: (1,3)
                {0,0,1,1}, // button2: (2,3)
                {1,0,0,0}, // button3: (0,2)
                {1,1,0,0}, // button4: (0,1)
                {0,1,1,0}  // button5: (2)
        };

        int[] J = {3,5,4,7};
        */
        long ans = 0;
        for(int idx = 0; idx < counters.size(); idx++){
            int M = counters.get(idx);
            int N = buttons.get(idx);
            int[][] K = K_list.get(idx);
            int[] J = J_list.get(idx);

            long presses = solveOneMachine(M, N, K, J);
            ans += presses;
        }
        System.out.println("Total button presses for all machines: " + ans);
    }
}