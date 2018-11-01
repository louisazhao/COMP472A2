import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.classifiers.evaluation.output.prediction.CSV;


public class NaiveBayesML {
    private float smoothParameter;
    private NaiveBayes naiveBayes;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;
    private NumericToBinary numericToBinaryFilter;

    public NaiveBayesML(FileProcess trainingData, FileProcess validationData) {
        setFilter();
        setTrainingSet(trainingData);
        setValidationSet(validationData);
    }

    public void setSmoothParameter(float smoothPara) {
        this.smoothParameter = smoothPara;
    }

    public void setFilter() {
        numericToBinaryFilter = new NumericToBinary();
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
     *
     * @param fileName
     * The function will train the model using the training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        naiveBayes = new NaiveBayes();
        try {
            numericToBinaryFilter.setInputFormat(trainingSet);
            /* filter debug line
            String[] options=numericToBinaryFilter.getOptions();
            for(int i=0;i<options.length;i++)
            {
                System.out.print(options[i]+" ");
            }
            System.out.println();
            */
            Instances filteredTrainingSet = Filter.useFilter(trainingSet, numericToBinaryFilter);
            naiveBayes.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, naiveBayes);
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
                System.out.println(evaluation.toSummaryString("\nResults [Naive Based]\n\n", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//TODO add function to classify new data without label

}
