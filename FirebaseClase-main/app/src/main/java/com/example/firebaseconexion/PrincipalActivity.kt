package com.example.firebaseconexion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PrincipalActivity : AppCompatActivity() {
    private lateinit var  tvEmail: TextView
    private  lateinit var  tvproveedor: TextView
    private  lateinit var  btnCerrar: Button

    private lateinit var  etNombre: EditText
    private lateinit var  etTelefono : EditText
    private  lateinit var etMunicipio: EditText
    private  lateinit var  btnGuardar: Button
    private lateinit var  btnConsultar : Button
    private lateinit var btnBorrar: Button

    val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        tvEmail = findViewById(R.id.tv_Email)
        tvproveedor = findViewById(R.id.tv_proveedor)
        btnCerrar = findViewById(R.id.btnCerrar)

        etNombre = findViewById(R.id.et_Nombre)
        etTelefono = findViewById(R.id.et_Telefono)
        etMunicipio = findViewById(R.id.et_Municipio)
        btnGuardar = findViewById( R.id.btnGuardar)
        btnConsultar = findViewById(R.id.btnConsultar)
        btnBorrar = findViewById(R.id.btnBorrar)





        val bundle:Bundle? = intent.extras
        val  email:String? = bundle?.getString("email")
        val  proveedor:String? = bundle?.getString("proveedor")

        setup(email?:"",proveedor?:"")



    }

   fun setup(email:String,proveedor:String){
       title = "Principal"
       tvEmail.text = email
       tvproveedor.text = proveedor

       btnCerrar.setOnClickListener{

           FirebaseAuth.getInstance().signOut()
           onBackPressed()


       }
       btnGuardar.setOnClickListener{
           bd.collection("usuarios").document(email).set(
           hashMapOf("Proveedor" to proveedor,
           "nombre" to etNombre.text.toString(),
               "telefono" to etTelefono.text.toString(),
               "municipio" to etMunicipio.text.toString())

           )

       }
    btnConsultar.setOnClickListener {
        bd.collection("usuarios").document(email).get().addOnSuccessListener {

            etNombre.setText(it.getString("nombre")as String)
            etTelefono.setText(it.getString("telefono")as String)
            etMunicipio.setText(it.getString("municipio")as String)
        }

      }
       btnBorrar.setOnClickListener {

    bd.collection("usuarios").document(email).delete()
       }

   }



}