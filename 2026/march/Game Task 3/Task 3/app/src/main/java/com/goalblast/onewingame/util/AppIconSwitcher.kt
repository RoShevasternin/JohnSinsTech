package com.goalblast.onewingame.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import androidx.core.content.edit

object AppIconSwitcher {

    private const val PREFS_NAME    = "icon_switcher"
    private const val KEY_VARIANT   = "current_variant"

    enum class Variant(val aliasName: String) {
        DEFAULT  ("AliasDefault"),
        VARIANT_2("AliasVariant2"),
    }

    fun checkAndSwitch(context: Context, gistUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json         = URL(gistUrl).readText()
                val variantIndex = JSONObject(json).getInt("icon_variant")
                val newVariant   = Variant.entries[variantIndex]

                withContext(Dispatchers.Main) {
                    // Перемикаємо ТІЛЬКИ якщо варіант змінився
                    if (getCurrentVariant(context) != newVariant) {
                        saveCurrentVariant(context, newVariant)
                        switchTo(context, newVariant)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun switchTo(context: Context, variant: Variant) {
        val pm  = context.packageManager
        val pkg = context.packageName

        Variant.entries.forEach { v ->
            pm.setComponentEnabledSetting(
                ComponentName(pkg, "$pkg.${v.aliasName}"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        pm.setComponentEnabledSetting(
            ComponentName(pkg, "$pkg.${variant.aliasName}"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun getCurrentVariant(context: Context): Variant {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val name  = prefs.getString(KEY_VARIANT, Variant.DEFAULT.name)
        return Variant.valueOf(name ?: Variant.DEFAULT.name)
    }

    private fun saveCurrentVariant(context: Context, variant: Variant) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_VARIANT, variant.name)
            }
    }
}