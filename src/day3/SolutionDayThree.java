package day3;

import utils.Utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDayThree {
    public static void main(String[] args) {
        try (Stream<String> a = Utilities.readInFile("src/day3/input");) {
            int[][] q = a.map(String::chars).map(IntStream::toArray).toList().toArray(new int[0][]);
            int t = Integer.parseInt(IntStream.range(0, q[0].length)
                    .mapToObj(r ->
                            Arrays.stream(q).map(x -> x[r]).toArray(Integer[]::new)).flatMap(x -> {
                        return Stream.of(Stream.of(Arrays.stream(x).filter(y -> y == '1').count(),
                                Arrays.stream(x).filter(y -> y == '0').count()));
                    }).map(y -> y.reduce((p, v) -> Long.valueOf(v > p ? '1' : '0')).orElseGet(() -> Long.valueOf(' ')))
                    .map(x -> String.valueOf((char) x.longValue())).collect(Collectors.joining()), 2);
            int z = Integer.numberOfLeadingZeros(t);
            System.out.println(t * ((~t << z) >>> z));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Part Two
        try (Stream<String> a = Utilities.readInFile("src/day3/input");) {
            final int[][] v = a.map(String::chars).map(IntStream::toArray).toList().toArray(new int[0][]);
            final int[][][] q = {v, v};
            IntStream.range(0, q[0][0].length).forEach(y -> {
                q[0] = q[0].length > 1 ? Arrays.stream(q[0])
                        .filter(x -> String.valueOf((char) x[y]).equals((String) getMostCommon(q[0])[y]))
                        .toList().toArray(new int[0][]) : q[0];
                q[1] = q[1].length > 1 ? Arrays.stream(q[1])
                        .filter(x -> String.valueOf((char) x[y]).equals((String) getLeastCommon(q[1])[y]))
                        .toList().toArray(new int[0][]) : q[1];
            });
            System.out.println(
                    Integer.parseInt(Arrays.stream(q[0][0]).mapToObj(x -> String.valueOf((char) x))
                            .collect(Collectors.joining()), 2) *
                            Integer.parseInt(Arrays.stream(q[1][0]).mapToObj(x -> String.valueOf((char) x))
                                    .collect(Collectors.joining()), 2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] getMostCommon(int[][] q) {
        return IntStream.range(0, q[0].length)
                .mapToObj(r ->
                        Arrays.stream(q).map(x -> x[r]).toArray(Integer[]::new)).flatMap(x -> {
                    return Stream.of(Stream.of(Arrays.stream(x).filter(y -> y == '1').count(),
                            Arrays.stream(x).filter(y -> y == '0').count()));
                }).map(y -> y.reduce((p, v) -> Long.valueOf((v <= p ? '1' : '0'))).orElseGet(() -> Long.valueOf(' ')))
                .map(x -> String.valueOf((char) x.longValue())).toArray();
    }

    public static Object[] getLeastCommon(int[][] q) {
        return IntStream.range(0, q[0].length)
                .mapToObj(r ->
                        Arrays.stream(q).map(x -> x[r]).toArray(Integer[]::new)).flatMap(x -> {
                    return Stream.of(Stream.of(Arrays.stream(x).filter(y -> y == '1').count(),
                            Arrays.stream(x).filter(y -> y == '0').count()));
                }).map(y -> y.reduce((p, v) -> Long.valueOf(v <= p ? '0' : '1')).orElseGet(() -> Long.valueOf(' ')))
                .map(x -> String.valueOf((char) x.longValue())).toArray();
    }



}
