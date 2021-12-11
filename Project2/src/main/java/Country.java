public class Country {

    /**
     * Nome do país.
     */
    private String countryName;

    /**
     * Nome do continente ao que o país pertence.
     */
    private String continent;

    /**
     * Populacao total do país.
     */
    private String population;

    /**
     * Capital do país.
     */
    private String capital;

    /**
     * Latitude da capital do país.
     */
    private double latitude;

    /**
     * Longitude da capital do país.
     */
    private double longitude;


    /**
     * Constructor da classe Country.
     */
    public Country(String countryName, String continent, String population, String capital,
                   double latitude, double longitude) {
        this.countryName = countryName;
        this.continent = continent;
        this.population = population;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getContinent() {
        return continent;
    }

    public String getPopulation() {
        return population;
    }

    public String getCapital() {
        return capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static double distance(Country c1, Country c2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(c2.getLatitude() - c1.getLatitude());
        double lonDistance = Math.toRadians(c2.getLongitude() - c1.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to meters
        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }


}
