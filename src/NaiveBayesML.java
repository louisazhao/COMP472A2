import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;


public class NaiveBayesML {
    private float smoothParameter;
    private NaiveBayes naiveBayes;
    private Evaluation evaluation;
    private Instances trainingSet;
    private Instances validationSet;

    public NaiveBayesML(FileProcess trainingData, FileProcess validationData)
    {
        setTrainingSet(trainingData);
        setValidationSet(validationData);
    }

    public void setSmoothParameter(float smoothPara) {
        this.smoothParameter = smoothPara;
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
        naiveBayes = new NaiveBayes();
        try {
            naiveBayes.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void evaluateModelWithValidationSet() {
        if (naiveBayes != null) {
            try {
                evaluation = new Evaluation(trainingSet);
                evaluation.evaluateModel(naiveBayes, validationSet);
                System.out.println(evaluation.toSummaryString("\nResults [Naive Based]\n\n", false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO add function to classify new data without label

}
