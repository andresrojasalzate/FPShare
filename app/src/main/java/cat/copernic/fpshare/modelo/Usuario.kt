package cat.copernic.fpshare.modelo


data class Usuario(var email : String = "", var nombre: String = "", var apellidos: String = "",
                   var telefono : String = "", var insituto : String = "", var esAdmin : Boolean = false){
}