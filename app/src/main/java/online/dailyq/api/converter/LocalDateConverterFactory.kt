package online.dailyq.api.converter

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.util.Locale

class LocalDateConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type == Locale::class.java) {
            return Converter<Locale, String> { it.toString() }
        }
        return null
    }
}