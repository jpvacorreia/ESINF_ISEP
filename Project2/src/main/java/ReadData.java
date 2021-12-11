import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ReadData implements Serializable {

    ArrayList<User> usersList;
    ArrayList<Country> countriesList;
    Graph<String, String> bordersMap;
    AdjacencyMatrixGraph<String, Double> relationsAdjMatrixGraph;


    public ReadData() {
        this.usersList = new ArrayList<>();
        this.countriesList = new ArrayList<>();
        this.bordersMap = new Graph<>(false);
        this.relationsAdjMatrixGraph = new AdjacencyMatrixGraph<>();
    }

    /**
     * Lê todas as linhas de um ficheiro e organiza-o num ArrayList
     *
     * @param filename
     */
    public void readfileCountries(String filename) {

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Country country = null;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line.trim();
            String[] list = line.split(",");
            list[0] = list[0].replace(" ", "");
            list[1] = list[1].replace(" ", "");
            list[2] = list[2].replace(" ", "");
            list[3] = list[3].replace(" ", "");
            list[4] = list[4].replace(" ", "");
            list[5] = list[5].replace(" ", "");
            country = new Country(list[0], list[1], list[2], list[3], Double.parseDouble(list[4]), Double.parseDouble(list[5]));
            countriesList.add(country);
        }
    }

    /**
     * Lê todas as linhas de um ficheiro e organiza-o num ArrayList
     *
     * @param filename
     */
    public void readfileUser(String filename) {

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        User user = null;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line.trim();
            String[] list = line.split(",");

            user = new User(list[0], Integer.parseInt(list[1]), list[2]);
            usersList.add(user);
        }
    }

    /**
     * Popula o grafo com vértices e lê todas as linhas de um ficheiro de arestas,
     * adicionando aquelas que ligam 2 vértices contidos no grafo.
     *
     * @param filename
     */
    public void readfileCountriesToMap(String filename) {

        for (Country country : countriesList) {
            // popula o graph com os vértices contendo apenas o nome de cada pais e não o objeto country (NÃO SEI SE ESTÀ BEM)
            bordersMap.insertVertex(country.getCountryName());
        }

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] list = line.split(",");
            list[0] = list[0].replace(" ", "");
            list[1] = list[1].replace(" ", "");
            Country c1 = getCountryByName(list[0]);
            Country c2 = getCountryByName(list[1]);
            if (c1 != null && c2 != null) {
                // AGAIN popula o graph com os Edges contendo apenas o nome de cada pais e não o objeto country (NÃO SEI SE ESTÀ BEM)
                bordersMap.insertEdge(c1.getCountryName(), c2.getCountryName(), null, Country.distance(c1, c2));
            }
        }
    }       // CHECK IF VERTEX EXISTE

    /**
     * Recebe o nome de um país e retorna o objeto do tipo Country com o nome igual
     *
     * @param countryName
     * @return c Country object
     */
    public Country getCountryByName(String countryName) {
        for (Country c : countriesList) {
            if (c.getCountryName().equals(countryName)) return c;
        }
        return null;
    }


    /**
     * Popula a matriz de adjacências com vértices e lê todas as linhas de um ficheiro de arestas,
     * adicionando aquelas que ligam 2 vértices contidos no grafo.
     *
     * @param filename
     */
    public void readfileUsersToMatrix(String filename) {


        for (User user : usersList) {
            // popula o graph com os vértices contendo apenas o nome de cada pais e não o objeto country (NÃO SEI SE ESTÀ BEM)
            relationsAdjMatrixGraph.insertVertex(user.getUserName());
        }

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line.trim();
            String[] list = line.split(",");
            list[0] = list[0].replace(" ", "");
            list[1] = list[1].replace(" ", "");
            User u1 = getUserByName(list[0]);
            User u2 = getUserByName(list[1]);
            if (u1 != null && u2 != null) {
                // AGAIN popula o graph com os Edges contendo apenas o nome de cada pais e não o objeto country (NÃO SEI SE ESTÀ BEM)
                relationsAdjMatrixGraph.insertEdge(u1.getUserName(), u2.getUserName(), 1.0);
            }
        }
    }

    /**
     * Recebe o nome de um user e retorna o objeto do tipo User com o nome igual
     *
     * @param userName
     * @return c Country object
     */
    public User getUserByName(String userName) {
        for (User u : usersList) {
            if (u.getUserName().equals(userName)) return u;
        }
        return null;
    }

    /**
     * Retorna uma ArrayList igual à ArrayList que contém todos as instâncias de Country
     * presentes na lista atributo da instância de ReadData
     *
     * @return arrayReturn
     */
    public ArrayList<Country> getCountriesList() {
        ArrayList<Country> arrayReturn = new ArrayList<>(countriesList);
        return arrayReturn;
    }

    /**
     * Retorna uma ArrayList com todos os nomes dos Paises da lista de instâncias de Country
     *
     * @return arrayReturn
     */
    public ArrayList<String> getCountriesListByString() {
        ArrayList<String> returnList = new ArrayList<>();
        for (Country c : countriesList) {
            returnList.add(c.getCountryName());
        }
        return returnList;
    }

    /**
     * Retorna uma ArrayList igual à ArrayList que contém todos as instâncias de User
     * presentes na lista atributo da instância de ReadData
     *
     * @return arrayReturn
     */
    public ArrayList<User> getUsersList() {
        ArrayList<User> arrayReturn = new ArrayList<>(usersList);
        return arrayReturn;
    }

    /**
     * Retorna um Map<String, Double> com o nome do User (String) e o número de
     * relacionamentos (Double) de cada user, ordenado descendente pelo value de cada key,
     * retornando apenas n (int) keys (users).
     *
     * @param graph, grafo a utilizar
     * @param n, número de users a retornar
     * @return resultN
     */
    public Map<String, Double> getRelationsFromMostPopularUsers(AdjacencyMatrixGraph<String, Double> graph, int n) {

        Map<String, Double> relations = new HashMap<>();
        for (String user : graph.vertices()) {
            relations.put(user, (double) graph.outDegree(user));
        }

        int sortingOrder = 2;
        List<Map.Entry<String, Double>> list = orderByValues(relations, sortingOrder);

        Map<String, Double> result = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        Map<String, Double> resultN = new LinkedHashMap<String, Double>();

        int counter = 0;
        for (Map.Entry<String, Double> aux : result.entrySet()) {
            if (counter < n) {
                resultN.put(aux.getKey(), aux.getValue());
                counter++;
            }
        }

        return resultN;
    }

    /**
     * Retorna uma ArrayList com os amigos comuns entre os n (int) Users (String) mais
     * populares da rede
     *
     * @param graph, grafo a utilizar
     * @param n, número de users mais populares a considerar
     * @return commonFriends
     */
    public ArrayList<String> commonFriends(AdjacencyMatrixGraph<String, Double> graph, int n) {
        Map<String, Double> orderedMap = getRelationsFromMostPopularUsers(graph, n);
        ArrayList<String> allTheirFriends = new ArrayList<>();
        for (String strUser : orderedMap.keySet()) {
            if (graph.checkVertex(strUser)) {
                Iterable<String> connects = new ArrayList<>();
                connects = graph.directConnections(strUser);
                for (String connect : connects) {
                    allTheirFriends.add(connect);
                }
            }
        }
        int counter = 0;
        ArrayList<String> UncommonFriends = new ArrayList<>();
        for (String uniqueFriend : allTheirFriends) {
            for (String repeatedFriend : allTheirFriends) {
                if (uniqueFriend.equals(repeatedFriend)) {
                    counter++;
                }
            }
            if (counter != n) {
                UncommonFriends.add(uniqueFriend);
            }
            counter = 0;
        }
        allTheirFriends.removeAll(UncommonFriends);
        ArrayList<String> commonFriends = new ArrayList<>();
        for (String element : allTheirFriends) {

            if (!commonFriends.contains(element)) {
                commonFriends.add(element);
            }
        }
        return commonFriends;
    }


    /**
     * Retorna True ou False consoante o grafo passado por parâmetro é conectado ou não
     *
     * @param graph, grafo a verificar
     * @return true/false
     */
    public boolean isConnected(AdjacencyMatrixGraph<String, Double> graph) {
        String v = graph.vertices.get(0);
        ArrayList<String> path = new ArrayList<>();
        path.addAll(GraphAlgorithms.BFS(graph, v));
        if (path.size() == graph.numVertices()) {
            return true;
        }
        return false;
    }

    /**
     * Retorna um valor (double) com o número mínimo de conexões entre Users para
     * que seja possível qualquer utilizador contactar qualquer outro utilizador
     *
     * @param graph, grafo a utilizar
     * @return minPath
     */
    public double minConnections(AdjacencyMatrixGraph<String, Double> graph) {
        if (!isConnected(graph)) {
            return -1;
        }
        double minPath = 0;
        for (User u1 : getUsersList()) {
            for (User u2 : getUsersList()) {
                if (!u1.equals(u2)) {
                    LinkedList<String> path = new LinkedList<>();
                    EdgeAsDoubleGraphAlgorithms.shortestPath(graph, u1.getUserName(), u2.getUserName(), path);
                    if (path.size() > minPath) {
                        minPath = path.size() - 1;
                    }
                }
            }
        }
        return minPath;
    }

    /**
     * Retorna a percentagem relativa de um valor (n) consoante o valor máximo (max)
     *
     * @param n, número para o cálculo
     * @param max, número máximo
     */
    public double relativePercentage(int n, int max) {
        return ((double) n / (double) max) * 100;
    }

    /**
     * Retorna uma ArrayList com n (int) cidades com maior centralidade
     *
     * @param graph, grafo a utilizar
     * @param n, número de cidades com maior centralidade
     * @param percentage percentagem a considerar
     * @return commonFriends
     */
    public ArrayList<String> greaterCentrality(Graph<String, String> graph, int n, double percentage) {
        LinkedList<String> lst = new LinkedList<>();
        Map<String, Double> centralities = new HashMap<>();
        for (Country c1 : getCountriesList()) {
            int usersInTheCity = 0;
            for (User u : getUsersList()) {
                if (u.getCity().equals(c1.getCapital())) {
                    usersInTheCity++;
                }
            }

            double relativePercentage = relativePercentage(usersInTheCity, getUsersList().size());
            if (percentage <= relativePercentage) {
                double distance = 0;
                for (Country c2 : getCountriesList()) {
                    if (!c1.equals(c2)) {
                        distance = distance + GraphAlgorithms.shortestPath(graph, c1.getCountryName(), c2.getCountryName(), lst);
                    }
                }
                centralities.put(c1.getCapital(), distance / getCountriesList().size());
            }
        }

        int sortingOrder = 1;
        List<Map.Entry<String, Double>> list = orderByValues(centralities, sortingOrder);

        ArrayList<String> result = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<String, Double> entry : list) {
            result.add(entry.getKey());
            counter++;
            if (counter == n) {
                return result;
            }
        }
        return result;
    }

    /**
     * Retorna um grafo clonado do grafo existente de fronteiras entre paises
     *
     * @return returnMap
     */
    public Graph<String, String> getBordersMap() {
        Graph<String, String> returnMap = bordersMap.clone();
        return returnMap;
    }

    /**
     * Retorna um grafo clonado do grafo existente de relacionamentos entre users
     *
     * @return returnGraph
     */
    public AdjacencyMatrixGraph<String, Double> getRelationsAdjMatrixGraph() {
        AdjacencyMatrixGraph<String, Double> returnGraph = new AdjacencyMatrixGraph<>();
        returnGraph = (AdjacencyMatrixGraph<String, Double>) relationsAdjMatrixGraph.clone();
        return returnGraph;
    }

    /**
     * Retorna uma ArrayList com os países dentro das n fronteiras a considerar
     *
     * @param bordersMap, grafo a utilizar
     * @param userName, nome do user
     * @param nBorders, número de fronteiras a procurar
     * @return Paises dentro das n fronteiras
     */
    public <V, E> ArrayList<String> getCountriesWithinNBorders(Graph<String, String> bordersMap, String userName, int nBorders) {

        String userCity = getUserByName(userName).getCity();
        String userCountry = null;
        for (Country c : getCountriesList()) {
            if (c.getCapital().equals(userCity)) userCountry = c.getCountryName();
        }
        if (!bordersMap.validVertex(userCountry)) {
            return null;
        }

        ArrayList<String> exit = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        exit.add(userCountry);
        aux.add(userCountry);

        int depth = 0;
        int increaseElements = 1;
        int nextIncrease = 0;

        while (!aux.isEmpty()) {
            userCountry = aux.get(0);
            aux.remove(0);

            nextIncrease += bordersMap.outDegree(userCountry);
            if (--increaseElements == 0 || depth == nBorders) {
                if (++depth > nBorders) {
                    return (ArrayList<String>) exit;
                }
                increaseElements = nextIncrease;
                nextIncrease = 0;
            }

            for (String countryAdj : bordersMap.adjVertices(userCountry)) {
                //increaseElements--;
                if (!exit.contains(countryAdj)) {
                    exit.add(countryAdj);
                    aux.add(countryAdj);
                }
            }
        }

        return exit;

    }

    /**
     * Retorna um Map<Country, ArrayList<User>> com os paises (key) e uma ArrayList (value) com os utilizadores
     * amigos dentro dessa cidade
     *
     * @param countriesToSearch, lista de cidades a procurar amigos
     * @param userName, nome do user
     * @return returnMap
     */
    public Map<Country, ArrayList<User>> getFriendsInCities(ArrayList<String> countriesToSearch, String userName) {

        Map<Country, ArrayList<User>> returnMap = new LinkedHashMap<>();

        for (String c : countriesToSearch) {
            Country country = getCountryByName(c);
            String capital = country.getCapital();
            ArrayList<User> returnUsers = new ArrayList<>();

            for (User u : getUsersList()) {
                String city = u.getCity();
                String nome = u.getUserName();
                if (city.equals(capital)) {
                    if (getRelationsAdjMatrixGraph().getEdge(nome, userName) != null) {
                        returnUsers.add(u);
                    }
                }
            }
            if (!returnUsers.isEmpty()) returnMap.put(country, returnUsers);
        }
        return returnMap;
    }

    /**
     * Retorna a distância total do percurso a percorrer entre o friend1 e friend2 com uma quantidade
     * de cidades intermédias. Devolve o path no result
     *
     * @param arrayCountries, lista de cidades a procurar amigos
     * @param friend1, nome do user1
     * @param friend2, nome do user2
     * @param nIntermediateCities, número de cidades intermédias obrigatórias por utilizador
     * @param result, cidades pelas quais o path vai passar
     * @return totalDist, distância total do percurso
     */
    public double getCountriesWithMoreFriends(ArrayList<String> arrayCountries, String friend1, String friend2,
                                              int nIntermediateCities, LinkedList<String> result) {
        result.clear();
        Map<Country, ArrayList<User>> mapFriend1 = getFriendsInCities(arrayCountries, friend1);
        Map<Country, ArrayList<User>> mapFriend2 = getFriendsInCities(arrayCountries, friend2);

        Map<String, Double> mapToOrder1 = new HashMap<>();
        Map<String, Double> mapToOrder2 = new HashMap<>();

        double counter = 0;
        for (Country c : mapFriend1.keySet()) {
            for (User u : mapFriend1.get(c)) {
                counter++;
            }
            mapToOrder1.put(c.getCountryName(), counter);
            counter = 0;
        }

        counter = 0;
        for (Country c : mapFriend2.keySet()) {
            for (User u : mapFriend2.get(c)) {
                counter++;
            }
            mapToOrder2.put(c.getCountryName(), counter);
            counter = 0;
        }

        int sortingOrder = 2; // descending order
        List<Map.Entry<String, Double>> orderedMapFriend1 = orderByValues(mapToOrder1, sortingOrder);
        List<Map.Entry<String, Double>> orderedMapFriend2 = orderByValues(mapToOrder2, sortingOrder);

        String[] pathKeys = new String[getCountriesList().size()];

        User u1 = getUserByName(friend1);
        User u2 = getUserByName(friend2);
        String c1 = null;
        String c2 = null;
        for (Country c : getCountriesList()) {
            if (c.getCapital().equals(u1.getCity())) {
                c1 = c.getCountryName();
            }
            if (c.getCapital().equals(u2.getCity())) {
                c2 = c.getCountryName();
            }
        }

        List<String> listFinal = getFinalCountriesList(c1, c2, orderedMapFriend1, orderedMapFriend2, nIntermediateCities);

        double totalDist = 0;
        //List<String> aux = new ArrayList<>(listFinal);
        LinkedList<String> path = new LinkedList<>();
        LinkedList<String> minDist = new LinkedList<>();
        LinkedList<String> res = new LinkedList<>();
        String last = "";
        String destiny = listFinal.get(listFinal.size()-1);
        listFinal.remove(destiny);
        int n=0;
        String v1 = listFinal.get(0);
        while (listFinal.size() != 0) {
            double distance = Double.MAX_VALUE;
            for (String v2 : listFinal) {
                if (!v2.equals(v1)) {
                    if (GraphAlgorithms.shortestPath(getBordersMap(), v1, v2, path) < distance) {
                        path = new LinkedList<>();
                        if(GraphAlgorithms.shortestPath(getBordersMap(), v1, v2, path) > 0) {
                            path = new LinkedList<>();
                            distance = GraphAlgorithms.shortestPath(getBordersMap(), v1, v2, path);
                            last = v2;
                        }
                        if (path.size() > 0) {
                            path.removeLast();
                            minDist = new LinkedList<>(path);
                        }

                    }

                }
            }
            listFinal.remove(v1);
            v1 = last;
            if(listFinal.size() != 0) {
                res.addAll(minDist);
                totalDist += distance;
            }
            n++;
            //Retira o infinitismo da procura caso o caminho terrestre seja impossível.
            if(n>getCountriesList().size() && distance==Double.MAX_VALUE && listFinal.size()<2){
                result.add("Impossible Land Path");
                return -1;
            }
        }
        path.clear();
        if(res.isEmpty()){
            result.add("Impossible Land Path");
            return -1;
        }
        if (!res.getLast().equals(destiny)) {
            totalDist += GraphAlgorithms.shortestPath(getBordersMap(), res.getLast(), destiny, path);
            if (path.size() > 2) {
                path.removeFirst();
                res.addAll(path);
            } else res.addLast(destiny);
        }
        for (String c : res) {
            Country country = getCountryByName(c);
            result.add(country.getCapital());
        }
        if (result.isEmpty()){
            result.add("Impossible Land Path");
            return -1;
        }

        return totalDist;
    }

    /**
     * Método de ordenação criado para ordenar um Map recebido por parâmetro. Utiliza o value das entradas
     * como termo de comparação para a ordenação.
     *
     * @param mapToOrder, map a ordenar
     * @param order, sentido de ordenação

     * @return list ordenada
     */
    public List<Map.Entry<String, Double>> orderByValues(Map<String, Double> mapToOrder, int order) {

        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(mapToOrder.entrySet());

        if (order == 2) {
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o2, Map.Entry<String, Double> o1) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

        } else if (order == 1) {
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o2, Map.Entry<String, Double> o1) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
        } else {
            return null;
        }
        return list;
    }

    /**
     * Retorna uma List<String> com a lista final de paises entre 2 amigos (Users) considerando n cidades
     * intermédias
     *
     * @param c1, nome do user1
     * @param c2, nome do user2
     * @param orderedMapFriend1, Map ornedado das cidades (key) e número de amigos (value) do user1
     * @param orderedMapFriend2, Map ornedado das cidades (key) e número de amigos (value) do user2
     * @param nIntermediateCities, número de cidades intermédias obrigatórias por utilizador
     * @return listFinal, lista com as cidades pelas quais é obrigatório passar
     */
    public List<String> getFinalCountriesList(String c1, String
            c2, List<Map.Entry<String, Double>> orderedMapFriend1,
                                              List<Map.Entry<String, Double>> orderedMapFriend2, int nIntermediateCities) {

        List<String> listFinal = new ArrayList<>();

        int counter = 0;
        listFinal.add(c1);
        listFinal.add(c2);
        for (Map.Entry<String, Double> entry : orderedMapFriend1) {
            counter++;
            if (counter <= nIntermediateCities) {
                String country = entry.getKey();
                if (!listFinal.contains(country)) {
                    listFinal.add(country);
                }
            }
        }

        counter = 0;
        for (Map.Entry<String, Double> entry : orderedMapFriend2) {
            counter++;
            if (counter <= nIntermediateCities) {
                String country = entry.getKey();
                if (!listFinal.contains(country)) {
                    listFinal.add(country);
                }
            }
        }

        listFinal.remove(c2);
        listFinal.add(c2);

        return listFinal;
    }

}
