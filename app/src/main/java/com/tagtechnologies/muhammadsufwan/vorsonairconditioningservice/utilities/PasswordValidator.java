package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator{

    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String PASSWORD_PATTERN2 =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN2);
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}