package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/* Created by kay on 5/11/2014. */
/**
 *
 * This is serializable because hibernate throws an exception if it isn't. This isn't good enough for me,
 * as I'm unconvinced that this isn't an indication of an error in the JPA annotations - it looks
 * like an instance of the User entity itself is being used as a primary key value, which sounds wrong.
 * I don't understand what's happening and this deserves further research.
 */
@Entity
@Table(name = "appuser", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User implements Serializable {
    @SequenceGenerator(name="user_userid_seq", sequenceName = "user_userid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_userid_seq")
    @Id
    private int userId;

    @Column(name = "username")
    private String userName;

    private String password;

    /* Experiment making userconnection transient - should not effect functionality */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private transient List<UserConnection> userConnection;

    @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Game> games;

    /***
     * Public constructor for default initialization.
     */
    public User() {
        /* Needed for serialization compliance */
    }

    /***
     * Create a User with the specified username.
     * @param userName username.
     */
    public User(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
