package com.spring.calculator.model;
import jakarta.validation.constraints.Min;

import javax.persistence.*;


@Entity // Entity tai PJO klasė sijungta su DB esančia lentele naudojant ORM technika
//Anotacija nurodo, jog susiesime POJO klasę su DB esančia lentelę, kurios pavadinimas "numbers"
@Table(name="numbers") // klasė visada vienaskaita, table daugyskaita

public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //attinka db AUTOINCREMENT
    @Column(name = "id")
    private int id;

    @Column(name = "sk1")
    @Min(value = 0, message = "Validacijos klaida: skaičius negali būti neigamas")
    private int sk1;

    @Column(name = "sk2")
    @Min(value = 0, message = "Validacijos klaida: skaičius negali būti neigamas")
    private int sk2;

    @Column(name = "action")
    private String action;

    @Column(name = "result")
    private double result;

    //Esamu duomenu bazėje įrašu paieškai ir trinimui
    public Number(int id, int sk1, int sk2, String action, double result) {
        this.id = id;
        this.sk1 = sk1;
        this.sk2 = sk2;
        this.action = action;
        this.result = result;
    }
//Naujo įrašo DB kūrimas
    public Number(int sk1, int sk2, String action, double result) {
        this.sk1 = sk1;
        this.sk2 = sk2;
        this.action = action;
        this.result = result;
    }

    // butinas naudojant Spring
    public Number() {
    }

    public int getId() {
        return id;
    }

    public int getSk1() {
        return sk1;
    }

    public int getSk2() {
        return sk2;
    }

    public String getAction() {
        return action;
    }

    public void setSk1(int sk1) {
        this.sk1 = sk1;
    }

    public void setSk2(int sk2) {
        this.sk2 = sk2;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    //Būtinas, kad Model vaikščiuotu tarp frontend ir backend
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Number{" +
                "id=" + id +
                "sk1=" + sk1 +
                ", sk2=" + sk2 +
                ", action='" + action + '\'' +
                ", result=" + result +
                '}';
    }
}
