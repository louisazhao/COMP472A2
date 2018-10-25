import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileProcess {
    public static String FILEPREFIX = "copy_";

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

    private void getHeader()
    {
        header = new StringBuilder("@relation " + FILEPREFIX + fileName + "\n\n");

        for (int i = 1; i < attributeNumber; i++)
        {
            String attributeInfo = "@attribute " + i + " numeric\n";
            header.append(attributeInfo);
        }
        String classAttribute = "{\'A\',\'B\',\'C\',\'D\',\'E\',\'F\',\'G\',\'H\',\'I\',\'J\',\'K\',\'L\',\'M\',\'N\',\'O\',\'P\',\'Q\',\'R\',\'S\',\'T\',\'U\',\'V\',\'W\',\'X\',\'Y\',\'Z\',\'a\',\'b\',\'c\',\'d\',\'e\',\'f\',\'g\',\'h\',\'i\',\'j\',\'k\',\'l\',\'m\',\'n\',\'o\',\'p\',\'q\',\'r\',\'s\',\'u\',\'v\',\'w\',\'x\',\'y\',\'z\'}";
        String classInfo = "@attribute " + attributeNumber + " " + classAttribute + "\n";
        header.append(classInfo);
        header.append("\n@data\n");
    }

    public void generateArffFile()
    {
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader(filePath + fileName + ".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<>();
        IndexToSymbol indexToSymbol = new IndexToSymbol();
        String dataRow;

        try {
            dataRow = read.readLine();
            while (dataRow != null) {
                list.add(dataRow);
                dataRow = read.readLine();
            }

            FileWriter writer = new FileWriter(filePath + FILEPREFIX + fileName + ".arff");
            getHeader();
            writer.append(header);

            for (String row : list)
            {
                writer.append(System.getProperty("line.separator"));
                List<String> buffer = Arrays.asList(row.split(","));
                buffer.set(buffer.size() - 1, indexToSymbol.intToLetter(Integer.parseInt(buffer.get(buffer.size() - 1))));
                writer.append(buffer.toString().replace("[", "").replace("]", ""));
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
