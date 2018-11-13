import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class MultilayerPerceptronML extends MachineLearningModel {
    private MultilayerPerceptron multilayerPerceptron;

    public MultilayerPerceptronML(FileProcess trainingData, FileProcess validationData, FileProcess testData) {
        super(trainingData, validationData, testData);
    }


    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        multilayerPerceptron = new MultilayerPerceptron();
        multilayerPerceptron.setTrainingTime(1000);
        multilayerPerceptron.setHiddenLayers("300");
        multilayerPerceptron.setLearningRate(0.1);
        multilayerPerceptron.setNormalizeNumericClass(false);
        multilayerPerceptron.setAutoBuild(false);
        multilayerPerceptron.setValidationSetSize(10);

        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(getTrainingSet());

            multilayerPerceptron.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, multilayerPerceptron);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
