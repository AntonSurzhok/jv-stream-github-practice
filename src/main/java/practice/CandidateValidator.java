package practice;

public class CandidateValidator implements java.util.function.Predicate<model.Candidate> {
    @Override
    public boolean test(model.Candidate candidate) {
        if (candidate.getAge() <= 35) {
            return false;
        }
        if (!candidate.isAllowedToVote()) {
            return false;
        }
        if (!"Ukrainian".equals(candidate.getNationality())) {
            return false;
        }

        String[] period = candidate.getPeriodsInUkr().split("-");
        if (period.length != 2) {
            return false;
        }
        try {
            int start = Integer.parseInt(period[0]);
            int end = Integer.parseInt(period[1]);
            return (end - start) >= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}