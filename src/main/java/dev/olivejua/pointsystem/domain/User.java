package dev.olivejua.pointsystem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class User extends BaseTimeEntity {

    @Column(name = "USER_ID")
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    protected User() {}

    public static User create(String name) {
        User user = new User();
        user.name = name;

        return user;
    }
}
