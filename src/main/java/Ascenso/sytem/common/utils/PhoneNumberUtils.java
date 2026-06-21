package Ascenso.sytem.common.utils;

import Ascenso.sytem.common.exception.BadRequestException;

public class PhoneNumberUtils {

    private  static final  String PHONE_REGEX = "^\\+263[1-9][0-9]{7}$";
    private PhoneNumberUtils(){

    }

    public static String normalize(String phoneNumber) throws IllegalAccessException {

        if(phoneNumber == null){
            return  null;
        }

        if(!phoneNumber.matches(PHONE_REGEX)){
            throw new BadRequestException(
                    "Invalid Phone number"
            );
        }

        phoneNumber = phoneNumber.trim().replace(" ","");

        if(phoneNumber.startsWith("+263")){
            return phoneNumber;
        }

        if(phoneNumber.startsWith("263")){
            return "+" + phoneNumber;
        }

        if (phoneNumber.startsWith("0")){
            return "+263" + phoneNumber.substring(1);

        }

        throw new IllegalAccessException(
                "Invalid Zimbabwean phone number"
        );

    }

}
