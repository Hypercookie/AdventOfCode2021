package day6;

import utils.Utilities;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDaySix {
    public static void main(String[] args) {
        try (Stream<String> a = Utilities.readInFile("src/day6/input")) {
            int simtime = 256;//until 10mil steps this works ... ok
            final Map<Integer, BigInteger>[] map = new HashMap[] {new HashMap<>()};
            IntStream.range(0, 9).forEach(x -> map[0].computeIfAbsent(x, integer -> BigInteger.ZERO));
            Arrays.stream(a.map(s -> s.split(",")).findFirst().orElseGet(() -> new String[0]))
                    .forEach(x -> map[0].put(Integer.parseInt(x), map[0].get(Integer.parseInt(x)).add(BigInteger.ONE)));
            IntStream.range(0, simtime).forEach(__ -> {
                final Map<Integer, BigInteger> tmpMap = new HashMap<>();
                map[0].keySet().stream().peek(x -> tmpMap.computeIfAbsent(x, integer -> BigInteger.ZERO))
                        .filter(x -> x == 0).forEach(k -> {
                            tmpMap.put(6, map[0].get(k));
                            tmpMap.put(8, map[0].get(k));
                        });
                map[0].keySet().stream().filter(x -> x > 0).forEach(k -> tmpMap.put(k - 1,
                        map[0].get(k)
                                .add(tmpMap.computeIfAbsent(k - 1, (bigInteger) -> BigInteger.ZERO))));
                map[0] = tmpMap;

            });
            final BigInteger[] rs = {BigInteger.ZERO};
            map[0].keySet().stream().map(x -> map[0].get(x)).forEach(x -> rs[0] = rs[0].add(x));
            System.out.println(rs[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
