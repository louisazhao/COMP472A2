import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.InMemory;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.FileWriter;

public class MachineLearningModel {
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;
    private NumericToBinary numericToBinaryFilter;
    private NumericToNominal numericToNominalFilter;

    public MachineLearningModel(FileProcess trainingData, FileProcess validationData, FileProcess testData) {
        setFilter();
        setTrainingSet(trainingData);
        setValidationSet(validationData);
        setTestSet(testData);
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

    public Instances getTrainingSet() {
        return trainingSet;
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

                System.out.println(evaluation.toSummaryString("\nResults for " + this.getClass() + "\n", false));
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


    public void classifyNewData(String modelFile, String predictionOutput) {
        try {
            Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelFile);
            if (classifier != null) {

                Instances testSetCopy = new Instances(testSet);


                Instances filteredTestSet = applyTwoFiltersOnDataSet(testSetCopy);

                for (int i = 0; i < filteredTestSet.numInstances(); i++) {
                    double clsLabel = classifier.classifyInstance(filteredTestSet.instance(i));
                    filteredTestSet.instance(i).setClassValue(clsLabel);
                }


                FileWriter writer = new FileWriter(predictionOutput);

                writer.append("instance");
                writer.append(',');
                writer.append("classifiedAs");
                writer.append('\n');
                for (int i = 0; i < filteredTestSet.numInstances(); i++) {
                    writer.append(String.valueOf(i + 1));
                    writer.append(',');
                    writer.append(String.valueOf((int) (filteredTestSet.instance(i).classValue())));
                    writer.append('\n');
                }
                writer.flush();
                writer.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Instances applyTwoFiltersOnDataSet(Instances originalData) {
        Instances temp = null;
        Instances result = null;
        try {
            numericToBinaryFilter.setInputFormat(originalData);
            String[] options = new String[2];
            options[0] = "-R";
            options[1] = "first-1024";
            numericToBinaryFilter.setOptions(options);

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
}
