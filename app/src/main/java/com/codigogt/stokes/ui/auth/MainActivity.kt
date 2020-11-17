package com.codigogt.stokes.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codigogt.stokes.util.PreferenceHelper.get
import com.codigogt.stokes.util.PreferenceHelper.set
import android.widget.Toast
import com.codigogt.stokes.ui.menu.MenuActivity
import com.codigogt.stokes.util.PreferenceHelper
import com.codigogt.stokes.R
import com.codigogt.stokes.io.ApiService
import com.codigogt.stokes.io.response.LoginResponse
import com.codigogt.stokes.util.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val snackBar by lazy {
        Snackbar.make(mainLayout,
            R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceHelper.defaultPrefs(this)
        if(preferences["jwt", ""].contains(".")) {
            goToMenuActivity()
        }
        else {
            btnLogin.setOnClickListener {
                performLogin()
/*                createSessionPreferences()
                goToMenuActivity()*/
            }
            tvGoToRegister.setOnClickListener {
                Toast.makeText(this, getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun performLogin(){
        when {
            etUsername.text.isEmpty() -> {
                etUsername.error = "Ingresa un correo electronico"
            }
            etPassword.text.isEmpty() ->{ etPassword.error = "Ingresa una contraseña"
            } else -> {

                val call = apiService.postLogin(etUsername.text.toString(), etPassword.text.toString())
                call.enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        toast(t.localizedMessage)
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful){
                            val loginResponse = response.body()
                            if (loginResponse == null){
                                toast("Se obtuvo una respuestas inesperada del servidor")
                            }
                            if (loginResponse!!.success){
                                createSessionPreferences(loginResponse.jwt)
                                goToMenuActivity()
                            }else{
                                toast("Correo y contraseña incorrecta")
                            }
                        }else{
                            toast("Peticion Incorrecta")
                        }
                    }
                })
            }
        }
    }

    private fun createSessionPreferences(jwt: String){
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["jwt"] = jwt
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()
    }
}
