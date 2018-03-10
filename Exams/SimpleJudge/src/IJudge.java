public interface IJudge {

    void addContest(int contestId);

    void addSubmission(Submission submission);

    void addUser(int userId);

    void deleteSubmission(int submissionId);

    Iterable<Submission> getSubmissions();

    Iterable<Integer> getUsers();

    Iterable<Integer> getContests();

    Iterable<Submission> submissionsWithPointsInRangeBySubmissionType(int minPoints, int maxPoints, SubmissionType
            submissionType);

    Iterable<Integer> contestsByUserIdOrderedByPointsDescThenBySubmissionId(int userId);

    Iterable<Submission> submissionsInContestIdByUserIdWithPoints(int points, int contestId, int userId);

    Iterable<Integer> contestsBySubmissionType(SubmissionType submissionType);
}