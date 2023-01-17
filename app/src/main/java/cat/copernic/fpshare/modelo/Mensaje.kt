package cat.copernic.fpshare.modelo

/**
 * Clase Mensaje para crear objetos tipo mensaje
 */
data class Mensaje(
    var emailautor: String = "",
    var mensaje: String = "",
    var nombreApellido: String = ""
)