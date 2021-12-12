package day5;

import utils.Utilities;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SolutionDayFive {
    public static void main(String[] args) {
        try (Stream<String> a = Utilities.readInFile("src/day5/input")) {
            List<Object> kTaskOne = a.map(x -> new Line(
                                    new Point(
                                            Integer.parseInt(x.split(" -> ")[0].split(",")[0]),
                                            Integer.parseInt(x.split(" -> ")[0].split(",")[1]))
                                    ,
                                    new Point(
                                            Integer.parseInt(x.split(" -> ")[1].split(",")[0]),
                                            Integer.parseInt(x.split(" -> ")[1].split(",")[1]))
                            )
                    ).filter(q -> Objects.equals(q.p1().x(), q.p2().x()) || Objects.equals(q.p1().y(), q.p2().y()))
                    .mapMulti((x, consumer) -> getPointsOnLine(x).forEach(consumer)).toList();
            printSolution(kTaskOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<String> a = Utilities.readInFile("src/day5/input")) {
            List<Object> kTaskOne = a.map(x -> new Line(
                            new Point(
                                    Integer.parseInt(x.split(" -> ")[0].split(",")[0]),
                                    Integer.parseInt(x.split(" -> ")[0].split(",")[1]))
                            ,
                            new Point(
                                    Integer.parseInt(x.split(" -> ")[1].split(",")[0]),
                                    Integer.parseInt(x.split(" -> ")[1].split(",")[1]))
                    )
            ).mapMulti((x, consumer) -> getPointsOnLine(x).forEach(consumer)).toList();
            printSolution(kTaskOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printSolution(List<Object> kTaskOne) {
        System.out.println(
                kTaskOne.stream().map(x -> new Object[] {Collections.frequency(kTaskOne, x), x})
                        .filter(u -> (int) u[0] > 1)
                        .map(u -> u[1]).unordered().distinct().count()); //<- THIS IS FUCKING SLOW
    }

    public static List<Point> getPointsOnLine(Line l) {
        final List<Point> list = new ArrayList<>();
        Stream.of(l).filter(x -> Objects.equals(x.p2().x(), x.p1().x())).forEach(x -> IntStream.range(Math.min(x.p1().y(), x.p2().y()), Math.max(x.p1().y(), x.p2().y()) + 1).forEach(k -> list.add(new Point(x.p1().x(), k))));
        Stream.of(l).filter(x -> Objects.equals(x.p2().y(), x.p1().y())).forEach(x -> IntStream.range(Math.min(x.p1().x(), x.p2().x()), Math.max(x.p1().x(), x.p2().x()) + 1).forEach(k -> list.add(new Point(k, x.p1().y()))));
        Stream.of(l)
                .filter(x -> !Objects.equals(x.p2().y(), x.p1().y()) && !Objects.equals(x.p2().x(), x.p1().x()))
                .forEach(x -> {
                    IntStream ax =
                            IntStream.range(Math.min(x.p1().x(), x.p2().x()), Math.max(x.p1().x(), x.p2().x()) + 1);
                    ax = x.p1().x() > x.p2().x() ? ax.map(i -> (x.p1().x() + 1) - i + x.p2().x() - 1) : ax;
                    IntStream ay =
                            IntStream.range(Math.min(x.p1().y(), x.p2().y()), Math.max(x.p1().y(), x.p2().y()) + 1);
                    ay = x.p1().y() > x.p2().y() ? ay.map(i -> (x.p1().y() + 1) - i + x.p2().y() - 1) : ay;
                    Spliterator<Integer> aS = ax.spliterator();
                    Spliterator<Integer> aB = ay.spliterator();
                    int characteristics = aS.characteristics() & aB.characteristics() &
                            ~(Spliterator.DISTINCT | Spliterator.SORTED);
                    long zipSize = ((characteristics & Spliterator.SIZED) != 0)
                            ? Math.min(aS.getExactSizeIfKnown(), aB.getExactSizeIfKnown())
                            : -1;

                    BiFunction<Integer, Integer, Integer[]> k = (a, b) -> new Integer[] {a, b};
                    Iterator<Integer> aI = Spliterators.iterator(aS);
                    Iterator<Integer> bI = Spliterators.iterator(aB);
                    Iterator<Integer[]> cI = new Iterator<>() {
                        @Override
                        public boolean hasNext() {
                            return aI.hasNext() && bI.hasNext();
                        }

                        @Override
                        public Integer[] next() {
                            return k.apply(aI.next(), bI.next());
                        }
                    };
                    Spliterator<Integer[]> split = Spliterators.spliterator(cI, zipSize, characteristics);
                    Stream<Integer[]> stream =
                            (ax.isParallel() || ay.isParallel()) ? StreamSupport.stream(split, true) :
                                    StreamSupport.stream(split, false);
                    stream.forEach(q -> list.add(new Point(q[0], q[1])));
                });
        return list;
    }
}


record Point(Integer x, Integer y) {
}

record Line(Point p1, Point p2) {
}