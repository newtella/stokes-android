package com.codigogt.stokes.io.response

import com.codigogt.stokes.model.User

data class LoginResponse (val success: Boolean, val user: User, val jwt: String, val message: String)
