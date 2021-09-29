package com.zupacademy.italo

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.zupacademy.italo")
		.start()
}

