package main.java.com.mipt;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
  String msg() default "Value cant be null";
}
