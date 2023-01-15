package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class Publicacion(@get:Exclude var id: String = "", var perfil: String = "", var titulo: String = "", var descripcion: String = "", var checked: String = "", var enlace: String = "", var imgPubli: String= "", var rating5: Int = 0, var rating4: Int = 0, var rating3: Int = 0, var rating2: Int = 0, var rating1: Int = 0, var idCiclo: String = "", var idModulo: String = "", var idUf: String = "", var pathFile: String = "")