
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.*;

public class CountriesData {

    /**
     * HashMap dos dados do ficheiro
     */
    Map<Country, ArrayList<DayData>> map;

    /**
     * Número de países do ficheiro
     */
    int numberOfCountries;

    /**
     * Construtor sem parâmetros da classe
     */
    public CountriesData() {
        map = new HashMap<>();
    }

    /**
     * Altera os valores dos atributso map e numberOfCountries de acordo com o parâmetro introduzido
     *
     * @param map
     */
    public void setMap(Map<Country, ArrayList<DayData>> map) {
        this.map = map;
        this.numberOfCountries = howManyCountriesInMap(map);
    }

    /**
     * Devolve o atributo map
     *
     * @return map
     */
    public Map<Country, ArrayList<DayData>> getMap() {
        return map;
    }

    /**
     * Devolve o tamanho da keySet do map, que corresponde ao número de países existentes no ficheiro .csv
     *
     * @param map
     * @return nº de países como integer
     */
    public int howManyCountriesInMap(Map map) {
        return map.keySet().size();
    }

    /**
     * Junta e organiza a informação de quantos dias demorou cada país a atingir 50000 casos. Utiliza o método
     * between da classe DAYS para calcular a diferença de dias. Utiliza também os métodos bubbleSort e join para
     * ordenar e juntar os arrays, respetivamente.
     *
     * @return a uma matriz de Strings com a informação do pais e do número de días que esse demorou a atingir o tal
     * limite de casos totais.
     */
    public String[][] orderCountriesByDays() {

        int order = 1; // ascending order
        String[] sigla = new String[numberOfCountries];
        int[] arrayMinDays = new int[numberOfCountries];
        int casesGreaterThan50k = 50000;
        int iterador = 0;

        for (Country c : map.keySet()) {
            ArrayList<DayData> arrayDay = map.get(c);
            LocalDate initial = LocalDate.of(2020, 1, 1);
            int casesAux = 1000000;
            for (DayData day : arrayDay) {
                if (day.getTotalCases() > casesGreaterThan50k) {
                    if (day.getTotalCases() < casesAux) {
                        casesAux = day.getTotalCases();
                        LocalDate dayAfter = day.getDay();
                        sigla[iterador] = c.getSigla();
                        arrayMinDays[iterador] = Math.abs(Integer.parseInt(String.valueOf(DAYS.between(dayAfter, initial))));
                        iterador++;
                    }
                }
            }
        }
        String[] siglaFinal = new String[iterador];
        int[] arrayMinDaysFinal = new int[iterador];
        for (int i = 0; i < iterador; i++) {
            arrayMinDaysFinal[i] = arrayMinDays[i];
            siglaFinal[i] = sigla[i];
        }

        String[][] array = join(arrayMinDaysFinal, siglaFinal);
        sortArrayByOrder(array, 1, order);

        return array;
    }


    /**
     * Recolhe a informação necessária do map e guardando-a numa matriz de Strings. Retorna a matriz sem
     * estar ordenada dado que inclui os nome do pais na primeira coluna de cada linha sendo que os seus dados
     * estão guardados nas restantes colunas dessa mesma linha.
     *
     * @param Continent, String com o continente escolhido
     * @param mes,       inteiro com o mês escolhido
     * @return casesByDay, matriz com a informação dos novos casos dos paises de um determinado continente e de
     * todos os dias de um determinado mes.
     */
    public String[][] newCasesByDayAndContinent(String Continent, int mes) {

        ArrayList<DayData> arrayDayData;
        int numberOfCountriesFromContinent = 0;

        for (Country c : map.keySet()) {
            if (c.getContinent().equals(Continent)) {
                numberOfCountriesFromContinent++;
            }
        }

        int numberOfDaysOfTheMonth = LocalDate.of(2020, mes, 1).lengthOfMonth();
        String[][] casesByDay = new String[numberOfCountriesFromContinent][numberOfDaysOfTheMonth + 1];

        int idx = 0;
        boolean flag = false;

        for (Country c : map.keySet()) {
            if (c.getContinent().equals(Continent)) {
                arrayDayData = map.get(c);
                for (DayData d : arrayDayData) {
                    if (d.getDay().getMonthValue() == mes) {
                        casesByDay[idx][d.getDay().getDayOfMonth()] = String.valueOf(d.getNewCases());
                        if (!flag) {
                            casesByDay[idx][0] = c.getCountryName();
                            flag = true;
                        }
                    }
                }
                idx++;
            }
            flag = false;
        }
        return casesByDay;
    }


    /**
     * Junta 2 arrays numa matriz de Strings, um por coluna.
     *
     * @param arr1
     * @param arr2
     * @return joined (Matriz do tipo String)
     */
    public String[][] join(int[] arr1, String[] arr2) {
        String[][] joined = new String[arr1.length][2];
        for (int i = 0; i < arr1.length; i++) {
            joined[i][0] = arr2[i];
            joined[i][1] = String.valueOf(arr1[i]);
        }
        return joined;
    }

    /**
     * Procura, calcula, organiza e devolve os novos casos e novas mortes de cada continente por cada mês.
     * Utiliza o método calculateNewInfo para calcular os novos casos e novas mortes de cada continente por mês
     * e recebe uma matriz com esses valores.
     *
     * @param map
     * @return allInfo, uma matriz que contém o nome do país, o número do mês e os valores calculados pelo método
     * calculateNewInfo em String.
     */
    public String[][] newInfoPerContinent(Map<Country, ArrayList<DayData>> map) {
        ArrayList<DayData> europeData = new ArrayList<>();
        ArrayList<DayData> africaData = new ArrayList<>();
        ArrayList<DayData> naData = new ArrayList<>();
        ArrayList<DayData> saData = new ArrayList<>();
        ArrayList<DayData> oceaniaData = new ArrayList<>();
        ArrayList<DayData> asiaData = new ArrayList<>();
        for (Country c : map.keySet()) {
            String continent = c.getContinent();
            ArrayList<DayData> arrayDay = map.get(c);
            for (DayData day : arrayDay) {
                switch (continent) {
                    case ("Europe"): {
                        europeData.add(day);
                        break;
                    }
                    case ("Africa"): {
                        africaData.add(day);
                        break;
                    }
                    case ("North America"): {
                        naData.add(day);
                        break;
                    }
                    case ("South America"): {
                        saData.add(day);
                        break;
                    }
                    case ("Oceania"): {
                        oceaniaData.add(day);
                        break;
                    }
                    case ("Asia"): {
                        asiaData.add(day);
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        int[][] africa = calculateNewInfo(africaData);
        int[][] asia = calculateNewInfo(asiaData);
        int[][] europe = calculateNewInfo(europeData);
        int[][] northAm = calculateNewInfo(naData);
        int[][] oceania = calculateNewInfo(oceaniaData);
        int[][] southAm = calculateNewInfo(saData);
        String[][] allInfo = new String[9 * 6][4];
        for (int i = 0; i < allInfo.length; i++) {
            if (i < africa.length) {
                allInfo[i][0] = "\"Africa\"";
                allInfo[i][1] = String.valueOf(i + 1);
                allInfo[i][2] = String.valueOf(africa[i][0]);
                allInfo[i][3] = String.valueOf(africa[i][1]);
            } else if (i < asia.length * 2) {
                allInfo[i][0] = "\"Asia\"";
                allInfo[i][1] = String.valueOf(i + 1 - (9));
                allInfo[i][2] = String.valueOf(asia[i - 9][0]);
                allInfo[i][3] = String.valueOf(asia[i - 9][1]);
            } else if (i < europe.length * 3) {
                allInfo[i][0] = "\"Europe\"";
                allInfo[i][1] = String.valueOf(i + 1 - (9 * 2));
                allInfo[i][2] = String.valueOf(europe[i - (9 * 2)][0]);
                allInfo[i][3] = String.valueOf(europe[i - (9 * 2)][1]);
            } else if (i < northAm.length * 4) {
                allInfo[i][0] = "\"North America\"";
                allInfo[i][1] = String.valueOf(i + 1 - (9 * 3));
                allInfo[i][2] = String.valueOf(northAm[i - (9 * 3)][0]);
                allInfo[i][3] = String.valueOf(northAm[i - (9 * 3)][1]);
            } else if (i < oceania.length * 5) {
                allInfo[i][0] = "\"Oceania\"";
                allInfo[i][1] = String.valueOf(i + 1 - (9 * 4));
                allInfo[i][2] = String.valueOf(oceania[i - (9 * 4)][0]);
                allInfo[i][3] = String.valueOf(oceania[i - (9 * 4)][1]);
            } else if (i < southAm.length * 6) {
                allInfo[i][0] = "\"South America\"";
                allInfo[i][1] = String.valueOf(i + 1 - (9 * 5));
                allInfo[i][2] = String.valueOf(southAm[i - (9 * 5)][0]);
                allInfo[i][3] = String.valueOf(southAm[i - (9 * 5)][1]);
            } else {
                allInfo[i][0] = "Something isn't right!";
                allInfo[i][1] = String.valueOf(i);
                allInfo[i][2] = "0";
                allInfo[i][3] = "0";
            }
        }
        return allInfo;
    }

    /**
     * Calcula os novos casos e as novas mortes de uma lista (ArrayList) passado por parâmetro e devolve uma matriz
     * de integers com essa informação.
     *
     * @param data
     * @return uma matriz de integers com essa informação acima.
     */
    public int[][] calculateNewInfo(ArrayList<DayData> data) {
        int[][] newCases = new int[9][2];
        for (DayData d : data) {
            newCases[d.getDay().getMonthValue() - 1][0] = newCases[d.getDay().getMonthValue() - 1][0] + d.getNewCases();
            newCases[d.getDay().getMonthValue() - 1][1] = newCases[d.getDay().getMonthValue() - 1][1] + d.getNewDeaths();
        }
        return newCases;
    }

    /**
     * Recolhe a informação necessária do map e guardando-a numa matriz de Strings
     * Ordena e retorna o a matriz ordenada pronta a imprimir ([pais, percentagem, total de mortes])
     *
     * @return countryPercentageTotalDeaths, matriz com a informação pronta a imprimir.
     */
    public String[][] allCountries70SmokersNewDeaths() {

        int order = 2; //descending order
        int percentOfSmokers = 70;
        int numberOfCountriesGreater70Smokers = 0;
        for (Country c : map.keySet()) {
            if ((c.getFemaleSmokers() + c.getMaleSmokers()) > percentOfSmokers) {
                numberOfCountriesGreater70Smokers++;
            }
        }

        String[][] countryPercentageTotalDeaths = new String[numberOfCountriesGreater70Smokers][3];
        ArrayList<DayData> arrayDayData;
        int idx = 0;
        int totalDeaths = 0;

        for (Country c : map.keySet()) {
            double totalPercentOfSmokers = (c.getFemaleSmokers() + c.getMaleSmokers());
            if (totalPercentOfSmokers > percentOfSmokers) {
                arrayDayData = map.get(c);
                for (DayData d : arrayDayData) {
                    totalDeaths = d.getTotalDeaths();
                }
                countryPercentageTotalDeaths[idx][0] = c.getCountryName();
                countryPercentageTotalDeaths[idx][1] = String.valueOf(totalPercentOfSmokers);
                countryPercentageTotalDeaths[idx][2] = String.valueOf(totalDeaths);
                idx++;
            }
            totalDeaths = 0;
        }
        sortArrayByOrder(countryPercentageTotalDeaths, 2, order);

        return countryPercentageTotalDeaths;
    }


    /**
     * Método de ordenação implementado (bubble sort optimized) para ordem descendente.
     *
     * @param arrayToOrder,  matriz genérica com informação dos paises, linhas = paises, colunas = int (dias, casos, etc.)
     * @param columnToOrder, coluna referência para comparação.
     * @param order,         order in which the array must be sorted (1 = ascending, 2 = descending)
     */
    public <E extends Comparable<E>> void sortArrayByOrder(E[][] arrayToOrder, int columnToOrder, int order) {
        boolean swap = true;
        int n = arrayToOrder.length;

        for (int i = 0; i < n - 1 && swap; i++) {
            swap = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arrayToOrder[j][columnToOrder] != null && arrayToOrder[j + 1][columnToOrder] != null) {
                    if (order == 2) {
                        if (Integer.parseInt(String.valueOf(arrayToOrder[j][columnToOrder])) < Integer.parseInt(String.valueOf(arrayToOrder[j + 1][columnToOrder]))) {
                            E[] temp = arrayToOrder[j];
                            arrayToOrder[j] = arrayToOrder[j + 1];
                            arrayToOrder[j + 1] = temp;
                            swap = true;
                        }
                    } else if (order == 1) {
                        if (Integer.parseInt(String.valueOf(arrayToOrder[j][columnToOrder])) > Integer.parseInt(String.valueOf(arrayToOrder[j + 1][columnToOrder]))) {
                            E[] temp = arrayToOrder[j];
                            arrayToOrder[j] = arrayToOrder[j + 1];
                            arrayToOrder[j + 1] = temp;
                            swap = true;
                        }

                    }
                } else {
                    int k = j + 1;
                    E[] aux = arrayToOrder[k];
                    while (k < n - i - 1) {
                        arrayToOrder[k] = arrayToOrder[k + 1];
                        k++;
                    }
                    arrayToOrder[k] = aux;
                }

            }
        }
    }
}