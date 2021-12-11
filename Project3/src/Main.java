import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ReadFile fileReadObject = new ReadFile();

        fileReadObject.readfileElements("info.txt");
        ArrayList<Element> lista = fileReadObject.getElementList();
        for (Element e : lista) {
            System.out.println(e.getAtomicNumber() + " - " + e.getElementName());
        }

        fileReadObject.insertData(fileReadObject.getElementList());

        System.out.print("\nExercício 1a:\n");
        Element e = fileReadObject.searchByAtomicMass(fileReadObject.amt.root(), 10.81);
        System.out.println(e.getAtomicNumber() + " - " + e.getElementName() + " - " + e.getSymbol());


        System.out.print("\nExercício 1b:\n");
        ArrayList<Element> arrayList = fileReadObject.searchElementsInBetween(20, 65);
        for (Element element : arrayList) {
            System.out.println(element.getAtomicNumber() + " - " + element.getElementName() + " - " + element.getSymbol());
        }
        String[][] typeAndPhase = fileReadObject.getTypeAndPhase(arrayList);
        for (int i = 0; i < typeAndPhase.length; i++) {
            for (int j = 0; j < typeAndPhase[0].length; j++) {
                System.out.printf(" %20s ", typeAndPhase[i][j]);
            }
            System.out.println();
        }

        System.out.print("\nExercício 2a:");
        List<Map.Entry<String, Integer>> map = fileReadObject.counterElecConfiguration();
        Map<Integer, ArrayList<String>> newMap = fileReadObject.getElectConfigurationBiggerThan(map, 1);
        for (int str : newMap.keySet()) {
            System.out.printf("\n%2d  [%s]", str, newMap.get(str));
        }

        System.out.print("\n\nExercício 2b:\n");
        ElectronConfigurationTree ect = fileReadObject.bstWithEletronicConfigurations();
        System.out.println(ect.toString());


        System.out.print("\n\nExercício 2c:\n");
        String distance = fileReadObject.getMostDistantNodesByElectronConfiguration();
        System.out.println(distance);


        System.out.print("\n\nExercício 2d:\n");
        BST<String> completeBST = fileReadObject.buildCompleteBSTFromExistingTree();
        System.out.println(completeBST.toString());
    }
}
