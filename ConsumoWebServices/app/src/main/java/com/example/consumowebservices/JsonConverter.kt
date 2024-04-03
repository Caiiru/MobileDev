import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class JsonConverter<T>(private val gson: Gson, private val type: Type) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        val json = value.string()
        return gson.fromJson(json, type)
    }
}

class JsonConverterFactory(private val gson: Gson) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (type == String::class.java) {
            JsonConverter<String>(gson, type)
        } else {
            null
        }
    }

    companion object {
        fun create(): JsonConverterFactory {
            return JsonConverterFactory(Gson())
        }
    }
}