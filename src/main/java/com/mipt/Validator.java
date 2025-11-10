package main.java.com.mipt;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {
  public static ValidationResult validate(Object obj) {
    ValidationResult res = new ValidationResult();

    if (obj == null) {
      res.addError("Object cant be null");
      return res;
    }

    Field[] fields = obj.getClass().getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);

      try {
        Object val = field.get(obj);

        if (field.isAnnotationPresent(NotNull.class)) {
          NotNull notNull = field.getAnnotation(NotNull.class);
          if (val == null) {
            res.addError(notNull.msg());
          }
        }

        if (field.isAnnotationPresent(Size.class)) {
          Size size = field.getAnnotation(Size.class);
          if (val instanceof String) {
            String strVal = (String) val;
            int len = strVal.length();
            if (len < size.min() || len > size.max()) {
              res.addError(size.msg());
            }
          } else {
            if (val != null) {
              res.addError("Cant cast object type " + val.getClass().getName() + " to String");
            } else {
              res.addError("Object cant be null");
            }
          }
        }

        if (field.isAnnotationPresent(Range.class)) {
          Range range = field.getAnnotation(Range.class);
          if (val instanceof Integer) {
            Integer intVal = (Integer) val;
            if (intVal < range.min() || intVal > range.max()) {
              res.addError(range.msg());
            }
          } else {
            if (val != null) {
              res.addError("Cant cast object type " + val.getClass().getName() + " to Integer");
            } else {
              res.addError("Object cant be null");
            }
          }
        }

        if (field.isAnnotationPresent(Email.class)) {
          Email email = field.getAnnotation(Email.class);
          if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.trim().isEmpty()) {
              res.addError(email.msg());
            } else {
              String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
              if (!Pattern.matches(regex, strVal.trim())) {
                res.addError(email.msg());
              }
            }

          } else {
            if (val != null) {
              res.addError("Cant cast object type " + val.getClass().getName() + " to Integer");
            } else {
              res.addError("Object cant be null");
            }
          }
        }

      } catch (IllegalAccessException e) {
        res.addError("Illegal access exception to the field " + field.getName());
      }
    }

    return res;
  }
}
