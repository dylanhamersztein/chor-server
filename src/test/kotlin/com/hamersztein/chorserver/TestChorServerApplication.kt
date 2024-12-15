package com.hamersztein.chorserver

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<ChorServerApplication>().with(TestcontainersConfiguration::class).run(*args)
}
