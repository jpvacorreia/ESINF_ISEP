import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class ReadFile extends AVL<Element> {

    ArrayList<Element> elementList;
    PeriodicTable pt;
    ElectronConfigurationTree ect;
    AtomicMassTree amt;
    SymbolTree st;
    ElementNameTree ent;
    BST<Element> bst;

    public ReadFile() {
        this.elementList = new ArrayList<>();
        pt = new PeriodicTable();
        bst = new BST<>();
        ect = new ElectronConfigurationTree();
        amt = new AtomicMassTree();
        st = new SymbolTree();
        ent = new ElementNameTree();
    }

    public void readfileElements(String filename) {

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Element element = null;
        String line = sc.nextLine();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] list = line.split(",");
            list[1] = list[1].replace(" ", "");
            list[2] = list[2].replace(" ", "");
            list[23] = list[23].replace(" ", "");
            element = new Element(Integer.parseInt(list[0]), list[1], list[2], Double.parseDouble(list[3]),
                    Double.parseDouble(list[4]), Double.parseDouble(list[5]), Double.parseDouble(list[6]),
                    list[7], Double.parseDouble(list[22]), Double.parseDouble(list[23]));
            if (list[8] != null && !list[8].equals("")) {
                element.setMostStableCrystal(list[8]);
            }
            if (list[9] != null && !list[9].equals("")) {
                element.setType(list[9]);
            }
            if (list[10] != null && !list[10].equals("")) {
                element.setIonicRadius(Double.parseDouble(list[10]));
            }
            if (list[11] != null && !list[11].equals("")) {
                element.setAtomicRadius(Double.parseDouble(list[11]));
            }
            if (list[12] != null && !list[12].equals("")) {
                element.setElectronegativity(Double.parseDouble(list[12]));
            }
            if (list[13] != null && !list[13].equals("")) {
                element.setFirstIonizationPotential(Double.parseDouble(list[13]));
            }
            if (list[14] != null && !list[14].equals("")) {
                element.setDensity(Double.parseDouble(list[14]));
            }
            if (list[15] != null && !list[15].equals("")) {
                element.setMeltingPoint(Double.parseDouble(list[15]));
            }
            if (list[16] != null && !list[16].equals("")) {
                element.setBoilingPoint(Double.parseDouble(list[16]));
            }
            if (list[17] != null && !list[17].equals("")) {
                element.setIsotopes(Double.parseDouble(list[17]));
            }
            if (list[18] != null && !list[18].equals("")) {
                element.setDiscoverer(list[18]);
            }
            if (list[19] != null && !list[19].equals("")) {
                element.setYearOfDiscovery(Integer.parseInt(list[19]));
            }
            if (list[20] != null && !list[20].equals("")) {
                element.setSpecificHeatCapacity(Double.parseDouble(list[20]));
            }
            if (list[21] != null && !list[21].equals("")) {
                element.setElectronConfiguration(list[21]);
            }
            elementList.add(element);
        }
    }

    public ArrayList<Element> getElementList() {
        return this.elementList;
    }

    //---------------------------------------------------------------------------------------

    public Element searchByAtomicNumber(Node<Element> node, int atomicNumber) {
        if (node == null) return null;

        if (node.getElement().getAtomicNumber() == atomicNumber) return node.getElement();

        if (atomicNumber < node.getElement().getAtomicNumber()) {
            return searchByAtomicNumber(node.getLeft(), atomicNumber);
        } else {
            return searchByAtomicNumber(node.getRight(), atomicNumber);
        }
    }

    public Element searchBySymbol(Node<Element> node, String symbol) {
        if (node == null) return null;

        if (node.getElement().getSymbol().compareTo(symbol) == 0) return node.getElement();

        if (symbol.compareTo(node.getElement().getSymbol()) < 0) {
            return searchBySymbol(node.getLeft(), symbol);
        } else {
            return searchBySymbol(node.getRight(), symbol);
        }
    }

    public Element searchByElementName(Node<Element> node, String element) {
        if (node == null) return null;

        if (node.getElement().getElementName().compareTo(element) == 0) return node.getElement();

        if (element.compareTo(node.getElement().getElementName()) < 0) {
            return searchByElementName(node.getLeft(), element);
        } else {
            return searchByElementName(node.getRight(), element);
        }
    }

    public Element searchByAtomicMass(Node<Element> node, double atomicMass) {
        if (node == null) return null;

        if (node.getElement().getAtomicMass() == atomicMass) return node.getElement();

        if (atomicMass < node.getElement().getAtomicMass()) {
            return searchByAtomicMass(node.getLeft(), atomicMass);
        } else {
            return searchByAtomicMass(node.getRight(), atomicMass);
        }
    }

    //-------------------------------------------------------------------------------
    public void insertData(ArrayList<Element> arrayList) {
        for (Element e : arrayList) {
            pt.insert(e);
            Element a = new CompareAtomicMass(e.getAtomicNumber(), e.getElementName(), e.getSymbol(), e.getAtomicWeight(), e.getAtomicMass(), e.getPeriod(), e.getGroup(), e.getPhase(), e.getDisplayRow(), e.getDisplayColumn());
            CompareAtomicMass eAM = (CompareAtomicMass) a;
            amt.insert(eAM);
            Element b = new CompareElement(e.getAtomicNumber(), e.getElementName(), e.getSymbol(), e.getAtomicWeight(), e.getAtomicMass(), e.getPeriod(), e.getGroup(), e.getPhase(), e.getDisplayRow(), e.getDisplayColumn());
            CompareElement eE = (CompareElement) b;
            ent.insert(eE);
            Element c = new CompareSymbol(e.getAtomicNumber(), e.getElementName(), e.getSymbol(), e.getAtomicWeight(), e.getAtomicMass(), e.getPeriod(), e.getGroup(), e.getPhase(), e.getDisplayRow(), e.getDisplayColumn());
            CompareSymbol eS = (CompareSymbol) c;
            st.insert(eS);
        }
    }


    public ArrayList<Element> searchElementsInBetween(double minAM, double maxAM) {
        ArrayList<Element> auxElements = new ArrayList<>();
        Map<Element, Integer> yearsToSort = new LinkedHashMap<>();
        Map<Element, String> discovererToSort = new LinkedHashMap<>();
        for (Element e : pt.inOrder()) {
            if (e.getAtomicMass() >= minAM && e.getAtomicMass() <= maxAM) {
                auxElements.add(e);
            }
        }
        for (Element e : auxElements) {
            yearsToSort.put(e, e.getYearOfDiscovery());
        }
        List<Map.Entry<Element, Integer>> list = new LinkedList<Map.Entry<Element, Integer>>(yearsToSort.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Element, Integer>>() {
            public int compare(Map.Entry<Element, Integer> o2, Map.Entry<Element, Integer> o1) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        for (Map.Entry<Element, Integer> entry : list) {
            discovererToSort.put(entry.getKey(), entry.getKey().getDiscoverer());
        }
        List<Map.Entry<Element, String>> list2 = new LinkedList<Map.Entry<Element, String>>(discovererToSort.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<Element, String>>() {
            public int compare(Map.Entry<Element, String> o1, Map.Entry<Element, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        ArrayList<Element> returnable = new ArrayList<>();
        for (Map.Entry<Element, String> entry : list2) {
            returnable.add(entry.getKey());
        }
        return returnable;
    }

    public String[][] getTypeAndPhase(ArrayList<Element> arrayList) {
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> phases = new ArrayList<>();
        for (Element e : arrayList) {
            if (!types.contains(e.getType())) {
                types.add(e.getType());
            }
        }
        for (Element e : getElementList()) {
            if (!phases.contains(e.getPhase())) {
                phases.add(e.getPhase());
            }
        }
        Collections.sort(types);
        Collections.sort(phases);

        String[][] typeAndPhase = new String[types.size() + 1][phases.size() + 2];
        for (int i = 0; i < typeAndPhase.length; i++) {
            for (int j = 0; j < typeAndPhase[0].length; j++) {
                typeAndPhase[i][j] = "0";
            }
        }

        typeAndPhase[0][0] = "";
        typeAndPhase[0][typeAndPhase[0].length - 1] = "TOTAL";
        for (int i = 1; i < types.size() + 1; i++) {
            typeAndPhase[i][0] = types.get(i - 1);
        }
        for (int i = 1; i < phases.size() + 1; i++) {
            typeAndPhase[0][i] = phases.get(i - 1);
        }
        for (Element e : arrayList) {
            for (int i = 1; i <= types.size(); i++) {
                for (int j = 1; j <= phases.size(); j++) {
                    if (typeAndPhase[i][0].equals(e.getType()) && typeAndPhase[0][j].equals(e.getPhase())) {
                        if (typeAndPhase[i][j] == null || typeAndPhase[i][j].equals("")) {
                            typeAndPhase[i][j] = "1";
                        } else {
                            typeAndPhase[i][j] = String.valueOf(Integer.parseInt(typeAndPhase[i][j]) + 1);
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= types.size(); i++) {
            for (int j = 1; j <= phases.size(); j++) {
                if (typeAndPhase[i][j] == null) {
                    typeAndPhase[i][j] = "0";
                }
            }
        }
        int aux = 0;
        for (int i = 1; i <= types.size(); i++) {
            for (int j = 1; j <= typeAndPhase[0].length - 2; j++) {
                aux += Integer.parseInt(typeAndPhase[i][j]);
            }
            typeAndPhase[i][typeAndPhase[0].length - 1] = String.valueOf(aux);
            aux = 0;
        }
        return typeAndPhase;
    }

    public List<Map.Entry<String, Integer>> counterElecConfiguration() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Iterable<Element> elements = pt.inOrder();
        ArrayList<Element> result = new ArrayList<>();
        Map<Element, String> orderedByElecConfig = new LinkedHashMap<>();
        elements.forEach(result::add);
        for (Element e : result) {
            if (e.getElectronConfiguration() != null)
                orderedByElecConfig.put(e, e.getElectronConfiguration());
        }
        List<Map.Entry<Element, String>> listAux = new LinkedList<Map.Entry<Element, String>>(orderedByElecConfig.entrySet());
        Collections.sort(listAux, new Comparator<Map.Entry<Element, String>>() {
            public int compare(Map.Entry<Element, String> o1, Map.Entry<Element, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        ArrayList<Element> resultOrdered = new ArrayList<>();
        for (Map.Entry<Element, String> entry : listAux) {
            resultOrdered.add(entry.getKey());
        }

        for (Element e : resultOrdered) {

            String elecConfig = e.getElectronConfiguration();
            if (elecConfig != null && !elecConfig.equals("")) {
                String[] splitted = elecConfig.split(" ");
                if (splitted.length > 1) {
                    for (int i = 1; i < splitted.length; i++) {
                        splitted[i] = splitted[i - 1] + " " + splitted[i];
                    }
                }
                int[] contador = new int[splitted.length];
                for (int i = 0; i < splitted.length; i++) {
                    map.put(splitted[i], contador[i]);
                }
                for (Element element : resultOrdered) {

                    String elecConfigToCompare = element.getElectronConfiguration();
                    String[] splittedToCompare;
                    if (elecConfigToCompare != null && !elecConfigToCompare.equals("")) {
                        splittedToCompare = elecConfigToCompare.split(" ");
                        if (splittedToCompare.length > 1) {
                            for (int i = 1; i < splittedToCompare.length; i++) {
                                splittedToCompare[i] = splittedToCompare[i - 1] + " " + splittedToCompare[i];
                            }
                        }
                        int i = 0;
                        while (splitted.length > i && splittedToCompare.length > i) {
                            if (splitted[i].equals(splittedToCompare[i])) {
                                contador[i]++;
                                map.replace(splitted[i], contador[i]);
                            }
                            i++;
                        }
                    }

                }
            }
        }
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o2, Map.Entry<String, Integer> o1) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        return list;
    }


    public Map<Integer, ArrayList<String>> getElectConfigurationBiggerThan(List<Map.Entry<String, Integer>> list, int n) {
        Map<Integer, ArrayList<String>> returnable = new LinkedHashMap<>();
        int value = 0;
        ArrayList<String> auxList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getValue() > n) {
                if (value != entry.getValue()) {
                    auxList = new ArrayList<>();
                    auxList.add(entry.getKey());
                    returnable.put(entry.getValue(), auxList);
                } else {
                    auxList.add(entry.getKey());
                    returnable.replace(entry.getValue(), auxList);
                }
            }
            value = entry.getValue();
        }
        return returnable;
    }

    public ElectronConfigurationTree bstWithEletronicConfigurations() {
        List<Map.Entry<String, Integer>> list = counterElecConfiguration();
        Map<Integer, ArrayList<String>> mapWithData = getElectConfigurationBiggerThan(list, 1);
        ect = new ElectronConfigurationTree();

        for (int i : mapWithData.keySet()) {
            if (i > 2) {
                for (String s : mapWithData.get(i)) {
                    ect.insert(s);
                }
            }
        }
        return ect;
    }


    public String getMostDistantNodesByElectronConfiguration() {
        Map<Integer, List<String>> listByLevel = new LinkedHashMap<>();
        listByLevel = ect.nodesByLevel();

        ArrayList<String> listToSearch = new ArrayList<>();

        for (Integer level : listByLevel.keySet()) {
            if (level >= (ect.height() - 1)) {
                listToSearch.addAll(listByLevel.get(level));
            }
        }

        Map<String, Integer> distancesMap = new LinkedHashMap<>();
        for (String node1 : listToSearch) {
            for (String node2 : listToSearch) {
                if (!node1.equalsIgnoreCase(node2)) {
                    int distance = getDistanceBetweenNodes(ect.root, node1, node2);
                    distancesMap.put(node1 + " <-> " + node2, distance);
                }
            }
        }
        return getMaximumDistance(distancesMap);

    }

    public int getDistanceBetweenNodes(Node root, String node1, String node2) {
        if (root != null) {
            Node lowestCommonAcestor = getLowestCommonAcestor(root, node1, node2);
            int dist1 = getLevel(lowestCommonAcestor, node1, 0);
            int dist2 = getLevel(lowestCommonAcestor, node2, 0);
            return dist1 + dist2;
        }
        return -1;
    }

    public static Node getLowestCommonAcestor(Node root, String node1, String node2) {
        if (root == null) return root;
        if (root.getElement() == node1 || root.getElement() == node2) return root;

        Node<String> left = getLowestCommonAcestor(root.getLeft(), node1, node2);
        Node<String> right = getLowestCommonAcestor(root.getRight(), node1, node2);

        if (left != null && right != null) return root;
        if (left == null && right == null) return null;
        if (left != null) return getLowestCommonAcestor(root.getLeft(), node1, node2);
        else return getLowestCommonAcestor(root.getRight(), node1, node2);
    }

    public static int getLevel(Node root, String node, int level) {
        if (root == null) return -1;
        if (root.getElement() == node) return level;
        int left = getLevel(root.getLeft(), node, level + 1);
        if (left == -1) return getLevel(root.getRight(), node, level + 1);
        return left;
    }


    public String getMaximumDistance(Map<String, Integer> distancesMap) {
        Map<String, Double> mapToOrder = new LinkedHashMap<>();
        for (String s : distancesMap.keySet()) {
            mapToOrder.put(s, Double.valueOf(distancesMap.get(s)));
        }
        List<Map.Entry<String, Double>> orderedMap = orderByValues(mapToOrder, 2);
        int dist = (int) Math.round(orderedMap.get(0).getValue());
        String distance = orderedMap.get(0).getKey() + ", distance: " + String.valueOf(dist);
        return distance;
    }


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

    public BST<String> buildCompleteBSTFromExistingTree() {
        LinkedList<String> listToAdd = new LinkedList<>();
        List<Map.Entry<String, Integer>> mapWithData = counterElecConfiguration();
        Map<Integer, ArrayList<String>> newMap = getElectConfigurationBiggerThan(mapWithData, 0);
        for (Integer level : newMap.keySet()) {
            if (level == 1) {
                listToAdd.addAll(newMap.get(level));
            }
        }
        CompleteBSTWithList(ect, listToAdd);
        return ect;
    }


    public void CompleteBSTWithList(ElectronConfigurationTree ect, LinkedList<String> listToAdd) {
        Map<Integer, List<String>> listByLevel = ect.nodesByLevel();
        String last = null;
        for (Integer level : listByLevel.keySet()) {
            if (level == ect.height()) {
                last = listByLevel.get(level).remove(listByLevel.get(level).size()-1);
            }
        }
        insertInEmptySpace(ect, ect.root, last, listToAdd, 0);
    }

    private void insertInEmptySpace(ElectronConfigurationTree ect, Node<String> node, String last, LinkedList<String> listToAdd, int level) {
        if (node == null || level > ect.height()) {
            return;
        }
        if (node.getElement().equals(last)) {
            listToAdd.clear();
            return;
        }
        if (level < ect.height() && node.getLeft() == null && listToAdd.size() != 0) {
            Node<String> n = new Node<>(listToAdd.getFirst(), null, null);
            node.setLeft(n);
            listToAdd.removeFirst();
        }
        if (level < ect.height() && node.getRight() == null && listToAdd.size() != 0) {
            Node<String> n = new Node<>(listToAdd.getFirst(), null, null);
            node.setRight(n);
            listToAdd.removeFirst();
        }
        insertInEmptySpace(ect, node.getLeft(), last, listToAdd, level + 1);
        insertInEmptySpace(ect, node.getRight(), last, listToAdd, level + 1);
    }
}
