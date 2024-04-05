import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class User {
    String userName;
    String passWord;
    String type;

    public User(String userName, String passWord, String type) {
        this.userName = userName;
        this.passWord = passWord;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return user.getUserName().equals(userName);
    }
}

class UserManager {
    static ArrayList<User> users = new ArrayList<>();
    static String filePath = "users.txt";
    static String adminCode = "Admin!";

    public void readFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 2){
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e){
            System.out.println("Read Users file failed!" + e.getMessage());
        }
    }

    public void signUp(Scanner console){
        String line = console.nextLine();
        String parts[] = line.split("\\s+");

        while (line != null && !line.equals("q")) {
            if (parts.length == 2) {
                addUser(parts[0], parts[1]);
                return;
            } else if (parts.length == 3) {
                if (parts[2].equals(adminCode)) {
                    addAdministrator(parts[0], parts[1]);
                    return;
                } else {
                    System.out.println("Wrong administrator code! Please reinput!");
                }
            } else {
                System.out.println("Invalid input! please reinput!");
            }
            System.out.print("sign up > ");
            line = console.nextLine();
        }
    }

    //1 means user, 2 means administrator
    public int login(Scanner console){
        String line = console.nextLine();
        String parts[] = line.split("\\s+");

        while (line != null && !line.equals("q")) {
            if (parts.length == 2) {
                User user = findUser(parts[0]);
                if (user == null || !user.getPassWord().equals(parts[1])) {
                    System.out.println("User name or password invalid! Please reinput");
                }
                else {
                    if (user.getType().equals("user")){
                        System.out.println("User login successfully!");
                        return 1;
                    }
                    else {
                        System.out.println("Administrator login successfully!");
                        return 2;
                    }
                }
            } else {
                System.out.println("Invalid input! Please reinput");
            }
            System.out.print("login > ");
            line = console.nextLine();
        }
        return 0;
    }

    public void addUser(String name, String password){
        String type = "user";
        User user = new User(name, password, type);
        for (User tempUser : users){
            if (tempUser.equals(user)){
                System.out.println("User name already exists!");
                return;
            }
        }
        users.add(user);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.newLine();
            writer.write(name + " " + password + " " + type);
            writer.close();
            System.out.println("Sign up successfully!");
        } catch (IOException e){
            System.out.println("Sign up failed! " + e.getMessage());
        }
    }

    public void addAdministrator(String name, String password){
        String type = "administrator";
        User user = new User(name, password, type);
        for (User tempUser : users){
            if (tempUser.equals(user)){
                System.out.println("User name already exists!");
                return;
            }
        }
        users.add(user);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.newLine();
            writer.write(name + " " + password + " " + type);
            writer.close();
            System.out.println("Sign up successfully!");
        } catch (IOException e){
            System.out.println("Sign up failed! " + e.getMessage());
        }
    }

    public User findUser(String name){
        for (User tempUser : users){
            if (tempUser.getUserName().equals(name)){
                return tempUser;
            }
        }
        return null;
    }
}
