# region database structure
# table names for reference:
commitMetaData: str = "commitmetadata"
methodMetrics: str = "methodmetric"
fieldMetrics: str = "fieldmetric"
variableMetrics: str = "variablemetric"
processMetrics: str = "processmetrics"
classMetrics: str = "classmetric"
project: str = "project"
refactoringCommits: str = "refactoringcommit"
stableCommits: str = "stablecommit"

# the ids are not included as they are the same for every table: id : long
classMetricsFields = ["classAnonymousClassesQty",
                      "classAssignmentsQty",
                      "classCbo",
                      "classComparisonsQty",
                      "classLambdasQty",
                      "classLcom",
                      "classLoc",
                      "classLoopQty",
                      "classMathOperationsQty",
                      "classMaxNestedBlocks",
                      "classNosi",
                      "classNumberOfAbstractMethods",
                      "classNumberOfDefaultFields",
                      "classNumberOfDefaultMethods",
                      "classNumberOfFields",
                      "classNumberOfFinalFields",
                      "classNumberOfFinalMethods",
                      "classNumberOfMethods",
                      "classNumberOfPrivateFields",
                      "classNumberOfPrivateMethods",
                      "classNumberOfProtectedFields",
                      "classNumberOfProtectedMethods",
                      "classNumberOfPublicFields",
                      "classNumberOfPublicMethods",
                      "classNumberOfStaticFields",
                      "classNumberOfStaticMethods",
                      "classNumberOfSynchronizedFields",
                      "classNumberOfSynchronizedMethods",
                      "classNumbersQty",
                      "classParenthesizedExpsQty",
                      "classReturnQty",
                      "classRfc",
                      "classStringLiteralsQty",
                      "classSubClassesQty",
                      "classTryCatchQty",
                      "classUniqueWordsQty",
                      "classVariablesQty",
                      "classWmc",
                      "isInnerClass"]
methodMetricsFields = ["fullMethodName",
                       "methodAnonymousClassesQty",
                       "methodAssignmentsQty",
                       "methodCbo",
                       "methodComparisonsQty",
                       "methodLambdasQty",
                       "methodLoc",
                       "methodLoopQty",
                       "methodMathOperationsQty",
                       "methodMaxNestedBlocks",
                       "methodNumbersQty",
                       "methodParametersQty",
                       "methodParenthesizedExpsQty",
                       "methodReturnQty",
                       "methodRfc",
                       "methodStringLiteralsQty",
                       "methodSubClassesQty",
                       "methodTryCatchQty",
                       "methodUniqueWordsQty",
                       "methodVariablesQty",
                       "methodWmc",
                       "shortMethodName",
                       "startLine"]
variableMetricsFields = ["variableAppearances",
                         "variableName"]
fieldMetricsFields = ["fieldAppearances",
                      "fieldName"]
processMetricsFields = ["authorOwnership",
                        "bugFixCount",
                        "linesAdded",
                        "linesDeleted",
                        "qtyMajorAuthors",
                        "qtyMinorAuthors",
                        "qtyOfAuthors",
                        "qtyOfCommits",
                        "refactoringsInvolved"]
commitMetaDataFields = ["commitDate",
                        "commitId",
                        "commitMessage",
                        "commitUrl",
                        "parentCommitId"]
projectFields = ["commitCountThresholds",
                 "commits",
                 "datasetName",
                 "dateOfProcessing",
                 "exceptionsCount",
                 "finishedDate",
                 "gitUrl",
                 "isLocal",
                 "javaLoc",
                 "lastCommitHash",
                 "numberOfProductionFiles",
                 "numberOfTestFiles",
                 "productionLoc",
                 "projectName",
                 "projectSizeInBytes",
                 "testLoc"]
refactoringCommitFields = ["className",
                           "filePath",
                           "isTest",
                           "level",
                           "refactoring",
                           "refactoringSummary"]
stableCommitFields = ["className",
                      "filePath",
                      "isTest",
                      "level"]

# all tables referenced from the instance base class for refactoring commit and stable commit
instanceReferences = ["classMetrics_id",
                      "commitMetaData_id",
                      "fieldMetrics_id",
                      "methodMetrics_id",
                      "processMetrics_id",
                      "project_id",
                      "variableMetrics_id"]

# maps table names onto their instance keys and their fields
tableMap = {commitMetaData: (commitMetaData + "_id", commitMetaDataFields),
            methodMetrics: (methodMetrics + "s_id", methodMetricsFields),
            fieldMetrics: (fieldMetrics + "s_id", fieldMetricsFields),
            variableMetrics: (variableMetrics + "s_id", variableMetricsFields),
            processMetrics: (processMetrics + "_id", processMetricsFields),
            classMetrics: (classMetrics + "s_id", classMetricsFields),
            project: (project + "_id", projectFields), refactoringCommits: ("id", refactoringCommitFields),
            stableCommits: ("id", stableCommitFields)}


# endregion


# region tables utils
# returns a sql condition as a string to filter instances based on the given project name
def project_filter(instance_name: str, project_name: str) -> str:
    return instance_name + ".project_id in (select id from project where datasetName = \"" + project_name + "\")"


# returns a sql condition to join instances with the given table
def join_tables(instance_name: str, table_name: str) -> str:
    return instance_name + "." + tableMap[table_name][0] + " = " + table_name + ".id"


# returns a list of all metrics for the given level
# e.g. classMetricsFields, methodMetricsFields and processMetricsFields for level 2 method level
def get_metrics_level(level: int):
    if level <= 3:
        return [(classMetrics, classMetricsFields), (methodMetrics, methodMetricsFields),
                (variableMetrics, variableMetricsFields)][:level] + \
               [(processMetrics, processMetricsFields)]
    elif level == 4:
        return [(classMetrics, classMetricsFields), (fieldMetrics, fieldMetricsFields),
                (processMetrics, processMetricsFields)]


# Create a sql select statement for the given instance and requested fields
# instance name: name of the instance table you are querying, e.g. refactoringcommit or stablecommit, this is also given in the fields
# fields: a list containing the table name as string and all required fields from the table as a list of strings e.g [commitMetaData, commitMetaDataFields]
# an instance has to be part of fields together with at least one field, either refactoring commit or stablecommit
# Optional conditions: a string with additional conditions for the instances, e.g. cm.isInnerClass = 1
# Optional dataset: filter the instances based on their project name, e.g. toyproject-1
# Optional order: order by command, e.g. order by commitMetaData.commitDate
def get_instance_fields(instance_name: str, fields, conditions: str = "", dataset: str = "", order: str = "") -> str:
    # combine the required fields with their table names
    required_fields: str = ""
    required_tables: str = ""
    join_conditions: str = ""
    for table_name, field_names in fields:
        required_tables += table_name + ", "
        # don't join the instance with itself
        if (instance_name != table_name):
            join_conditions += join_tables(instance_name, table_name) + " AND "
        for field_name in field_names:
            required_fields += table_name + "." + field_name + ", "
    # remove the last chars because it is either a ", " or an " AND "
    required_fields = required_fields[:-2]
    required_tables = required_tables[:-2]
    join_conditions = join_conditions[:-5]

    sql: str = "SELECT " + required_fields + " FROM " + required_tables + " WHERE " + join_conditions
    if conditions != "":
        if not sql.endswith(' WHERE '):
            sql += " AND "
        sql += conditions
    if dataset != "":
        if not sql.endswith(' WHERE '):
            sql += " AND "
        sql += project_filter(instance_name, dataset)

    return sql + " " + order


# endregion


# region Public interaction
# get the count of all refactoring levels
def get_refactoring_levels(dataset="") -> str:
    return "SELECT refactoring, count(*) total from refactoringcommit where " + project_filter("refactoringcommit",
                                                                                               dataset) \
           + " group by refactoring order by count(*) desc"


def __get_level(instance_name: str, level: int, m_refactoring: str, dataset: str = "") -> str:
    refactoring_condition: str = instance_name + ".level = " + str(level)
    if m_refactoring != "":
        refactoring_condition += " AND " + refactoringCommits + ".refactoring = \"" + m_refactoring + "\""

    return get_instance_fields(instance_name, [(instance_name, tableMap[instance_name][1]),
                                               (commitMetaData, ["commitDate"])] + get_metrics_level(level),
                               refactoring_condition, dataset, " order by " + commitMetaData + ".commitDate")


# get the count of all refactorings for the given level
def get_level_refactorings_count(level: int, dataset: str = "") -> str:
    return "SELECT refactoring, count(*) FROM (" + \
           get_instance_fields(refactoringCommits, [(refactoringCommits, ["refactoring"])],
                               refactoringCommits + ".level = " + str(level), dataset) + \
           ") t group by refactoring order by count(*) desc"


# get all refactoring instances with the given refactoring type and metrics in regard to the level
def get_level_refactorings(level: int, m_refactoring: str, dataset: str = "") -> str:
    return __get_level(refactoringCommits, level, m_refactoring, dataset)


# get all refactoring instances with the given level and the corresponding metrics
def get_all_level_refactorings(level: int, dataset: str = "") -> str:
    return __get_level(refactoringCommits, level, "", dataset)


# get all stable instances with the given level and the corresponding metrics
def get_all_level_stable(level: int, dataset: str = "") -> str:
    return __get_level(stableCommits, level, "", dataset)


# get all unique refactoring types as a list
# Optional dataset: filter to this specific project
def get_refactoring_types(dataset: str = "") -> str:
    return ""
    # TODO: implement
# endregion
