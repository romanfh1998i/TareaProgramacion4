package edu.pucmm.grails.domain

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.gorm.transactions.Transactional


@Transactional(readOnly = true)
class AuthenticationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        Authentication.async.task {
            [authenticationList: list(params), count: count() ]
        }.then { result ->
            respond result.authenticationList, model:[authenticationCount: result.count]
        }
    }

    def show(Long id) {
        Authentication.async.get(id).then { authentication ->
            respond authentication
        }
    }

    def create() {
        respond new Authentication(params)
    }
    @Transactional
    def save(Authentication authentication) {
        Authentication.async.withTransaction { TransactionStatus status ->
            if (authentication == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            if(authentication.hasErrors()) {
                status.setRollbackOnly()
                respond authentication.errors, view:'create' // STATUS CODE 422
                return
            }

            authentication.save flush:true
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'authentication.label', default: 'Authentication'), authentication.id])
                    redirect authentication
                }
                '*' { respond authentication, [status: CREATED] }
            }
        }
    }
    @Transactional
    def edit(Long id) {
        Authentication.async.get(id).then { authentication ->
            respond authentication
        }
    }
    @Transactional
    def update(Long id) {
        Authentication.async.withTransaction { TransactionStatus status ->
            def authentication = Authentication.get(id)
            if (authentication == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            authentication.properties = params
            if( !authentication.save(flush:true) ) {
                status.setRollbackOnly()
                respond authentication.errors, view:'edit' // STATUS CODE 422
                return
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'Authentication.label', default: 'Authentication'), authentication.id])
                    redirect authentication
                }
                '*'{ respond authentication, [status: OK] }
            }
        }
    }
    @Transactional

    def delete(Long id) {
        Authentication.async.withTransaction { TransactionStatus status ->
            def authentication = Authentication.get(id)
            if (authentication == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            authentication.delete flush:true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'Authentication.label', default: 'Authentication'), authentication.id])
                    redirect action:"index", method:"GET"
                }
                '*'{ render status: NO_CONTENT }
            }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'authentication.label', default: 'Authentication'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}