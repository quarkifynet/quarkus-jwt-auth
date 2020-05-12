package tech.donau.quarkify.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class User extends PanacheEntity {
    public String login;
    public String email;
    public String password; // Use e.g Bcrypt to encrypt password, don't store it as plain text :)
}
