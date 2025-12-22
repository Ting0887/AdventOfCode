import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.*;

public class day3 {

    private static final Pattern PART1 = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    private static final Pattern PART2 = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don'?t\\(\\)");

    public static void main(String[] args) throws Exception {

        Path p = Path.of("src/main/java/testInput/input3.txt");
        String text = Files.readString(p);

        part1(text);
        part2(text);
    }

    private static void part1(String text) {

        Matcher m = PART1.matcher(text);

        long sum = 0;

        while (m.find()) {
            sum += Long.parseLong(m.group(1)) *
                   Long.parseLong(m.group(2));
        }

        System.out.println("part1 = " + sum);
    }

    private static void part2(String text) {

        Matcher m = PART2.matcher(text);

        boolean enabled = true;
        long sum = 0;

        while (m.find()) {

            String g1 = m.group(1);
            String g2 = m.group(2);

            // mul(a,b)
            if (g1 != null && g2 != null) {
                if (enabled) {
                    sum += Long.parseLong(g1) *
                           Long.parseLong(g2);
                }
                continue;
            }

            // do()
            if (m.group().equals("do()")) {
                enabled = true;
                continue;
            }

            // don't()
            if (m.group().startsWith("don")) {
                enabled = false;
            }
        }

        System.out.println("part2 = " + sum);
    }
}
