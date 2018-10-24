public class Main {

    public static String FILEPREFIX = "copy_";

    public static void main(String[] args) {

        FileProcess trainningData1 = new FileProcess(1025);
        trainningData1.AddAttributeLineToCSV("DataSet-Release 1/ds1/", "ds1Train.csv");

        FileProcess validationData1 = new FileProcess(1025);
        trainningData1.AddAttributeLineToCSV("DataSet-Release 1/ds1/", "ds1Val.csv");


        NaiveBayesML naiveBayesML = new NaiveBayesML();
        naiveBayesML.setTrainningSet("DataSet-Release 1/ds1/", FILEPREFIX + "ds1Train.csv");
        naiveBayesML.setValidationSet("DataSet-Release 1/ds1/", FILEPREFIX + "ds1Val.csv");

        naiveBayesML.trainModel();
        naiveBayesML.evaluateModelWithValidationSet();


    }
}
