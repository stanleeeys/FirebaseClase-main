package com.example.firebaseconexion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private  lateinit var  etEmail: EditText
    private lateinit var  etPass: EditText
    private  lateinit var  btnRegistrar: Button
    private  lateinit var  btnLogin: Button

    private  lateinit var  llAutenticar: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.tv_Email)
        etPass = findViewById(R.id.tv_pass)
        btnRegistrar = findViewById(R.id.btnregistrar)
        btnLogin = findViewById(R.id.btnLogin)

        ejecutarAnalitica()
        setup()
    }
    fun ejecutarAnalitica(){
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString("Mensaje","interaccion con la bd")
        analytics.logEvent("InitScreen", bundle)


    }

    fun setup(){

        title = "Autentificacion"

    btnRegistrar.setOnClickListener{

        if( etEmail.text.isNotEmpty() && etPass.text.isNotEmpty()){

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.text.toString(),etPass.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        mostrarPrincipal(it.result?.user?.email?:"",TiposProveedor.BASICO)

                    }else{
                        mostrarAlerta()

                    }

                    }
                 }

                }

        btnLogin.setOnClickListener {

            if( etEmail.text.isNotEmpty() && etPass.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(),etPass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            mostrarPrincipal(it.result?.user?.email?:"",TiposProveedor.BASICO)

                        }else{
                            mostrarAlerta()

                        }

                    }
            }



        }


    }
    fun mostrarAlerta(){


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de conexion")
        builder.setMessage("Se ha producidor un error con el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog = builder.create()
        dialog.show()

    }

    fun mostrarPrincipal(email:String,proveedor:TiposProveedor){

        val ventana: Intent = Intent(this,PrincipalActivity::class.java).apply {
            putExtra("email",email)
            putExtra("proveedor",proveedor.name.toString())

        }
        startActivity(ventana)


    }

}
enum class TiposProveedor {
    BASICO

}