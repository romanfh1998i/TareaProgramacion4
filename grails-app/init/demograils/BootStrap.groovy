package demograils

import edu.pucmm.grails.domain.Authentication
import edu.pucmm.grails.domain.Koffee
import edu.pucmm.grails.utils.Grails

class BootStrap {

    def init = { servletContext ->
        createKofee("Capuchino", new BigDecimal(50))
        createKofee("Frapuchino", new BigDecimal(80))

        createAuthentication("aluis", "123")
        createAuthentication("franco", "rfranco")
    }

    private Koffee createKofee(String name, BigDecimal price) {
        Koffee koffee = Koffee.findByName(name)
        if (koffee == null) {
            koffee = new Koffee()
            koffee.name = name
            koffee.price = price
            return koffee.save(flush: true, failOnError: true)
        }
        return koffee
    }

    private Authentication createAuthentication(String name, String password) {
        Authentication authentication = Authentication.findByUsername(name)
        if (authentication == null) {
            return Grails.get(AuthenticationService).registerUser(name, password)
        }
        return authentication
    }


    def destroy = {
    }
}
