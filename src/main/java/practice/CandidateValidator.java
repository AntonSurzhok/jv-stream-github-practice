package practice;

import java.util.function.Predicate;
import model.Candidate;

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
