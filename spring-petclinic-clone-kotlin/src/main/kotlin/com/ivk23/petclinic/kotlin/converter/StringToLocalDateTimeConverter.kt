package com.ivk23.petclinic.kotlin.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDate.parse
import java.time.format.DateTimeFormatter.ofPattern


@Component
class StringToLocalDateTimeConverter : Converter<String, LocalDate> {
    override fun convert(text: String): LocalDate? =
            if (text == "") null else parse(text, ofPattern("yyyy-MM-dd"))
}