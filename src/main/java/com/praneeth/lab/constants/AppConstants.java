package com.praneeth.lab.constants;


import java.util.regex.Pattern;

/**
 * @author Navishka Darshana - navishka@jevigsoft.com
 * @project  ancybtrading-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/06/2023 - 23.16
 */

public class AppConstants {

    public static final class DetailConstants {
        public static final String HEADER_AUTH = "Authorization";

        public static final String TOKEN_SIGN_IN_KEY = "x+CTL*E4$D8Sp@CC";

        public static final String INTERNAL_CLIENT_BASIC_KEY = "cmVnaXN0ZXJlZF9kb25vcjo=";

        public static final String TEMPORARY_PASSWORD = "User@123";
        public static final String ASYNC_EXECUTOR = "threadPoolTaskExecutor";
        public static final String FORBIDDEN_RESOURCE = "You are not authorized to access this resource!";
    }

    public static final class PatternConstants {
        public static final String DATE_TIME_RESPONSE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        public static final String DATE_PATTERN = "yyyy-MM-dd";
        public static final String TIME_PATTERN = "HH:mm:ss";
        public static final String TIME_ZONE = "GMT";
        public static final String REGEX = "[^A-Za-z0-9]";
        public static final Pattern ALLOWED_CHARACTERS_PATTERN = Pattern.compile("[A-Za-z0-9\\s" + Pattern.quote("\",-[]()&.@!<>/\\s\\\\ ") +  "]*");

    }

    public static final class CacheConstants {
        public static final String STARPOINTS_ACCESS_TOKEN="STARPOINTS_ACCESS_TOKEN";
        public static final String CATEGORY_LIST="CATEGORY_LIST";
        public static final String POPULAR_CATEGORY_LIST="POPULAR_CATEGORY_LIST";
        public static final String DONOR_HOME_STATISTIC="DONOR_HOME_STATISTIC";
        public static final String FILTER_PROGRAM = "FILTER_PROGRAM";
        public static final String RECOMMENDED_PROGRAM = "RECOMMENDED_PROGRAM";
    }

    public static final class Email {
        public static final String VERIFY_EMAIL="Verify your email";
        public static final String EMAIL_CONFORM_URL="{frontend_base_url}/auth/email-verify?uid={token}";
        public static final String FORGOT_PASSWORD_URL="{frontend_base_url}/auth/reset-password?uid={token}";
        public static final String FORGOT_PASSWORD="Reset Your Password Now";

    }

    public static final class EmailBody {
        public static final String VERIFY_EMAIL_STARTING_LINE="You’re almost done creating your abc lab profile.";
        public static final String VERIFY_EMAIL_BODY="In order to verify your email, please click on the button below. Once you verify your email, you’ll be able to successfully complete creating your account and start registering your causes.";


        public static final String RESET_USER_PASSWORD_STARTING_LINE="Please reset your password";
        public static final String RESET_PASSWORD_STARTING_BODY="Please click the button below to access the link to reset your password.";

    }

    public static final class StarPointsConstants {
    }

    public static final class NotFoundConstants {
        public static final String NO_USER_FOUND = "user not found!";
        public static final String INVALID_USER_FOUND = "invalid referral user!";
        public static final String NO_PACKAGE_FOUND = "package not found!";

    }

    public static final class FileTypeConstants {
    }

    public static final class GenieCardConstant {

    }

    public static final class DuplicatedConstants {

    }

    public static final class SmsConstants {

    }

    public static final class proofDocumentNameConstants  {

    }

    public static final class ErrorConstants {

        public static final String INVALID_EMAIL = "Invalid email!";
        public static final String INVALID_CLIENT_ID = "Invalid client id!!";
        public static final String INVALID_ACTION = "Invalid action!";
        public static final String INVALID_DATE = "Invalid date format!";
        public static final String INVALID_PASSWORD = "Invalid password!";

        public static final String INVALID_LOGIN= "Invalid login credentials.";

        public static final String INVALID_LOGIN_ATTEMPTS = "Invalid login credentials. You have <count> attempts remaining.";
        public static final String ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please click on forgot password to a set a new password and try again.";
        public static final String ACCOUNT_BLOCKED = "Your account is currently blocked by the administrator. Please contact them for further information.";
        public static final String ACCOUNT_NOT_VERIFY = "our account is not yet verified. Please verify your account first!";
        public static final String ADMIN_ACCOUNT_LOCKED = " Your account was locked after too many failed login attempts. Please contact the service provider in order to set a new password and try again.";
        public static final String MODERATOR_ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please contact an administrator in the platform in order to set a new password and try again.";
        public static final String FINANCE_REVIEWER_ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please contact an administrator in the platform in order to set a new password and try again.";
        public static final String INVALID_OLD_PASSWORD = "Old password is invalid!";
        public static final String INVALID_PASSWORD_CHARACTER_LENGTH = "Password must be at least 6 characters!";
        public static final String INVALID_RE_TYPE_PASSWORD = "The password confirmation doest not match.";
        public static final String OTP_SEND_ERROR_MESSAGE = "Unable to send OTP.";
        public static final String OTP_VALID_ALREADY_ERROR = "Your final OTP code is still valid. Please use it during the OTP authentication step.";
        public static final String EMAIL_RESEND_ERROR = "You can only request the verification link twice within a minute. Please try again after the mentioned time period.";
        public static final String OTP_HAS_EXPIRED = "OTP has expired!";
        public static final String LARGE_FILE_SIZE = "File is too large to be uploaded. Files larger than 15MB are not currently supported.";
        public static final String INVALID_FILE_TYPE = "Invalid file type!";
        public static final String ORGANIZATION_LOGO_INVALID_FORMAT = "Organization logo should be in JPG/JPEG or PNG format!";
        public static final String REQ_CATEGORY_LIST_EMPTY = "Please select categories!";
        public static final String ACCOUNT_ALREADY_VERIFIED ="Account Already verified.";


        public static final String S3_FAILED_TO_SAVE_FILE ="Failed to save document to s3 bucket!";
        public static final String TOKEN_HAS_EXPIRED = "Authentication Failed. Your token has expired or is no longer active.";
    }


    public static final class RequiredFieldConstant{
        public static final String CHARITY_LOGO_REQUIRED = "Organization logo is required!";
    }

    public static final class LogConstant{

    }

    public static final class CacheConstant{
        /*
    Cache Constants
     */
        public static final String HOME_CACHE = "HOME_CACHE";
    }
}
