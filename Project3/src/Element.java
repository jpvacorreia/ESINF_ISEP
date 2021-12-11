public class Element implements Comparable<Element> {

    private int atomicNumber;
    private String elementName;
    private String symbol;
    private double atomicWeight;
    private double atomicMass;
    private double period;
    private double group;
    private String phase;
    private String mostStableCrystal;
    private String type;
    private double ionicRadius;
    private double atomicRadius;
    private double electronegativity;
    private double firstIonizationPotential;
    private double density;
    private double meltingPoint; //in Kelvin
    private double boilingPoint; // in Kelvin
    private double isotopes;
    private String discoverer;
    private int yearOfDiscovery;
    private double specificHeatCapacity;
    private String electronConfiguration;
    private double displayRow;
    private double displayColumn;

    public Element(int atomicNumber, String elementName, String symbol, double atomicWeight, double atomicMass, double period, double group,
                   String phase, double displayRow, double displayColumn){
        this.atomicNumber = atomicNumber;
        this.elementName = elementName;
        this.symbol = symbol;
        this.atomicWeight = atomicWeight;
        this.atomicMass = atomicMass;
        this.period = period;
        this.group = group;
        this.phase = phase;
        this.displayRow = displayRow;
        this.displayColumn = displayColumn;
    }

    public void setMostStableCrystal(String mostStableCrystal) {
        this.mostStableCrystal = mostStableCrystal;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIonicRadius(double ionicRadius) {
        this.ionicRadius = ionicRadius;
    }

    public void setFirstIonizationPotential(double firstIonizationPotential) {
        this.firstIonizationPotential = firstIonizationPotential;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getDiscoverer() {
        return this.discoverer;
    }

    public void setYearOfDiscovery(int yearOfDiscovery) {
        this.yearOfDiscovery = yearOfDiscovery;
    }

    public void setElectronConfiguration(String electronConfiguration) {
        this.electronConfiguration = electronConfiguration;
    }

    public void setSpecificHeatCapacity(double specificHeatCapacity) {
        this.specificHeatCapacity = specificHeatCapacity;
    }

    public void setIsotopes(double isotopes) {
        this.isotopes = isotopes;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    public void setBoilingPoint(double boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    public void setMeltingPoint(double meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public void setAtomicRadius(double atomicRadius) {
        this.atomicRadius = atomicRadius;
    }

    public void setElectronegativity(double electronegativity) {
        this.electronegativity = electronegativity;
    }

    public int getYearOfDiscovery() {
        return yearOfDiscovery;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getElementName() {
        return elementName;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getDisplayColumn() {
        return displayColumn;
    }

    public double getDisplayRow() {
        return displayRow;
    }

    public double getGroup() {
        return group;
    }

    public double getPeriod() {
        return period;
    }


    public double getAtomicWeight() {
        return atomicWeight;
    }

    public double getBoilingPoint() {
        return boilingPoint;
    }

    public double getDensity() {
        return density;
    }

    public double getAtomicMass() {
        return atomicMass;
    }

    public String getElectronConfiguration() {
        return electronConfiguration;
    }

    public String getPhase() {
        return phase;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Element element) {
        if (this.getAtomicNumber() > element.getAtomicNumber()){
            return 1;
        } else if (this.getAtomicNumber() < element.getAtomicNumber()){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return  atomicNumber + " - " + elementName + '\'';
    }

}
