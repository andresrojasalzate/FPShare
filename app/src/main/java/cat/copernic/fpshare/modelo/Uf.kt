package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

/**
 * Clase para crear objetos de tipo UF
 */
data class Uf(@get:Exclude var idUf: String, var nombre: String)
