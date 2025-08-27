package practice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Candidate;
import model.Cat;
import model.Person;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.NoSuchElementException;
import java.util.stream.*;

public class StreamPractice {
    public static int findMin(List<String> input) {
        return input.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(String::trim)
                .map(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .min(Integer::compareTo)
                .orElseThrow(() ->
                        new RuntimeException("Can't get min value from list: " + input));
    }

    public int findMinNumber(List<String> numbersmin) {
        return findMin(numbersmin);
    }

    public static double findMidle(List<String> input) {
        List<Integer> numbers = input.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return IntStream.range(0, numbers.size())
                .map(i -> (i % 2 == 1) ? numbers.get(i) - 1 : numbers.get(i))
                .filter(n -> n % 2 != 0)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }

    public static double getOddNumsAverage(List<Integer> numbersmidle) {
        return IntStream.range(0, numbersmidle.size())
                .map(i -> (i % 2 == 1) ? numbersmidle.get(i) - 1 : numbersmidle.get(i))
                .filter(n -> n % 2 != 0)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= fromAge)
                .filter(p -> (p.getSex() == Sex.Man && p.getAge() <= maleToAge)
                        || (p.getSex() == Sex.Woman && p.getAge() <= femaleToAge))
                .collect(Collectors.toList());
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(p -> p.getSex() == Sex.WOMAN && p.getAge() >= femaleAge)
                .flatMap(p -> p.getCats().stream())
                .map(Cat::getName)
                .collect(Collectors.toList());
    }

    public class CandidateValidator implements Predicate<Candidate> {
        @Override
        public boolean test(Candidate candidate) {
            if (candidate == null) {
                return false;
            }

            if (candidate.getAge() <= 35) {
                return false;
            }

            if (!candidate.isAllowedToVote()) {
                return false;
            }

            if (!"Ukrainian".equalsIgnoreCase(candidate.getNationality())) {
                return false;
            }

            String[] years = candidate.getPeriodsInUkr().split("-");
            if (years.length != 2) {
                return false;
            }

            try {
                int fromYear = Integer.parseInt(years[0]);
                int toYear = Integer.parseInt(years[1]);
                int yearsInUkr = toYear - fromYear;
                if (yearsInUkr < 10) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            return true;
        }
    }
}