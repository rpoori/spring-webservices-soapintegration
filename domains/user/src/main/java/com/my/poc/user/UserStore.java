package com.my.poc.user;

import java.util.List;

public interface UserStore {
    List<User> getUsers(String segment);
}
