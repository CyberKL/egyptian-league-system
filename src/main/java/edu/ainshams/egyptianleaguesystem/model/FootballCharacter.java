package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.Period;

public abstract class FootballCharacter {

    protected String name;
    protected LocalDate dateOfBirth;
    protected String nationality;
    protected int yellowCards;
    protected int redCards;
    protected int age;

    protected FootballCharacter(String name, LocalDate dateOfBirth, String nationality){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.yellowCards=0;
        this.redCards=0;
        calculateAge();
    }

    protected void calculateAge(){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        this.age = period.getYears();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
