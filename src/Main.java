public class Main {

    public static void main(String[] args) {

        FileProcess trainingData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Train");
        trainingData1.generateArffFile();

        FileProcess validationData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Val");
        validationData1.generateArffFile();

        NaiveBayesML naiveBayesData1 = new NaiveBayesML(trainingData1, validationData1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //naiveBayesData1.trainAndSaveModel("DataSet-Release 1/ds1/naiveBayes1.model");
        naiveBayesData1.evaluateModelWithValidationSet("DataSet-Release 1/ds1/naiveBayes1.model","DataSet-Release 1/ds1/naiveBayes1Prediction.csv");

        DecisionTree decisionTreeData1 = new DecisionTree(trainingData1, validationData1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //decisionTreeData1.trainAndSaveModel("DataSet-Release 1/ds1/decisionTree1.model");
        decisionTreeData1.evaluateModelWithValidationSet("DataSet-Release 1/ds1/decisionTree1.model","DataSet-Release 1/ds1/decisionTree1Prediction.csv");
    }
}
