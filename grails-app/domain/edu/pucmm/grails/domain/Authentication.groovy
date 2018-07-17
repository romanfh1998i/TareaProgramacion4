package edu.pucmm.grails.domain;
import org.hibernate.envers.Audited;

@Audited
class Authentication {
    String name
    String username
    String password

    static constraints = {
    }
}
