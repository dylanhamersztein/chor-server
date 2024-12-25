package com.hamersztein.chorserver.infrastructure

import com.hamersztein.chorserver.TestcontainersConfiguration
import com.hamersztein.chorserver.shared.GeometryFactoryConfiguration
import com.hamersztein.chorserver.shared.R2DBCMappingConfig
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import

@DataR2dbcTest
@Import(TestcontainersConfiguration::class, GeometryFactoryConfiguration::class, R2DBCMappingConfig::class)
annotation class RepositoryTest
