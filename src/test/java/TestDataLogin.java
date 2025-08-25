public class TestDataLogin {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public TestDataLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public TestDataLogin() {
    }
}
