package wbse.api

import wbse.storage.external.StatisticsRepository
import wbse.storage.internal.TokenRepository
import java.time.Instant
import java.time.temporal.ChronoUnit

suspend fun main() {
    val tokenRepo = TokenRepository()
    val statRepo = StatisticsRepository()

    val testToken = tokenRepo.getByAccountId("to_use_place_test_token_into_source")!!
    val testParams = StatisticsRepository.Companion.ExtractionParams(
        dateFrom = Instant.now().minus(7, ChronoUnit.DAYS),
        dateTo = Instant.now(),
        token = testToken
    )

    statRepo.getDetailedReport(testParams)
}
