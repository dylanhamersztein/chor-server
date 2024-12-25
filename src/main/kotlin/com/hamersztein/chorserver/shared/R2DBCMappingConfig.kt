package com.hamersztein.chorserver.shared

import io.r2dbc.spi.ConnectionFactories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2DBCMappingConfig : AbstractR2dbcConfiguration() {

    @Value("\${spring.r2dbc.url}")
    lateinit var databaseUrl: String

    override fun connectionFactory() = ConnectionFactories.get(databaseUrl)

    override fun getCustomConverters() = listOf(
        // org.locationtech.jts.geom.Point
        ByteArrayToPointReader(),
        PointToByteArrayWriter(),
    )
}
