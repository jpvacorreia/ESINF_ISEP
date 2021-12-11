import java.util.*;


public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {


        // 1. Carregar e guardar a informação relativa aos países e respetivos dados da pandemia COVID-19 a partir do
        // ficheiro de texto fornecido.

        CountriesData countriesData = new CountriesData();
        countriesData.setMap(ReadData.readfile("owid-covid-data.csv"));
        Map<Country, ArrayList<DayData>> map = countriesData.getMap();
        CountriesData cd = new CountriesData();
        cd.setMap(map);

        System.out.println("O ficheiro foi lido.\n");
        System.out.println("De que exercício quer ver os resultados?");
        System.out.println("(2) Apresentar uma lista de países ordenados por ordem crescente do número mínimo de dias que foi necessário para atingir os 50.000 casos positivos.");
        System.out.println("(3) Devolver o total de novos_casos/novas_mortes por continente/mês, ordenado por continente/mês");
        System.out.println("(4) Devolver para cada dia de um determinado mês e para um dado continente, os países ordenados por ordem decrescente do número de novos casos positivos. Por exemplo, para o mês de setembro e para o continente Europa");
        System.out.println("(5) Devolver numa estrutura adequada, todos os países com mais de 70% de fumadores, ordenados por ordem decrescente do número de novas mortes.");
        System.out.println("(0) Sair");
        int exercise = sc.nextInt();
        int limit1 = 0;
        int limit2 = 0;

        while (exercise != 0) {
            switch (exercise) {
                case 2:
                    System.out.println("Pretende ver o resultado igual ao enunciado(1)? Ou toda a informação(!=1)?");
                    int type = sc.nextInt();
                    limit1 = 1000;
                    limit2 = -1;
                    if (type == 1) {
                        limit1 = 6;
                        limit2 = 55;
                    }
                    System.out.println("\n\nExercise 2:\n");
                    System.out.println("  iso_code        continent               location              date         total_cases      mindays");
                    String[][] orderedArrayEx2 = cd.orderCountriesByDays(/*map*/);
                    for (int i = 0; i < orderedArrayEx2.length; i++) {
                        if (i < limit1 || i > limit2) {
                            boolean flag = false;
                            for (Country c : map.keySet()) {
                                if (c.getSigla().compareTo(orderedArrayEx2[i][0]) == 0) {
                                    ArrayList<DayData> arrayDay = map.get(c);
                                    for (DayData day : arrayDay) {
                                        if (day.getTotalCases() > 50000 && !flag) {
                                            System.out.printf("%8s - %15s - %25s - %15s - %15s - %5s\n", orderedArrayEx2[i][0], c.getContinent(), c.getCountryName(), day.getDay(), day.getTotalCases(), orderedArrayEx2[i][1]);
                                            flag = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("Pretende ver os dias iguais ao enunciado(1)? Ou toda a informação(!=1)?");
                    type = sc.nextInt();
                    if (type == 1) {
                        limit1 = 6;
                        limit2 = 50;
                    } else {
                        limit1 = 100;
                        limit2 = -1;
                    }
                    System.out.println("\n\n Exercise 3:\n");
                    System.out.println("          continent    month           new_cases           new_deaths");
                    String[][] infoEx3 = cd.newInfoPerContinent(map);
                    for (int i = 0; i < infoEx3.length; i++) {
                        if (i < limit1 || i > limit2)
                            System.out.printf("%20s - %6s - %20s - %20s\n", infoEx3[i][0], infoEx3[i][1], infoEx3[i][2], infoEx3[i][3]);
                    }
                    break;


                case 5:
                    limit1 = 1000;
                    String[][] allCountries70SmokersNewDeaths = countriesData.allCountries70SmokersNewDeaths();

                    System.out.println("\n\nExercise 5:");
                    int iter = 1;
                    for (int i = 0; i < allCountries70SmokersNewDeaths.length; i++) {
                        if (i < limit1) {
                            System.out.printf("%d - [%s, %2.1f, %d]\n", iter, allCountries70SmokersNewDeaths[i][0], Double.parseDouble(allCountries70SmokersNewDeaths[i][1]), Integer.parseInt(allCountries70SmokersNewDeaths[i][2]));
                        }
                        iter++;
                    }
                    break;

                case 4:
                    System.out.println("Pretende ver os dias iguais ao enunciado(1)? Ou escolher a informação(!=1)?");
                    String continente = "Europe";
                    type = sc.nextInt();
                    int month = 9;
                    if (type == 1) {
                        limit1 = 3;
                        limit2 = 28;
                    } else {
                        limit1 = 1000;
                        limit2 = -1;
                        System.out.println("Digite o continente, o mês(1-9) e o dia(1-29/30/31) que quer ver.");
                        System.out.println("Continente:");
                        sc.nextLine();
                        continente = sc.nextLine();
                        if (continente.compareTo("Europe") == 0 || continente.compareTo("Africa") == 0 || continente.compareTo("Oceania") == 0 || continente.compareTo("North America") == 0 || continente.compareTo("South America") == 0 || continente.compareTo("Asia") == 0) {
                            System.out.println("Mês:");
                            month = sc.nextInt();
                            if (month >= 1 && month <= 9) {
                                System.out.println("Dia:");
                            } else {
                                System.out.println("O mês não existe");
                            }
                        }


                    }
                    System.out.println("\n\nExercise 4:\n");

                    int order = 2; //descending order
                    String continent = continente;

                    String[][] arrayNewCases = countriesData.newCasesByDayAndContinent(continent, month);

                    int indexDia = 1;
                    for (int dia = 1; dia < arrayNewCases[0].length; dia++) {
                        if (dia < limit1 || dia > limit2) {
                            System.out.printf("Dia %d --> \n", indexDia);
                            countriesData.sortArrayByOrder(arrayNewCases, dia, order);
                            for (int pais = 0; pais < arrayNewCases.length; pais++) {
                                if (arrayNewCases[pais][0] != null && arrayNewCases[pais][dia] != null) {
                                    System.out.printf("%s%-25s (%s)\n", "           ", arrayNewCases[pais][0], arrayNewCases[pais][dia]);
                                }
                            }
                        }
                        indexDia++;
                    }
                    break;
                default:
                    System.out.println("\nEsse exercício não existe\n");
                    break;
            }
            System.out.println("\nDe que exercício quer ver os resultados?");
            System.out.println("(2) Apresentar uma lista de países ordenados por ordem crescente do número mínimo de dias que foi necessário para atingir os 50.000 casos positivos.");
            System.out.println("(3) Devolver o total de novos_casos/novas_mortes por continente/mês, ordenado por continente/mês");
            System.out.println("(4) Devolver para cada dia de um determinado mês e para um dado continente, os países ordenados por ordem decrescente do número de novos casos positivos. Por exemplo, para o mês de setembro e para o continente Europa");
            System.out.println("(5) Devolver numa estrutura adequada, todos os países com mais de 70% de fumadores, ordenados por ordem decrescente do número de novas mortes.");
            System.out.println("(0) Sair");
            exercise = sc.nextInt();
        }
    }
}





