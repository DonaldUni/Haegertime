package repository;

import Helper.Helper;
import model.Employee;
import model.Project;
import model.enumeration.Power;
import model.enumeration.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ProjektRepositoryTest {

    private final ProjektRepository projektRepository = new ProjektRepository();
    private final String PROJECTSWITHFINALHOUR = "ProjectsWitFinalHour";
    private final String PROJECTWITHNONFINALHOUR = "ProjejectWithNonFinalHour";




    @Nested
    class GetMethodeTest{

        @Test
        @DisplayName("testGetFinalProjects")
        void testGetFinalProjects(){

            int expectedSize = 1;
            ArrayList<Project> projects = projektRepository.getFinalProjects();

            assertEquals(expectedSize, projects.size(), "should be equals");
        }

        @Test
        @DisplayName("testGetNonFinalProjects")
        void testGetNonFinalProjects(){

            int expectedSize = 3;
            ArrayList<Project> projects = projektRepository.getNonFinalProjects();

            boolean isDifferent = true;
            for (int i = 0; i < projects.size()-1; i++){

                Project project1 = projects.get(i);
                Project project2 = projects.get(i+1);

                if (isTotalyEquals(project1,project2)) {
                    isDifferent = false;
                    break;
                }
            }

            boolean isDifferent1 = isDifferent;

            assertAll(
                    () -> assertTrue(isDifferent1, "The projects must be different."),
                    () -> assertEquals(expectedSize, projects.size(), "The size is not expected.")
            );
        }

    }

    @Nested
    class ExistMethodeTest{

        @Test
        @DisplayName("testExistHour")
        void testExistHour(){

            Project existingProjectWithFinalHour = new Project(1,	"Autohaus",	1,	80,	12,	8,	"01.10.2021-01.11.2021");
            Project notExistingProjectWithFinalHour = new Project(30,	"NotExistingProjektName",	1,	80,	12,	8,	"01.10.2021-01.11.2021");;
            Project existingProjectWithNonFinalHour = new Project(2,	"HaegerTime",	3,	160,	0,	0,	"01.10.2021-01.11.2021");
            Project notExistingProjectWithNonFinalHour = new Project(100,	"NotExistingProjektName",	3,	160,	0,	0,	"01.10.2021-01.11.2021");


            assertAll(
                    ()-> assertTrue(projektRepository.existHour(existingProjectWithFinalHour, PROJECTSWITHFINALHOUR), ()-> "Should be true."),
                    ()-> assertTrue(projektRepository.existHour(existingProjectWithNonFinalHour, PROJECTWITHNONFINALHOUR), ()-> "Should be true."),
                    ()-> assertFalse(projektRepository.existHour(notExistingProjectWithFinalHour, PROJECTSWITHFINALHOUR), ()-> "Should be false."),
                    ()-> assertFalse(projektRepository.existHour(notExistingProjectWithNonFinalHour, PROJECTWITHNONFINALHOUR), ()-> "Should be false.")
            );
        }

        @Test
        @DisplayName("testExistHour")
        void existProject(){

            Project existingProjectWithNonFinalHour = new Project(2,	"HaegerTime",	3,	160,	0,	0,	"01.10.2021-01.11.2021");
            Project notExistingProjectWithNonFinalHour = new Project(100,	"NotExistingProjektName",	3,	160,	0,	0,	"01.10.2021-01.11.2021");

            assertAll(
                    ()-> assertTrue(projektRepository.existProject(existingProjectWithNonFinalHour),  "Should be true."),
                    ()-> assertFalse(projektRepository.existProject(notExistingProjectWithNonFinalHour),  "Should be false.")
            );
        }
    }

    @Nested
    class AddMethodTest{

        @Test
        @DisplayName("testAddProjectWithFinalHour")
        void testAddProjectWithFinalHour(){

            Project project = new Project(5,	"Motorrad",	7,	160,	100,	10,	"01.10.2021-01.11.2021");

            projektRepository.addProjectWithFinalHour(project);

            Project project1 = projektRepository.findProjectByID(project.getId_Projeckt(), PROJECTSWITHFINALHOUR);

            assertTrue( isTotalyEquals(project, project1), "should be equals.");
        }

        @Test
        @DisplayName("testAddProjectWithNonFinalHour")
        void testAddProjectWithNonFinalHour(){

            Project project = new Project(7,	"Flugzeug",	8,	150,	10,	10,	"01.10.2021-01.11.2021");

            projektRepository.addProjectWithNonFinalHour(project);

            Project project1 = projektRepository.findProjectByID(project.getId_Projeckt(), PROJECTWITHNONFINALHOUR);

            assertTrue( isTotalyEquals(project, project1), "should be equals.");
        }
    }


    @Nested
    class SetMethodeTest{

        @Test
        @DisplayName("testSetHourOfFinalProject")
        void testSetHourOfFinalProject(){

            Project newProject = new Project(1,	"Autohaus",	1,	83,	13,	9,	"01.10.2021-01.11.2021");
            Employee employee = new Employee( 1, "Richard", "Obama", 1,"Robama", "password1", "richard_obama@gmail.com",Power.Employee, Status.actived, 0f,0f,0f,"false");
            projektRepository.setHourOfFinalProject(newProject);

            ArrayList<Project> projects = projektRepository.findProjectsWithFinalHourByEmployeeNummer(employee);
            boolean equal = false;
            for (Project project: projects){

                if (project.getId_Projeckt() == newProject.getId_Projeckt()){
                    equal = isTotalyEquals(project, newProject);
                    break;
                }
            }

            assertTrue(equal, "Should be true.");

        }

        @Test
        @DisplayName("testSetHourOfNonFinalProject")
        void testSetHourOfNonFinalProject(){

            Project newProject = new Project(1,	"Autohaus",	1,	100,	100,	0,	"01.10.2021-01.11.2021");
            Employee employee = new Employee( 1, "Richard", "Obama", 1,"Robama", "password1", "richard_obama@gmail.com",Power.Employee, Status.actived, 0f,0f,0f,"false");
            projektRepository.setHourOfNonFinalProject(newProject);

            ArrayList<Project> projects = projektRepository.findProjectsWithNonFinalHourByEmployeeNummer(employee);
            boolean equal = false;
            for (Project project: projects){

                if (project.getId_Projeckt() == newProject.getId_Projeckt()){
                    equal = isTotalyEquals(project, newProject);
                    break;
                }
            }

            assertTrue(equal, "Should be true.");
        }

    }

    @Nested
    class FindMethodeTest {

        @Test
        @DisplayName("testFindProjectWithFinalHourByID")
        void testFindProjectWithFinalHourByID(){

            long existingProjectIDOfWithFinalHour = 1;
            long existingProjectIDWithNonFinalHour = 2;
            long notExistingProjectIDOfFinalHour = 12;
            long notExistingProjectIDOfNonFinalHour = 18;

            Project existingProjectWithFinalHour = new Project(1,	"Autohaus",	1,	80,	12,	8,	"01.10.2021-01.11.2021");
            Project existingProjectWithNonFinalHour = new Project(2,	"HaegerTime",	3,	160,	0,	0,	"01.10.2021-01.11.2021");

            Project requestedProject1 = projektRepository.findProjectByID(existingProjectIDOfWithFinalHour,PROJECTSWITHFINALHOUR);
            Project requestedProject2 = projektRepository.findProjectByID(existingProjectIDWithNonFinalHour, PROJECTWITHNONFINALHOUR);

            boolean equal1 = isEquals(existingProjectWithFinalHour, requestedProject1);
            boolean equal2 = isEquals(existingProjectWithNonFinalHour, requestedProject2);

            assertAll(
                    ()-> assertTrue(equal1, "should be true."),
                    ()-> assertTrue(equal2, "should be true."),
                    ()-> assertNull(projektRepository.findProjectByID(notExistingProjectIDOfFinalHour, PROJECTSWITHFINALHOUR), "should be null."),
                    ()-> assertNull(projektRepository.findProjectByID(notExistingProjectIDOfNonFinalHour,PROJECTWITHNONFINALHOUR), "should be null.")
            );
        }

        @Test
        @DisplayName("testFindProjectsWithNonFinalHourByEmployeeNummer")
        void testFindProjectsWithNonFinalHourByEmployeeNummer(){

            Employee employee = new Employee(3,		"Sandra",	"Merkel", 3,"Smerkel",	"password3",	"sandra_merkel@gmail.com",
                    Power.Administrator, Status.actived,	0f,	0f,	0f,	"false");

            Employee employee1 = new Employee(20,		"Sandra",	"Merkel", 20,"Smerkel",	"password3",	"sandra_merkel@gmail.com",
                    Power.Administrator, Status.actived,	0f,	0f,	0f,	"false");

            Project project = new Project(2,	"HaegerTime",	3,	160,	0,	0,	"01.10.2021-01.11.2021");

            ArrayList<Project> projects_1 = projektRepository.findProjectsWithNonFinalHourByEmployeeNummer(employee);

            boolean equals = isEquals(project,projects_1.get(0));
            int expectedSize = 1;

            assertAll(
                    ()-> assertTrue(equals, "should be true."), // test if the findMethod works for valid input
                    ()-> assertEquals(expectedSize, projects_1.size(), "should be true."), // test if the findMethod works for valid input
                    ()-> assertEquals(0, projektRepository.findProjectsWithNonFinalHourByEmployeeNummer(employee1).size(), "should be null") // test if the findMethod works for invalid input
            );
        }

        @Test
        @DisplayName("testFindProjectsWithFinalHourByEmployeenummer")
        void testFindProjectsWithFinalHourByEmployeenummer(){

            Employee employee = new Employee(1,	"Richard",	"Obama", 1, "Robama", "password1", "richard_obama@gmail.com",
                    Power.Employee,	Status.actived,	0f,	0f,	0f,	"false");

            Employee employee1 = new Employee(20,		"Sandra",	"Merkel", 20,"Smerkel",	"password3",	"sandra_merkel@gmail.com",
                    Power.Administrator, Status.actived,	0f,	0f,	0f,	"false");

            Project existingProject1OfEmployee = new Project(1,	"Autohaus",	1,	83,	13,	9,	"01.10.2021-01.11.2021");
            Project existingProject2OfEmployee = new Project(3,	"Bauhaus",	1, 160,	100,	10,	"01.10.2021-01.11.2021");

            ArrayList<Project> projects_1 = projektRepository.findProjectsWithFinalHourByEmployeeNummer(employee);

            boolean isEquals1 = isEquals(existingProject1OfEmployee,projects_1.get(0));
            boolean isEquals2 = isEquals(existingProject2OfEmployee,projects_1.get(1));
            int expectedSize = 2;

            assertAll(
                    ()-> assertTrue(isEquals1, "should be true."), // test if the findMethod works for valid input
                    ()-> assertTrue(isEquals2, "should be true."), // test if the findMethod works for valid input
                    ()-> assertEquals(expectedSize, projects_1.size(), "should be equals.") // test if the findMethod works for valid input
                    //()-> assertEquals(0, projektRepository.findProjectsWithFinalHourByEmployeeNummer(employee1).size(), "should be null") // test if the findMethod works for invalid input
            );
        }

    }


    @Test
    @DisplayName("testFinaliseAllMyWorkHourOfMouth")
    void testFinaliseAllMyWorkHourOfMouth(){

        long existingProjectId = 5;
        Employee employee = new Employee(5,		"Mark",	"Zuckenberg", 5,	"Mzugkenberg",	"password5",	"mark_zugkenberg@gmail.com",
                Power.Bookkeeper,	Status.actived,	0f,	0f,	0f,		"false");

        Employee employee1 = new Employee(20,		"Sandra",	"Merkel", 20,"Smerkel",	"password3",	"sandra_merkel@gmail.com",
                Power.Administrator, Status.actived,	0f,	0f,	0f,	"false");

        Project existingProject = projektRepository.findProjectByID(existingProjectId,PROJECTWITHNONFINALHOUR);
        projektRepository.finaliseAllMyWorkHourOfMouth(employee);
        ArrayList<Project> finalizedProject = projektRepository.findProjectsWithFinalHourByEmployeeNummer(employee);
        ArrayList<Project> projects = projektRepository.findProjectsWithNonFinalHourByEmployeeNummer(employee);

        boolean isEquals = isTotalyEquals(existingProject,finalizedProject.get(0));
        int expectedSize = 0;
        assertAll(
                ()-> assertTrue(isEquals, "should be true."), // test if the findMethod works for valid input
                ()-> assertEquals(expectedSize, projects.size(), "The size of the non final Project of this employee should be zero.") // test if the findMethod works for valid input
        );
    }


    @Nested
    class DeleteMethodeTest{

        @Test
        @DisplayName("testDeleteProjectsWithFinalHour")
        void testDeleteProjectsWithFinalHour(){

            projektRepository.deleteProjectsWithFinalHour();

            ArrayList<Project> projects = projektRepository.getFinalProjects();

            int size = 0;               //actuel size of the database for RequestOfHolidays

            assertEquals(size, projects.size(), "should be equals.");
        }

        @Test
        @DisplayName("testDeleteProjectsWithNonFinalHour")
        void testDeleteProjectsWithNonFinalHour(){

            projektRepository.deleteProjectsWithNonFinalHour();

            ArrayList<Project> projects = projektRepository.getNonFinalProjects();

            int expectedSize = 0;

            assertEquals(expectedSize, projects.size(), "The size of the list of Project should be zero.");

        }

        @Test
        @DisplayName("testDropByEmployeenummer")
        void testDropByEmployeenummer(){

            Project existingProjectWithFinalHour = new Project(5,	"Motorrad",	7,	160,	100,	10,	"01.10.2021-01.11.2021");
            Project existingProjectWithNonFinalHour = new Project(2,	"HaegerTime",	3,	160,	0,	0,	"01.10.2021-01.11.2021");

            projektRepository.dropByEmployeeNummer(existingProjectWithFinalHour.getEmployeeNummer(), PROJECTSWITHFINALHOUR);
            projektRepository.dropByEmployeeNummer(existingProjectWithNonFinalHour.getEmployeeNummer(), PROJECTWITHNONFINALHOUR);

            Project project1 = projektRepository.findProjectByID(existingProjectWithFinalHour.getId_Projeckt(), PROJECTSWITHFINALHOUR);
            Project project2 = projektRepository.findProjectByID(existingProjectWithNonFinalHour.getId_Projeckt(), PROJECTWITHNONFINALHOUR);

            assertAll(
                    () -> assertNull(project1),
                    () -> assertNull(project2)
            );
        }

    }

    private boolean isEquals(Project project1, Project project2){

        boolean equal1 = project1.getId_Projeckt() == project2.getId_Projeckt();
        boolean equal2 = project1.getProjectName().equals(project2.getProjectName());
        boolean equal3 = project1.getEmployeeNummer() == project2.getEmployeeNummer();

        return equal1 && equal2 && equal3;
    }

    private boolean isTotalyEquals(Project project1, Project project2){

        boolean equal1 = project1.getId_Projeckt() == project2.getId_Projeckt();
        boolean equal2 = project1.getProjectName().equals(project2.getProjectName());
        boolean equal3 = project1.getEmployeeNummer() == project2.getEmployeeNummer();
        boolean equal4 = project1.getWorkhour() == project2.getWorkhour();
        boolean equal5 = project1.getOvertime() == project2.getOvertime();
        boolean equal6 = project1.getUndertime() == project2.getUndertime();

        return equal1 && equal2 && equal3 && equal4 && equal5 && equal6;
    }



}
