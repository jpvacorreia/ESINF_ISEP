import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    @Test
    public void distance() {
        Country c1 = new Country("a","a","10","a",-34.6131500, -58.3772300);
        Country c2 = new Country("a","a","10","a",-16.5000000, -68.1500000);
        Country c3 = new Country("a","a","10","a",-15.7797200, -47.9297200);
        Country c4 = new Country("a","a","10","a",-33.4569400, -70.6482700);
        Country c5 = new Country("a","a","10","a",4.6097100, -74.0817500);
        Country c6 = new Country("a","a","10","a",-0.2298500, -78.5249500);
        Country c7 = new Country("a","a","10","a",6.8044800, -58.1552700);
        Country c8 = new Country("a","a","10","a",4.9333300, -52.3333300);
        Country c9 = new Country("a","a","10","a",-25.3006600, -57.6359100);
        Country c10 = new Country("a","a","10","a",-12.0431800, -77.0282400);
        assertEquals(2334,(int) Country.distance(c1,c3),10);
        assertEquals(2159,(int) Country.distance(c2,c3),10);
        assertEquals(3007,(int) Country.distance(c3,c4),10);
        assertEquals(3664,(int) Country.distance(c3,c5),10);
        assertEquals(3777,(int) Country.distance(c3,c6),10);
        assertEquals(2750,(int) Country.distance(c3,c7),10);
        assertEquals(2354,(int) Country.distance(c3,c8),10);
        assertEquals(1462,(int) Country.distance(c3,c9),10);
        assertEquals(3165,(int) Country.distance(c3,c10),10);
        //10 km de margem porque existem informações diferentes sobre as distâncias entre as cidades.
    }


}