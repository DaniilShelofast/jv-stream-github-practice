package practice;

import java.util.function.Predicate;
import model.Candidate;

public class CandidateValidator implements Predicate<Candidate> {

    @Override
    public boolean test(Candidate candidate) {
        String[] date = candidate.getPeriodsInUkr().split("-");
        int timeInUkraine = Integer.parseInt(date[1]) - Integer.parseInt(date[0]);
        return candidate.getAge() >= 35
                && candidate.isAllowedToVote()
                && candidate.getNationality().equals("Ukrainian")
                && timeInUkraine >= 10;
    }
}
