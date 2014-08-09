package com.test.yura.jul31;

import java.io.Serializable;

/**
 * Created by yura on 31.07.14.
 */
public class Person implements Serializable {
    int Id;
    private String FirstName;
    private String LastName;
    private int DataDB;
    public Person () {};
    public Person (String FirstName, String LastName, int DataDB)
        {
            this.FirstName=FirstName;
            this.LastName=LastName;
            this.DataDB=DataDB;
        }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getDataDB() {
        return DataDB;
    }

    public void setDataDB(int dataDB) {
        DataDB = dataDB;
    }

    @Override
    public String toString() {
        String str=FirstName+" "+LastName+" "+DataDB;
        return str;
    }


}
