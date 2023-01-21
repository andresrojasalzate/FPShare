package cat.copernic.fpshare.modelo

/**
 * Clase Usuario para crear objetos de tipo usuario
 */
data class User(
    var email: String = "",
    var nombre: String = "",
    var apellidos: String = "",
    var telefono: String = "",
    var instituto: String = "",
    var imgPerfil: String = "",
    var esAdmin: Boolean = false
)