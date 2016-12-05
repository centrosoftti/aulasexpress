package aulasexpress

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_PROF', 'ROLE_USER'])
class DisciplinaController {

//    def index() {
//		render 'yes!!!'
//	}

//@Secured('ROLE_ADMIN')
	def save = {
		println "\n\n Entrei na funcao salvar Disciplina..."
		println request.method
		println "\n\n Params:\n ${params}\n\n"
		
		def json = JSON.parse(request)

		println json
		
		def disciplina = new Disciplina(json.data)
				
		if(disciplina.save())
		{
			println "\n\n Salva a Disciplina"
		
			response.status = 201
			def jsonResponse = ['response':['status':0,'data':disciplina]]
			render jsonResponse as JSON
			
			println jsonResponse
		}
		else
		{
			def errorsJson = [:]
			disciplina.errors.allErrors.each {err ->
				errorsJson.put("${err.field}",["errorMessage":err.defaultMessage])
			}
			
			def jsonResponse = ['response':['status':-4,'errors':errorsJson]]
			
			render jsonResponse as JSON
			
			println jsonResponse
		}
		
	}
	
	//recuperar /buscar
//	@Secured(['ROLE_ADMIN', 'ROLE_FUNC'])
	def show = {
		println "\n\n Entrei no show da Disciplina"
		println request.method
		println "Params: ${params}"
		
		
		def disciplina = null
		if(params.id)
		{
			println "Vou pesquisar Disciplina por id ${params.id}"
			disciplina = Disciplina.get(params.id)
		}
		else if(params.nome)
		{
			println "Vou pesquisar Disciplina..."
			
			def a = Disciplina.createCriteria()
			disciplina = a{
				
				if (params.nome)
					ilike("nome", "%${params.nome}%")
									
			}
		}
		else
		{
			println "Vou pesquisar todas as Disciplinas..."
			disciplina = Disciplina.getAll()
		}
		
		println "\n\n Disciplinas encontradas: ${disciplina} \n\n"
		
		def jsonResponse = ['response':['status':0,'data':disciplina]]
		println "\n\n Disciplina como JSONRESPONSE: ${jsonResponse} \n\n"
		render jsonResponse as JSON
		return
	}
	
	//update
//	@Secured(['ROLE_ADMIN', 'ROLE_FUNC'])
	def update = {
		println "\n\n Entrei no update de Disciplina..."
		println request.method
		println "\n\n Params:\n ${params}\n"
		
		def json = JSON.parse (request)
		println json
		
		def disciplina = Disciplina.get(json.data.id)
		
		if (disciplina)
		{
			disciplina.properties = json.data
			
			if (disciplina.save())
			{
				response.status = 200 //ok
				def jsonResponse = ['response':['status':0,'data':disciplina]]
				render jsonResponse as JSON
			}
			else
			{
				//Todo processar menssagens de erro com os parametros
				def errorsJson = [:]
				
				disciplina.errors.allErrors.each {err ->
					errorsJson.put("${err.field}",["errorMessage":err.defaultMessage])
				}
				
				def jsonResponse = ['response':['status':-4,'errors':errorsJson]]
				render jsonResponse as JSON
			}
		}
	}
	
	//delete
//	@Secured(['ROLE_ADMIN', 'ROLE_FUNC'])
	def delete = {
		
		println "\n\n Entrei no delete da Disciplina..."
		println request.method
		println "\n\n Params:\n ${params}"
		
		if(params.id)
		{
			def disciplina = Disciplina.get(params.id)
			
			//Verifica se foi encontrado algum Disciplina
			if (!disciplina)
			{
				response.status = 404 //Not Found
				render "Disciplina com id ${params.id} nao encontrado"
				return
			}
			
			disciplina.delete()
			
			def jsonResponse = ['response':['status':0,'data':disciplina]]
			render jsonResponse as JSON
			return
		}
		
		response.status = 400 //Bad request
		render "Parametros invalidos. Informe o id da Disciplina."
	}
	
}
