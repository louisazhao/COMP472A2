import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class DecisionTree extends MachineLearningModel {

    private J48 decisionTree;

    public DecisionTree(FileProcess trainingData, FileProcess validationData, FileProcess testSet) {
        super(trainingData, validationData, testSet);
    }

    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName) {
        decisionTree = new J48();
        //TODO call set functions to change hyper-parameters, may need another function if 2 dataset using different hyper-parameters
        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(getTrainingSet());

            decisionTree.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, decisionTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
