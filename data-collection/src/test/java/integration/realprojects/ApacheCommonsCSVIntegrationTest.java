package integration.realprojects;

import integration.IntegrationBaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import refactoringml.db.No;
import refactoringml.db.Yes;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApacheCommonsCSVIntegrationTest extends IntegrationBaseTest {

    @Override
    protected String getLastCommit() {
        return "70092bb303af69b09bf3978b24c1faa87c909e3c";
    }

    @Override
    protected String getRepo() {
        return "https://www.github.com/apache/commons-csv.git";
    }

    @Override
    protected String track() {
        return "src/main/java/org/apache/commons/csv/CSVStrategy.java";
    }

    @Override
    protected int threshold() {
        return 50;
    }

    // this test checks the Rename Parameter that has happened in #b58168683d01149a568734df21568ffcc41105fe,
    // method isSet
    @Test
    public void randomRefactoring() {

        // manually verified
        Yes instance1 = (Yes) session.createQuery("from Yes where refactoring = :refactoring and methodMetrics.fullMethodName = :method and refactorCommit = :refactorCommit and project = :project")
                .setParameter("refactoring", "Rename Parameter")
                .setParameter("method", "isSet/1[int]")
                .setParameter("refactorCommit", "b58168683d01149a568734df21568ffcc41105fe")
                .setParameter("project", project)
                .uniqueResult();

        Assert.assertNotNull(instance1);

        Assert.assertEquals("isSet/1[int]", instance1.getMethodMetrics().getFullMethodName());
        Assert.assertEquals(0, instance1.getMethodMetrics().getMethodVariablesQty());
        Assert.assertEquals(0, instance1.getMethodMetrics().getMethodMaxNestedBlocks());
        Assert.assertEquals(1, instance1.getMethodMetrics().getMethodReturnQty());
        Assert.assertEquals(0, instance1.getMethodMetrics().getMethodTryCatchQty());

    }

    // this test follows the src/main/java/org/apache/commons/csv/CSVFormat.java file
    @Test
    public void yes_CSVFormat() {


        // it had suffered 69 refactorings with the new name: 'CSVFormat'...
        List<Yes> yesList = session.createQuery("From Yes where filePath = :filePath and project = :project order by refactoringDate desc")
                .setParameter("filePath", "src/main/java/org/apache/commons/csv/CSVFormat.java")
                .setParameter("project", project)
                .list();
        Assert.assertEquals(69, yesList.size());

        assertRefactoring(yesList,"040c2606eb7e2cfe60e4bbcbf2928f1e971ce4b4", "Rename Method",1);
        assertRefactoring(yesList,"040c2606eb7e2cfe60e4bbcbf2928f1e971ce4b4", "Rename Parameter",1);
        assertRefactoring(yesList,"0dbb499888e5e17322d08802222f2453bf5621a6", "Rename Method",1);
        assertRefactoring(yesList,"322fad25ad96da607a3045a19702a55381a426e7", "Rename Method",1);
        assertRefactoring(yesList,"38741a48c692ae2fc13cd2445e77ace6ecea1156", "Rename Method",1);
        assertRefactoring(yesList,"38741a48c692ae2fc13cd2445e77ace6ecea1156", "Rename Parameter",1);
        assertRefactoring(yesList,"4695d73e3b1974454d55a30be2b1c3a4bddbf3db", "Rename Method",4);
        assertRefactoring(yesList,"50e2719bb646870dc08dd71f2bc2314ce46def76", "Extract Method",4);
        assertRefactoring(yesList,"56ca5858db4765112dca44e5deeda0ac181a6766", "Extract Class",1);
        assertRefactoring(yesList,"5a0894f9e0ee9f4703b8db3f200ff4a507bf043b", "Rename Method",1);
        assertRefactoring(yesList,"5a0894f9e0ee9f4703b8db3f200ff4a507bf043b", "Rename Parameter",1);
        assertRefactoring(yesList,"67bbc35289bb3435eae0bd6f20cc6b15280e66e0", "Rename Method",4);
        assertRefactoring(yesList,"67bbc35289bb3435eae0bd6f20cc6b15280e66e0", "Rename Parameter",2);
        assertRefactoring(yesList,"67d150adc88b806e52470d110a438d9107e72ed5", "Rename Method",2);
        assertRefactoring(yesList,"6a34b823c807325bc251ef43c66c307adcd947b8", "Extract Class",1);
        assertRefactoring(yesList,"6a34b823c807325bc251ef43c66c307adcd947b8", "Move Method",7);
        assertRefactoring(yesList,"6e57364216b78bca031f764b8d0a46494ba27b46", "Rename Method",1);
        assertRefactoring(yesList,"73ec29f75f1dd6f0c52e9c310dc4f8414346f49a", "Rename Method",3);
        assertRefactoring(yesList,"73ec29f75f1dd6f0c52e9c310dc4f8414346f49a", "Rename Parameter",2);
        assertRefactoring(yesList,"75f39a81a77b3680c21cd3f810da62ebbe9944b8", "Rename Method",1);
        assertRefactoring(yesList,"7ac5dd3ec633d64603bb836d0576f34a51f93f08", "Rename Method",2);
        assertRefactoring(yesList,"7c770e0b53235e90a216ae3b214048b765cda0c0", "Inline Method",1);
        assertRefactoring(yesList,"9fb2b4f2b100c8d5a769532ee26973832c2a61c0", "Rename Method",1);
        assertRefactoring(yesList,"a72c71f5cc6431890f82707a2782325be6747dd1", "Rename Method",2);
        assertRefactoring(yesList,"a72c71f5cc6431890f82707a2782325be6747dd1", "Rename Parameter",2);
        assertRefactoring(yesList,"aa0762d538c52f4384f629bb799769f5f85c1c9e", "Rename Method",1);
        assertRefactoring(yesList,"aa0762d538c52f4384f629bb799769f5f85c1c9e", "Rename Parameter",1);
        assertRefactoring(yesList,"afc9de71bd8bb51dfc7ab12df2aeb7a38b709ef2", "Rename Parameter",1);
        assertRefactoring(yesList,"c0d91d205d5494dc402dab13edcc89679aecd547", "Move Method",1);
        assertRefactoring(yesList,"ecea0c35993b2428e0a938933896329c413de40e", "Rename Method",1);
        assertRefactoring(yesList,"ecea0c35993b2428e0a938933896329c413de40e", "Rename Parameter",1);
        assertRefactoring(yesList,"f51f89828df4d763148362e312e310435864ba96", "Rename Method",5);
        assertRefactoring(yesList,"f80c5bd0ad8ed0739f43a2ed6392d94bbceae1c9", "Rename Parameter",1);
        assertRefactoring(yesList,"f9a3162037f7e82ce6927bbe944b7d61349f8c11", "Rename Method",9);

    }

    // this test follows the src/main/java/org/apache/commons/csv/CSVFormat.java file
    @Test
    public void no_CSVFormat() {
        List<No> noList = session.createQuery("From No where type = 1 and filePath = :filePath and project = :project")
                .setParameter("filePath", "src/main/java/org/apache/commons/csv/CSVFormat.java")
                .setParameter("project", project)
                .list();

        // there's just one sentence of 50 commits without refactoring
        Assert.assertEquals(1, noList.size());

        assertNoRefactoring(noList, "67d150adc88b806e52470d110a438d9107e72ed5");
        Assert.assertEquals(4, noList.get(0).getProcessMetrics().getQtyOfAuthors());

        // in yes_CSVFormat, we see that there are 69 refactorings in total.
        // after this commit, there was just one more refactoring. Thus, 68 refactorings
        Assert.assertEquals(68, noList.get(0).getProcessMetrics().getRefactoringsInvolved());

        // also manually validated
        Assert.assertEquals(198, noList.get(0).getProcessMetrics().getQtyOfCommits());

        // also manually validated
        Assert.assertEquals(5, noList.get(0).getClassMetrics().getClassNumberOfPublicFields());
        Assert.assertEquals(39, noList.get(0).getClassMetrics().getClassNumberOfPublicMethods());
        Assert.assertEquals(13, noList.get(0).getClassMetrics().getClassNumberOfPrivateFields());
        Assert.assertEquals(4, noList.get(0).getClassMetrics().getClassNumberOfPrivateMethods());
    }

    @Test
    public void yes_CSVStrategy() {
        // and 10 with the old name 'CSVStrategy.java'
        List<Yes> yesList = session.createQuery("From Yes where filePath = :filePath and project = :project order by refactoringDate desc")
                .setParameter("filePath", "src/main/java/org/apache/commons/csv/CSVStrategy.java")
                .setParameter("project", project)
                .list();
        Assert.assertEquals(22, yesList.size());

        assertRefactoring(yesList, "42476f4b08fe4b075aa36f688f0801857f3635d9", "Rename Method", 5);
        assertRefactoring(yesList, "42476f4b08fe4b075aa36f688f0801857f3635d9", "Rename Parameter", 4);
        assertRefactoring(yesList, "43b777b9829141a3eb417ebf3ce49c8444884af0", "Inline Method", 2);
        assertRefactoring(yesList, "43b777b9829141a3eb417ebf3ce49c8444884af0", "Rename Parameter", 1);
        assertRefactoring(yesList, "cb99634ab3d6143dffc90938fc68e15c7f9d25b8", "Rename Class", 1);
        assertRefactoring(yesList, "cb99634ab3d6143dffc90938fc68e15c7f9d25b8", "Rename Variable", 9);
    }

    @Test
    public void no_CSVStrategy() {
        List<Yes> noList = session.createQuery("From No where filePath = :filePath and project = :project")
                .setParameter("filePath", "src/main/java/org/apache/commons/csv/CSVStrategy.java")
                .setParameter("project", project)
                .list();
        Assert.assertEquals(0, noList.size());
    }

    // check the number of test and production files as well as their LOC
    @Test
    public void projectSize() {

        // the next two assertions come directly from a 'cloc .' in the project
        Assert.assertEquals(6994L, project.getJavaLoc());
        Assert.assertEquals(35L, project.getNumberOfProductionFiles() + project.getNumberOfTestFiles());

        // find . -name "*.java" | grep "/test/" | wc
        Assert.assertEquals(23, project.getNumberOfTestFiles());

        // 35 - 23
        Assert.assertEquals(12, project.getNumberOfProductionFiles());

        // cloc . --by-file | grep "/test/"
        Assert.assertEquals(5114, project.getTestLoc());

        // 6994 - 5114
        Assert.assertEquals(1880, project.getProductionLoc());


    }


}
