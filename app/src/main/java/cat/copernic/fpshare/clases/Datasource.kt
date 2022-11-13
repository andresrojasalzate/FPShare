package cat.copernic.fpshare.clases

import cat.copernic.fpshare.R
import cat.copernic.fpshare.clases.Publicacion

class Datasource{
    fun loadPublicacion(): List<Publicacion>{
        return listOf<Publicacion>(
            Publicacion(R.string.profile_name),
            Publicacion(R.string.title_post),
            Publicacion(R.string.content_description)
        )
    }
}
