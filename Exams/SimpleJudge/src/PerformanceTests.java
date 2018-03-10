import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PerformanceTests {

    private IJudge judge;
    private Random idGen;

    @Test
    @Before
    public void TestSetUp()
    {
        this.judge = new Judge();
        this.idGen = new Random();
    }

    @Test(timeout = 500)
    public void GetUsers_MultipleUsers_ShouldReturnOrderedCollection()
    {
        HashSet<Integer> expected = new HashSet<Integer>();

        for (int i = 0; i < 50000; i++)
        {
            expected.add(this.idGen.nextInt(100000));
        }

        long start = System.currentTimeMillis();

        for (int user : expected)
        {
            this.judge.addUser(user);
        }

        List<Integer> actual = new ArrayList<>();
        for (int s : this.judge.getUsers()) {
            actual.add(s);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue(end - start <= 250);
        Assert.assertEquals(expected.stream().sorted().collect(Collectors.toList()), actual);
    }

    @Test(timeout = 700)
    public void AddContest_MultipleContests_ShouldIncreaseCount()
    {
        HashSet<Integer> expected = new HashSet<>();

        for (int i = 0; i < 50000; i++)
        {
            expected.add(this.idGen.nextInt(100000));
        }

        long start = System.currentTimeMillis();

        for (int c : expected)
        {
            this.judge.addContest(c);
        }

        List<Integer> actual = new ArrayList<>();
        for (int s : this.judge.getContests()) {
            actual.add(s);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue(end - start <= 300);
        Assert.assertEquals(expected.stream().sorted().collect(Collectors.toList()), actual);
    }

    @Test(timeout = 1000)
    public void GetSubmissions_MultipleSubmissions_ShouldReturnOrderedCollection()
    {
        LinkedHashMap<Integer, Submission> expected = new LinkedHashMap<Integer, Submission>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 30000; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            int contestId = this.idGen.nextInt(100000);
            int points = this.idGen.nextInt(100000);
            SubmissionType type = SubmissionType.CSHARP_CODE;

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!expected.containsKey(submissionId))
            {
                expected.put(submissionId, submission);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        List<Submission> actual = new ArrayList<>();
        for (Submission s : this.judge.getSubmissions()) {
            actual.add(s);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue(end - start <= 550);

        List<Submission> correct = expected.values().stream()
                .sorted((x, y) -> Integer.compare(x.getUserId(), y.getUserId())).collect(Collectors.toList());

        Assert.assertEquals(correct, actual);
    }

    @Test(timeout = 3000)
    public void ContestsBySubmissionType_MultipleSubmissionsWithSingleType_ShouldReturnCorrectContests()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<>();
        LinkedHashMap<Integer, SubmissionType> types = new LinkedHashMap<>();
        types.put(0, SubmissionType.CSHARP_CODE);
        types.put(1, SubmissionType.PHP_CODE);
        types.put(2, SubmissionType.JAVA_CODE);
        types.put(3, SubmissionType.JAVASCRIPT_CODE);


        for (int i = 0; i < 80000; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(100000);
            SubmissionType type = types.get(this.idGen.nextInt(4));
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

        long start = System.currentTimeMillis();

        List<Integer> actual = new ArrayList<>();
        for (Integer c : this.judge.contestsBySubmissionType(SubmissionType.JAVASCRIPT_CODE)) {
            actual.add(c);
        }

        long end = System.currentTimeMillis();

        List<Integer> expected = submissions.values().stream()
                .filter(x -> x.getType().equals(SubmissionType.JAVASCRIPT_CODE))
                .map(Submission::getContestId)
                .distinct()
                .collect(Collectors.toList());

        Assert.assertTrue(end - start <= 100);

        Collections.sort(actual);
        Collections.sort(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 1200)
    public void SubmissionsInContestIdByUserIdWithPoints_WithValidData_ShouldReturnValidResult()
    {
        LinkedHashMap<Integer, Submission> submissions = new LinkedHashMap<Integer, Submission>();
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < 50000; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(5);
            SubmissionType type = SubmissionType.CSHARP_CODE;
            int contestId = this.idGen.nextInt(5);
            int points = this.idGen.nextInt(5);

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

        long start = System.currentTimeMillis();
        List<Submission> actual = new ArrayList<>();
        for (Submission s : this.judge.submissionsInContestIdByUserIdWithPoints(sub.getPoints(), sub.getContestId(), sub.getUserId())) {
            actual.add(s);
        }

        long end = System.currentTimeMillis();

        List<Submission> expected = submissions.values().stream().filter(x -> x.getContestId() == sub.getContestId()
                && x.getUserId() == sub.getUserId() &&
                x.getPoints() == sub.getPoints())
                .collect(Collectors.toList());

        Assert.assertTrue(end - start <= 250);

        Collections.sort(actual);
        Collections.sort(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 7000)
    public void SubmissionsInRange_WithValidData_ShouldReturnCorrectSubmissions()
    {
        HashMap<Integer, Submission> submissions = new HashMap<>();
        LinkedHashMap<Integer, SubmissionType> types = new LinkedHashMap<>();
        types.put(0, SubmissionType.CSHARP_CODE);
        types.put(1, SubmissionType.PHP_CODE);
        types.put(2, SubmissionType.JAVA_CODE);
        types.put(3, SubmissionType.JAVASCRIPT_CODE);

        for (int i = 0; i < 20000; i++)
        {
            int submissionId = this.idGen.nextInt(100000);
            int userId = this.idGen.nextInt(150);
            SubmissionType type = SubmissionType.CSHARP_CODE;
            int contestId = this.idGen.nextInt(30);
            int points = this.idGen.nextInt(100);

            Submission submission = new Submission(submissionId, points, type, contestId, userId);

            if (!submissions.containsKey(submissionId))
            {
                submissions.put(submissionId, submission);
            }

            this.judge.addContest(contestId);
            this.judge.addUser(userId);
            this.judge.addSubmission(submission);
        }

        int minPoints = this.idGen.nextInt(50);
        int maxPoints = ThreadLocalRandom.current().nextInt(50, 100);
        SubmissionType expectedType = types.get(this.idGen.nextInt(4));

        long start = System.currentTimeMillis();
        List<Submission> actual = new ArrayList<>();
        for (Submission s : this.judge.submissionsWithPointsInRangeBySubmissionType(minPoints, maxPoints, expectedType)) {
            actual.add(s);
        }

        long end = System.currentTimeMillis();

        List<Submission> expected = submissions.values().stream()
                .filter(x -> x.getPoints() >= minPoints && x.getPoints() <= maxPoints && x.getType().equals(expectedType))
                .collect(Collectors.toList());

        Assert.assertTrue(end - start <= 100);

        Collections.sort(expected);
        Collections.sort(actual);

        Assert.assertEquals(expected, actual);
    }
}
