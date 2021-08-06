package services;

import model.Employee;
import model.enumeration.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EmployeeRepository;

import java.util.ArrayList;

public class LogInAndLogOutService {

    private static final EmployeeRepository EMPLOYEEREPOSITORY = new EmployeeRepository();
    private static final Logger logger = LoggerFactory.getLogger(LogInAndLogOutService.class);


    public static ArrayList<Object> login(String username, String password){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        ArrayList<Object> loginData = new ArrayList<>();

        boolean logged = false;
        boolean freezed = false;

        if (employee != null){

            if (employee.getStatus() == Status.actived){

                if (employee.getPassword().equals(password)){

                    logged = true;
                    EMPLOYEEREPOSITORY.setLoggedValue(employee ,logged);
                }
            }else {

                freezed = true;
            }
        }

        loginData.add(logged);
        loginData.add(freezed);
        loginData.add(employee);

        return loginData;
    }

    public static boolean logout(String username){

        Employee employee = EMPLOYEEREPOSITORY.findByUsername(username);
        boolean logged = true;

        if (employee!= null){

            logged = false;
            EMPLOYEEREPOSITORY.setLoggedValue(employee, logged);
        }

        return logged;
    }
}
