package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private static UserService service = new UserServiceImpl();
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        service.createUsersTable();

        service.saveUser("peter","parker", (byte) 18);
        service.saveUser("j.j.","jameson", (byte) 54);
        service.saveUser("doctor","octopus", (byte) 37);
        service.saveUser("cat","woman", (byte) 22);

        service.removeUserById(1);

        List<User> users = service.getAllUsers();
        for(User u : users) {
            System.out.println(u.toString());
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
