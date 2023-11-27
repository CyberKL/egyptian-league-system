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

    protected FootballCharacter(String name, LocalDate dateOfBirth, String nationality, int yellowCards, int redCards){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        calculateAge();
    }

    protected void calculateAge(){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        this.age = period.getYears();
    }

}
