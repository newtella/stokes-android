package com.codigogt.stokes.model

data class Specialty (
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}

