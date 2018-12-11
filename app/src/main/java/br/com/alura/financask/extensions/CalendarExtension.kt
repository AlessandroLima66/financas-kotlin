package br.com.alura.financask.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro() : String {
    return SimpleDateFormat("dd/MM/yyy").format(this.time)
}