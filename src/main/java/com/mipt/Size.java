package main.java.com.mipt;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Size {
  int min();
  int max();
  String msg() default "String length is out of bounds";
}
