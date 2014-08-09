package com.test.yura.jul31;



import java.util.LinkedList;

/**
 * Created by yura on 04.08.14.
 */
public interface PersonDAO {
    public void create(Person p);
    public void update(Person p);
    public void delete(Person p);
    public LinkedList<Person> readAll();
}
