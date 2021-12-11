
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReadDataBigTest {

    ReadData rd = new ReadData();

    public ReadDataBigTest() {
        readfileCountries();
        readfileUser();
        readfileCountriesToMap();
        readfileUsersToMatrix();
    }

    @Test
    public void readfileCountries() {
        rd.readfileCountries("bcountries.txt");
    }

    @Test
    public void readfileUser() {
        rd.readfileUser("busers.txt");
    }

    @Test
    public void readfileCountriesToMap() {
        rd.readfileCountriesToMap("bborders.txt");
    }

    @Test
    public void readfileUsersToMatrix() {
        rd.readfileUsersToMatrix("brelationships.txt");
    }


    @Test
    public void getCountriesListByStringBig() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("argentina");
        expected.add("bolivia");
        expected.add("brasil");
        expected.add("chile");
        expected.add("colombia");
        expected.add("equador");
        expected.add("guiana");
        expected.add("guianafrancesa");
        expected.add("paraguai");
        expected.add("peru");
        expected.add("suriname");
        expected.add("venezuela");
        expected.add("uruguai");
        expected.add("albania");
        expected.add("alemanha");
        expected.add("armenia");
        expected.add("austria");
        expected.add("belgica");
        expected.add("bielorussia");
        expected.add("bosnia");
        expected.add("bulgaria");
        expected.add("chipre");
        expected.add("croacia");
        expected.add("dinamarca");
        expected.add("eslovaquia");
        expected.add("eslovenia");
        expected.add("espanha");
        expected.add("estonia");
        expected.add("finlandia");
        expected.add("franca");
        expected.add("georgia");
        expected.add("grecia");
        expected.add("holanda");
        expected.add("hungria");
        expected.add("irlanda");
        expected.add("islandia");
        expected.add("italia");
        expected.add("kosovo");
        expected.add("letonia");
        expected.add("liechtenstein");
        expected.add("lituania");
        expected.add("luxemburgo");
        expected.add("macedonia");
        expected.add("malta");
        expected.add("moldavia");
        expected.add("monaco");
        expected.add("montenegro");
        expected.add("noruega");
        expected.add("polonia");
        expected.add("portugal");
        expected.add("reinounido");
        expected.add("republicacheca");
        expected.add("romenia");
        expected.add("russia");
        expected.add("servia");
        expected.add("suecia");
        expected.add("suica");
        expected.add("turquia");
        expected.add("ucrania");
        assertIterableEquals(expected, rd.getCountriesListByString());
    }

    @Test
    public void getRelationsFromMostPopularUsers() {
        Map<String, Double> expected = new LinkedHashMap<>();
        AdjacencyMatrixGraph<String, Double> clone = rd.getRelationsAdjMatrixGraph();
        expected.put("u709", 248.0);
        expected.put("u90", 203.0);
        expected.put("u223", 194.0);
        assertIterableEquals(expected.keySet(), rd.getRelationsFromMostPopularUsers(clone, 3).keySet());
    }

    @Test
    public void commonFriends() {
        ArrayList<String> expected = new ArrayList<>();
        AdjacencyMatrixGraph<String, Double> clone = (AdjacencyMatrixGraph<String, Double>) rd.relationsAdjMatrixGraph.clone();
        expected.add("u1");
        expected.add("u146");
        expected.add("u487");
        expected.add("u534");
        expected.add("u559");
        expected.add("u591");
        expected.add("u687");
        expected.add("u691");
        assertIterableEquals(expected, rd.commonFriends(clone, 5));

        expected.clear();
        expected.add("u487");
        expected.add("u591");
        assertIterableEquals(expected, rd.commonFriends(clone, 6));
    }

    @Test
    public void isConnected() {
        AdjacencyMatrixGraph<String, Double> clone = (AdjacencyMatrixGraph<String, Double>) rd.relationsAdjMatrixGraph.clone();
        assertFalse(rd.isConnected(clone));
    }

    @Test
    public void minConnections() {
        //Adjacency Matrix Graph is not connected
        double expected = -1.0;
        AdjacencyMatrixGraph<String, Double> clone = (AdjacencyMatrixGraph<String, Double>) rd.relationsAdjMatrixGraph.clone();
        assertEquals(expected, rd.minConnections(clone), 0.1);
    }

    @Test
    public void relativePercentage() {
        int max = 200;
        int n = 2;
        double expected = 1.0;
        assertEquals(expected, rd.relativePercentage(n, max), 0.1);
        n = 10;
        expected = 5;
        assertEquals(expected, rd.relativePercentage(n, max), 0.1);
        max = 50;
        expected = 20;
        assertEquals(expected, rd.relativePercentage(n, max), 0.1);
    }

    @Test
    public void greaterCentrality() {
        Graph<String, String> clone = (Graph<String, String>) rd.bordersMap.clone();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("reiquiavique");
        expected.add("valletta");
        expected.add("nicosia");
        expected.add("dublin");
        expected.add("londres");
        int n = 5;
        double percentage = 1.1;
        assertIterableEquals(expected, rd.greaterCentrality(clone, n, percentage));
        n = 2;
        percentage = 2.2;
        expected.clear();
        expected.add("valletta");
        expected.add("nicosia");
        assertIterableEquals(expected, rd.greaterCentrality(clone, n, percentage));
    }

    @Test
    public void getClosestFriends() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("georgia");
        ArrayList<String> result = rd.getCountriesWithinNBorders(rd.getBordersMap(), "u1", 0);
        assertIterableEquals(expected, result);
        expected.clear();
        expected.add("bolivia");
        expected.add("argentina");
        expected.add("brasil");
        expected.add("chile");
        expected.add("paraguai");
        expected.add("peru");
        result = rd.getCountriesWithinNBorders(rd.getBordersMap(), "u691", 1);
        assertIterableEquals(expected, result);
    }

    @Test
    public void getFriendsInCities() {
        String strU1 = "u597";
        ArrayList<String> aux = new ArrayList<>();
        aux.add("argentina");
        aux.add("bolivia");
        aux.add("brasil");
        aux.add("chile");
        aux.add("colombia");
        aux.add("equador");
        aux.add("guiana");
        aux.add("guianafrancesa");
        aux.add("paraguai");
        aux.add("peru");
        aux.add("suriname");
        aux.add("venezuela");
        aux.add("uruguai");
        aux.add("islandia");
        aux.add("italia");
        aux.add("macedonia");
        Map<Country, ArrayList<User>> result = rd.getFriendsInCities(aux, strU1);
        ArrayList<String> countriesRes = new ArrayList<>();
        ArrayList<String> usersRes = new ArrayList<>();
        for (Country c : result.keySet()) {
            countriesRes.add(c.getCountryName());
            for (User u : result.get(c)) {
                usersRes.add(u.getUserName());
            }
        }
        Map<Country, ArrayList<User>> expected = new LinkedHashMap<>();
        ArrayList<User> auxList1 = new ArrayList<>();
        ArrayList<User> auxList2 = new ArrayList<>();
        ArrayList<User> auxList3 = new ArrayList<>();
        auxList1.add(rd.getUserByName("u336"));
        auxList2.add(rd.getUserByName("u414"));
        auxList3.add(rd.getUserByName("u430"));
        expected.put(rd.getCountryByName("islandia"), auxList1);
        expected.put(rd.getCountryByName("italia"), auxList2);
        expected.put(rd.getCountryByName("macedonia"), auxList3);

        ArrayList<String> countriesExp = new ArrayList<>();
        ArrayList<String> usersExp = new ArrayList<>();
        for (Country c : expected.keySet()) {
            countriesExp.add(c.getCountryName());
            for (User u : expected.get(c)) {
                usersExp.add(u.getUserName());
            }
        }
        assertIterableEquals(countriesExp, countriesRes);
        assertIterableEquals(usersExp, usersRes);
    }

    @Test
    public void orderByValues() {
        Map<String, Double> aux = new HashMap<>();
        aux.put("d", 2.0);
        aux.put("c", 3.0);
        aux.put("e", 1.0);
        aux.put("a", 5.0);
        aux.put("b", 4.0);
        Map<String, Double> entries = new LinkedHashMap<>();
        entries.put("a", 5.0);
        entries.put("b", 4.0);
        entries.put("c", 3.0);
        entries.put("d", 2.0);
        entries.put("e", 1.0);

        List<Map.Entry<String, Double>> expected = new LinkedList<Map.Entry<String, Double>>(entries.entrySet());
        assertIterableEquals(expected, rd.orderByValues(aux, 2));
    }

    @Test
    public void getCountriesWithMoreFriends() {
        String friend1 = "u8";
        String friend2 = "u594";
        int n = 1;
        LinkedList<String> expectedList = new LinkedList<>();
        expectedList.add("oslo");
        expectedList.add("moscovo");
        expectedList.add("kiev");
        expectedList.add("budapeste");
        expectedList.add("zagreb");
        expectedList.add("liubliana");
        expectedList.add("viena");
        expectedList.add("berna");
        expectedList.add("berlim");
        expectedList.add("copenhaga");
        double expectedDouble = 6220.51;
        LinkedList<String> path = new LinkedList<>();
        double resDouble = rd.getCountriesWithMoreFriends(rd.getCountriesListByString(),friend1,friend2,n,path);
        LinkedList<String> resList = path;
        assertEquals(expectedDouble,resDouble,0.1);
        assertIterableEquals(expectedList,resList);
        friend1 = "u8";
        friend2 = "u9";
        expectedList.clear();
        expectedList.add("Impossible Land Path");
        expectedDouble = -1.00;
        path.clear();
        resList.clear();
        resDouble = rd.getCountriesWithMoreFriends(rd.getCountriesListByString(),friend1,friend2,n,path);
        resList = path;
        assertEquals(expectedDouble,resDouble,0.1);
        assertIterableEquals(expectedList,resList);
    }
}
