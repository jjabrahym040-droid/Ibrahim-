package com.androidify.smoke

/**
 * Generated automatically at build time. BASE_URL points at the real backend of
 * this app; every network call must go through it. Only public keys live here —
 * secret keys stay on the server.
 */
object ApiConfig {
    const val BASE_URL: String = "https://project--cdf00319-fff9-4698-9507-5d417afff22f-dev.lovable.app"
    const val SUPABASE_URL: String = "https://upwedtwxzejovxbdcouf.supabase.co"
    const val SUPABASE_PUBLISHABLE_KEY: String = ""

    fun endpoint(path: String): String =
        BASE_URL.trimEnd('/') + "/" + path.trimStart('/')
}
