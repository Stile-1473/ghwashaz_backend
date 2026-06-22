package Ascenso.sytem.common.utils;

public class PhoneNumberUtils {

    private static final String PHONE_REGEX = "^\\+263[1-9][0-9]{8}$";

    private PhoneNumberUtils(){

    }

    public static String normalize(String phoneNumber) {

        if(phoneNumber == null){
            return  null;
        }

        phoneNumber = phoneNumber.trim().replace(" ","");

        if(phoneNumber.startsWith("+263")){
            return validate(phoneNumber);
        }

        if(phoneNumber.startsWith("263")){
            return validate("+" + phoneNumber);
        }

        if (phoneNumber.startsWith("0")){
            return validate("+263" + phoneNumber.substring(1));

        }

        throw new IllegalArgumentException(
                "Invalid Zimbabwean phone number"
        );

    }

    private static String validate(String phoneNumber) {
        if (!phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Invalid Zimbabwean phone number");
        }

        return phoneNumber;
    }

}
