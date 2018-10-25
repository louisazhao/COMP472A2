public class Main {

    public static void main(String[] args) {

        FileProcess trainingData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Train");
        trainingData1.generateArffFile();

        FileProcess validationData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Val");
        validationData1.generateArffFile();

        NaiveBayesML naiveBayesData1 = new NaiveBayesML(trainingData1, validationData1);
        naiveBayesData1.trainModel();
        naiveBayesData1.evaluateModelWithValidationSet();

        DecisionTree decisionTreeData1 = new DecisionTree(trainingData1, validationData1);
        decisionTreeData1.trainModel();
        decisionTreeData1.evaluateModelWithValidationSet();
    }
}
