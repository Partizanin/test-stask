package com.clear.solution.webapp.component;

import com.clear.solution.webapp.model.User;
import java.lang.reflect.Field;

public class Patcher {
    public static void userPatcher(User existingUser, User patchedUser) throws IllegalAccessException {

        Class<?> userClass = User.class;
        Field[] userFields = userClass.getDeclaredFields();
        System.out.println(userFields.length);
        for (Field field : userFields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            Object value = field.get(patchedUser);
            if (value != null) {
                field.set(existingUser, value);
            }
            field.setAccessible(false);
        }

    }
}