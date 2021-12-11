public class CompareSymbol extends Element implements Comparable<Element> {


public CompareSymbol(int atomicNumber, String element, String symbol, double atomicWeight, double atomicMass, double period, double group, String phase, double displayRow, double displayColumn) {
        super(atomicNumber, element, symbol, atomicWeight, atomicMass, period, group, phase, displayRow, displayColumn);
        }

@Override
public int compareTo(Element element) {
        return Integer.compare(this.getSymbol().compareTo(element.getSymbol()), 0);
        }
}
