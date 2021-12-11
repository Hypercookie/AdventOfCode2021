package day1;

import utils.Utilities;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDayOne {
    public static void main(String[] args) {
        //Part One
        try (Stream<String> a = Utilities.readInFile("src/day1/input");) {
            AtomicInteger i = new AtomicInteger();
            a.reduce((q, b) -> {
                if (Integer.parseInt(q) < Integer.parseInt(b)) {
                    i.getAndIncrement();
                }
                return b;
            }).orElseGet(() -> "");
            System.out.println(i.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Part Two
        try (Stream<String> a = Utilities.readInFile("/Users/jannes/IdeaProjects/AdventOfCode2021/src/day1/input");) {
            AtomicInteger i = new AtomicInteger();
            String[] k = a.toArray(String[]::new);
            IntStream.range(0, k.length - 2).map(q -> {
                return Integer.parseInt(k[q]) + Integer.parseInt(k[q + 1]) + Integer.parseInt(k[q + 2]);
            }).reduce((q, b) -> {
                if (q < b) {
                    i.getAndIncrement();
                }
                return b;
            });
            System.out.println(i.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
