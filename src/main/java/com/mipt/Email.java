package main.java.com.mipt;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Email {
  String msg() default "The Email value is invalid";
}
