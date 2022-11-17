package cat.copernic.fpshare.modelo

data class Foro  (var titulo: String, var descripcion : String, var emailautor: String, var mensajes :ArrayList<Mensaje>){
}