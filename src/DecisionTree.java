import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class DecisionTree {

    private J48 decisionTree;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;
    private Instances testSet;

    public DecisionTree(FileProcess trainingData, FileProcess validationData)
    {
        setTrainingSet(trainingData);
        setValidationSet(validationData);
    }

    public void setTrainingSet(FileProcess fileProcess)
    {
        trainingSet = fileProcess.loadDataFromFile();
    }

    public void setValidationSet(FileProcess fileProcess)
    {
        validationSet = fileProcess.loadDataFromFile();
    }

    public void trainModel() {
        decisionTree = new J48();
        try {
            decisionTree.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluateModelWithValidationSet() {
        if (decisionTree != null) {
            try {
                evaluation = new Evaluation(trainingSet);
                evaluation.evaluateModel(decisionTree, validationSet);
                System.out.println(evaluation.toSummaryString("\nResults [Decision Tree]\n\n", false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO add function to classify new data without label

}
