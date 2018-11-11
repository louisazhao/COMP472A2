import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.output.prediction.InMemory;
import weka.classifiers.evaluation.output.prediction.InMemory.PredictionContainer;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.FileWriter;


public class NaiveBayesML {
    private NaiveBayes naiveBayes;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;
    private NumericToBinary numericToBinaryFilter;
    private NumericToNominal numericToNominalFilter;

    public NaiveBayesML(FileProcess trainingData, FileProcess validationData) {
        setFilter();
        setTrainingSet(trainingData);
        setValidationSet(validationData);
    }

    public void setFilter() {
        numericToBinaryFilter = new NumericToBinary();
        numericToNominalFilter = new NumericToNominal();
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

    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        naiveBayes = new NaiveBayes();
        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(trainingSet);

            naiveBayes.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, naiveBayes);
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

                /*Debug output
                int i = 0;
                for (PredictionContainer cont: store.getPredictions()) {
                    i++;
                    System.out.println("\nContainer #" + i);
                    //System.out.println("- instance:\n" + cont.instance);
                    System.out.println("- actual:\n" + cont.prediction.actual());
                    System.out.println("- prediction:\n" + cont.prediction.predicted());
                }
                */


                FileWriter writer = new FileWriter(predictionOutput);

                writer.append("instance");
                writer.append(',');
                writer.append("predictedAs");
                writer.append('\n');
                int i = 1;
                for (PredictionContainer cont : store.getPredictions()) {
                    writer.append(String.valueOf(i));
                    i++;
                    writer.append(',');
                    writer.append(String.valueOf(cont.prediction.predicted()));
                    writer.append('\n');
                }
                writer.flush();
                writer.close();

                System.out.println(evaluation.toSummaryString("\nResults [Naive Based]\n\n", false));
                System.out.println(evaluation.toMatrixString()); //confusion matrix, will be useful for report
                /*recall output
                for(int j=0;j<10;j++) {
                    System.out.println("class: "+j+ " "+evaluation.recall(j));
                }
                */
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
