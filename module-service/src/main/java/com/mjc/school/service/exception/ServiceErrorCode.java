package com.mjc.school.service.exception;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST(Constants.ERROR_000002, "Author Id does not exist. Author Id is: %s"),
    TAG_ID_DOES_NOT_EXIST(Constants.ERROR_000003, "Tag Id does not exist. Tag Id is: %s"),
    COMMENT_ID_DOES_NOT_EXIST(Constants.ERROR_000004, "Comment Id does not exist. Comment Id is: %s"),
    VALIDATE_NEGATIVE_OR_NULL_NUMBER(
            Constants.ERROR_000010, "%s can not be null or less than 1. %s is: %s"),
    VALIDATE_NULL_STRING(Constants.ERROR_000011, "%s can not be null. %s is null"),
    VALIDATE_STRING_LENGTH(
            Constants.ERROR_000012, "%s can not be less than %d and more than %d symbols. %s is %s"),
    VALIDATE_INT_VALUE(Constants.ERROR_000013, "%s should be number"),
    ARGUMENT_TYPE_MISMATCH(Constants.ERROR_000021, "Argument type mismatch"),
    INVALID_URL(Constants.ERROR_000020, "Invalid URL");

    private final String message;

    ServiceErrorCode(String errorCode, String errorMessage) {
        this.message = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + errorMessage;
    }

    public String getMessage() {
        return message;
    }

    private static class Constants {
        private static final String ERROR_000001 = "000001";
        private static final String ERROR_000002 = "000002";
        private static final String ERROR_000003 = "000003";
        private static final String ERROR_000004 = "000004";
        private static final String ERROR_000010 = "000010";
        private static final String ERROR_000011 = "000011";
        private static final String ERROR_000012 = "000012";
        private static final String ERROR_000013 = "000013";
        private static final String ERROR_000020 = "000020";
        private static final String ERROR_000021 = "000021";

        private Constants() {}
    }
}
