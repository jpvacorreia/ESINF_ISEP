public class CompareAtomicMass extends Element implements Comparable<Element> {

    public CompareAtomicMass(int atomicNumber, String element, String symbol, double atomicWeight, double atomicMass, double period, double group, String phase, double displayRow, double displayColumn) {
        super(atomicNumber, element, symbol, atomicWeight, atomicMass, period, group, phase, displayRow, displayColumn);
    }

    @Override
    public int compareTo(Element element) {
        if (this.getAtomicMass() > element.getAtomicMass()){
            return 1;
        } else if (this.getAtomicNumber() < element.getAtomicNumber()){
            return -1;
        } else {
            return 0;
        }
    }
}
