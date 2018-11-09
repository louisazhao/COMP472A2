import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.InMemory;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.FileWriter;


public class DecisionTree {

    private J48 decisionTree;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;
    private NumericToBinary numericToBinaryFilter;
    private NumericToNominal numericToNominalFilter;


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
        numericToNominalFilter = new NumericToNominal();
    }


    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        decisionTree = new J48();
        //TODO call set functions to change hyper-parameters, may need another function if 2 dataset using different hyper-parameters
        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(trainingSet);

            decisionTree.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, decisionTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param modelFile
     * @param predictionOutput The evaluate function will read the saved model, do the evaluation, and then save the predictions in a csv file
     */
    public void evaluateModelWithValidationSet(String modelFile, String predictionOutput) {
        try {
            Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelFile);
            if (classifier != null) {
                Instances filteredValidationSet = applyTwoFiltersOnDataSet(validationSet);
                evaluation = new Evaluation(filteredValidationSet);
                StringBuffer buffer = new StringBuffer();
                InMemory store = new InMemory();
                store.setBuffer(buffer);
                store.setHeader(filteredValidationSet);
                store.setAttributes("1");
                store.printHeader();
                evaluation.evaluateModel(classifier, filteredValidationSet, store);


                FileWriter writer = new FileWriter(predictionOutput);

                writer.append("instance");
                writer.append(',');
                writer.append("predictedAs");
                writer.append('\n');
                int i = 1;
                for (InMemory.PredictionContainer cont : store.getPredictions()) {
                    writer.append(String.valueOf(i));
                    i++;
                    writer.append(',');
                    writer.append(String.valueOf(cont.prediction.predicted()));
                    writer.append('\n');
                }
                writer.flush();
                writer.close();
                System.out.println(evaluation.toSummaryString("\nResults [Decision Tree]\n\n", false));
                System.out.println(evaluation.toMatrixString()); //confusion matrix, will be useful for report
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Instances applyTwoFiltersOnDataSet(Instances originalData) {
        Instances temp = null;
        Instances result = null;
        try {
            numericToBinaryFilter.setInputFormat(originalData);
            String[] options = new String[2];
            options[0] = "-R";
            options[1] = "first-1024";
            numericToBinaryFilter.setOptions(options);
            //filter Debug
            /*
            String[] deBugOptions = numericToBinaryFilter.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.print(deBugOptions[i] + " ");
            }
            System.out.println();
            */

            temp = Filter.useFilter(originalData, numericToBinaryFilter);

            numericToNominalFilter.setInputFormat(temp);
            options = new String[2];
            options[0] = "-R";
            options[1] = "1025";
            numericToNominalFilter.setOptions(options);

            result = Filter.useFilter(temp, numericToNominalFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//TODO add function to classify new data without label

}
