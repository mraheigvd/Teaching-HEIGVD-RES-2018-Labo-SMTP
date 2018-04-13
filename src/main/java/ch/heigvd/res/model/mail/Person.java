package ch.heigvd.res.model.mail;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: A person is reprensenting by a simple email adress
 * @Date: 13.04.2018
 */
public class Person {
    private String email;

    public Person(String e) {
        email = e;
    }

    public String getEmail() {
        return email;
    }
}
