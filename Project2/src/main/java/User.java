public class User {

    /**
     * Nome do User.
     */
    private String userName;

    /**
     * Idade do User.
     */
    private int age;

    /**
     * Cidade do User.
     */
    private String city;

    /**
     * Constructor da classe User.
     */
    public User(String userName, int age, String city) {
        this.userName = userName;
        this.age = age;
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

}
