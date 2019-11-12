package com.example.rxjavabeginning.UnionOperations.FlatMap;

import java.util.ArrayList;

public class UserGroup {
    final private ArrayList<String> users;

    public UserGroup(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}