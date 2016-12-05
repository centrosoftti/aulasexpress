import seguranca.Perfil
import seguranca.Usuario
import seguranca.UsuarioPerfil
import aulasexpress.Disciplina

class BootStrap {

	def springSecurityService
	def adminUser
	
    def init = { servletContext ->
		
		println "Bootstrap de dados do BD";
		//Dados gerais previos carregados para todos os ambientes
//		carregarDadosIniciaisParaTodosOsAmbientes();
		 
		
		environments {
			production {
//				carregaDadosDeProducao();
			}
			development {
				carregaDadosDeDesenvolvimento();
			}

		}
		
		//Dados gerais carregados para todos os ambientes apos a carga especifica de cada ambiente
//		carregarDadosFinaisParaTodosOsAmbientes();
    }
	
	
    def destroy = {
    }
	
	private void carregarDadosIniciaisParaTodosOsAmbientes(){
	
	}
	
	private void carregaDadosDeProducao(){
		//Se for necessario dados especificos para producao, carregar aqui.
		
	//		carregaDadosDeDesenvolvimento();
//		criarUsuarios();
//		carregaDadosDeCredito();
//		carregaDadosProfissionais();
		
	}
	
	private void carregarDadosFinaisParaTodosOsAmbientes(){
		//Se for necessario criar dados apos a carga especifica de ambientes
	}
	
	private void criarUsuarios()
	{
		println "Carregando usuarios..."
				
		def adminPerfil = new Perfil(authority: 'ROLE_ADMIN',descricao:"Administrador").save(flush: true)
		def professorPerfil = new Perfil(authority: 'ROLE_PROF',descricao:"Professor").save(flush: true)
		def usuarioComumPerfil = new Perfil(authority: 'ROLE_USER',descricao:"Aluno").save(flush: true)
		
		
		String password = springSecurityService.encodePassword('1234')
			
		adminUser = new Usuario(username: 'admin', enabled: true, email: 'admin@test.com', first_name: 'Andre', last_name: 'Passos', password: '1234')
		adminUser.save(flush: true)
		
		UsuarioPerfil.create(adminUser, adminPerfil, true)
		
		def professorUser = new Usuario(username: 'professor', enabled: true, email: 'professor@test.com', first_name: 'Jean', last_name: 'Travassos', password: '1234')
		professorUser.save(flush: true)
		
		UsuarioPerfil.create(professorUser, professorPerfil, true)
		

		def usuarioUser = new Usuario(username: 'aluno', enabled: true, email: 'thiago@test.com', first_name: 'Thiago', last_name: 'Albuquerque', password: '1234')
		usuarioUser.save(flush: true)
		
		UsuarioPerfil.create(usuarioUser, usuarioComumPerfil, true)
		
		assert Usuario.count() == 3
		assert Perfil.count() == 3
		assert UsuarioPerfil.count() == 3

	}
	
	private void carregaDadosDeDesenvolvimento(){
		criarUsuarios();
		
		println "Carregando Disciplinas..."
		
		def disciplina1 = new Disciplina(
			nome:"Matemática").save();
		
		def disciplina2 = new Disciplina(
			nome:"Português").save();
		
		def disciplina3 = new Disciplina(
			nome:"Geografia").save();
		
		def disciplina4 = new Disciplina(
			nome:"História").save();
		
		def disciplina5 = new Disciplina(
			nome:"Física").save();
		
		def disciplina6 = new Disciplina(
			nome:"Química").save();
		
		def disciplina7 = new Disciplina(
			nome:"Inglês").save();
	}
}
