package com.example.desafiomobile2you.view.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.transactionFragment(executa: FragmentTransaction.() -> Unit){
    val transacao = supportFragmentManager.beginTransaction()

    executa(transacao)
    transacao.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    transacao.commit()

}