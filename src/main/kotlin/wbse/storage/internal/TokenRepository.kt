package wbse.storage.internal

class TokenRepository {

    fun getByAccountId(id: String): String? {
        // TODO("implement token repo")
        return "" // here we SHOULD place token for simply testing
    }

    fun upsert(info: TokenInfo): Unit {
        TODO("implement token repo")
    }

    companion object {
        data class TokenInfo(
            val accountId: String,
            val storedToken: String
        )
    }
}