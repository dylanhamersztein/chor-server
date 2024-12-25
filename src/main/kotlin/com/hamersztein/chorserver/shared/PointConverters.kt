package com.hamersztein.chorserver.shared

import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKBReader
import org.locationtech.jts.io.WKBWriter
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class PointToByteArrayWriter : Converter<Point, ByteArray> {
    private val writer = WKBWriter()

    override fun convert(source: Point): ByteArray = writer.write(source)
}

@ReadingConverter
class ByteArrayToPointReader : Converter<String, Point> {
    private val reader = WKBReader()

    @OptIn(ExperimentalStdlibApi::class)
    override fun convert(source: String) = reader.read(source.hexToByteArray()) as Point
}
