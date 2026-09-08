package com.androidify.myandroida

/**
 * Generated automatically at build time. BASE_URL points at the real backend of
 * this app; every network call must go through it. Only public keys live here —
 * secret keys stay on the server.
 */
object ApiConfig {
    const val BASE_URL: String = "https://growth-playground-pro.lovable.app"
    const val SUPABASE_URL: String = "https://upwedtwxzejovxbdcouf.supabase.co"
    const val SUPABASE_PUBLISHABLE_KEY: String = "sb_publishable_5nYKSdt1RlXxh1MAaMaV2Q_0T4_b3dj"

    fun endpoint(path: String): String =
        BASE_URL.trimEnd('/') + "/" + path.trimStart('/')
}
