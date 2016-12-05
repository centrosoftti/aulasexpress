package aulasexpress

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_PROF', 'ROLE_USER'])
class DisciplinaController {

    def index() { }
	
}
