package aulasexpress

import grails.rest.Resource

@Resource(uri='/disciplinas')
class Disciplina {

	String nome
	
    static constraints = {
		nome(nullable:false,blank:false)
    }
}
