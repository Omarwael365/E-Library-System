package Classes;

import java.util.Random;

import Database.DAO.UsersDAO;

public class Users 
{
    private String name;
    private String email;
    private String password;
    private int id;

    public static Random R = new Random();

    public final String role;

    public Users(String name, String role ,String email,String password, int id)
    {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.id = id;
    }
    public Users(String name, String role ,String email,String password)
    {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.id = UsersDAO.giveID();
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public int getId() 
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getRole() 
    {
        return role;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}