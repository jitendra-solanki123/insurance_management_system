package insurance_management_system;


public class UserSession {
    private static boolean loggedIn = false;

    public static void setLoggedIn(boolean status) {
        loggedIn = status;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }
}

