public class CompareElement extends Element implements Comparable<Element> {

    public CompareElement(int atomicNumber, String element, String symbol, double atomicWeight, double atomicMass, double period, double group, String phase, double displayRow, double displayColumn) {
        super(atomicNumber, element, symbol, atomicWeight, atomicMass, period, group, phase, displayRow, displayColumn);
    }

    @Override
    public int compareTo(Element element) {
        if (this.getElementName().compareTo(element.getElementName()) > 0){
            return 1;
        } else if (this.getElementName().compareTo(element.getElementName()) <0 ){
            return -1;
        } else {
            return 0;
        }
    }
}
