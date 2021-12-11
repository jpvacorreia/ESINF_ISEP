import java.time.LocalDate;

public class DayData {

    /**
     * Data à qual a informação importada representa.
     */
    private LocalDate day;

    /**
     * Total de casos acumulados.
     */
    private int totalCases;

    /**
     * Total de novos casos do dia.
     */
    private int newCases;

    /**
     * Total de mortes acumulados.
     */
    private int totalDeaths;

    /**
     * Total de mortes do dia.
     */
    private int newDeaths;

    /**
     * Total de testes realizados do dia.
     */
    private int newTests;

    /**
     * Total de testes realizados.
     */
    private int totalTests;


    /**
     * Constructor da classe DayData.
     */
    public DayData(String day, String totalCases, String newCases, String totalDeaths,
                   String newDeaths, String newTests, String totalTests) {

        String[] dayTemp = day.split("-");
        this.day = LocalDate.of(Integer.parseInt(dayTemp[0]),
                Integer.parseInt(dayTemp[1]), Integer.parseInt(dayTemp[2]));


        if (totalCases.equals("NA")) {
            this.totalCases = 0;
        } else {
            this.totalCases = Integer.parseInt(totalCases);
        }

        if (newCases.equals("NA")) {
            this.newCases = 0;
        } else {
            this.newCases = Integer.parseInt(newCases);
        }

        if (totalDeaths.equals("NA")) {
            this.totalDeaths = 0;
        } else {
            this.totalDeaths = Integer.parseInt(totalDeaths);
        }

        if (newDeaths.equals("NA")) {
            this.newDeaths = 0;
        } else {
            this.newDeaths = Integer.parseInt(newDeaths);
        }

        if (newTests.equals("NA")) {
            this.newTests = 0;
        } else {
            this.newTests = Integer.parseInt(newTests);
        }

        if (totalTests.equals("NA")) {
            this.totalTests = 0;
        } else {
            this.totalTests = Integer.parseInt(totalTests);
        }
    }

    /**
     * Devolve o total de casos de um país numa data.
     * @return totalCases
     */
    public int getTotalCases() {
        return totalCases;
    }

    /**
     * Devolve a data dos dados em questão relativos a um país
     * @return day
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * Devolve os novos casos de um país numa data.
     * @return
     */
    public int getNewCases() {
        return newCases;
    }

    /**
     * Devolve o total de mortes de um país numa data.
     * @return
     */
    public int getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * Devolve as mortes de um país numa data.
     * @return
     */
    public int getNewDeaths() {
        return newDeaths;
    }
}
