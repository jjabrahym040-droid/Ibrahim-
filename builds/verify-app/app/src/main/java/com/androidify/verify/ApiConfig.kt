package com.androidify.verify

/**
 * Generated automatically at build time. BASE_URL points at the real backend of
 * this app; every network call must go through it. Only public keys live here —
 * secret keys stay on the server.
 */
object ApiConfig {
    const val BASE_URL: String = "https://dev-hub-paradise.lovable.app"
    const val SUPABASE_URL: String = "https://ycdhwzouwzwvgfocszoe.supabase.co"
    const val SUPABASE_PUBLISHABLE_KEY: String = "sb_publishable_YpLS0dCLRpShOSuwFDMq3Q_dPeNwGAF"
    const val APP_KEY: String = "0f3685f7e10355186ceaab9840a5823e8bed"

    fun endpoint(path: String): String =
        BASE_URL.trimEnd('/') + "/" + path.trimStart('/')
}
