package cat.copernic.fpshare.modelo


data class User(var email : String = "", var nombre: String = "", var apellidos: String = "",
                var telefono : String = "", var insituto : String = "",var imgPerfil: String = "", var esAdmin : Boolean = false){
}