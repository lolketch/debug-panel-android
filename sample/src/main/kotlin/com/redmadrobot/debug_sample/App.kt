package com.redmadrobot.debug_sample

import android.app.Application
import com.redmadrobot.core.data.PreInstalledData
import com.redmadrobot.core.data.storage.entity.DebugAccount
import com.redmadrobot.core.data.storage.entity.DebugServer
import com.redmadrobot.debug_panel.accounts.Authenticator
import com.redmadrobot.debug_panel.inapp.toggles.FeatureToggleChangeListener
import com.redmadrobot.debug_panel.inapp.toggles.FeatureTogglesConfig
import com.redmadrobot.debug_panel.internal.DebugPanel
import com.redmadrobot.debug_panel.internal.plugin.account.AccountsPlugin
import com.redmadrobot.debug_panel.internal.plugin.app_settings.AppSettingsPlugin
import com.redmadrobot.debug_panel.internal.plugin.feature_togle.FeatureTogglesPlugin
import com.redmadrobot.debug_panel.internal.plugin.server.ServersPlugin
import com.redmadrobot.debug_sample.storage.AppTestSettings

class App : Application(), Authenticator, FeatureToggleChangeListener {
    override fun onCreate() {
        super.onCreate()

        DebugPanel.initialize(
            this, listOf(
                AccountsPlugin(
                    preInstalledAccounts = PreInstalledData(
                        getPreInstalledAccounts()
                    ),
                    //TODO Временная реализация. Здесь это не должно делаться.
                    authenticator = this
                ),
                ServersPlugin(
                    preInstalledServers = PreInstalledData(
                        getPreInstalledServers()
                    )
                ),
                FeatureTogglesPlugin(
                    featureTogglesConfig = FeatureTogglesConfig(
                        FeatureToggleWrapperImpl.toggleNames,
                        FeatureToggleWrapperImpl(),
                        this
                    )
                ),
                AppSettingsPlugin(
                    sharedPreferences = listOf(
                        AppTestSettings(this.applicationContext).testSharedPreferences,
                        AppTestSettings(this.applicationContext).testSharedPreferences,
                        AppTestSettings(this.applicationContext).testSharedPreferences
                    )
                )
            )
        )
    }

    override fun authenticate(account: DebugAccount) {
        println("Login - ${account.login}, Password - ${account.password}")
    }

    override fun onFeatureToggleChange(name: String, newValue: Boolean) {
        // Feature toggle was changed. You need
        println("New value for key \"$name\" = $newValue")
    }

    private fun getPreInstalledServers(): List<DebugServer> {
        return listOf(
            DebugServer(url = "https://testserver1.com")
        )
    }

    private fun getPreInstalledAccounts(): List<DebugAccount> {
        return listOf(
            DebugAccount(
                login = "7882340482",
                password = "Qq!11111"
            ),
            DebugAccount(
                login = "2777248041",
                password = "Qq!11111"
            ),
            DebugAccount(
                login = "4183730054",
                password = "Ww!11111"
            ),
            DebugAccount(
                login = "1944647499",
                password = "Qq!11111"
            )
        )
    }
}
