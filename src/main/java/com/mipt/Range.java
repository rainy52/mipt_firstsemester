package main.java.com.mipt;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
  int min();
  int max();
  String msg() default "The int value is out of bounds";
}
