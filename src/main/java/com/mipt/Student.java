package com.mipt;

import java.util.Objects;

public class Student {
    private final int id;
    private String name;
    private double grade;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student entity = (Student) obj;
        return id == entity.id && name.equals(entity.name) && Double.compare(grade, this.grade) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }
}
