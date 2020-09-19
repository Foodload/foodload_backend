package se.foodload.repository.utils;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.DoubleType;
import org.hibernate.type.ObjectType;

public class MyPostgreSQLDialect extends PostgreSQL82Dialect {
	public MyPostgreSQLDialect() {
		registerFunction("fts", new PgFullTextFunction());
		registerFunction("ts_rank", new StandardSQLFunction("ts_rank", DoubleType.INSTANCE));
		registerFunction("to_tsquery", new StandardSQLFunction("to_tsquery", ObjectType.INSTANCE));
	}
}
