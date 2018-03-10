import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Judge implements IJudge {

    private TreeSet<Integer> users;
    private TreeSet<Integer> contests;
    private TreeMap<Integer, Submission> submissions;
    private HashMap<String, List<Submission>> submissionsByContestUserPoints;
    private HashMap<Integer, List<Submission>> usersSubmissions;
    private HashMap<String, TreeSet<Integer>> contestsBySubType;
    private HashMap<String, List<Submission>> submissionsBySubType;

    public Judge() {
        this.users = new TreeSet<>();
        this.contests = new TreeSet<>();
        this.submissions = new TreeMap<>();
        this.submissionsByContestUserPoints = new HashMap<>();
        this.contestsBySubType = new HashMap<>();
        this.submissionsBySubType = new HashMap<>();
        this.usersSubmissions = new HashMap<>();
    }

    public void addContest(int contestId) {
        this.contests.add(contestId);
    }

    public void addSubmission(Submission submission) {
        if (!this.users.contains(submission.getUserId())
                || !this.contests.contains(submission.getContestId())) {
            throw new IllegalArgumentException();
        }
        if (this.submissions.containsKey(submission.getId())) {
            return;
        }
        this.submissions.put(submission.getId(), submission);
        String key = String.format("%s %s %s",
                submission.getContestId(), submission.getUserId(), submission.getPoints());
        this.submissionsByContestUserPoints.putIfAbsent(key, new ArrayList<>());
        this.submissionsByContestUserPoints.get(key).add(submission);
        this.contestsBySubType.putIfAbsent(submission.getType().name(), new TreeSet<>());
        this.contestsBySubType.get(submission.getType().name()).add(submission.getContestId());
        this.submissionsBySubType.putIfAbsent(submission.getType().name(), new ArrayList<>());
        this.submissionsBySubType.get(submission.getType().name()).add(submission);
        this.usersSubmissions.putIfAbsent(submission.getUserId(), new ArrayList<>());
        this.usersSubmissions.get(submission.getUserId()).add(submission);
    }

    public void addUser(int userId) {
        this.users.add(userId);
    }

    public void deleteSubmission(int submissionId) {
        Submission sub = this.submissions.get(submissionId);
        String key = String.format("%s %s %s",
                sub.getContestId(), sub.getUserId(), sub.getPoints());
        this.submissionsByContestUserPoints.remove(key);
        this.usersSubmissions.get(sub.getUserId()).remove(sub);
        this.submissionsBySubType.get(sub.getType().name()).remove(sub);
        this.submissions.remove(submissionId);
    }

    public Iterable<Submission> getSubmissions() {
        return new ArrayList<>(this.submissions.values());
    }

    public Iterable<Integer> getUsers() {
        return new ArrayList<>(this.users);
    }

    public Iterable<Integer> getContests() {
        return new ArrayList<>(this.contests);
    }

    public Iterable<Submission> submissionsWithPointsInRangeBySubmissionType(int minPoints, int maxPoints, SubmissionType submissionType) {
        if (!this.submissionsBySubType.containsKey(submissionType.name())) {
            return new ArrayList<>();
        }
        List<Submission> result = this.submissionsBySubType.get(submissionType.name()).stream()
                .filter(s -> s.getPoints() <= maxPoints && s.getPoints() >= minPoints)
                .collect(Collectors.toList());
        return result;
    }

    public Iterable<Integer> contestsByUserIdOrderedByPointsDescThenBySubmissionId(int userId) {
        return this.usersSubmissions.get(userId).stream()
                .sorted(Comparator.comparing(Submission::getPoints, Comparator.reverseOrder())
                        .thenComparing(Submission::getId))
                .map(Submission::getContestId)
                .distinct()
                .collect(Collectors.toList());
    }

    public Iterable<Submission> submissionsInContestIdByUserIdWithPoints(int points, int contestId, int userId) {
        String key = String.format("%s %s %s",
                contestId, userId, points);
        if (!this.submissionsByContestUserPoints.containsKey(key)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.submissionsByContestUserPoints.get(key));
    }

    public Iterable<Integer> contestsBySubmissionType(SubmissionType submissionType) {
        if (!contestsBySubType.containsKey(submissionType.name())) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.contestsBySubType.get(submissionType.name()));
    }
}
