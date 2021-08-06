package services;

import model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminServicesTest {

    private final AdminServices adminServices = new AdminServices();

    @Test
    @DisplayName("getAllUserSortByRole")
    void getAllUserSortByRole(){

        String notExistingUserName = "WrongUsername";
        String existingUserName = "Smerkel";

        ArrayList<ArrayList<Employee>> employees = adminServices.getAllUserSortByRole(existingUserName);

        assertAll(
                () -> assertNull(adminServices.getAllUserSortByRole(notExistingUserName), "The result should be null."),
                () -> assertThat(employees.get(0).size(), is(2)),
                () -> assertThat(employees.get(1).size(), is(3)),
                () -> assertThat(employees.get(2).size(), is(5))
        );
    }
}
