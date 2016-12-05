package seguranca

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Perfil perfil

	UsuarioPerfil(Usuario u, Perfil r) {
		this()
		usuario = u
		perfil = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioPerfil)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.perfil?.id == perfil?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (perfil) builder.append(perfil.id)
		builder.toHashCode()
	}

	static UsuarioPerfil get(long usuarioId, long perfilId) {
		criteriaFor(usuarioId, perfilId).get()
	}

	static boolean exists(long usuarioId, long perfilId) {
		criteriaFor(usuarioId, perfilId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long perfilId) {
		UsuarioPerfil.where {
			usuario == Usuario.load(usuarioId) &&
			perfil == Perfil.load(perfilId)
		}
	}

	static UsuarioPerfil create(Usuario usuario, Perfil perfil, boolean flush = false) {
		def instance = new UsuarioPerfil(usuario: usuario, perfil: perfil)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Usuario u, Perfil r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = UsuarioPerfil.where { usuario == u && perfil == r }.deleteAll()

		if (flush) { UsuarioPerfil.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioPerfil.where { usuario == u }.deleteAll()

		if (flush) { UsuarioPerfil.withSession { it.flush() } }
	}

	static void removeAll(Perfil r, boolean flush = false) {
		if (r == null) return

		UsuarioPerfil.where { perfil == r }.deleteAll()

		if (flush) { UsuarioPerfil.withSession { it.flush() } }
	}

	static constraints = {
		perfil validator: { Perfil r, UsuarioPerfil ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioPerfil.withNewSession {
				existing = UsuarioPerfil.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'perfil']
		version false
	}
}
