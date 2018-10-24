import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileProcess {

    private int attributeNumber;

    public FileProcess(int attributeNumber) {
        this.attributeNumber = attributeNumber;
    }

    public void AddAttributeLineToCSV(String pathName, String fileName) {
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader(pathName + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList();
        String dataRow = null;
        StringBuffer AttributeName = new StringBuffer();
        for (int i = 1; i < attributeNumber; i++) {
            AttributeName.append(i);
            AttributeName.append(",");
        }
        AttributeName.append(attributeNumber);

        IndexToSymbol indexToSymbol = new IndexToSymbol();

        try {
            dataRow = read.readLine();
            while (dataRow != null) {
                list.add(dataRow);
                dataRow = read.readLine();
            }

            FileWriter writer = null;
            writer = new FileWriter(pathName + "copy_" + fileName);

            writer.append(AttributeName.toString());

            for (int i = 0; i < list.size(); i++) {
                writer.append(System.getProperty("line.separator"));
                List<String> buffer = Arrays.asList(list.get(i).split(","));
                buffer.set(buffer.size() - 1, indexToSymbol.intToLetter(Integer.parseInt(buffer.get(buffer.size() - 1))));
                writer.append(buffer.toString().replace("[", "").replace("]", ""));
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
