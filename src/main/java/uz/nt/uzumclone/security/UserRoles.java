package uz.nt.uzumclone.security;

import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumclone.security.UserAuthorities.*;

public enum UserRoles {
    GUEST(List.of(READ)),
    USER(List.of(READ, BUY)),
    MODERATOR(List.of(READ, CREATE)),
    ADMIN(List.of(READ, UPDATE, CREATE)),
    SUPER_ADMIN(List.of(READ, UPDATE, DELETE, CREATE));

    UserRoles(List<UserAuthorities> authorities) {
        this.authorities = authorities;
    }

    List<UserAuthorities> authorities;

    public List<String> getAuthorities() {
        List<String> list = new ArrayList<>(this.authorities.stream()
                .map(UserAuthorities::getName)
                .toList());
        list.add("ROLE_" + this.name());

        return list;
    }
}
