package com.netcracker.crm.exception.sqltranslator;

/**
 * Created by bpogo on 5/5/2017.
 * <p>
 * https://www.postgresql.org/docs/current/static/errcodes-appendix.html
 */
public enum PostgreSqlError {
    DUPLICATE_COLUMN(42701);

    private final int code;

    PostgreSqlError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
