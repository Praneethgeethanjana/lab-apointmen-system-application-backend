package com.praneeth.lab.utilities;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class MobileNumberValidator {


    private static final int VALID_MOBILE_LENGTH_STANDARD = 10;
    private static final int TAG_MOBILE_NUMBER_LENGTH = 11;
    private static final String MOBILE_NUMBER_START_PREFIX = "0";
    private static final String TAG_MOBILE_NUMBER_START_PREFIX = "94";
    private static final String HIDDEN_NUMBER_FORMAT = "XXXXXXX";

    public String getMobileStandardFormat(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isEmpty()) return null;

        String msisdn = ""; //LIKE 07XXXXXXXX

        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        phoneNumber = phoneNumber.replaceAll("\\D+", "");

        if (phoneNumber.startsWith("+940") && phoneNumber.length() == 13) {
            msisdn = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(4);
        } else if (phoneNumber.startsWith("+94") && phoneNumber.length() == 12) {
            msisdn = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("94") && phoneNumber.length() == 11) {
            msisdn = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(2);
        } else if (phoneNumber.startsWith("0") && phoneNumber.length() == 10) {
            msisdn = phoneNumber;
        } else if (phoneNumber.startsWith("7") && phoneNumber.length() == 9) {
            msisdn = MOBILE_NUMBER_START_PREFIX + phoneNumber;
        } else {
            //check foreign number or not
            log.info("Method : getMobileStandardFormat Foreign mobile number found : " + phoneNumber);

            msisdn = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
            return ((msisdn.length() > 8 && msisdn.length() <= 20) && msisdn.matches("[0-9]+")) ? msisdn : null;
        }
        log.info("Method : getMobileStandardFormat local mobile number found : " + phoneNumber);
        return msisdn.length() == VALID_MOBILE_LENGTH_STANDARD && msisdn.matches("[0-9]+") ? msisdn : null;

    }


    public String getMobileStandardFormatWithOutZero(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return null;

        String msisdn = ""; //LIKE 07XXXXXXXX

        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        phoneNumber = phoneNumber.replaceAll("\\D+", "");

        if (phoneNumber.startsWith("+940") && phoneNumber.length() == 13) {
            msisdn = phoneNumber.substring(4);
        } else if (phoneNumber.startsWith("+94") && phoneNumber.length() == 12) {
            msisdn =  phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("94") && phoneNumber.length() == 11) {
            msisdn = phoneNumber.substring(2);
        } else if (phoneNumber.startsWith("0") && phoneNumber.length() == 10) {
            msisdn = phoneNumber;
        } else if (phoneNumber.startsWith("7") && phoneNumber.length() == 9) {
            msisdn = phoneNumber;
        } else {
            //check foreign number or not
            log.info("Method : getMobileStandardFormat Foreign mobile number found : " + phoneNumber);
            msisdn = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
            return ((msisdn.length() > 8 && msisdn.length() <= 20) && msisdn.matches("[0-9]+")) ? msisdn : null;
        }
        log.info("Method : getMobileStandardFormat local mobile number found : " + phoneNumber);
        return msisdn.length() == 9 && msisdn.matches("[0-9]+") ? msisdn : null;

    }

    public String getMobileNumberDonationPlatformStandardFormat(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.isEmpty()) return null;

        String msisdn = ""; // LIKE 947XXXXXXXX

        mobileNumber= mobileNumber.trim().replaceAll("\\s","");
        mobileNumber = mobileNumber.replaceAll("\\s+", "");
        mobileNumber = mobileNumber.replaceAll("\\D+", "");

        if (mobileNumber.startsWith("+094") && mobileNumber.length() == 13) {
            msisdn = mobileNumber.substring(2);
        } else if (mobileNumber.startsWith("+94") && mobileNumber.length() == 12) {
            msisdn = mobileNumber.substring(1);
        } else if (mobileNumber.startsWith("94") && mobileNumber.length() == 11) {
            msisdn = mobileNumber;
        } else if (mobileNumber.startsWith("0") && mobileNumber.length() == 10) {
            msisdn = TAG_MOBILE_NUMBER_START_PREFIX + mobileNumber.substring(1);
        } else if (mobileNumber.startsWith("7") && mobileNumber.length() == 9) {
            msisdn = TAG_MOBILE_NUMBER_START_PREFIX + mobileNumber;
        } else {
            //check foreign number or not
            log.info("Method : getMobileNumberTagsStandardFormat Foreign mobile number found : " + mobileNumber);

            msisdn = mobileNumber.startsWith("+") ? mobileNumber.substring(1) : mobileNumber;
            return ((msisdn.length() > 8 && msisdn.length() <= 20) && msisdn.matches("[0-9]+")) ? msisdn : null;
        }
        log.info("Method : getMobileNumberTagsStandardFormat local mobile number found : " + mobileNumber);
        return msisdn.length() == TAG_MOBILE_NUMBER_LENGTH && msisdn.matches("[0-9]+") ? msisdn : null;
    }

    public String getHiddenMobileNumber(String number) {

        String mobileStandardFormat = getMobileStandardFormat(number);
        if (mobileStandardFormat == null) return null;
        return HIDDEN_NUMBER_FORMAT + mobileStandardFormat.substring(7);
    }


}
