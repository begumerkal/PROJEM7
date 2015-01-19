package com.wyd.db.hibernate;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;
/**
 * 
 * 自定逻辑与处理函数,如果不进行此转换，执行逻辑与操作时作抛出：exception: unexpected char: '&'.<br>
 * e.g.：where 1 & flag = 1 可写成 where bitand(flag, 1) = 1
 * @author sunzx
 */
@SuppressWarnings("rawtypes")
public class BitAndFunction implements SQLFunction {
    public Type getReturnType(Type type, Mapping mapping) {
        return Hibernate.LONG;
    }

    public boolean hasArguments() {
        return true;
    }

    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    public String render(List args, SessionFactoryImplementor factory) throws QueryException {
        if (args.size() != 2) {
            throw new IllegalArgumentException("BitAndFunction requires 2 arguments!");
        }
        return args.get(0).toString() + " & " + args.get(1).toString();
    }
}
