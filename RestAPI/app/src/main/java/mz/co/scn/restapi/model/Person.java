package mz.co.scn.restapi.model;

/**
 * Created by Sidónio Goenha on 06/08/2020.
 */
public class Person {
    private long id;
    private String name;

    public Person(){

    }
    public Person(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
