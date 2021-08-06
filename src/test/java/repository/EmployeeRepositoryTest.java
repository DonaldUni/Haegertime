package repository;

import Helper.Helper;
import model.Employee;
import model.enumeration.Power;
import model.enumeration.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository = new EmployeeRepository();


    @Test
    @DisplayName("testCloseConnection")
    void testCloseConnection(){

        employeeRepository.closeConnection();
        try {
            assertTrue(employeeRepository.getConnection().isClosed(), ()-> "should be open.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("testOpenConnection")
    void testOpenConnection(){

        employeeRepository.openConnection();
        try {
            assertFalse(employeeRepository.getConnection().isClosed(), ()-> "should be open.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("testExistUsername")
    void testExistUsername(){

        String existingUsername = "Smerkel";
        String notExistingUsername = "abc";

        assertAll(
                () ->assertTrue(employeeRepository.existUsername(existingUsername), ()-> "should be true with the username "+existingUsername),
                () ->assertFalse(employeeRepository.existUsername(notExistingUsername), ()-> "should be false with the employeenummer "+notExistingUsername)
        );
    }

    @Test
    @DisplayName("testExistEmployeenummer")
    void testExistEmployeenummer(){

        long existingEmployeenummer = 3;
        long notExistingEmployeenummer = 40;

        assertAll(
                () ->assertTrue(employeeRepository.existEmployeeNummer(existingEmployeenummer), ()-> "should be true with the employeenummer "+existingEmployeenummer),
                () ->assertFalse(employeeRepository.existEmployeeNummer(notExistingEmployeenummer), ()-> "should be false with the employeenummer "+notExistingEmployeenummer)
        );
    }


    @Test
    @DisplayName("testFindByUsername")
    void testFindByUsername(){

        Employee employee = new Employee("Sebastian", "Schwarz", 2, "schwarz", "password2", "sebastian_schwarz@gmail.com");
        String existingUsername = "schwarz";
        String notExistingUsername = "abc";
        Employee employee1 = employeeRepository.findByUsername(existingUsername);

        final boolean equals = isEquals(employee, employee1);

        Employee employee2 = employeeRepository.findByUsername(notExistingUsername);

        assertAll(

                () ->assertTrue(equals, ()-> "should be equals."),
                () ->assertNull(employee2,()-> "should be null.")
        );
    }

    @Test
    @DisplayName("testFindByEmployeenummer")
    void testFindByEmployeenummer(){

        Employee employee = new Employee("Sebastian", "Schwarz", 2, "schwarz", "password2", "sebastian_schwarz@gmail.com");
        long existingEmployeenummer = 2;
        long notExistingEmployeenummer = 40;
        Employee employee1 = employeeRepository.findByEmployeenummer(existingEmployeenummer);

        final boolean equals = isEquals(employee, employee1);

        Employee employee2 = employeeRepository.findByEmployeenummer(notExistingEmployeenummer);

        assertAll(

                () ->assertTrue(equals, ()-> "should be equals."),
                () ->assertNull(employee2,()-> "should be null.")
        );
    }

    @Test
    @DisplayName("testFindByAddEmployee")
    void testAddEmployee(){

        Employee employee = new Employee("Abc", "Def", 13, "abcdef", "password13", "abcdef@gmail.com");
        employeeRepository.addEmployee(employee);

        long employeenummer = 13;
        Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

        final boolean equals = isEquals(employee, employee1);

        assertTrue(equals, ()-> "should be equals.");

    }

    @Test
    @DisplayName("testGetAllUsser")
    void testGetAllUsser(){

        Helper.createEmployees().forEach( employeeRepository::addEmployee );
        int size = Helper.createEmployees().size();

        boolean equals = true;

        for(Employee employee : employeeRepository.getAllUser()){

            boolean tmp = contains(employee);

            if (!tmp){
                equals = false;
                break;
            }
        }

        assertTrue(equals, ()-> "should be true.");
        assertEquals(size,employeeRepository.getAllUser().size(), ()-> "should be equals.");

    }


    // I try many method to execute Test
    @Nested
    class UpdateMethodeRepositoryTest{

         @Test
         @DisplayName("testSetPasswordOfEmployee")
         void testSetPasswordOfEmployee(){

             Employee employee = new Employee("Abc", "Def", 13, "abcdef", "password13", "abcdef@gmail.com");
             String newPassword = "testPassword";
             employeeRepository.setPasswordOfEmployee(employee, newPassword);

             Employee employee1 = employeeRepository.findByEmployeenummer(employee.getEmployeeNummer());

             boolean equals = employee1.getPassword().equals(newPassword);

             assertTrue(equals, ()-> "should be equals.");

         }

        @Test
        @DisplayName("testSetUsedAndRestHolidays")
        void testSetUsedAndRestHolidays(){

            Employee employee = new Employee("test1", "Test2", 14, "test", "password14", "test@gmail.com");

            employeeRepository.addEmployee(employee);

            float numberOfUsedHolidays = 10;
            employee.setNumberOfUsedAndRestHoliday(numberOfUsedHolidays);

            float numberOfRestHolidays = employee.getNumberOfRestHoliday();
            float numberOfHolidays = employee.getNUMBEROFHOLIDAY();

            employeeRepository.setUsedAndRestHolidays(employee);

            Employee employee1 = employeeRepository.findByEmployeenummer(employee.getEmployeeNummer());

            boolean equal =  employee1.getNumberOfUsedHoliday() == numberOfUsedHolidays;
            boolean equal1 =  employee1.getNumberOfRestHoliday() == numberOfRestHolidays;
            boolean equal2 =  employee1.getNUMBEROFHOLIDAY() == numberOfHolidays;

            boolean equals = equal && equal1 && equal2;

            assertTrue(equals, ()-> "should be equals.");

        }

        @Test
        @DisplayName("testSetUsernameOfUserByUsername")
        void testSetUsernameOfUserByUsername(){

            String oldUsername = "test";
            String newUsername = "TEST";
            long employeenummer = 14;
            Employee employee = new Employee("test1", "Test2", employeenummer, oldUsername, "password14", "test@gmail.com");

            employeeRepository.addEmployee(employee);

            employeeRepository.setUsernameOfUserByUsername(oldUsername, newUsername);
            Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

            boolean equals = employee1.getUserName().equals(newUsername);

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetUsernameOfUserByEmployeenummer")
        void testSetUsernameOfUserByEmployeenummer(){

            String oldUsername = "username";
            String newUsername = "USERNAME";
            long employeenummer = 14;
            Employee employee = new Employee("em", "Ployee", employeenummer, oldUsername, "password15", "employee@gmail.com");

            employeeRepository.addEmployee(employee);

            employeeRepository.setUsernameOfUserByEmployeeNummer(employeenummer, newUsername);
            Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

            boolean equals = employee1.getUserName().equals(newUsername);

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetPowerOfUserByUsername")
        void testSetPowerOfUserByUsername(){


            Power newPower = Power.Administrator;
            String username = "USERNAME";

            employeeRepository.setPowerOfUserByUsername(username, newPower);
            Employee employee1 = employeeRepository.findByUsername(username);

            boolean equals = newPower == employee1.getPower();

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetPowerOfUserByEmployeenummer")
        void testSetPowerOfUserByEmployeenummer(){


            Power newPower = Power.Bookkeeper;
            long employeenummer = 14;

            employeeRepository.setPowerOfUserByEmployeeNummer(employeenummer, newPower);
            Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

            boolean equals = newPower == employee1.getPower();

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetPowerOfUserByUsername")
        void testSetStatusOfUserByUsername(){


            Status newStatus = Status.deactived;
            String username = "USERNAME";

            employeeRepository.setStatusOfUserByUsername(username, newStatus);
            Employee employee1 = employeeRepository.findByUsername(username);

            boolean equals = newStatus == employee1.getStatus();

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetStatusOfUserEmployeenummer")
        void testSetStatusOfUserEmployeenummer(){


            Status newStatus = Status.deactived;
            long employeenummer = 7;

            employeeRepository.setStatusOfUserByEmployeeNummer(employeenummer, newStatus);
            Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

            boolean equals = newStatus == employee1.getStatus();

            assertTrue(equals, "should be equals.");

        }

        @Test
        @DisplayName("testSetStatusOfUserEmployeenummer")
        void testSetLoggedValue(){

            boolean newloginValue = true;
            long employeenummer = 9;
            Employee employee = employeeRepository.findByEmployeenummer(employeenummer);

            employeeRepository.setLoggedValue(employee, newloginValue);

            Employee employee1 = employeeRepository.findByEmployeenummer(employeenummer);

            boolean equals = newloginValue == Helper.convertStringToBoolean(employee1.getLogged());

            assertTrue(equals, "should be equals.");

        }

    }

    @Nested
    class DeleteTest {

        @Test
        @DisplayName("testDeleteEmployees")
        void testDeleteEmployees(){

            employeeRepository.deleteEmployees();
            int size = 0;

            assertEquals(size,employeeRepository.getAllUser().size(), ()-> "should be equals.");

        }

        @Test
        @DisplayName("testDeleteEmployeeByUsername")
        void testDeleteEmployeeByUsername(){

            String existingUsername = "abcdef";
            long existingEmployeenummer = 13;
            employeeRepository.deleteEmployeeByUsername(existingUsername);

            assertNull(employeeRepository.findByEmployeenummer(existingEmployeenummer), ()-> "should be return null value.");

        }

        @Test
        @DisplayName("testDeleteEmployeeByEmployeenummer")
        void testDeleteEmployeeByEmployeenummer(){

            long existingEmployeenummer = 4;
            employeeRepository.deleteEmployeeByEmployeeNummer(existingEmployeenummer);

            assertNull(employeeRepository.findByEmployeenummer(existingEmployeenummer), ()-> "should be return null value.");

        }

    }


    private boolean isEquals(Employee employee, Employee employee1){

        boolean equalsFirstname = employee.getFirstname().equals(employee1.getFirstname());
        boolean equalsLastname = employee.getLastname().equals(employee1.getLastname());
        boolean equalsEmployeenummer = employee.getEmployeeNummer()  == employee1.getEmployeeNummer();
        boolean equalsUsername = employee.getUserName().equals(employee1.getUserName());
        boolean equalsPassword = employee.getPassword().equals(employee1.getPassword());
        boolean equalsEmail = employee.getEmail().equals(employee1.getEmail());
        boolean equalsPower = employee.getPower().equals(employee1.getPower());
        boolean equalsStatus = employee.getStatus().equals(employee1.getStatus());

        return equalsFirstname && equalsLastname && equalsEmployeenummer && equalsUsername && equalsPassword && equalsEmail && equalsPower && equalsStatus;
    }

    private boolean contains(Employee employee){

        boolean contain = false;

        for (Employee employee1 : Helper.createEmployees()){

            if (isEquals(employee, employee1)){

                contain = true;
                break;
            }
        }

        return contain;
    }

}
