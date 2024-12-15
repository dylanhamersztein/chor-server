package com.hamersztein.chorserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChorServerApplication

fun main(args: Array<String>) {
	runApplication<ChorServerApplication>(*args)
}
