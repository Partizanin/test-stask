package com.clear.solution.webapp.component;

import com.clear.solution.webapp.model.User;
import java.lang.reflect.Field;

public class Patcher {
    public static void userPatcher(User existingIntern, User incompleteIntern) throws IllegalAccessException {

        Class<?> userClass = User.class;
        Field[] userFields = userClass.getDeclaredFields();
        System.out.println(userFields.length);
        for (Field field : userFields) {
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            Object value = field.get(incompleteIntern);
            if (value != null) {
                field.set(existingIntern, value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }

    }
}