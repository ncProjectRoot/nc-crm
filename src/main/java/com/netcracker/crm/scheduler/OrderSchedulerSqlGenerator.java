package com.netcracker.crm.scheduler;

import org.springframework.stereotype.Component;

/**
 * Created by Pasha on 13.05.2017.
 */
@Component
public class OrderSchedulerSqlGenerator {
    private String fieldName = "field";


    public String generateSqlForOnlineCsr(String baseSql, String field, int count) {
        StringBuilder builder = new StringBuilder(baseSql);
        builder.append(" AND ");
        builder.append(field);
        builder.append(" IN (");
        for (int i = 0; i < count; i++) {
            builder.append(":" + fieldName + i);
            if (i < count - 1) {
                builder.append(",");
            }
        }
        builder.append(")");
        return builder.toString();
    }


    public String getField() {
        return fieldName;
    }
}
