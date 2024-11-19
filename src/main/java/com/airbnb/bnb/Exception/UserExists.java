package com.airbnb.bnb.Exception;

public class UserExists  extends RuntimeException{

    public UserExists(String msg){
        super(msg);
    }
}
