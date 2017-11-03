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
public class TransactionWrapper<T> extends Wrapper<T>{

    private static final Logger log = LogManager.getLogger(TransactionWrapper.class);

    @Override
    public T wrap(Operation<T> operation){
        T result = null;
        Base.open(DataSource.getDataSource());
        Base.openTransaction();

        try{
            result = operation.operate();
            Base.commitTransaction();
        }catch (JsonException | ClassCastException ex){
            Base.rollbackTransaction();
            log.error("Error: ", ex);
            halt(UNPROCESSABLE_ENTITY.getCode(), UNPROCESSABLE_ENTITY.getDesc());
        }catch (OperationException ex){
            Base.rollbackTransaction();
            log.error("Error: "+ex.getMessage(), ex);
            halt(ex.getCode(), ex.getMessage());
        }catch (DBException ex){
            Base.rollbackTransaction();
            handleDBException(ex);
        }catch(Throwable ex){
            Base.rollbackTransaction();
            log.error("Error: ", ex);
            halt(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getDesc());
        } finally{
            Base.close();
        }

        return result;
    }

}
