package demograils

import edu.pucmm.grails.domain.Authentication
import grails.gorm.transactions.Transactional

@Transactional
class AuthenticationService {


    boolean autenticate(String user, String password) {
        return Authentication.findByUsernameAndPassword(user, password)
    }

    Authentication registerUser(String user, String password) {
        Authentication authentication = new Authentication()
        authentication.username = user
        authentication.password = password
        return authentication.save(flush: true, failOnError: true)
    }
}
