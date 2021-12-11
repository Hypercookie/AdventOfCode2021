package day4;

import utils.Utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDayFour {

    public static void main(String[] args) throws IOException {
        try (Stream<String> a = Utilities.readInFile("src/day4/input");) {
            String[] lines = a.toArray(String[]::new);

            Integer[] numbers =
                    Arrays.stream(lines[0].split(",")).map(x -> Integer.parseInt(x)).toArray(Integer[]::new);
            final List<Integer[][]>[] map = new List[] {Arrays.stream(
                            Arrays.stream(Arrays.copyOfRange(lines, 2, lines.length)).collect(Collectors.joining("\n"))
                                    .split("\n\n")).map(x -> Stream.of(x.split("\n"))
                            .map(p -> Stream.of(p.replaceAll("  ", " ").strip().split(" ")).map(u -> Integer.parseInt(u))
                                    .toList()).toList())
                    .map(x -> toArray(x)).toList()};

            //System.out.println(getWinner(numbers, map));
            System.out.println(getLoser(numbers,map));
        }
        //.filter(x -> !"".equals(x))
        //       .map(x -> Stream.of(x.split("\n")).filter(p -> !"".equals(p)).map(p -> Stream.of(p.split(" ")).map(y -> Integer.parseInt(y))))
        //     .map(u -> toArray(u)).toList().toArray(new Integer[0][][]);
//        int[][] x = {{0, 1, 1}, {1, 1, 5}, {1, 1, 7}};
//        System.out.println(wins(x));
//        x = mark(x, 1);
//        System.out.println(wins(x));
    }

    public static int getLoser(Integer[] numbers, List<Integer[][]>[] map) {
        final Integer[][][] f = {null};
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(numbers).forEach(x -> {
            if(map[0].size() == 1){
                map[0].forEach( r -> {
                    f[0] = r;
                });
                i.set(x);
            }
            map[0] = map[0].stream().map(y -> mark(y, x)).filter(t -> !wins(t)).toList();
            map[0].forEach(y -> {
                System.out.println(Arrays.deepToString(y));

            });
            System.out.println("\n\n");


        });
        return Arrays.stream(f[0])
                .map(x -> Arrays.stream(x).map(p->p==i.get()?-1:p).filter(p -> p != -1).reduce((a, b) -> a + b).orElseGet(() -> 0))
                .reduce((a, b) -> a + b).orElseGet(() -> 0) * i.get();


    }

    public static int getWinner(Integer[] numbers, List<Integer[][]>[] map) {
        final Integer[][][] f = {null};
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(numbers).forEach(x -> {
            map[0] = map[0].stream().map(y -> mark(y, x)).toList();
            map[0].stream().filter(y -> wins(y)).toList().forEach(r -> {
                if (f[0] == null) {
                    f[0] = r;
                    i.set(x);
                    return;
                }
            });
        });
        return Arrays.stream(f[0])
                .map(x -> Arrays.stream(x).filter(p -> p != -1).reduce((a, b) -> a + b).orElseGet(() -> 0))
                .reduce((a, b) -> a + b).orElseGet(() -> 0) * i.get();
    }

    public static Integer[][] toArray(List<List<Integer>> t) {
        return t.stream().map(x -> x.toArray(Integer[]::new)).toList().toArray(new Integer[0][]);
    }

    public static Integer[][] mark(Integer[][] a, Integer mark) {

        return Arrays.stream(a).map(x -> Arrays.stream(x).map(y -> y == mark ? -1 : y))
                .map(x -> x.toArray(Integer[]::new)).toList()
                .toArray(new Integer[0][]);
    }

    public static boolean wins(Integer[][] a) {
        return Arrays.stream(a).anyMatch(x -> Arrays.stream(x).allMatch(y -> y == -1)) ||
                IntStream.range(0, a[0].length).anyMatch(x -> Arrays.stream(a).map(t -> t[x]).allMatch(y -> y == -1));
    }

}
