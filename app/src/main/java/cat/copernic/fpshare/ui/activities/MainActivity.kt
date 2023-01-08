package cat.copernic.fpshare.ui.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import cat.copernic.fpshare.R
import cat.copernic.fpshare.databinding.ActivityMainBinding
import cat.copernic.fpshare.modelo.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var user = Firebase.auth.currentUser
    private var bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get the navigation host fragment from this Activity

        // Instantiate the navController using the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        setupBottomNavMenu(navController)
        // Make sure actions in the ActionBar get propagated to the NavController
        //setupActionBarWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.pantalla_principal, R.id.menuAdministracion, R.id.fp_ajustes, R.id.login),
            binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        esAdmin()

        val signupmenuitem = binding.navView.menu.findItem(R.id.nav_logout)
        signupmenuitem.setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
            true
        }
        val recordatorio = binding.navView.menu.findItem(R.id.recordatorio)
        recordatorio.setOnMenuItemClickListener {
            crearAlarma()
            true
        }

    }
    private fun esAdmin(){
        val email = user?.email.toString()

        bd.collection("Usuarios").document(email).get().addOnSuccessListener { document ->

            val esAdmin = document["esAdmin"] as Boolean
            if(!esAdmin){
                binding.navView.menu.findItem(R.id.menuAdministracion).setVisible(false)
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun setupBottomNavMenu(navController: NavController){
        val bottomNav=findViewById<BottomNavigationView>(R.id.bottom_view)
        bottomNav?.setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(
            findNavController(R.id.nav_host_fragment)
        ) || super.onOptionsItemSelected(item)
    }

    private fun crearAlarma() {
        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 5)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
        /*
        return item.onNavDestinationSelected(

            findNavController(R.id.nav_host_fragment)
        ) || super.onOptionsItemSelected(item)




        startActivity(Intent(this, Login::class.java))
        finish()
         */


}