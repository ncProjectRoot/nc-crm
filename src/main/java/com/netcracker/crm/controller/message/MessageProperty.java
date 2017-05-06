package com.netcracker.crm.controller.message;

/**
 * Created by bpogo on 5/6/2017.
 */
public final class MessageProperty {
    //Validation Keys
    public static final String ERROR_CODE_REQUIRED = "validation.required";
    public static final String ERROR_CODE_USER_ALREADY_EXIST = "validation.user-already-exist";
    public static final String ERROR_CODE_WRONG_FORMAT = "validation.wrong-format";
    public static final String REPLACE_WILD_CARD = "%wild_card%";
    //Success Keys
    public static final String SUCCESS_USER_CREATED = "success.user-create";
    //Error Keys
    public static final String ERROR_SERVER_ERROR = "error.server-error";

    private MessageProperty() {
    }
}
