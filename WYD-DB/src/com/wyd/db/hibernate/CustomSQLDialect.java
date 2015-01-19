package com.wyd.db.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

/**
 * 自定义MySQLDialect，解决HIBERNATE不支持逻辑与(&)查询功能
 * @author sunxz
 *
 */
public class CustomSQLDialect extends MySQLDialect {
    public CustomSQLDialect() {
        super();
        registerFunction("bitand", new BitAndFunction());
        registerFunction("date_add", new SQLFunctionTemplate(Hibernate.DATE, "date_add(?1, INTERVAL ?2 ?3)"));  
    }
}
