import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class NaiveBayesML extends MachineLearningModel {
    private NaiveBayes naiveBayes;

    public NaiveBayesML(FileProcess trainingData, FileProcess validationData, FileProcess testData) {
        super(trainingData, validationData, testData);
    }

    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        naiveBayes = new NaiveBayes();
        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(getTrainingSet());

            naiveBayes.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, naiveBayes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
