import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IndexToSymbol {

    private HashMap<Integer, String> symbolMap;


    public IndexToSymbol() {
        symbolMap=new HashMap<>();
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader("DataSet-Release 1/ds1/ds1Info.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String dataRow = null;

        try {
            String discarded=read.readLine();
            dataRow = read.readLine();
            while (dataRow != null) {
                List<String> buffer = Arrays.asList(dataRow.split(","));
                symbolMap.put(Integer.parseInt(buffer.get(0)), buffer.get(1));
                dataRow=read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, String> getSymbolMap() {
        return symbolMap;
    }

    public String intToLetter(int i)
    {
        return symbolMap.get(i);
    }
}
