package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class Publicacion(@get:Exclude var id: String, var perfil: String, var titulo: String, var descripcion: String, var checked: String, var enlace: String)