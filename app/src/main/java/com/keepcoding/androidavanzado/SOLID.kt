package com.keepcoding.androidavanzado

import kotlin.math.pow

interface Areable {
    fun getArea(): Float
    fun decirHola()
//    fun getEsquinas(): Int?
}

interface Esquinable {
    fun getEsquinas(): Int
}

class Triangulo() : Areable, Esquinable {
    val base = 1
    val altura = 2
    override fun getArea(): Float {
        return (base * altura / 2).toFloat()
    }

    override fun decirHola() {
        print("HOLA")
    }

    override fun getEsquinas(): Int {
        return 3
    }

}

class Rectangulo() : Areable, Esquinable {
    val largo = 1
    val alto = 2

    override fun getArea(): Float {
        return (largo * alto).toFloat()
    }

    override fun decirHola() {
        print("HOLA")

    }

    override fun getEsquinas(): Int {
        return 4
    }

}

class Circulo() : Areable {
    val radio = 1
    override fun getArea(): Float {
        return (Math.PI * radio.toDouble().pow(2.0)).toFloat()
    }

    override fun decirHola() {
        // Este método no hace falta aquí
    }

    // 1º ESTO NO SE PUEDE USAR NUNCA! -> MAL
    // 2º HACERLO NULLABLE -> NO OPTIMO
    // 3º QUITARLO DE LA ABSTRACCIÓN
//    override fun getEsquinas(): Int? {
//        return null
//    }

}

fun getAreaTriangulo(triangulo: Triangulo) {

}

fun getAreaRectangulo(rectangulo: Rectangulo) {

}

fun getFigura(figura: Areable) {
    figura.getArea()
//    figura.getEsquinas()
}

fun main() {
    getFigura(Triangulo())
    getFigura(Rectangulo())
}
