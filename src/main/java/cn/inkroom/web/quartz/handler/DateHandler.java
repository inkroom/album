package cn.inkroom.web.quartz.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 墨盒
 * @Date 18-8-21
 */
@MappedTypes({Date.class})
@MappedJdbcTypes({JdbcType.DATE, JdbcType.DATETIMEOFFSET, JdbcType.TIMESTAMP, JdbcType.TIME})
public class DateHandler extends BaseTypeHandler<Date> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public DateHandler() {
        logger.debug("{}被创建", this.getClass().toString());
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
//        super.setParameter(ps, i, parameter, jdbcType);
        Timestamp timestamp = new Timestamp(parameter.getTime());
        //指定Calendar即可解决时差
        ps.setTimestamp(i, timestamp, Calendar.getInstance());

    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, date.toString());
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Date date = resultSet.getTimestamp(s, Calendar.getInstance());
        logger.debug("获取到的时间={}", date);

        return date;

    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Date date = resultSet.getTimestamp(i, Calendar.getInstance());
        logger.debug("获取到的时间={}", date);

        return date;
//        log.info("getNullableResult(ResultSet resultSet, int i)  " + resultSet.getDate(i).toString());
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {

        Date date = callableStatement.getTime(i, Calendar.getInstance());
        logger.debug("获取到的时间={}", date);

        return date;

    }
}
