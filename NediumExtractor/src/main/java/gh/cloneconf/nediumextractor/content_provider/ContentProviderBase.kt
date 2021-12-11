package gh.cloneconf.nediumextractor.content_provider

import gh.cloneconf.nediumextractor.models.Article
import okhttp3.OkHttpClient

abstract class ContentProviderBase(val okhttp : OkHttpClient) {
    abstract fun article(id: String) : Article
}