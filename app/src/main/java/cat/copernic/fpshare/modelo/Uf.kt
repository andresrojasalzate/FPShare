package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

class Uf(@get:Exclude var idUf: String, var nombre: String) {
}