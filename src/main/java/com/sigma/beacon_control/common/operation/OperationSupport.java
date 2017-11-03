package com.sigma.beacon_control.common.operation;

import com.sigma.beacon_control.common.util.HttpStatus;
import com.sigma.beacon_control.common.util.exceptions.OperationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boon.json.JsonFactory;
import org.javalite.activejdbc.Model;
import spark.Request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * Created by Wilson on 4/16/17.
 */
public class OperationSupport {

    private static final Logger log = LogManager.getLogger(OperationSupport.class);
    private static final HttpStatus UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    public static void assertContentIsNotNullOrEmpty(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new OperationException("Body missing or invalid content", UNPROCESSABLE_ENTITY.getCode());
        }
    }

    public static void assertModelExistById(Class modelClass, long entityId) {
        try {
            Method exist = modelClass.getDeclaredMethod("exist", Object.class);
            if (!(boolean) exist.invoke(null, entityId))
                throw new OperationException("Entity : " + modelClass.getSimpleName() + " could not be found",
                        NOT_FOUND.getCode());
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            ex.printStackTrace();

            log.error("Error: ", ex);
            throw new OperationException(INTERNAL_SERVER_ERROR.getDesc(), INTERNAL_SERVER_ERROR.getCode());
        }
    }

    public static void assertModelHasNotChild(Model model, Class modelChildClass) {
        List<Model> children = model.getAll(modelChildClass);
        if (children != null && !children.isEmpty())
            throw new OperationException("Entity: " + modelChildClass.getSimpleName() + " already exist",
                    UNPROCESSABLE_ENTITY.getCode());
    }

    public static void assertModelHasNotParent(Model model, Class modelParentClass) {
        Model parent = model.parent(modelParentClass);
        if (parent != null)
            throw new OperationException("Entity: " + modelParentClass.getSimpleName() + " already exist",
                    UNPROCESSABLE_ENTITY.getCode());
    }

    public static void assertModelHasChild(Model parent, Class childClass, long childId) {
        List<Model> children = parent.get(childClass, "id = ?");
        if (!(children != null && !children.isEmpty()))
            throw new OperationException("Entity: " + childClass.getSimpleName() + " does not exist",
                    UNPROCESSABLE_ENTITY.getCode());
    }

    public static Model findModelById(Class modelClass, long id) {
        assertModelExistById(modelClass, id);
        try {
            Method findById = modelClass.getDeclaredMethod("findById", Object.class);
            return (Model) findById.invoke(null, id);
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            log.error("Error: ", ex);
            throw new OperationException(INTERNAL_SERVER_ERROR.getDesc(), INTERNAL_SERVER_ERROR.getCode());
        }
    }

    public static Model findModelByEmail(Class modelClass, String email) {
        try {
            Class<String> stringClass = String.class;
            Class<Object[]> arrayObjectClass = Object[].class;
            Method find = modelClass.getDeclaredMethod("find", stringClass, arrayObjectClass);
            Object[] params = new Object[1];
            params[0] = email;
            List<Model> modelList = (List<Model>) find.invoke(null, "email = ?", params);
            if (modelList != null && !modelList.isEmpty())
                return modelList.get(0);

            throw new OperationException("Entity: " + modelClass.getName() + " could not be found",
                    NOT_FOUND.getCode());
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            log.error("Error: ", ex);
            throw new OperationException(INTERNAL_SERVER_ERROR.getDesc(), INTERNAL_SERVER_ERROR.getCode());
        }
    }

    public static Model findChild(Model parent, Class childModelClass, long childId) {
        List<Model> children = parent.get(childModelClass, "id = ?", childId);
        if (children != null && !children.isEmpty())
            return children.get(0);

        throw new OperationException("Entity: " + childModelClass.getSimpleName() + " could not be found",
                NOT_FOUND.getCode());
    }

    public static Model findFirstChild(Model model, Class childClass) {
        List<Model> childrenModel = model.getAll(childClass);
        if (childrenModel != null && !childrenModel.isEmpty())
            return childrenModel.get(0);

        throw new OperationException("Entity: " + childClass.getSimpleName() + " could not be found",
                NOT_FOUND.getCode());
    }

    public static Model findModel(Class modelClass, String fieldName, Object value) {
        try {
            Class<String> stringClass = String.class;
            Class<Object[]> arrayObjectClass = Object[].class;
            Method find = modelClass.getDeclaredMethod("find", stringClass, arrayObjectClass);
            Object[] params = new Object[1];
            params[0] = value;
            List<Model> modelList = (List<Model>) find.invoke(null, fieldName + " = ?", params);
            if (modelList != null && !modelList.isEmpty())
                return modelList.get(0);

            throw new OperationException("Entity: " + modelClass.getSimpleName() + " could not be found",
                    NOT_FOUND.getCode());
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            log.error("Error: ", ex);
            throw new OperationException(INTERNAL_SERVER_ERROR.getDesc(), INTERNAL_SERVER_ERROR.getCode());
        }
    }

    public static Map<String, Object> getBean(String content) {
        OperationSupport.assertContentIsNotNullOrEmpty(content);
        return JsonFactory.fromJson(content, Map.class);
    }

    public static void addObjToSession(Request request, String fieldName, Object fieldValue) {
        request.session().attribute(fieldName, fieldValue);
    }

    public static Object getAndVerifyObjFromSession(Request request, String fieldName) {
        if (request.session().attribute(fieldName) != null)
            return request.session().attribute(fieldName);

        throw new OperationException("Invalid " + fieldName, UNPROCESSABLE_ENTITY.getCode());
    }

    public static String getStringFromParam(Request request, String name) {
        try {
            String result = request.queryParams(name);
            if (result != null && !result.trim().isEmpty())
                return result;

            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        } catch (Exception ex) {
            log.error("Error: ", ex);
            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        }
    }

    public static long getLongFromParam(Request request, String name) {
        try {
            String result = request.queryParams(name);
            if (result != null && !result.trim().isEmpty())
                return Long.parseLong(result);

            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        } catch (Exception ex) {
            log.error("Error: ", ex);
            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        }
    }

    public static int getIntFromParam(Request request, String name) {
        try {
            String result = request.queryParams(name);
            if (result != null && !result.trim().isEmpty())
                return Integer.parseInt(result);

            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        } catch (Exception ex) {
            log.error("Error: ", ex);
            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        }
    }

    public static int getIntFromUrl(Request request, String name) {
        try {
            String result = request.params(":".concat(name));
            if (result != null && !result.trim().isEmpty())
                return Integer.parseInt(result);

            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        } catch (Exception ex) {
            log.error("Error: ", ex);
            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        }
    }

    public static long getLongFromUrl(Request request, String name) {
        try {
            String result = request.params(":".concat(name));
            if (result != null && !result.trim().isEmpty())
                return Long.parseLong(result);

            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        } catch (Exception ex) {
            log.error("Error: ", ex);
            throw new OperationException("Param " + name + " not found", NOT_FOUND.getCode());
        }
    }

    public static long getLongFromHeader(Request request, String header) {
        String value = request.headers(header);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new OperationException("invalid " + header + " header", UNAUTHORIZED.getCode());
        }
    }

    public static void checkHeaders(Request request, String... values) {
        for (String value : values) {
            if (request.headers(value) == null || request.headers(value).isEmpty())
                throw new OperationException(value + " header not found", UNPROCESSABLE_ENTITY.getCode());
        }
    }
}
