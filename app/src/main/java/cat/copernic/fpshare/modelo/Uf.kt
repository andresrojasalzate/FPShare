package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class Uf(@get:Exclude var idUf: String, var nombre: String)
