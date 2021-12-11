import java.util.*;


public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        /*
         * Professora, temos um main para o caso da professora querer ver os dados mais rapidamente.
         */

        String sborders = "sborders.txt";
        String scountries = "scountries.txt";
        String srelationships = "srelationships.txt";
        String susers = "susers.txt";

        ReadData fileReadObject = new ReadData();
        fileReadObject.readfileCountries(scountries);
        fileReadObject.readfileUser(susers);

        System.out.println("________________________");
        System.out.println("| Paises importados:   |");
        for (Country c : fileReadObject.getCountriesList()) {
            System.out.printf("| %-20s |\n", c.getCountryName());
        }

        System.out.println();
        System.out.println("________________________");
        System.out.println("| Users importados:    |");
        for (User u : fileReadObject.getUsersList()) {
            System.out.printf("| %-20s |\n", u.getUserName());
        }

        fileReadObject.readfileCountriesToMap(sborders);
        fileReadObject.readfileUsersToMatrix(srelationships);

        Graph<String, String> bordersMap = fileReadObject.bordersMap;
        AdjacencyMatrixGraph<String, Double> relationsAdjMatrixGraph = fileReadObject.relationsAdjMatrixGraph;

        Iterable<Edge<String, String>> edges = bordersMap.edges();

        System.out.println("______________________________________________");
        System.out.println("|   Fronteiras e distância entre cidades:    |");
        for (Edge e : edges) {
            System.out.printf("|%15s - %-15s : %-5.2f |\n", e.getVOrig(), e.getVDest(), e.getWeight());
        }

        System.out.println("");
        System.out.println("Alínea 2");
        ArrayList<String> commonFriends = fileReadObject.commonFriends(relationsAdjMatrixGraph,5);
        for (String user : commonFriends) {
            System.out.printf("%s \n", user);
        }

        System.out.println("\nAlínea 3:");
        if (fileReadObject.minConnections(relationsAdjMatrixGraph) == -1){
            System.out.println("Graph ins't connected");
        } else {
            System.out.println("Graph is connected");
            System.out.println(fileReadObject.minConnections(relationsAdjMatrixGraph));
        }

        System.out.println("\nAlínea 4:");
        String userName = "u1";
        int nBorders = 2;
        ArrayList<String> citiesWithinNBorders = fileReadObject.getCountriesWithinNBorders(fileReadObject.getBordersMap(), userName, nBorders);

        Map<Country, ArrayList<User>> friendsInCities = fileReadObject.getFriendsInCities(citiesWithinNBorders, userName);
        for (Country country : friendsInCities.keySet()) {
            System.out.printf("País: %s - Cidade: %s\n", country.getCountryName(), country.getCapital());
            ArrayList<User> users = friendsInCities.get(country);
            for (User user : users) {
                System.out.printf("User: %5s, Idade: %s\n", user.getUserName(), user.getAge());
            }
        }


        System.out.println("\nAlínea 5:");
        ArrayList<String> map1 = fileReadObject.greaterCentrality(bordersMap, 2, 2.2);
        int counter = 1;
        for (String city : map1) {
            System.out.printf("%2dª cidade : %s \n", counter++, city);
        }


        System.out.println("\nAlínea 6:");
        String friend1 = "u5";
        String friend2 = "u32";
        int n = 2;
        LinkedList<String> res = new LinkedList<>();
        double value = fileReadObject.getCountriesWithMoreFriends(fileReadObject.getCountriesListByString(), friend1, friend2, n,res);
        System.out.printf("distance : %.2f\n",value);
        for(String c : res){
            System.out.println("- " + c);
        }

    }
}





