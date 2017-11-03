package com.sigma.beacon_control.common.util;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Wilson on 4/16/17.
 */
public final class DataSource {

    private static final Logger log = LogManager.getLogger(DataSource.class);
    private static final HikariDataSource dataSource = new HikariDataSource();

    private DataSource(){}

    static {
        try {
            dataSource.setDriverClassName(Config.getString("ds.driver"));
            dataSource.setJdbcUrl(Config.getString("ds.url"));
            dataSource.setUsername(Config.getString("ds.user"));
            dataSource.setPassword(Config.getString("ds.password"));

            log.debug("DataSource Ok");
        }catch (RuntimeException e){
            log.error("Error: ", e);
            throw e;
        }catch (Exception ex){
            log.error("Error: ", ex);
        }
    }

    public static javax.sql.DataSource getDataSource(){
        return dataSource;
    }
}
