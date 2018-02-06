import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class twenty {

    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Integer> points = new ArrayList<>();

        for (int i = 0; i < 1000; i++){
            String a = in.readLine().split("<")[3];
            a = a.substring(0, a.length() - 1);
            points.add(min(a));
        }

        int max = 1000;
        int index = 0;

        for (int i = 0; i < points.size(); i++) {
            if (max > points.get(i)) {
                max = points.get(i);
                index = i;
            } else if (max == points.get(i)) {
                System.out.println(max);
                System.out.println(i);
            }
        }
        System.out.println(max);
        System.out.println(index);

    }

    public static int min(String a) {
        return Arrays.stream(a.split(",")).mapToInt(Integer::parseInt).map(f -> Math.abs(f)).max().getAsInt();
    }
}
