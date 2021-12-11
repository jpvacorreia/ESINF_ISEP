
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


class ReadDataTest {

    ArrayList<User> usersList;
    ArrayList<Country> countriesList;
    Graph<String, String> bordersMap;
    AdjacencyMatrixGraph<String, Double> relationsAdjMatrixGraph;
    Country c1 = new Country("a","a","10","aa",-34.6131500, -58.3772300);
    Country c2 = new Country("b","a","20","bb",-16.5000000, -68.1500000);
    Country c3 = new Country("c","a","15","cc",-15.7797200, -47.9297200);
    Country c4 = new Country("d","a","5","dd",-33.4569400, -70.6482700);
    Country c5 = new Country("e","a","17","ee",4.6097100, -74.0817500);
    User u1 = new User("u1",19,"aa");
    User u2 = new User("u2",19,"aa");
    User u3 = new User("u3",19,"bb");
    User u4 = new User("u4",19,"cc");
    User u5 = new User("u5",19,"cc");
    User u6 = new User("u6",19,"ee");
    User u7 = new User("u7",19,"ee");
    User u8 = new User("u8",19,"ee");
    User u9 = new User("u9",19,"aa");
    User u10 = new User("u10",19,"ee");
    ReadData rd = new ReadData();

    ReadDataTest(){
        usersList = new ArrayList<>();
        countriesList = new ArrayList<>();
        bordersMap = new Graph<>(false);
        this.relationsAdjMatrixGraph = new AdjacencyMatrixGraph<>();
        rd.usersList.add(u1);
        rd.usersList.add(u2);
        rd.usersList.add(u3);
        rd.usersList.add(u4);
        rd.usersList.add(u5);
        rd.usersList.add(u6);
        rd.usersList.add(u7);
        rd.usersList.add(u8);
        rd.usersList.add(u9);
        rd.usersList.add(u10);
        rd.countriesList.add(c1);
        rd.countriesList.add(c2);
        rd.countriesList.add(c3);
        rd.countriesList.add(c4);
        rd.countriesList.add(c5);
        rd.bordersMap.insertVertex(c1.getCountryName());
        rd.bordersMap.insertVertex(c2.getCountryName());
        rd.bordersMap.insertVertex(c3.getCountryName());
        rd.bordersMap.insertVertex(c4.getCountryName());
        rd.bordersMap.insertVertex(c5.getCountryName());
        rd.bordersMap.insertEdge(c1.getCountryName(),c2.getCountryName(),null,Country.distance(c1,c2));
        rd.bordersMap.insertEdge(c1.getCountryName(),c3.getCountryName(),null,Country.distance(c1,c3));
        rd.bordersMap.insertEdge(c3.getCountryName(),c4.getCountryName(),null,Country.distance(c3,c4));
        rd.bordersMap.insertEdge(c2.getCountryName(),c5.getCountryName(),null,Country.distance(c2,c5));
        rd.relationsAdjMatrixGraph.insertVertex(u1.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u2.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u3.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u4.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u5.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u6.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u7.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u8.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u9.getUserName());
        rd.relationsAdjMatrixGraph.insertVertex(u10.getUserName());
        rd.relationsAdjMatrixGraph.insertEdge(u1.getUserName(),u3.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u1.getUserName(),u5.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u1.getUserName(),u7.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u1.getUserName(),u10.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u2.getUserName(),u3.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u2.getUserName(),u10.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u3.getUserName(),u4.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u4.getUserName(),u5.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u5.getUserName(),u10.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u6.getUserName(),u9.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u6.getUserName(),u7.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u6.getUserName(),u2.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u7.getUserName(),u10.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u7.getUserName(),u9.getUserName(),1.0);
        rd.relationsAdjMatrixGraph.insertEdge(u8.getUserName(),u9.getUserName(),1.0);
    }

    @Test
    void getCountriesList() {
        ArrayList<Country> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);
        expected.add(c3);
        expected.add(c4);
        expected.add(c5);
        assertIterableEquals(expected,rd.countriesList);
    }

    @Test
    void getUsersList() {
        ArrayList<User> expected = new ArrayList<>();
        expected.add(u1);
        expected.add(u2);
        expected.add(u3);
        expected.add(u4);
        expected.add(u5);
        expected.add(u6);
        expected.add(u7);
        expected.add(u8);
        expected.add(u9);
        expected.add(u10);
        assertIterableEquals(expected,rd.usersList);
    }

    @Test
    public void getRelationsFromMostPopularUsers() {
        Map<String,Integer> expected = new HashMap<>();
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        expected.put(u10.getUserName(),4);
        expected.put(u7.getUserName(),4);
        assertIterableEquals(expected.keySet(),rd.getRelationsFromMostPopularUsers(clone,2).keySet());
        expected.put(u1.getUserName(), 4);
        assertIterableEquals(expected.keySet(),rd.getRelationsFromMostPopularUsers(clone,3).keySet());
    }

    @Test
    public void commonFriends() {
        ArrayList<String> expected = new ArrayList<>();
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        expected.add(u1.getUserName());
        expected.add(u6.getUserName());
        expected.add(u9.getUserName());
        expected.add(u10.getUserName());
        assertIterableEquals(expected,rd.commonFriends(clone,1));
        expected.remove(u9.getUserName());
        expected.remove(u6.getUserName());
        expected.remove(u10.getUserName());
        assertIterableEquals(expected,rd.commonFriends(clone,2));
    }

    @Test
    public void isConnected() {
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        assertEquals(true,rd.isConnected(clone));
        clone.removeEdge(u8.getUserName(),u9.getUserName());
        assertEquals(false,rd.isConnected(clone));
    }

    @Test
    public void minConnections() {
        double expected = 5.0;
        AdjacencyMatrixGraph<String,Double> clone = (AdjacencyMatrixGraph<String,Double>) rd.relationsAdjMatrixGraph.clone();
        assertEquals(expected,rd.minConnections(clone),0.1);
        clone.removeEdge(u8.getUserName(),u9.getUserName());
        expected = -1;
        assertEquals(expected,rd.minConnections(clone),0.1);
    }

    @Test
    public void relativePercentage() {
        int max = 100;
        int n = 1;
        double expected = 1.0;
        assertEquals(expected,rd.relativePercentage(n,max),0.1);
        n = 7;
        expected = 7.0;
        assertEquals(expected,rd.relativePercentage(n,max),0.1);
        max = 50;
        expected = 14.0;
        assertEquals(expected,rd.relativePercentage(n,max),0.1);
    }

    @Test
    public void greaterCentrality() {
        Graph<String,String> clone = (Graph<String,String>)rd.bordersMap.clone();
        ArrayList<String> expected = new ArrayList<>();
        expected.add(c1.getCapital());
        expected.add(c2.getCapital());
        int n=2;
        double percentage = 0;
        assertIterableEquals(expected,rd.greaterCentrality(clone,n,percentage));
        n = 3;
        percentage = 1.1;
        expected.add(c3.getCapital());
        assertIterableEquals(expected,rd.greaterCentrality(clone,n,percentage));
    }

    @Test
    public void getClosestFriends() {
        String strU1 = u1.getUserName();
        LinkedList<String> expected = new LinkedList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        ArrayList<String> result = rd.getCountriesWithinNBorders(rd.getBordersMap() , strU1,1);
        assertIterableEquals(expected,result);
        expected.add("e");
        expected.add("d");
        result = rd.getCountriesWithinNBorders(rd.getBordersMap(), strU1,2);
        assertIterableEquals(expected,result);
    }

    @Test
    public void getFriendsInCities() {
        String strU1 = u1.getUserName();
        ArrayList<String> aux = new ArrayList<>();
        aux.add("b");
        aux.add("c");
        aux.add("a");
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
        auxList1.add(u3);
        auxList2.add(u5);
        expected.put(c2,auxList1);
        expected.put(c3,auxList2);
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
        strU1 = u5.getUserName();
        aux.clear();
        aux.add("b");
        aux.add("a");
        aux.add("c");

        result = rd.getFriendsInCities(aux,strU1);
        countriesRes = new ArrayList<>();
        usersRes = new ArrayList<>();
        for (Country c: result.keySet()){
            countriesRes.add(c.getCountryName());
            for (User u : result.get(c)){
                usersRes.add(u.getUserName());
            }
        }
        expected = new LinkedHashMap<>();
        auxList1 = new ArrayList<>();
        auxList2 = new ArrayList<>();
        auxList1.add(u1);
        auxList2.add(u4);
        expected.put(c1,auxList1);
        expected.put(c3,auxList2);
        countriesExp = new ArrayList<>();
        usersExp = new ArrayList<>();
        for (Country c: expected.keySet()){
            countriesExp.add(c.getCountryName());
            for (User u : expected.get(c)){
                usersExp.add(u.getUserName());
            }
        }
        assertIterableEquals(countriesExp,countriesRes);
        assertIterableEquals(usersExp,usersRes);
    }
}