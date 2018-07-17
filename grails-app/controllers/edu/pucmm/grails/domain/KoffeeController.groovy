package edu.pucmm.grails.domain

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus

@Transactional(readOnly = true)
class KoffeeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        Koffee.async.task {
            [koffeeList: list(params), count: count() ]
        }.then { result ->
            respond result.koffeeList, model:[koffeeCount: result.count]
        }
    }

    def show(Long id) {
        Koffee.async.get(id).then { koffee ->
            respond koffee
        }
    }

    def create() {
        respond new Koffee(params)
    }
    @Transactional
    def save(Koffee koffee) {
        Koffee.async.withTransaction { TransactionStatus status ->
            if (koffee == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            if(koffee.hasErrors()) {
                status.setRollbackOnly()
                respond koffee.errors, view:'create' // STATUS CODE 422
                return
            }

            koffee.save flush:true
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'koffee.label', default: 'Koffee'), koffee.id])
                    redirect koffee
                }
                '*' { respond koffee, [status: CREATED] }
            }
        }
    }
    @Transactional
    def edit(Long id) {
        Koffee.async.get(id).then { koffee ->
            respond koffee
        }
    }
    @Transactional
    def update(Long id) {
        Koffee.async.withTransaction { TransactionStatus status ->
            def koffee = Koffee.get(id)
            if (koffee == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            koffee.properties = params
            if( !koffee.save(flush:true) ) {
                status.setRollbackOnly()
                respond koffee.errors, view:'edit' // STATUS CODE 422
                return
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'Koffee.label', default: 'Koffee'), koffee.id])
                    redirect koffee
                }
                '*'{ respond koffee, [status: OK] }
            }
        }
    }
    @Transactional
    def delete(Long id) {
        Koffee.async.withTransaction { TransactionStatus status ->
            def koffee = Koffee.get(id)
            if (koffee == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            koffee.delete flush:true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'Koffee.label', default: 'Koffee'), koffee.id])
                    redirect action:"index", method:"GET"
                }
                '*'{ render status: NO_CONTENT }
            }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'koffee.label', default: 'Koffee'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}