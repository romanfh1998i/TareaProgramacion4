package demograils

import edu.pucmm.grails.domain.Authentication
import edu.pucmm.grails.domain.Koffee
import edu.pucmm.grails.utils.Grails

class BootStrap {

    def init = { servletContext ->
        createKofee("CA","Capuchino", new BigDecimal(50))
        createKofee("CA","Frapuchino", new BigDecimal(80))

        createAuthentication("ALuis Marte", "aluis",  "123")
        createAuthentication("Roman Franco Ho","franco", "rfranco")
    }

    private Koffee createKofee(String shortName,String name, BigDecimal price) {
        Koffee koffee = Koffee.findByName(name)
        if (koffee == null) {
            koffee = new Koffee()

            koffee.name = name
            koffee.price = price
            koffee.shortName=shortName

            return koffee.save(flush: true, failOnError: true)
        }
        return koffee
    }

    private Authentication createAuthentication(String name, String userName,  String password) {
        Authentication authentication = Authentication.findByUsername(name)
        if (authentication == null) {
            return Grails.get(AuthenticationService).registerUser(userName, name, password)
        }
        return authentication
    }


    def destroy = {
    }
}
