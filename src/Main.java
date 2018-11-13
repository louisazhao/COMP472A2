public class Main {

    public static void main(String[] args) {

        /********        dataset1      ******/


        FileProcess trainingData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Train");
        trainingData1.generateArffFile(FileProcess.DATASET1);

        FileProcess validationData1 = new FileProcess(1025, "DataSet-Release 1/ds1/", "ds1Val");
        validationData1.generateArffFile(FileProcess.DATASET1);

        FileProcess testSet1=new FileProcess(1025,"DataSet-Release 1/ds1/","ds1Test");
        testSet1.generateArffFileForTestSet(FileProcess.DATASET1);


        NaiveBayesML naiveBayesData1 = new NaiveBayesML(trainingData1, validationData1,testSet1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //naiveBayesData1.trainAndSaveModel("OutputFiles/ds1/naiveBayes1.model");
        naiveBayesData1.evaluateModelWithValidationSet("OutputFiles/ds1/naiveBayes1.model", "OutputFiles/ds1/ds1Val-nb.csv");
        naiveBayesData1.classifyNewData("OutputFiles/ds1/naiveBayes1.model", "OutputFiles/ds1/ds1Test-nb.csv");



        DecisionTree decisionTreeData1 = new DecisionTree(trainingData1, validationData1,testSet1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //decisionTreeData1.trainAndSaveModel("OutputFiles/ds1/decisionTree1.model");
        decisionTreeData1.evaluateModelWithValidationSet("OutputFiles/ds1/decisionTree1.model", "OutputFiles/ds1/ds1Val-dt.csv");
        decisionTreeData1.classifyNewData("OutputFiles/ds1/decisionTree1.model", "OutputFiles/ds1/ds1Test-dt.csv");

        MultilayerPerceptronML multilayerPerceptronML1=new MultilayerPerceptronML(trainingData1,validationData1,testSet1);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //multilayerPerceptronML1.trainAndSaveModel("OutputFiles/ds1/multilayerPerceptron1.model");
        multilayerPerceptronML1.evaluateModelWithValidationSet("OutputFiles/ds1/multilayerPerceptron1.model","OutputFiles/ds1/ds1Val-3.csv");
        multilayerPerceptronML1.classifyNewData("OutputFiles/ds1/multilayerPerceptron1.model","OutputFiles/ds1/ds1Test-3.csv");


        /********        dataset2         ******/

        FileProcess trainingData2 = new FileProcess(1025, "DataSet-Release 1/ds2/", "ds2Train");
        trainingData2.generateArffFile(FileProcess.DATASET2);

        FileProcess validationData2 = new FileProcess(1025, "DataSet-Release 1/ds2/", "ds2Val");
        validationData2.generateArffFile(FileProcess.DATASET2);

        FileProcess testSet2=new FileProcess(1025,"DataSet-Release 1/ds2/","ds2Test");
        testSet2.generateArffFileForTestSet(FileProcess.DATASET2);

        NaiveBayesML naiveBayesData2 = new NaiveBayesML(trainingData2, validationData2,testSet2);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //naiveBayesData2.trainAndSaveModel("OutputFiles/ds2/naiveBayes2.model");
        naiveBayesData2.evaluateModelWithValidationSet("OutputFiles/ds2/naiveBayes2.model", "OutputFiles/ds2/ds2Val-nb.csv");
        naiveBayesData2.classifyNewData("OutputFiles/ds2/naiveBayes2.model", "OutputFiles/ds2/ds2Test-nb.csv");


        DecisionTree decisionTreeData2 = new DecisionTree(trainingData2, validationData2,testSet2);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //decisionTreeData2.trainAndSaveModel("OutputFiles/ds2/decisionTree2.model");
        decisionTreeData2.evaluateModelWithValidationSet("OutputFiles/ds2/decisionTree2.model", "OutputFiles/ds2/ds2Val-dt.csv");
        decisionTreeData2.classifyNewData("OutputFiles/ds2/decisionTree2.model", "OutputFiles/ds2/ds2Test-dt.csv");


        MultilayerPerceptronML multilayerPerceptronML2=new MultilayerPerceptronML(trainingData2,validationData2,testSet2);
        //comment out the following line to see the saved model actually works, you need to run the line at least once
        //multilayerPerceptronML2.trainAndSaveModel("OutputFiles/ds2/multilayerPerceptron2.model");
        multilayerPerceptronML2.evaluateModelWithValidationSet("OutputFiles/ds2/multilayerPerceptron2.model","OutputFiles/ds2/ds2Val-3.csv");
        multilayerPerceptronML2.classifyNewData("OutputFiles/ds2/multilayerPerceptron2.model","OutputFiles/ds2/ds2Test-3.csv");
    }
}
