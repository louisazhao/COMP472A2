import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class DecisionTreeJ48 extends MachineLearningModel {

    private J48 decisionTree;

    public DecisionTreeJ48(FileProcess trainingData, FileProcess validationData, FileProcess testSet) {
        super(trainingData, validationData, testSet);
    }

    /**
     * @param fileName The function will train the model using the filtered training set, and then save the trained model into a .model file
     */
    public void trainAndSaveModel(String fileName, float confidenceFactor, int minNumObj) {
        decisionTree = new J48();
        decisionTree.setConfidenceFactor(confidenceFactor);
        decisionTree.setMinNumObj(minNumObj);
        try {
            Instances filteredTrainingSet = applyTwoFiltersOnDataSet(getTrainingSet());
            decisionTree.buildClassifier(filteredTrainingSet);
            SerializationHelper.write(fileName, decisionTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
