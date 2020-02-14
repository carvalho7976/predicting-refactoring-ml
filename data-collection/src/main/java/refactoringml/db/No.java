package refactoringml.db;

import refactoringml.util.JGitUtils;
import refactoringml.util.RefactoringUtils;

import javax.persistence.*;
import java.util.Calendar;

//TODO: create a Baseclass for both Yes and No, as they share a lot of logic
@Entity
@Table(name = "no", indexes = {@Index(columnList = "project_id"), @Index(columnList = "type"), @Index(columnList = "isTest")})
public class No {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	//project id: referencing the project information, e.g. name or gitUrl
	private Project project;

	//id of this commit
	private String commit;
	//original commit message
	private String commitMessage;
	//url to the commit on its remote repository, e.g. https://github.com/mauricioaniche/predicting-refactoring-ml/commit/36016e4023cb74cd1076dbd33e0d7a73a6a61993
	private String commitUrl;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar commitDate;

	//relative filepath to the java file of the refactored class
	private String filePath;
	//name of the refactored class, @Warning: might differ from the filename
	private String className;
	//is this commit affecting a test?
	private boolean isTest;

	@Embedded
	private ClassMetric classMetrics;

	@Embedded
	private MethodMetric methodMetrics;

	@Embedded
	private VariableMetric variableMetrics;

	@Embedded
	private FieldMetric fieldMetrics;

	@Embedded
	private ProcessMetrics processMetrics;

	//TODO: what exactly describes this field?
	private int type;

	@Deprecated // hibernate purposes
	public No() {}

	public No(Project project, String commit, String commitMessage, Calendar commitDate, String filePath, String className,
	          ClassMetric classMetrics, MethodMetric methodMetrics, VariableMetric variableMetrics, FieldMetric fieldMetrics, int type) {
		this.project = project;
		this.commit = commit;
		this.commitDate = commitDate;
		this.filePath = filePath;
		this.className = className;
		this.classMetrics = classMetrics;
		this.methodMetrics = methodMetrics;
		this.variableMetrics = variableMetrics;
		this.fieldMetrics = fieldMetrics;
		this.type = type;
		this.commitMessage = commitMessage.trim();
		this.commitUrl = JGitUtils.generateCommitUrl(project.getGitUrl(), commit, project.isLocal());

		this.isTest = RefactoringUtils.isTestFile(this.filePath);
	}

	public void setProcessMetrics(ProcessMetrics processMetrics) {
		this.processMetrics = processMetrics;
	}

	public String getCommit() {
		return commit;
	}

	public ProcessMetrics getProcessMetrics() { return processMetrics; }

	public ClassMetric getClassMetrics() {
		return classMetrics;
	}

	public String getClassName() { return className; }

	public String getCommitMessage (){return commitMessage;}

	public String getCommitUrl (){return commitUrl;}

	@Override
	public String toString() {
		return "No{" +
				"id=" + id +
				", project=" + project +
				", commit='" + commit + '\'' +
				", commitMessage=" + commitMessage +
				", commitUrl=" + commitUrl +
				", filePath='" + filePath + '\'' +
				", className='" + className + '\'' +
				", classMetrics=" + classMetrics +
				", methodMetrics=" + methodMetrics +
				", variableMetrics=" + variableMetrics +
				", fieldMetrics=" + fieldMetrics +
				", processMetrics=" + processMetrics +
				", type=" + type +
				'}';
	}
}
