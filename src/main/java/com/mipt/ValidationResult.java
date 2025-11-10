package main.java.com.mipt;

import java.util.ArrayList;

public class ValidationResult {
  private boolean isValid;
  private ArrayList<String> errors;

  public ValidationResult() {
    this.isValid = true;
    this.errors = new ArrayList<>();
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean value) {
    isValid = value;
  }

  public ArrayList<String> getErrors() {
    return errors;
  }

  public void addError(String error) {
    errors.add(error);
    isValid = false;
  }
}
