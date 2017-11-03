package com.sigma.beacon_control.common.operation;

import com.sigma.beacon_control.common.util.DataSource;
import com.sigma.beacon_control.common.util.exceptions.OperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.json.JsonException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;

import static spark.Spark.halt;

/**
 * Created by Wilson on 4/16/17.
 */
public class ConnectionWrapper<T> extends Wrapper<T>{
    private static final Logger log = LogManager.getLogger(ConnectionWrapper.class);

    @Override
    public T wrap(Operation<T> operation){
        T result = null;
        Base.open(DataSource.getDataSource());
        try{
            result = operation.operate();
        }catch (JsonException | ClassCastException ex){
            log.error("Error: " + ex.getMessage(), ex);
            halt(UNPROCESSABLE_ENTITY.getCode(), UNPROCESSABLE_ENTITY.getDesc());
        }catch (OperationException ex){
            log.error("Error: "+ ex.getMessage(), ex);
            halt(ex.getCode(), ex.getMessage());
        }catch (DBException ex){
            handleDBException(ex);
        }catch(Throwable ex){
            log.error("Error: ", ex);
            halt(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getDesc());
        } finally{
            Base.close();
        }
        return result;
    }


}
