public class Country {

    /**
     * Sigla do país.
     */
    private String sigla;

    /**
     * Nome do continente ao que o país pertence.
     */
    private String continent;

    /**
     * Nome do país.
     */
    private String countryName;

    /**
     * Populacao total do país.
     */
    private String population;

    /**
     * Populacao com idade acima dos 65 anos do país.
     */
    private double ageOlder65;

    /**
     * Total de mortes relacionadas com doenças cardiovasculares no país.
     */
    private double cardiovascDeathRate;

    /**
     * Prevalencia de diabetes em percentagem no país.
     */
    private double diabetesPrevalence;

    /**
     * Prevalencia de mulheres fumadoras em percentagem no país.
     */
    private double femaleSmokers;

    /**
     * Prevalencia de homens fumadoras em percentagem no país.
     */
    private double maleSmokers;

    /**
     * Número de camas de hospital por cada 1000 pessoas no país.
     */
    private double hospitalBedsPerThousand;

    /**
     * Esperança de vida no país.
     */
    private double lifeExpectancy;

    /**
     * Constructor da classe Country.
     */
    public Country(String sigla, String continent, String countryName, String population, String agedOlder65,
                   String cardiovasc_death_rate, String diabetes_prevalence, String female_smokers, String male_smokers,
                   String hospital_beds_per_thousand, String life_expectancy) {
        this.sigla = sigla;
        this.continent = continent;
        this.countryName = countryName;
        this.population = population;

        if (agedOlder65.equals("NA")) {
            this.ageOlder65 = 0;
        } else {
            this.ageOlder65 = Double.parseDouble(agedOlder65);
        }

        if (cardiovasc_death_rate.equals("NA")){
            this.cardiovascDeathRate = 0;
        } else {
            this.cardiovascDeathRate = Double.parseDouble(cardiovasc_death_rate);
        }

        if (diabetes_prevalence.equals("NA")) {
            this.diabetesPrevalence = 0;
        } else {
            this.diabetesPrevalence = Double.parseDouble(diabetes_prevalence);
        }

        if (female_smokers.equals("NA")) {
            this.femaleSmokers = 0;
        } else {
            this.femaleSmokers = Double.parseDouble(female_smokers);
        }

        if (male_smokers.equals("NA")) {
            this.maleSmokers = 0;
        } else {
            this.maleSmokers = Double.parseDouble(male_smokers);
        }

        if (hospital_beds_per_thousand.equals("NA")) {
            this.hospitalBedsPerThousand = 0;
        } else {
            this.hospitalBedsPerThousand = Double.parseDouble(hospital_beds_per_thousand);
        }

        if (life_expectancy.equals("NA")){
            this.lifeExpectancy = 0;
        } else {
            this.lifeExpectancy = Double.parseDouble(life_expectancy);
        }
    }

    /**
     * Devolve a sigla do país
     * @return sigla
     */
    public String getSigla(){
        return this.sigla;
    }

    /**
     * Devolve o continente ao qual o país pertence
     * @return continent
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Devolve o nome do país em questão
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Devolve a percentagem de mulheres fumadoras do país
     * @return femaleSmokers
     */
    public double getFemaleSmokers() {
        return femaleSmokers;
    }

    /**
     * Devolve a percentagem de homens fumadores do país
     * @return maleSmokers
     */
    public double getMaleSmokers() {
        return maleSmokers;
    }
}
