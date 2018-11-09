import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileProcess {
    public static String FILEPREFIX = "copy_";
    public static String DATASET1="dataset1";
    public static String DATASET2="dataset2";


    private int attributeNumber;
    private String fileName;
    private String filePath;
    private StringBuilder header;

    public FileProcess(int attributeNumber, String path, String name)
    {
        this.attributeNumber = attributeNumber;
        this.filePath = path;
        this.fileName = name;
    }

    private void getHeader(String dataset)
    {
        header = new StringBuilder("@relation " + FILEPREFIX + fileName + "\n\n");

        for (int i = 1; i < attributeNumber; i++)
        {
            String attributeInfo = "@attribute " + i + " numeric\n";
            header.append(attributeInfo);
        }

        StringBuilder headerContent = new StringBuilder();
        if(dataset.equals(DATASET1)) {
            headerContent.append("{");
            for (int i = 0; i < 50; i++) {
                headerContent.append(i);
                headerContent.append(",");
            }
            headerContent.append(50);
            headerContent.append("}");
        }
        else if(dataset.equals(DATASET2))
        {
            headerContent.append("{");
            for (int i = 0; i < 9; i++) {
                headerContent.append(i);
                headerContent.append(",");
            }
            headerContent.append(9);
            headerContent.append("}");
        }
        else
        {
            System.out.println("ERROR, NAME OF DATASET IS NOT VALID");
        }

        String classInfo = "@attribute " + attributeNumber + " " + headerContent + "\n";
        header.append(classInfo);
        header.append("\n@data\n");
    }

    public void generateArffFile(String dataset)
    {
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader(filePath + fileName + ".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<>();
        //IndexToSymbol indexToSymbol = new IndexToSymbol();
        String dataRow;

        try {
            dataRow = read.readLine();
            while (dataRow != null) {
                list.add(dataRow);
                dataRow = read.readLine();
            }

            FileWriter writer = new FileWriter(filePath + FILEPREFIX + fileName + ".arff");
            getHeader(dataset);
            writer.append(header);

            for (String row : list)
            {
                writer.append(System.getProperty("line.separator"));
                //List<String> buffer = Arrays.asList(row.split(","));
                //buffer.set(buffer.size() - 1, indexToSymbol.intToLetter(Integer.parseInt(buffer.get(buffer.size() - 1))));
                //writer.append(buffer.toString().replace("[", "").replace("]", ""));
                writer.append(row);
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Instances loadDataFromFile() {

        ArffLoader loader = new ArffLoader();
        Instances data = null;
        try {
            loader.setSource(new File(filePath + FILEPREFIX + fileName + ".arff"));
            data = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        return data;
    }

}
