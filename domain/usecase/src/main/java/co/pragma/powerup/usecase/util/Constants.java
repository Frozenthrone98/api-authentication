package co.pragma.powerup.usecase.util;

import java.math.BigDecimal;

public class Constants {

    //VALUES
    public static final BigDecimal MAX_BASE_SALARY = new BigDecimal("15000000");
    public static final BigDecimal MIN_BASE_SALARY = new BigDecimal("0");

    //ERRORS
    public static final String B001 = "B001";
    public static final String B002 = "B002";
    public static final String B003 = "B003";

    //MESSAGES
    public static final String BUSINESS_VALIDATIONS_BASE_SALARY_B001 = "Base Salary is required.";
    public static final String BUSINESS_VALIDATIONS_MIN_BASE_SALARY_B001 = "Minimum allowed base salary is ";
    public static final String BUSINESS_VALIDATIONS_MAX_BASE_SALARY_B001 = "Maximum allowed base salary is ";
    public static final String BUSINESS_VALIDATIONS_EMAIL_EXIST_B002 = "User with given email already exists.";
    public static final String BUSINESS_VALIDATIONS_USER_WITH_ROL_EXIST_B002 = "User with given rol already exists.";
    public static final String BUSINESS_VALIDATIONS_ROLE_NOT_FOUND_B003 = "Role not found.";

    private Constants() {
    }
}
