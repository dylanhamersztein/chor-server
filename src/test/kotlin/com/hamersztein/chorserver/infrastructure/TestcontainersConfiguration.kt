package com.hamersztein.chorserver.infrastructure

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer() = PostgreSQLContainer(
        DockerImageName.parse("postgis/postgis:latest")
            .asCompatibleSubstituteFor("postgres:latest")
    )

}
