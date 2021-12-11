import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ReadFileTest {

    ReadFile rd;

    public ReadFileTest() {
        rd = new ReadFile();
        rd.readfileElements("info.txt");
        rd.insertData(rd.getElementList());
        rd.bstWithEletronicConfigurations();
    }

    @Test
    public void searchByAtomicNumber() {
        Element element = rd.searchByAtomicNumber(rd.pt.root(), 8);
        assertEquals("Oxygen", element.getElementName());
        assertNull(rd.searchByAtomicNumber(rd.pt.root(), 120));
        element = rd.searchByAtomicNumber(rd.pt.root(), 5);
        assertEquals("Boron", element.getElementName());
    }

    @Test
    public void searchBySymbol() {
        Element element = rd.searchBySymbol(rd.st.root(), "O");
        assertEquals("Oxygen", element.getElementName());
        assertNull(rd.searchBySymbol(rd.st.root(), "çl"));
        element = rd.searchBySymbol(rd.st.root(), "Al");
        assertEquals("Aluminum", element.getElementName());
    }

    @Test
    public void searchByElementName() {
        Element element = rd.searchByElementName(rd.ent.root(), "Oxygen");
        assertEquals(8, element.getAtomicNumber());
        assertNull(rd.searchByElementName(rd.ent.root(), "çl"));
        element = rd.searchByElementName(rd.ent.root(), "Boron");
        assertEquals(10.81, element.getAtomicMass(), 0.1);
    }

    @Test
    public void searchByAtomicMass() {
        Element element = rd.searchByAtomicMass(rd.amt.root(), 15.999);
        assertEquals("Oxygen", element.getElementName());
        assertNull(rd.searchByAtomicMass(rd.amt.root(), 0));
        element = rd.searchByAtomicMass(rd.amt.root(), 10.81);
        assertEquals("Boron", element.getElementName());
    }

    @Test
    public void searchElementsInBetween() {
        ArrayList<Element> elements = rd.searchElementsInBetween(20, 65);
        ArrayList<Integer> returned = new ArrayList<>();
        for (Element e : elements) {
            returned.add(e.getAtomicNumber());
        }
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(23);
        expected.add(14);
        expected.add(12);
        expected.add(15);
        expected.add(27);
        expected.add(28);
        expected.add(20);
        expected.add(11);
        expected.add(19);
        expected.add(25);
        expected.add(22);
        expected.add(21);
        expected.add(16);
        expected.add(26);
        expected.add(29);
        expected.add(10);
        expected.add(18);
        expected.add(17);
        expected.add(24);
        expected.add(13);
        assertEquals(expected, returned);
    }

    @Test
    public void getTypeAndPhase() {
        ArrayList<Element> arrayList = new ArrayList<Element>();
        for (Element e : rd.elementList) {
            if (arrayList.size() < 10) {
                arrayList.add(e);
            }
        }

        String[][] expected = new String[7][6];
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                expected[i][j] = "0";
            }
        }

        expected[0][0] = "";
        expected[1][0] = "Alkali Metal";
        expected[2][0] = "Alkaline Earth Metal";
        expected[3][0] = "Halogen";
        expected[4][0] = "Metalloid";
        expected[5][0] = "Noble Gas";
        expected[6][0] = "Nonmetal";
        expected[0][1] = "artificial";
        expected[0][2] = "gas";
        expected[0][3] = "liq";
        expected[0][4] = "solid";
        expected[0][5] = "TOTAL";
        //
        expected[6][2] = "3"; // Nonmetal - gas
        expected[5][2] = "2"; // noble gas - gas
        expected[3][2] = "1"; // Halogen - gas
        expected[1][4] = "1"; // Alkali metal - solid
        expected[2][4] = "1"; // Alkaline Earth Metal - solid
        expected[4][4] = "1"; // Metalloid - solid
        expected[6][4] = "1"; // Nonmetal - solid

        expected[1][5] = "1"; // Alkali metal - Total
        expected[2][5] = "1"; // Alkaline Earth Metal - Total
        expected[3][5] = "1"; // Halogen - Total
        expected[4][5] = "1"; // Metalloid - Total
        expected[5][5] = "2"; // noble gas - Total
        expected[6][5] = "4"; // Nonmetal - Total

        String[][] returned = rd.getTypeAndPhase(arrayList);
        assertEquals(expected, returned);
    }

    @Test
    public void counterElecConfiguration() {
        Map<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
        ArrayList<Element> elements = new ArrayList<Element>();
        for (Element e : rd.pt.inOrder()) {
            elements.add(e);
        }

        ArrayList<String> array = new ArrayList<>();
        array.add("[Xe]");
        expected.put(32, array);
        ArrayList<String> array2 = new ArrayList<>();
        array2.add("[Ar]");
        array2.add("[Kr]");
        expected.put(18, array2);
        ArrayList<String> array3 = new ArrayList<>();
        array3.add("[Xe] 4f14");
        expected.put(17, array3);
        ArrayList<String> array4 = new ArrayList<>();
        array4.add("[Kr] 4d10");
        array4.add("[Rn]");
        expected.put(9, array4);
        ArrayList<String> array5 = new ArrayList<>();
        array5.add("[Ar] 3d10");
        array5.add("[He]");
        array5.add("[Ne]");
        array5.add("[Xe] 4f14 5d10");
        expected.put(8, array5);
        ArrayList<String> array6 = new ArrayList<>();
        array6.add("[Ar] 3d10 4s2");
        array6.add("[He] 2s2");
        array6.add("[Kr] 4d10 5s2");
        array6.add("[Ne] 3s2");
        array6.add("[Xe] 4f14 5d10 6s2");
        expected.put(7, array6);
        ArrayList<String> array7 = new ArrayList<>();
        array7.add("[Ar] 3d5");
        array7.add("[Kr] 4d5");
        array7.add("[Xe] 4f7");
        expected.put(2, array7);

        List<Map.Entry<String, Integer>> map = rd.counterElecConfiguration();
        Map<Integer, ArrayList<String>> returned = rd.getElectConfigurationBiggerThan(map, 1);
        assertEquals(expected, returned);
    }

    @Test
    public void bstWithEletronicConfigurations() {
        List<Map.Entry<String, Integer>> map = rd.counterElecConfiguration();
        Map<Integer, ArrayList<String>> mapToCreate = rd.getElectConfigurationBiggerThan(map, 1);
        LinkedList<String> listToSearch = new LinkedList<>();
        for (Integer level : mapToCreate.keySet()) {
            if (level > 2) {
                listToSearch.addAll(mapToCreate.get(level));
            }
        }

        String expected = rd.ect.toString();

        ElectronConfigurationTree ect = rd.bstWithEletronicConfigurations();
        String returned = ect.toString();

        assertEquals(expected, returned);
    }

    @Test
    public void getElectConfigturationBiggerThan() {
        List<Map.Entry<String, Integer>> map = rd.counterElecConfiguration();
        Map<Integer, ArrayList<String>> result = rd.getElectConfigurationBiggerThan(map, 31);
        Map<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("[Xe]");
        expected.put(32, expectedList);
        assertEquals(expected, result);
        result = rd.getElectConfigurationBiggerThan(map, 17);
        expectedList = new ArrayList<>();
        expectedList.add("[Ar]");
        expectedList.add("[Kr]");
        expected.put(18, expectedList);
        assertEquals(expected, result);
    }

    /*
        @Test
        public void getDistanceBetweenNodes() {
            ReadFile rd2 = new ReadFile();
            Element root = new Element(0, null, "Xe", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(root);
            Element elem1 = new Element(0, null, "H", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem1);
            Element elem2 = new Element(0, null, "O", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem2);
            Element elem3 = new Element(0, null, "C", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem3);
            Element elem4 = new Element(0, null, "I", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem4);
            Element elem5 = new Element(0, null, "Fe", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem5);
            Element elem6 = new Element(0, null, "Ar", 0, 0, 0, 0, null, 0, 0);
            rd2.insert(elem6);


            String node2 = "Xe";
            String node1 = "Ar";
            int returned = rd2.getDistanceBetweenNodes(rd2.root(), node1, node2);

            int expected = 6;
            assertEquals(expected, returned);
            // TEST NOT COMPLETE
        }
    */
    @Test
    public void getLowestCommonAcestor() {
        ReadFile rd2 = new ReadFile();
        Element root = new Element(0, null, "Xe", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(root);

        BST.Node<String> expected = rd2.ect.root();

        BST.Node<String> result = ReadFile.getLowestCommonAcestor(rd.ect.root(), "[Ar]", "[Kr]");
        assertEquals(expected, result);
    }
/*
    @Test
    public void getLevel() {
        ReadFile rd2 = new ReadFile();
        Element root = new Element(0, null, "Xe", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(root);
        Element elem1 = new Element(0, null, "H", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem1);
        Element elem2 = new Element(0, null, "O", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem2);
        Element elem3 = new Element(0, null, "C", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem3);
        Element elem4 = new Element(0, null, "I", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem4);
        Element elem5 = new Element(0, null, "Fe", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem5);
        Element elem6 = new Element(0, null, "Ar", 0, 0, 0, 0, null, 0, 0);
        rd2.insert(elem6);

        int expected = 3;
        int result = ReadFile.getLevel(rd2.root(), "H", 0);

        assertEquals(expected, result);
    }

*/

    @Test
    public void buildCompleteBSTFromExistingTree() {
        String expected = "[[Kr] 4d10, [He], [Xe], [Ar] 3d10, [Kr], [Ne], [Xe] 4f14 5d10, [Ar], [Ar] 3d10 4s2, [He] 2s2, [Ar] 3d10 4s1, [Kr] 4d10 5s2, [Rn], [Xe] 4f14, [Xe] 4f14 5d10 6s2, 1s1, 1s2, [Ar] 3d1, [Ar] 3d1 4s2, [Ar] 3d10 4s2 4p1, [Ar] 3d10 4s2 4p2, [Ar] 3d10 4s2 4p3, [Ar] 3d10 4s2 4p4, [Ar] 3d10 4s2 4p5, [Ar] 3d10 4s2 4p6, [Ne] 3s2, [Ar] 3d2]";

        BST<String> newTree = rd.buildCompleteBSTFromExistingTree();
        Map<Integer, List<String>> mapResult = newTree.nodesByLevel();
        ArrayList<String> listResult = new ArrayList<>();

        for (Integer lvl : mapResult.keySet()) {
            listResult.addAll(mapResult.get(lvl));
        }
        String returnTree = listResult.toString();
        assertEquals(expected,returnTree);
    }


}