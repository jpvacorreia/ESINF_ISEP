
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReadDataSmallTest {

    ReadData rd = new ReadData();
    ArrayList<User> usersList = new ArrayList<>();
    ArrayList<Country> countriesList = new ArrayList<>();
    AdjacencyMatrixGraph<String,Double> relationshipsGraph = new AdjacencyMatrixGraph<>();
    Graph<String,String> bordersGraph = new Graph<>(false);

    public ReadDataSmallTest(){
        readfileCountries();
        readfileUser();
        readfileCountriesToMap();
        readfileUsersToMatrix();
        usersList = rd.getUsersList();
        countriesList = rd.getCountriesList();
        relationshipsGraph = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        bordersGraph = rd.bordersMap.clone();
    }
    @Test
    public void readfileCountries() {
        rd.readfileCountries("scountries.txt");
    }

    @Test
    public void readfileUser() {
        rd.readfileUser("susers.txt");
    }

    @Test
    public void readfileCountriesToMap() {
        rd.readfileCountriesToMap("sborders.txt");
    }

    @Test
    public void readfileUsersToMatrix() {
        rd.readfileUsersToMatrix("srelationships.txt");
    }

    @Test
    public void getCountriesListByString() {
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
        assertIterableEquals(expected, rd.getCountriesListByString());
    }


    @Test
    public void getRelationsFromMostPopularUsers() {
        Map<String,Integer> expected = new HashMap<>();
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        expected.put("u33",11);
        expected.put("u1", 10);
        expected.put("u3",7);
        assertIterableEquals(expected.keySet(), rd.getRelationsFromMostPopularUsers(clone,3).keySet());
    }

    @Test
    public void commonFriends() {
        ArrayList<String> expected = new ArrayList<>();
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        expected.add("u9");
        assertIterableEquals(expected, rd.commonFriends(clone,3));
    }

    @Test
    public void isConnected() {
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        assertTrue(rd.isConnected(clone));
    }

    @Test
    public void minConnections() {
        double expected = 5.0;
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        assertEquals(expected, rd.minConnections(clone),0.1);
    }

    @Test
    public void relativePercentage() {
        int max = 200;
        int n = 2;
        double expected = 1.0;
        assertEquals(expected, rd.relativePercentage(n,max),0.1);
        n = 10;
        expected = 5;
        assertEquals(expected, rd.relativePercentage(n,max),0.1);
        max = 50;
        expected = 20;
        assertEquals(expected, rd.relativePercentage(n,max),0.1);
    }

    @Test
    public void greaterCentrality() {
        Graph<String,String> clone = (Graph<String,String>) rd.bordersMap.clone();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("brasilia");
        expected.add("lapaz");
        int n=2;
        double percentage = 0;
        assertIterableEquals(expected, rd.greaterCentrality(clone,n,percentage));
        n = 3;
        percentage = 1.1;
        expected.add("lima");
        assertIterableEquals(expected, rd.greaterCentrality(clone,n,percentage));
    }

    @Test
    public void getClosestFriends() {
        LinkedList<String> expected = new LinkedList<>();
        expected.add("brasil");
        ArrayList<String> result = rd.getCountriesWithinNBorders(rd.bordersMap ,"u1",0);
        assertIterableEquals(expected,result);
        expected.remove("brasil");
        expected.add("uruguai");
        expected.add("argentina");
        expected.add("brasil");
        result = rd.getCountriesWithinNBorders(rd.bordersMap ,"u7",1);
        assertIterableEquals(expected,result);
    }

    @Test
    public void getFriendsInCities() {
        String strU1 = "u2";
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
        Map<Country,ArrayList<User>> result = rd.getFriendsInCities(aux,strU1);
        ArrayList<String> countriesRes = new ArrayList<>();
        ArrayList<String> usersRes = new ArrayList<>();
        for (Country c: result.keySet()){
            countriesRes.add(c.getCountryName());
            for (User u : result.get(c)){
                usersRes.add(u.getUserName());
            }
        }
        Map<Country,ArrayList<User>> expected = new LinkedHashMap<>();
        ArrayList<User> auxList1 = new ArrayList<>();
        ArrayList<User> auxList2 = new ArrayList<>();
        ArrayList<User> auxList3 = new ArrayList<>();
        ArrayList<User> auxList4 = new ArrayList<>();
        auxList1.add(rd.getUserByName("u31"));
        auxList1.add(rd.getUserByName("u1"));
        auxList2.add(rd.getUserByName("u20"));
        auxList2.add(rd.getUserByName("u3"));
        auxList3.add(rd.getUserByName("u4"));
        auxList4.add(rd.getUserByName("u14"));
        expected.put(rd.getCountryByName("argentina"),auxList1);
        expected.put(rd.getCountryByName("brasil"),auxList2);
        expected.put(rd.getCountryByName("equador"),auxList3);
        expected.put(rd.getCountryByName("suriname"),auxList4);

        ArrayList<String> countriesExp = new ArrayList<>();
        ArrayList<String> usersExp = new ArrayList<>();
        for (Country c: expected.keySet()){
            countriesExp.add(c.getCountryName());
            for (User u : expected.get(c)){
                usersExp.add(u.getUserName());
            }
        }
        assertIterableEquals(countriesExp,countriesRes);
        assertIterableEquals(usersExp,usersRes);
    }

    @Test
    public void orderByValues() {
        Map<String, Double> aux = new HashMap<>();
        aux.put("b", 2.0);
        aux.put("c", 3.0);
        aux.put("a", 1.0);
        aux.put("e", 5.0);
        aux.put("d", 4.0);
        Map<String, Double> entries = new LinkedHashMap<>();
        entries.put("a", 1.0);
        entries.put("b", 2.0);
        entries.put("c", 3.0);
        entries.put("d", 4.0);
        entries.put("e", 5.0);
        List<Map.Entry<String, Double>> expected = new LinkedList<Map.Entry<String, Double>>(entries.entrySet());
        assertIterableEquals(expected, rd.orderByValues(aux, 1));
    }

    @Test
    public void getCountriesWithMoreFriends() {
        String friend1 = "u5";
        String friend2 = "u32";
        int n = 2;
        LinkedList<String> expectedList = new LinkedList<>();
        expectedList.add("quito");
        expectedList.add("bogota");
        expectedList.add("caracas");
        expectedList.add("georgetwon");
        expectedList.add("brasilia");
        double expectedDouble = 7833.35;
        LinkedList<String> path = new LinkedList<>();
        double resDouble = rd.getCountriesWithMoreFriends(rd.getCountriesListByString(),friend1,friend2,n,path);
        LinkedList<String> resList = path;
        assertEquals(expectedDouble,resDouble,0.1);
        assertIterableEquals(expectedList,resList);
    }
}
