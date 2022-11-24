package cat.copernic.fpshare.modelo

import com.google.firebase.firestore.Exclude

data class Modul(@get:Exclude var idModul: String,var nombre: String,
            var Ufs: List<Uf>
            ){}
