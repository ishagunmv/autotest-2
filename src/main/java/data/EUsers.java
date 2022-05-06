package data;

public enum EUsers {
    tester("tester", "test"),
    dbuser("dbuser", "test"),
    user("user", "test"),
    tadmin("tadmin", "test"),
    admin("admin", "test"),
    support("support", "test"),
    system("system", "test");

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    private String login;
    private String password;
    EUsers(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
