import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.classifiers.evaluation.output.prediction.CSV;


public class DecisionTree {

    private J48 decisionTree;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;
    private NumericToBinary numericToBinaryFilter;


    public DecisionTree(FileProcess trainingData, FileProcess validationData) {
        setTrainingSet(trainingData);
        setValidationSet(validationData);
        setFilter();
    }

    public void setTrainingSet(FileProcess fileProcess) {
        trainingSet = fileProcess.loadDataFromFile();
    }

    public void setValidationSet(FileProcess fileProcess) {
        validationSet = fileProcess.loadDataFromFile();
    }

    public void setTestSet(FileProcess fileProcess) {
        testSet = fileProcess.loadDataFromFile();
    }

    public void setFilter() {
        numericToBinaryFilter = new NumericToBinary();
    }

    /**
     *
     * @param fileName
     * The function will train the model using the training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        decisionTree = new J48();
        try {
            numericToBinaryFilter.setInputFormat(trainingSet);
            Instances filteredTrainingSet = Filter.useFilter(trainingSet, numericToBinaryFilter);
            decisionTree.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, decisionTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param modelFile
     * @param predictionOutput
     * The evaluate function will read the saved model, do the evaluation, and then save the predictions in a csv file
     */
    public void evaluateModelWithValidationSet(String modelFile,String predictionOutput) {
        try {
            Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelFile);
            if (classifier != null) {
                numericToBinaryFilter.setInputFormat(validationSet);
                Instances filteredValidationSet = Filter.useFilter(validationSet, numericToBinaryFilter);
                evaluation = new Evaluation(filteredValidationSet);
                StringBuffer buffer = new StringBuffer();
                CSV csv = new CSV();
                csv.setBuffer(buffer);
                csv.setHeader(new Instances(filteredValidationSet,0));
                csv.setOutputFile(new java.io.File(predictionOutput));
                csv.printHeader();
                evaluation.evaluateModel(classifier, filteredValidationSet,csv);
                csv.printFooter();
                System.out.println(evaluation.toSummaryString("\nResults [Decision Tree]\n\n", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//TODO add function to classify new data without label

}
