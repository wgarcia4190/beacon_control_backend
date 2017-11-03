package com.sigma.beacon_control.services;

import com.sigma.beacon_control.common.operation.ConnectionWrapper;
import com.sigma.beacon_control.common.operation.OperationSupport;
import com.sigma.beacon_control.common.operation.TransactionWrapper;
import com.sigma.beacon_control.common.util.*;
import com.sigma.beacon_control.common.util.exceptions.OperationException;
import com.sigma.beacon_control.common.util.exceptions.UnsentEmailException;
import com.sigma.beacon_control.model.Admin;
import com.sigma.beacon_control.model.Application;
import com.sigma.beacon_control.model.OauthApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by Wilson on 4/30/17.
 */
public class UserService extends Service {

    private static final String USER_PREFIX = "/user";
    private static final Logger log = LogManager.getLogger(UserService.class);

    private static final Route loginUserRoute = (Request request, Response response) -> {
        log.debug("****************** Login");

        String email = OperationSupport.getStringFromParam(request, "email");
        String password = OperationSupport.getStringFromParam(request, "password");

        if (EmailUtility.validate(email)) {
            Admin user = new ConnectionWrapper<Admin>().wrap(() ->
                    Admin.findFirst("email = ?", email)
            );

            boolean success = Password.checkPassword(password, user.getEncryptedPassword());

            if (success && user.getConfirmedAt() != null) {
                String token = JwtManager.generateToken(email, Long.parseLong(user.getId().toString()));
                OperationSupport.addObjToSession(request, "X-API-TOKEN", token);
                OperationSupport.addObjToSession(request, "userId", user.getLongId());
                OperationSupport.addObjToSession(request, "accountId", user.getAccountId());
                response.header("X-API-TOKEN", token);

                boolean userUpdated = new TransactionWrapper<Boolean>().wrap(() -> {
                    user.setSignInCount(user.getSignInCount() + 1);
                    user.setLastSignInAt(user.getCurrentSignInAt());
                    user.setCurrentSignInAt(new Timestamp(System.currentTimeMillis()));
                    user.setLastSignInIp(user.getCurrentSignInIp());
                    user.setCurrentSignInIp(request.ip());

                    return user.save();
                });

                Long applicationId = new ConnectionWrapper<Long>().wrap(()->{
                    Application application = Application.findFirst("account_id = ? AND default_app = true",
                            user.getAccountId());
                    return application.getLongId();
                });

                JSONObject userObject = new JSONObject();
                userObject.put("user",user.toJson(false,
                        "email", "default_beacon_uuid", "id", "role"));
                userObject.put("application_id", applicationId);

                if (userUpdated)
                    return getReturnJson("Sucess", 200, userObject.toJSONString());
                else
                    return getReturnJson("Error updating user", 500);
            }
            return getReturnJson("User not found", 401);
        }
        return getReturnJson("email format not valid", 301);

    };

    private static final Route signInUserRoute = (Request request, Response response) -> {
        log.debug("****************** Sign In");

        String email = OperationSupport.getStringFromParam(request, "email");

        if (EmailUtility.validate(email)) {
            boolean exist = new ConnectionWrapper<Boolean>().wrap(() -> {
                List<Admin> users = Admin.where("email = ?", email);
                return !users.isEmpty();
            });

            String emailMessage;

            if (!exist) {
                String password = new BigInteger(130, new SecureRandom()).toString(32);
                String confirmationToken = String.valueOf(UUID.randomUUID().getMostSignificantBits());
                int accountId = Config.getInt("app.account.id");

                boolean userSaved = new TransactionWrapper<Boolean>().wrap(() -> {
                    Admin admin = new Admin();
                    admin.setEmail(email);
                    admin.setEncryptedPassword(password);
                    admin.setSignInCount(0);
                    admin.setConfirmationToken(confirmationToken);
                    admin.setConfirmationSentAt(new Timestamp(System.currentTimeMillis()));
                    admin.setAccountId(accountId);
                    admin.setRole(0);
                    admin.setWalkthrough(false);

                    return admin.save();
                });

                if (userSaved) {
                    emailMessage = Config.getConfigFile("/email-templates/sign-template.html");
                    emailMessage = emailMessage.replace("{{confirmation_token}}", confirmationToken)
                            .replace("{{email}}", email).replace("{{password}}", password);

                    try {
                        EmailUtility.sendEmail(email, "Welcome to SigmaPlus", emailMessage);
                    } catch (UnsentEmailException ex) {
                        Admin user = new ConnectionWrapper<Admin>().wrap(() ->
                                Admin.findFirst("email = ?", email)
                        );

                        user.delete(true);
                        return Service.getReturnJson(ex.getMessage(), ex.getCode());
                    }

                    return getReturnJson("Sucess", 200);
                }
                return getReturnJson("Error creating user", 500);
            }
            return getReturnJson("User already exits", 400);
        }
        return getReturnJson("email format not valid", 301);
    };

    private static Route updateUserRoute = (Request request, Response response) -> {
        log.debug("****************** Update User");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());
        String currentPassword = (String) requestJson.get("current_password");
        String newPassword = (String) requestJson.get("password");

        long userId = (long) OperationSupport.
                getAndVerifyObjFromSession(request, "userId");

        boolean isUpdated = new TransactionWrapper<Boolean>().wrap(() -> {
            Admin admin = Admin.findById(userId);
            admin.fromMap(OperationSupport.getBean(request.body()));

            if (currentPassword != null && !currentPassword.isEmpty()) {
                if (!Password.checkPassword(currentPassword, admin.getEncryptedPassword()))
                    throw new OperationException("Current password is not valid", 401);

                if (newPassword != null && !newPassword.isEmpty()) {
                    admin.setEncryptedPassword(newPassword);
                    admin.setResetPasswordSentAt(new Timestamp(System.currentTimeMillis()));
                    admin.setResetPasswordToken(request.headers("Authorization")
                            .replace("Bearer ", ""));
                }

            }
            return admin.save();
        });

        if (isUpdated)
            return getReturnJson("Sucess", 200, request.body());

        return getReturnJson("The user could'nt be updated", 422);
    };

    private static Route confirmUserRoute = (Request request, Response response) -> {
        log.debug("****************** Confirm User");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());
        String confirmationToken = (String) requestJson.get("confirmation_token");
        Admin user = new ConnectionWrapper<Admin>().wrap(() ->
                Admin.findFirst("confirmation_token = ?", confirmationToken)
        );

        if (user != null) {
            user.setConfirmedAt(new Timestamp(System.currentTimeMillis()));
            boolean isUpdated = new TransactionWrapper<Boolean>().wrap(user::save);

            if (isUpdated)
                return getReturnJson("Sucess", 200, request.body());
        }

        return getReturnJson("The user could'nt be confirmed", 422);
    };

    private static Route setPasswordRoute = (Request request, Response response) -> {
        log.debug("****************** Set Password");

        JSONObject requestJson = (JSONObject) getParser().parse(request.body());

        String confirmationToken = (String) requestJson.get("confirmation_token");
        String securityToken = (String) requestJson.get("security_token");
        String password = (String) requestJson.get("password");

        Admin user = new ConnectionWrapper<Admin>().wrap(() ->
                Admin.findFirst("confirmation_token = ?", confirmationToken)
        );

        boolean isUpdated = new TransactionWrapper<Boolean>().wrap(() -> {
            if (password != null && !password.isEmpty()) {
                if (!Password.checkPassword(securityToken, user.getEncryptedPassword()))
                    throw new OperationException("Current password is not valid", 401);

                user.setEncryptedPassword(password);
            }
            return user.save();
        });

        if (isUpdated)
            return getReturnJson("Sucess", 200, request.body());

        return getReturnJson("The user could'nt be updated", 422);
    };

    public static void loadRoutes() {
        log.debug("**************** Loading User Routes");
        Spark.path(AppFilter.PREFIX.concat(USER_PREFIX), () -> {
            Spark.post("/login", loginUserRoute);
            Spark.post("/signin", signInUserRoute);
            Spark.post("/confirm", confirmUserRoute);
            Spark.post("/password", setPasswordRoute);

            Spark.path("/secure", () -> {
                Spark.before("/*", AppFilter.addAuthenticationFilter);
                Spark.patch("/update", updateUserRoute);
            });
        });
    }
}
