public class Main {

    public static void main(String[] args) {

        /********        dataset1      ******/


        FileProcess trainingData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Train");
        trainingData1.generateArffFile(FileProcess.DATASET1);

        FileProcess validationData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Val");
        validationData1.generateArffFile(FileProcess.DATASET1);


        NaiveBayesML naiveBayesData1 = new NaiveBayesML(trainingData1, validationData1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //naiveBayesData1.trainAndSaveModel("DataSet-Release 1/ds1/naiveBayes1.model");
        naiveBayesData1.evaluateModelWithValidationSet("DataSet-Release 1/ds1/naiveBayes1.model", "DataSet-Release 1/ds1/ds1Val-nb.csv");


        DecisionTree decisionTreeData1 = new DecisionTree(trainingData1, validationData1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //decisionTreeData1.trainAndSaveModel("DataSet-Release 1/ds1/decisionTree1.model");
        decisionTreeData1.evaluateModelWithValidationSet("DataSet-Release 1/ds1/decisionTree1.model", "DataSet-Release 1/ds1/ds1Val-dt.csv");



        /********        dataset2         ******/

        FileProcess trainingData2 = new FileProcess(1025, "DataSet-Release 1/ds2/", "ds2Train");
        trainingData2.generateArffFile(FileProcess.DATASET2);

        FileProcess validationData2 = new FileProcess(1025, "DataSet-Release 1/ds2/", "ds2Val");
        validationData2.generateArffFile(FileProcess.DATASET2);

        NaiveBayesML naiveBayesData2 = new NaiveBayesML(trainingData2, validationData2);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //naiveBayesData2.trainAndSaveModel("DataSet-Release 1/ds2/naiveBayes2.model");
        naiveBayesData2.evaluateModelWithValidationSet("DataSet-Release 1/ds2/naiveBayes2.model", "DataSet-Release 1/ds2/ds2Val-nb.csv");


        DecisionTree decisionTreeData2 = new DecisionTree(trainingData2, validationData2);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //decisionTreeData2.trainAndSaveModel("DataSet-Release 1/ds2/decisionTree2.model");
        decisionTreeData2.evaluateModelWithValidationSet("DataSet-Release 1/ds2/decisionTree2.model", "DataSet-Release 1/ds2/ds2Val-dt.csv");
    }
}
