import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;


public class NaiveBayesML {
    private float smoothPara;
    private NaiveBayes naiveBayes;
    private Evaluation evaluation;
    private Instances trainningSet;
    private Instances validationSet;
    private Instances testSet;

    public NaiveBayesML() {

    }

    public void setSmoothPara(float smoothPara) {
        this.smoothPara = smoothPara;
    }

    public void setTrainningSet(String pathName, String fileName) {
        this.trainningSet = loadDataFromFile(pathName, fileName);
    }

    public void setValidationSet(String pathName, String fileName) {
        this.validationSet = loadDataFromFile(pathName, fileName);
    }

    public void setTestSet(String pathName, String fileName) {
        this.testSet = loadDataFromFile(pathName, fileName);
    }

    public void trainModel() {
        naiveBayes = new NaiveBayes();
        try {
            naiveBayes.buildClassifier(trainningSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void evaluateModelWithValidationSet() {
        if (naiveBayes != null) {
            try {
                evaluation = new Evaluation(trainningSet);
                evaluation.evaluateModel(naiveBayes, validationSet);
                System.out.println(evaluation.toSummaryString("\nResults\n\n", false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO add function to classify new data without label


    private Instances loadDataFromFile(String pathName, String fileName) {
        CSVLoader loader = new CSVLoader();
        Instances data = null;
        try {
            loader.setSource(new File(pathName + fileName));
            data = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        return data;
    }


}
