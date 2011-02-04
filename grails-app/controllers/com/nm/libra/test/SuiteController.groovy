package com.nm.libra.test

import com.nm.libra.test.Suite;

class SuiteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [suiteInstanceList: Suite.list(params), suiteInstanceTotal: Suite.count()]
    }

    def create = {
        def suiteInstance = new Suite()
        suiteInstance.properties = params
        return [suiteInstance: suiteInstance]
    }

    def save = {
        def suiteInstance = new Suite(params)
        if (suiteInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'suite.label', default: 'Suite'), suiteInstance.id])}"
            redirect(action: "show", id: suiteInstance.id)
        }
        else {
            render(view: "create", model: [suiteInstance: suiteInstance])
        }
    }

    def show = {
        def suiteInstance = Suite.get(params.id)
        if (!suiteInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
            redirect(action: "list")
        }
        else {
            [suiteInstance: suiteInstance]
        }
    }

    def edit = {
        def suiteInstance = Suite.get(params.id)
        if (!suiteInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [suiteInstance: suiteInstance]
        }
    }

    def update = {
        def suiteInstance = Suite.get(params.id)
        if (suiteInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (suiteInstance.version > version) {

                    suiteInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'suite.label', default: 'Suite')] as Object[], "Another user has updated this Suite while you were editing")
                    render(view: "edit", model: [suiteInstance: suiteInstance])
                    return
                }
            }
            suiteInstance.properties = params
            if (!suiteInstance.hasErrors() && suiteInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'suite.label', default: 'Suite'), suiteInstance.id])}"
                redirect(action: "show", id: suiteInstance.id)
            }
            else {
                render(view: "edit", model: [suiteInstance: suiteInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def suiteInstance = Suite.get(params.id)
        if (suiteInstance) {
            try {
                suiteInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'suite.label', default: 'Suite'), params.id])}"
            redirect(action: "list")
        }
    }
}
