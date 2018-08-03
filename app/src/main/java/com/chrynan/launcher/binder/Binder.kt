package com.chrynan.launcher.binder

interface Binder<M, V> {

    val view: V

    fun bind(stateModel: M)
}