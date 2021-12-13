package day7;

import utils.Utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDaySeven {
    public static void main(String[] args) {
        try (Stream<String> a = Utilities.readInFile("src/day7/input")) {

            Integer[] ar = Arrays.stream(a.map(x -> x.split(",")).findFirst().orElseGet(() -> new String[0]))
                    .map(Integer::parseInt).sorted().toList().toArray(Integer[]::new);
            System.out.println(
                    Arrays.stream(ar).map(x -> Math.abs(ar[(int) Math.ceil(ar.length / 2.0)] - x)).reduce(Integer::sum)
                            .orElse(0));
            int targetC =
                    Math.min((int) Math.ceil(Arrays.stream(ar).reduce(Integer::sum).orElse(0) / (double) ar.length),
                            (int) Math.floor(Arrays.stream(ar).reduce(Integer::sum).orElse(0) / (double) ar.length));
            System.out.println(Arrays.stream(ar).map(x -> (Math.abs(targetC - x) * (Math.abs(targetC - x) + 1)) / 2)
                    .reduce(Integer::sum).orElse(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
