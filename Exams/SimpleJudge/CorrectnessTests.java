import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CorrectnessTests {

    private IJudge judge;
    private Random idGen;

    @Test
    @Before
    public void TestSetUp() {
        this.judge = new Judge();
        this.idGen = new Random();
    }

    @Test
    public void AddUser_SingleUser_ShouldAddUser() {
        int id = this.idGen.nextInt(100000);
        this.judge.addUser(id);

        int result = this.judge.getUsers().iterator().next();
        Assert.assertEquals(id, result);
    }

    @Test
    public void GetUsers_MultipleUsers_ShouldReturnOrderedCollection() {
        // Arrange
        List<Integer> users = new ArrayList<Integer>();

        users.add(this.idGen.nextInt(100000));
        users.add(this.idGen.nextInt(100000));
        users.add(this.idGen.nextInt(100000));
        users.add(this.idGen.nextInt(100000));

        // Act
        for (Integer user : users) {
            this.judge.addUser(user);
        }

        Iterable<Integer> result = this.judge.getUsers();

        List<Integer> correct = users.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(users.stream().sorted().collect(Collectors.toList()), result);
    }

    @Test
    public void AddContest_SingleContest_ShouldAddContest() {
        int id = this.idGen.nextInt(100000);
        this.judge.addContest(id);

        int result = this.judge.getContests().iterator().next();

        Assert.assertEquals(id, result);
    }

    @Test
    public void GetSubmissions_MultipleSubmissions_ShouldReturnOrderedCollection() {
        List<Submission> submissions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            int contestId = this.idGen.nextInt(100000);
            int points = this.idGen.nextInt(100000);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            submissions.add(submission);

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        Iterable<Submission> result = this.judge.getSubmissions();

        Assert.assertEquals(submissions.stream().sorted().collect(Collectors.toList()), result);
    }

    @Test
    public void AddSubmission_SingleSubmissionWithValidContestAndUser_ShouldAddCorrectContest() {
        int submissionId = this.idGen.nextInt(100000);
        int userId = this.idGen.nextInt(100000);
        int contestId = this.idGen.nextInt(100000);
        int points = this.idGen.nextInt(100000);
        SubmissionType type = SubmissionType.CSHARP_CODE;

        Submission submission = new Submission(submissionId, points, type, contestId, userId);

        this.judge.addContest(contestId);
        this.judge.addUser(userId);
        this.judge.addSubmission(submission);

        Submission result = this.judge.getSubmissions().iterator().next();

        Assert.assertEquals(submissionId, result.getId());
        Assert.assertEquals(type, result.getType());
        Assert.assertEquals(points, result.getPoints());
        Assert.assertEquals(contestId, result.getContestId());
        Assert.assertEquals(userId, result.getUserId());
    }

    @Test(expected = Exception.class)
    public void AddSubmission_SingleSubmissionInvalidUserId_ShouldThrow() {
        int submissionId = this.idGen.nextInt(100000);
        int userId = this.idGen.nextInt(100000);
        int contestId = this.idGen.nextInt(100000);
        int points = this.idGen.nextInt(100000);
        SubmissionType type = SubmissionType.CSHARP_CODE;

        Submission submission = new Submission(submissionId, points, type, contestId, userId);

        this.judge.addContest(contestId);

        this.judge.addSubmission(submission);
    }

    @Test(expected = Exception.class)
    public void AddSubmission_SingleSubmissionInvalidContestId_ShouldThrow() {
        int submissionId = this.idGen.nextInt(100000);
        int userId = this.idGen.nextInt(100000);
        int contestId = this.idGen.nextInt(100000);
        int points = this.idGen.nextInt(100000);
        SubmissionType type = SubmissionType.CSHARP_CODE;

        Submission submission = new Submission(submissionId, points, type, contestId, userId);

        this.judge.addUser(userId);

        this.judge.addSubmission(submission);
    }

    @Test
    public void DeleteSubmission_ExistingSubmission_ShouldDeleteCorrectSubmission()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();
        List<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            int contestId = this.idGen.nextInt(100000);
            int points = this.idGen.nextInt(100000);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
                ids.add(submissionId);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }
        int subId = this.idGen.nextInt(ids.size());
        Submission sub = submissions.get(ids.get(subId));

        this.judge.deleteSubmission(sub.getId());
        submissions.remove(sub.getId());

        List<Submission> actual = new ArrayList<>();
        for (Submission s : judge.getSubmissions()) {
            actual.add(s);
        }

        Assert.assertEquals(submissions.size(), actual.size());
        Assert.assertEquals(submissions.values().stream().sorted().collect(Collectors.toList()), actual);
    }

    @Test(expected = Exception.class)
    public void DeleteSubmission_NonExistingSubmission_ShouldThrow()
    {
        int submissionId = this.idGen.nextInt(100000);

        this.judge.deleteSubmission(submissionId);
    }

    @Test
    public void ContestsBySubmissionType_WithSingleSubmission_ShouldReturnSingleContest()
    {
        int submissionId = this.idGen.nextInt(100000);
        int userId = this.idGen.nextInt(100000);
        int contestId = this.idGen.nextInt(100000);
        int points = this.idGen.nextInt(100000);
        SubmissionType type = SubmissionType.CSHARP_CODE;

        Submission submission = new Submission(submissionId, points, type, contestId, userId);

        this.judge.addContest(contestId);
        this.judge.addUser(userId);
        this.judge.addSubmission(submission);

        int result = this.judge.contestsBySubmissionType(submission.getType()).iterator().next();

        Assert.assertEquals(contestId, result);
    }

    @Test
    public void ContestsBySubmissionType_WithNonExistingType_ShouldReturnEmptyCollection()
    {
        int submissionId = this.idGen.nextInt(100000);
        int userId = this.idGen.nextInt(100000);
        int contestId = this.idGen.nextInt(100000);
        int points = this.idGen.nextInt(100000);
        SubmissionType type = SubmissionType.CSHARP_CODE;

        Submission submission = new Submission(submissionId, points, type, contestId, userId);

        this.judge.addContest(contestId);
        this.judge.addUser(userId);
        this.judge.addSubmission(submission);

        List<Integer> actual = new ArrayList<>();
        for (Integer s : this.judge.contestsBySubmissionType(SubmissionType.PHP_CODE)) {
            actual.add(s);
        }

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void ContestsBySubmissionType_MultipleSubmissionsWithSingleType_ShouldReturnCorrectContests()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<>();
        SubmissionType type = SubmissionType.CSHARP_CODE;

        for (int i = 0; i < 10; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            int contestId = this.idGen.nextInt(100000);
            int points = this.idGen.nextInt(100000);

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
            }
            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        List<Integer> actual = new ArrayList<>();
        for (Integer s : this.judge.contestsBySubmissionType(type)) {
            actual.add(s);
        }

        List<Integer> expected = submissions.values().stream()
                .map(Submission::getContestId)
                .collect(Collectors.toList());

        Collections.sort(actual);
        Collections.sort(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ContestsBySubmissionType_MultipleSubmissionsWithDifferentTypes_ShouldReturnPartialContests()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();

        for (int i = 0; i < 10; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            int contestId = this.idGen.nextInt(100000);
            int points = this.idGen.nextInt(100000);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        List<Integer> actual = new ArrayList<>();
        for (Integer s : this.judge.contestsBySubmissionType(SubmissionType.CSHARP_CODE)) {
            actual.add(s);
        }

        List<Integer> expected = submissions.values().stream()
                .filter(x -> x.getType().equals(SubmissionType.CSHARP_CODE))
                .map(Submission::getContestId)
                .collect(Collectors.toList());

        Collections.sort(actual);
        Collections.sort(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void SubmissionsInContestIdByUserIdWithPoints_WithValidData_ShouldReturnValidResult()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<>();
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < 500; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(5);
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(5);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
                ids.add(submissionId);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int subId = this.idGen.nextInt(ids.size());
        Submission sub = submissions.get(ids.get(subId));

        List<Submission> actual = new ArrayList<>();
        for (Submission s : this.judge.submissionsInContestIdByUserIdWithPoints(sub.getPoints(), sub.getContestId(), sub.getUserId())) {
            actual.add(s);
        }

        List<Submission> expected = submissions.values().stream()
                .filter(x -> x.getContestId() == sub.getContestId()
                && x.getUserId() == sub.getUserId()
                && x.getPoints() == sub.getPoints())
                .collect(Collectors.toList());

        Collections.sort(actual);
        Collections.sort(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = Exception.class)
    public void SubmissionsInContestIdByUserIdWithPoints_WithInvalidPoints_ShouldThrow()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<>();
        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 0; i < 500; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(5);
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(5);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
                ids.add(submissionId);
            }
            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int subId = this.idGen.nextInt(ids.size());
        Submission sub = submissions.get(ids.get(subId));

        this.judge.submissionsInContestIdByUserIdWithPoints(15, sub.getContestId(), sub.getUserId());
    }

    @Test(expected = Exception.class)
    public void SubmissionsInContestIdByUserIdWithPoints_WithInvalidContest_ShouldThrow()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(5);
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(5);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
                ids.add(submissionId);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int subId = this.idGen.nextInt(ids.size());
        Submission sub = submissions.get(ids.get(subId));

        this.judge.submissionsInContestIdByUserIdWithPoints(sub.getPoints(), 6, sub.getUserId());
    }

    @Test(expected = Exception.class)
    public void SubmissionsInContestIdByUserIdWithPoints_WithInvalidUser_ShouldThrow()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(5);
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(5);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
                ids.add(submissionId);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int subId = this.idGen.nextInt(ids.size());
        Submission sub = submissions.get(ids.get(subId));

        this.judge.submissionsInContestIdByUserIdWithPoints(sub.getPoints(), sub.getContestId(), 7);
    }

    @Test
    public void SubmissionsInRange_WithValidData_ShouldReturnCorrectSubmissions()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();

        for (int i = 0; i < 500; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(50);
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(30);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int minPoints = this.idGen.nextInt(15);
        int maxPoints = ThreadLocalRandom.current().nextInt(15, 30);
        SubmissionType expectedType = SubmissionType.CSHARP_CODE;

        List<Submission> actual = new ArrayList<>();
        for (Submission s : this.judge.submissionsWithPointsInRangeBySubmissionType(minPoints, maxPoints, expectedType)) {
            actual.add(s);
        }

        List<Submission> expected = submissions.values().stream()
                .filter(x -> x.getPoints() >= minPoints && x.getPoints() <= maxPoints && x.getType().equals(expectedType))
                .collect(Collectors.toList());

        Collections.sort(actual);
        Collections.sort(expected);


        Assert.assertEquals(expected, actual);
    }
}
