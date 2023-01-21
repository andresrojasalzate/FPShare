package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

/**
 * Clase para crear objetos de tipo Modulo
 */
data class Modul(@get:Exclude var idModul: String, var nombre: String)
