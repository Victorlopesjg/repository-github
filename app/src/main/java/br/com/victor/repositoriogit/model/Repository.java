package br.com.victor.repositoriogit.model;

import java.io.Serializable;

/**
 * Created by Victor Oliveira on 20/08/18.
 */
public class Repository implements Serializable {

    private String name;
    private String language;
    private Owner owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
