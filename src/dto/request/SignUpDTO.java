package dto.request;

import java.util.HashSet;
import java.util.Set;

public class SignUpDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> strRole = new HashSet<>();

    public SignUpDTO() {
    }

    public SignUpDTO(int id, String name, String username, String email, String password, Set<String> strRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.strRole = strRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getStrRole() {
        return strRole;
    }

    public void setStrRole(Set<String> strRole) {
        this.strRole = strRole;
    }

    @Override
    public String toString() {
        return "SignUpDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", strRole=" + strRole +
                '}';
    }
}
