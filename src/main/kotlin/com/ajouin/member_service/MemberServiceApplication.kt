package com.ajouin.member_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MemberServiceApplication

fun main(args: Array<String>) {
	runApplication<MemberServiceApplication>(*args)
}
