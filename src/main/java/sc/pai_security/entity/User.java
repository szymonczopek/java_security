package sc.pai_security.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Users")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer userid;
 private String name;
 private String surname;
 private String login;
 private String password;
 public User() {
 }
 public User(String name, String surname, String login,
 String password) {
 this.name = name;
 this.surname = surname;
 this.login = login;
 this.password = password;
 }

    public Integer getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
} 