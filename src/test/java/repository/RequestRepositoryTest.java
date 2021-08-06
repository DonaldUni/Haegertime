package repository;

import model.RequestOfHoliday;
import model.enumeration.RequestStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class RequestRepositoryTest {

    private final RequestRepository requestRepository = new RequestRepository();
    @Test
    @DisplayName("testExistRequestOfHolidayById")
    void testExistRequestOfHolidayById(){

        long existingRequestID= 4;
        long notExistingRequestID = 40;

        assertAll(
                () ->assertTrue(requestRepository.existRequestOfHolidayById(existingRequestID), ()-> "should be true with the requestID "+existingRequestID),
                () ->assertFalse(requestRepository.existRequestOfHolidayById(notExistingRequestID), ()-> "should be false with the employeenummer "+ notExistingRequestID)
        );
    }

    @Test
    @DisplayName("testGetRequestOfHolidayById")
    void testGetRequestOfHolidayById(){

        long requestId = 14;
        long notExistingRequestId = 40;
        RequestOfHoliday request = new RequestOfHoliday(requestId, 10, Integer.parseInt("10"), "01.10.2021", "08.10.2021");

        requestRepository.addRequestOfHoliday(request);

        RequestOfHoliday request1 = requestRepository.getRequestOfHolidayById(requestId);

        boolean equals = isEquals(request, request1);

        RequestOfHoliday request2 = requestRepository.getRequestOfHolidayById(notExistingRequestId);;

        assertAll(

                () ->assertTrue(equals, ()-> "should be equals."),
                () ->assertNull(request2,()-> "should be null.")
        );
    }

    @Test
    @DisplayName("testGetAllRequestOfHoliday")
    void testGetAllRequestOfHoliday(){

        ArrayList<RequestOfHoliday> requests = requestRepository.getAllRequestOfHoliday();

        boolean duplicate = true;
        for (int i = 0; i < requests.size()-1; i++) {  // control if the array contains duplicate RequestOfHolidays

            RequestOfHoliday request = requests.get(i);
            RequestOfHoliday request1 = requests.get(i+1);

            duplicate = duplicate && isEquals(request, request1);
        }

        boolean equals = duplicate;
        int size = 8;               //actuel size of the database for RequestOfHolidays

        assertAll(
                () ->assertFalse(equals, "should be false."),
                () ->assertEquals(size, requests.size(),()-> "should be equals.")
        );
    }


    @Test
    @DisplayName("testAddRequestOfHoliday")
    void testAddRequestOfHoliday(){

        RequestOfHoliday newRequest = new RequestOfHoliday(20,	17,	3,	"01.10.2021",	"08.10.2021");
        requestRepository.addRequestOfHoliday(newRequest);

        RequestOfHoliday request = requestRepository.getRequestOfHolidayById(newRequest.getRequestID());

        boolean equals = isEquals(newRequest, request);

        assertTrue(equals, "should be equals.");

    }


    @Nested
    class UpdateMethodeRequestRepositoryTest{

        @Test
        @DisplayName("testSetStatusOfRequestOfHoliday")
        void testSetStatusOfRequestOfHoliday(){

            long requestId1 = 4;
            long requestId2 = 8;
            RequestStatus answerFromBookkeeper1 = RequestStatus.Confirm;
            RequestStatus answerFromBookkeeper2 = RequestStatus.Reject;

            requestRepository.setStatusOfRequestOfHoliday(requestId1, answerFromBookkeeper1); // test if that modifie the value of the status as expected
            requestRepository.setStatusOfRequestOfHoliday(requestId2, answerFromBookkeeper2); // test if that modifie the value of the status as expected

            RequestOfHoliday request1 = requestRepository.getRequestOfHolidayById(requestId1);
            RequestOfHoliday request2 = requestRepository.getRequestOfHolidayById(requestId2);

            assertAll(
                    () ->assertEquals(RequestStatus.Confirm, request1.getStatus(), ()-> "should be Confirm."),
                    () ->assertEquals(RequestStatus.Reject, request2.getStatus(), ()-> "should be Reject.")
            );
        }

        @Test
        @DisplayName("testSetRequestOfHoliday")
        void testSetRequestOfHoliday(){

        RequestOfHoliday newRequest = new RequestOfHoliday(5,	14,	20,	"01.10.2021",	"08.10.2021",	RequestStatus.Waiting,	"18.06.2021",	"17:41");
        requestRepository.setRequestOfHoliday(newRequest);

        RequestOfHoliday request = requestRepository.getRequestOfHolidayById(newRequest.getRequestID());

        boolean equals = isEquals(request, newRequest);

        assertTrue(equals, "should be equals.");

        }

    }

    @Test
    @DisplayName("testConvertStringToRequestStatus")
    void testConvertStringToRequestStatus(){

        String confirmStatus = "Confirm";
        String rejectStatus = "Reject";
        String waitingSatus = "abc";

        assertAll(
                () ->assertEquals(RequestStatus.Confirm, requestRepository.convertStringToRequestStatus(confirmStatus), ()-> "should be confirm."),
                () ->assertEquals(RequestStatus.Reject, requestRepository.convertStringToRequestStatus(rejectStatus), ()-> "should be reject."),
                () ->assertEquals(RequestStatus.Waiting, requestRepository.convertStringToRequestStatus(waitingSatus), ()-> "should be waiting.")
                );
    }

    @Test
    @DisplayName("testDeleteRequestOfHolidays")
    void testDeleteRequestOfHolidays(){

        requestRepository.deleteRequestOfHolidays();

        ArrayList<RequestOfHoliday> requests = requestRepository.getAllRequestOfHoliday();

        int size = 0;               //actuel size of the database for RequestOfHolidays

        assertEquals(size, requests.size(), "should be equals.");
    }



    private boolean isEquals(RequestOfHoliday request, RequestOfHoliday request1){

        boolean equal1 = request.getRequestID() == request1.getRequestID();
        boolean equal2 = request.getEmployeeNummer() == request1.getEmployeeNummer();
        boolean equal3 = request.getNumberOfRequestedDay() == request1.getNumberOfRequestedDay();
        boolean equal4 = request.getStartDate().equals(request1.getStartDate());
        boolean equal5 = request.getFinishDate().equals(request1.getFinishDate());
        boolean equal6 = request.getStatus() == request1.getStatus();

        return equal1 && equal2 && equal3 && equal4 && equal5 && equal6;

    }
}
