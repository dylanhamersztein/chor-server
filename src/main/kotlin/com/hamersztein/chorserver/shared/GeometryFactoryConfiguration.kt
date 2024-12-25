package com.hamersztein.chorserver.shared

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeometryFactoryConfiguration {

    @Bean
    fun customGeometryFactory() = object : GeometryFactory() {
        override fun createPoint(coordinate: Coordinate): Point {
            val point = super.createPoint(coordinate)
            point.srid = 4326

            return point
        }
    }
}
