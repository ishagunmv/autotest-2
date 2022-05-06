package data;

public enum EUsers {
    tester("tester", "test", "Гос. поверитель"),
    dbuser("dbuser", "test", "Оператор баз данных"),
    user("user", "test", "Пользователь"),
    tadmin("tadmin", "test", "Тех. Администратор"),
    admin("admin", "test", "Администратор"),
    support("support", "test", "Тех. Поддержка"),
    system("system", "test", "Root");


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    private String login;
    private String password;
    private String name;

    EUsers(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
}
