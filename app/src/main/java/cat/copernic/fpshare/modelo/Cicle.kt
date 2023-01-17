package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

/**
 * Clase para crear objetos de tipo Ciclo
 */
data class Cicle(@get:Exclude var idCiclo: String, var nombre: String)

