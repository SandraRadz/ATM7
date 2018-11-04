public class User {
    private static int identityNumber;
    private static String firstName;
    private static String lastName;

    User(int id, String firstName, String lastName) {
        identityNumber = id;
        firstName = firstName;
        lastName = lastName;
    }

    public static int getIdentityNumber() {
        return identityNumber;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setIdentityNumber(int identityNumber) {
        User.identityNumber = identityNumber;
    }

    public static void setFirstName(String firstName) {
        User.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        User.lastName = lastName;
    }
}
