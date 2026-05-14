package Classes;
import java.util.ArrayList;

public class Admin extends Users 
{
    private static ArrayList<Admin> adminList = new ArrayList<>();

    public Admin(String name, String email, String password, int id) 
    {
        super(name, "admin", email, password, id);
    }

    // Getters and Setters
    public static ArrayList<Admin> getAdminList() 
    {
        return adminList;
    }
    public static void setAdminList(ArrayList<Admin> adminList2) 
    {
        adminList = adminList2;
    }
}
