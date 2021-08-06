package Exceptions;

public class CustomException extends Exception{

    public CustomException(String message, Exception e){

        super(message);
        e.printStackTrace();
    }

}
