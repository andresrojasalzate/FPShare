package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class User(@get:Exclude var email : String, var nombre: String, var apellidos: String, var telefono : String, var Insituto : String, var esAdmin : Boolean){
}