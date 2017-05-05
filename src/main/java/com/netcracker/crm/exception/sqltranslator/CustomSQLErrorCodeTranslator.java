package com.netcracker.crm.exception.sqltranslator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.netcracker.crm.exception.sqltranslator.PostgreSqlError.DUPLICATE_COLUMN;

/**
 * Created by bpogo on 5/5/2017.
 */
@Component
public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Autowired
    public CustomSQLErrorCodeTranslator(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected DataAccessException customTranslate
            (String task, String sql, SQLException sqlException) {
        if (sqlException.getErrorCode() == DUPLICATE_COLUMN.getCode()) {
            return new DuplicateKeyException(
                    "DuplicateKeyException qwe qwe 123", sqlException);
        }
        return null;
    }
}
