package wbse.storage.external

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneId

class StatisticsRepository {

    private val client = HttpClient(Java)

    suspend fun getDetailedReport(params: ExtractionParams): ReportData = run {
        val response: HttpResponse =
            client.get(statisticsMethodUrl) {
                headers {
                    append(HttpHeaders.Authorization, params.token)
                }
                url {
                    parameters.append("dateFrom", toRfcFormatted(params.dateFrom))
                    parameters.append("dateTo", toRfcFormatted(params.dateTo))
                    params.rowId.let { parameters.append("rrdid", it.toString()) }
                }
            }

        val responseBody = response.body<String>()
        when (val resultStatusCode = response.status.value) {
            200 -> return ReportData(responseBody)
            else -> throw RuntimeException("Unexpected statistics status code: $resultStatusCode; response: $responseBody")
        }
    }

    private fun toRfcFormatted(dateTime: Instant): String = rfcDateTimeFormatter.format(dateTime)


    companion object {
        private const val statisticsMethodUrl =
            "https://statistics-api.wildberries.ru/api/v1/supplier/reportDetailByPeriod"
        private val rfcDateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"))

        data class ExtractionParams(
            val dateFrom: Instant,
            val dateTo: Instant,
            val token: String,
            val rowId: Int? = 0
        )

        data class ReportData(
            val rawData: String
        )
    }
}
