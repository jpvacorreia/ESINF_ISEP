import java.io.*;
import java.util.*;

public class ReadData implements Serializable{

    /**
     * Lê todas as linhas de um ficheiro e organiza-o num HashMap
     * @param filename
     * @return m (HashMap com todos os dados do ficheiro)
     */
    public static Map<Country, ArrayList<DayData>> readfile(String filename) {
        Map<Country, ArrayList<DayData> > m = new HashMap<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String siglaAux = "";
        Country country = null;
        ArrayList<DayData> listDayData;
        sc.nextLine();
        while(sc.hasNextLine()){
            String line =  sc.nextLine();
            String[] list = line.split(",");
            if (!siglaAux.equals(list[0])) {
                country = new Country(list[0].replace("\"",""), list[1].replace("\"",""), list[2].replace("\"",""), list[10], list[11], list[12], list[13], list[14], list[15], list[16], list[17]);
                siglaAux = list[0];
                listDayData = new ArrayList<>();
                m.put(country,listDayData);
            }
            DayData dayData = new DayData(list[3],list[4],list[5],list[6],list[7],list[8],list[9]);
            listDayData = m.get(country);
            listDayData.add(dayData);
        }
        return m;
    }

}
