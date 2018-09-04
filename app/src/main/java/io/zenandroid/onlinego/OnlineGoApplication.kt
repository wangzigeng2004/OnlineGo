package io.zenandroid.onlinego

import android.app.Application
import android.arch.persistence.room.Room
import android.os.Build
import android.support.text.emoji.EmojiCompat
import android.support.text.emoji.FontRequestEmojiCompatConfig
import android.support.v4.provider.FontRequest
import com.google.firebase.analytics.FirebaseAnalytics
import io.zenandroid.onlinego.db.Database
import io.zenandroid.onlinego.ogs.ChatRepository


/**
 * Created by alex on 04/11/2017.
 */
class OnlineGoApplication : Application() {

    companion object {
        lateinit var instance: OnlineGoApplication
    }

    val db by lazy {
        Room.databaseBuilder(this, Database::class.java, "database.db")
                .fallbackToDestructiveMigration()
                .build()
    }
    val analytics by lazy { FirebaseAnalytics.getInstance(this) }
    val chatRepository by lazy { ChatRepository(db.gameDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val config: EmojiCompat.Config
        val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs)
        config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
        EmojiCompat.init(config)
    }
}