package gmikhail.colorpicker

/**
 * Generated automatically. The app is already linked to its backend space:
 * every request goes through BACKEND_URL. Only public values live here —
 * secret keys (payments, private APIs, service role) stay on the server.
 */
object BackendConfig {
    const val SUPABASE_URL: String = "https://hdvgvvvlsyxzmemepwtz.supabase.co"
    const val SUPABASE_ANON_KEY: String = "sb_publishable_imJ1pPZ9vrQxWDLjqAhM8g_nGrfneNw"
    const val BACKEND_URL: String = "https://dev-hub-paradise.lovable.app"
    const val PROJECT_ID: String = "project-a05b8158-f393-430a-8f22-c8f251f81de7"
    const val APP_KEY: String = "9ad08e909ae767e6d309d24c11b52aa08e78"

    fun endpoint(path: String): String =
        BACKEND_URL.trimEnd('/') + "/" + path.trimStart('/')
}
