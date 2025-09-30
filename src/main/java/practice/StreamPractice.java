package practice;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .filter(i -> i % 2 == 0)
                .min()
                .orElseThrow(() ->
                        new RuntimeException("Can't get min value from list: " + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .mapToObj(i -> i % 2 == 1 ? numbers.get(i) - 1 : numbers.get(i))
                .filter(integer -> integer % 2 == 1)
                .mapToInt(Integer::intValue)
                .average()
                .getAsDouble();
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(person -> person.getSex() == Person.Sex.MAN
                        && (fromAge <= person.getAge() && person.getAge() <= toAge))
                .collect(Collectors.toList());
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        Predicate<Person> female = person -> person.getSex() == Person.Sex.WOMAN
                && (person.getAge() >= fromAge && person.getAge() <= femaleToAge);
        Predicate<Person> male = person -> person.getSex() == Person.Sex.MAN
                && (person.getAge() >= fromAge && person.getAge() <= maleToAge);
        return peopleList.stream()
                .filter(male.or(female))
                .collect(Collectors.toList());
    }

    public static List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        Predicate<Person> validateFemale = person -> person.getSex() == Person.Sex.WOMAN
                && person.getAge() >= femaleAge;
        return peopleList.stream()
                .filter(validateFemale)
                .flatMap(l -> l.getCats().stream())
                .map(Cat::getName)
                .collect(Collectors.toList());
    }

    public static List<String> validateCandidates(List<Candidate> candidates) {
        Predicate<Candidate> validator = new CandidateValidator();
        return candidates.stream()
                .filter(validator)
                .sorted(Comparator.comparing(Candidate::getName))
                .map(Candidate::getName)
                .collect(Collectors.toList());
    }
}
