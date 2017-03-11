package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TipoDocumentoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TipoDocumento.list(params), model:[tipoDocumentoCount: TipoDocumento.count()]
    }

    def show(TipoDocumento tipoDocumento) {
        respond tipoDocumento
    }

    def create() {
        respond new TipoDocumento(params)
    }

    @Transactional
    def save(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoDocumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumento.errors, view:'create'
            return
        }

        tipoDocumento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tipoDocumento.label', default: 'TipoDocumento'), tipoDocumento.id])
                redirect tipoDocumento
            }
            '*' { respond tipoDocumento, [status: CREATED] }
        }
    }

    def edit(TipoDocumento tipoDocumento) {
        respond tipoDocumento
    }

    @Transactional
    def update(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoDocumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumento.errors, view:'edit'
            return
        }

        tipoDocumento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoDocumento.label', default: 'TipoDocumento'), tipoDocumento.id])
                redirect tipoDocumento
            }
            '*'{ respond tipoDocumento, [status: OK] }
        }
    }

    @Transactional
    def delete(TipoDocumento tipoDocumento) {

        if (tipoDocumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tipoDocumento.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoDocumento.label', default: 'TipoDocumento'), tipoDocumento.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoDocumento.label', default: 'TipoDocumento'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
