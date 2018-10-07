package com.eljo.agileboard.graphql.input;

import com.eljo.agileboard.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInput {
    private long id;
    private String username;
    private String password;
    private String email;
    private String name;

    public User convertToUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setId(this.id);
        user.setPassword(this.password);
        user.setUsername(this.username);
        return user;
    }
}
