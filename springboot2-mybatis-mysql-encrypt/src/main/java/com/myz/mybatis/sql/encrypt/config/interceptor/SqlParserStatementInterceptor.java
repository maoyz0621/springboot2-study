package com.myz.mybatis.sql.encrypt.config.interceptor;// /**

import com.myz.mybatis.sql.encrypt.config.visitor.SelectVisitorImpl;
import com.myz.mybatis.sql.encrypt.config.visitor.UpsertStatementVisitor;
import com.myz.mybatis.sql.encrypt.utils.ReflectionUtils;
import com.myz.mybatis.sql.encrypt.utils.SqlParserUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StatementHandler 用来处理SQL的执行过程，我们可以在这里重写SQL非常常用。
 *
 * @author maoyz0621 on 2021/10/21
 * @version v1.0
 */
@Intercepts({
        // @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        // @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        // @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class SqlParserStatementInterceptor implements Interceptor {

    private static final int MAPPED_STATEMENT_INDEX = 0;

    private final static String QUERY = "query";

    private final static String UPDATE = "update";

    private final static String PREPARE = "prepare";

    private final static Map<Object, StatementHandler> STATEMENT_MAP = new ConcurrentHashMap<>(128);
    private final static Map<String, Statement> SQL_PARSER_MAP = new ConcurrentHashMap<>(1024);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();
        if (PREPARE.equals(name)) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = ReflectionUtils.getFieldValue(statementHandler, "delegate");

            ParameterHandler parameterHandler = statementHandler.getParameterHandler();
            MappedStatement mappedStatement = (MappedStatement) SqlParserUtils.getProperty(parameterHandler, "mappedStatement");
            // 枚举类
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

            // StatementHandler statementHandler0 = (StatementHandler) invocation.getTarget();
            // BoundSql boundSql1 = statementHandler0.getBoundSql();

            // SQL语句
            BoundSql boundSql = delegate.getBoundSql();
            String sql = boundSql.getSql();
            Statement statement;
            if ((statement = SQL_PARSER_MAP.get(sql)) == null) {
                SQL_PARSER_MAP.put(sql, statement = SqlParserUtils.getStatement(sql));
            }

            // select 语句
            if (statement instanceof Select) {
                Select select = (Select) statement;
                SelectBody selectBody = select.getSelectBody();
                selectBody.accept(new SelectVisitorImpl(boundSql));
            } else if (statement instanceof Insert) {
                Insert insert = (Insert) statement;
                insert.accept(new UpsertStatementVisitor(boundSql));
            } else if (statement instanceof Update) {
                Update update = (Update) statement;
                update.accept(new UpsertStatementVisitor(boundSql));
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap((Executor) target, this);
        } else if (target instanceof StatementHandler) {
            return Plugin.wrap((StatementHandler) target, this);
        }
        return Plugin.wrap(target, this);
    }
}