import static org.junit.Assert.assertArrayEquals;

class CountriesDataTest {


    @org.junit.jupiter.api.Test
    void orderArrayDescOrder2() {

        CountriesData cd = new CountriesData();

        int order = 2;
        String alpha = "192873465549786321";
        String expectedAlpha = "998877665544332211";

        String[][] matrixToOrder = new String[18][1];
        String[][] matrixOrdered = new String[18][1];

        for (int i = 0; i< matrixToOrder.length; i++) {
            matrixToOrder[i][0] = String.valueOf(alpha.charAt(i));
            matrixOrdered[i][0] = String.valueOf(expectedAlpha.charAt(i));
        }

        cd.sortArrayByOrder(matrixToOrder, 0, order);

        assertArrayEquals(matrixToOrder, matrixOrdered);
    }

    @org.junit.jupiter.api.Test
    void orderArrayDescOrder() {

        CountriesData cd = new CountriesData();

        int order = 2;
        String alpha = "549786321";
        String expectedAlpha = "987654321";

        String[][] matrixToOrder = new String[9][1];
        String[][] matrixOrdered = new String[9][1];

        for (int i = 0; i< matrixToOrder.length; i++) {
            matrixToOrder[i][0] = String.valueOf(alpha.charAt(i));
            matrixOrdered[i][0] = String.valueOf(expectedAlpha.charAt(i));
        }

        cd.sortArrayByOrder(matrixToOrder, 0, order);

        assertArrayEquals(matrixToOrder, matrixOrdered);
    }


    @org.junit.jupiter.api.Test
    void orderArrayAscOrder2() {

        CountriesData cd = new CountriesData();

        int order = 1;
        String alpha = "549712435679886321";
        String expectedAlpha = "112233445566778899";

        String[][] matrixToOrder = new String[18][1];
        String[][] matrixOrdered = new String[18][1];

        for (int i = 0; i< matrixToOrder.length; i++) {
            matrixToOrder[i][0] = String.valueOf(alpha.charAt(i));
            matrixOrdered[i][0] = String.valueOf(expectedAlpha.charAt(i));
        }

        cd.sortArrayByOrder(matrixToOrder, 0, order);

        assertArrayEquals(matrixToOrder, matrixOrdered);
    }

    @org.junit.jupiter.api.Test
    void orderArrayAscOrder() {

        CountriesData cd = new CountriesData();

        int order = 1; //ascending order
        String alpha = "549786321";
        String expectedAlpha = "123456789";

        String[][] matrixToOrder = new String[9][1];
        String[][] matrixOrdered = new String[9][1];

        for (int i = 0; i< matrixToOrder.length; i++) {
            matrixToOrder[i][0] = String.valueOf(alpha.charAt(i));
            matrixOrdered[i][0] = String.valueOf(expectedAlpha.charAt(i));
        }

        cd.sortArrayByOrder(matrixToOrder, 0, 1);

        assertArrayEquals(matrixToOrder, matrixOrdered);
    }

    @org.junit.jupiter.api.Test
    void joinTest(){
        CountriesData cd = new CountriesData();
        int[] arrayToJoin1 = {2,3,5,6,7,1};
        String[] arrayToJoin2 = {"b","a","c","d","u","i"};
        String[][] matrixJoinedExpected = new String[arrayToJoin1.length][2];

        for (int i = 0; i < matrixJoinedExpected.length; i++){
            matrixJoinedExpected[i][0] = arrayToJoin2[i];
            matrixJoinedExpected[i][1] = String.valueOf(arrayToJoin1[i]);
        }

        String[][] matrixJoinedGotFromMethod = cd.join(arrayToJoin1,arrayToJoin2);
        assertArrayEquals(matrixJoinedGotFromMethod,matrixJoinedExpected);
    }


}