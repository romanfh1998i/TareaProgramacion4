package demograils

import edu.pucmm.grails.domain.Authentication
import grails.gorm.transactions.Transactional

@Transactional
class AuthenticationService {


    boolean autenticate(String user, String password) {
        return Authentication.findByUsernameAndPassword(user, password)
    }

    def registerUser(String user, String name, String password) {
        Authentication authentication = new Authentication()
        authentication.name = name
        authentication.username = user
        authentication.password = password
        return authentication.save(flush: true, failOnError: true)
    }
}
