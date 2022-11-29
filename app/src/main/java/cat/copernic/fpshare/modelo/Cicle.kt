package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class Cicle(@get:Exclude var idCiclo: String, var nombre: String)

