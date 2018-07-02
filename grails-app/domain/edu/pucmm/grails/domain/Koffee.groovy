package edu.pucmm.grails.domain

class Koffee {

    String shortName;
    String name;
    String description

    BigDecimal price

    static constraints = {
        shortName nullabe:true
        description nullable: true
    }
}
