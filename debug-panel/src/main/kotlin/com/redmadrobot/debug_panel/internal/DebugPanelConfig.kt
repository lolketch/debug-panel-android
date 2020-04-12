package com.redmadrobot.debug_panel.internal

import android.content.SharedPreferences
import com.redmadrobot.debug_panel.accounts.Authenticator
import com.redmadrobot.debug_panel.accounts.DefaultAuthenticator
import com.redmadrobot.debug_panel.data.PreInstalledData
import com.redmadrobot.debug_panel.data.settings.DefaultSharedPreferences
import com.redmadrobot.debug_panel.data.storage.entity.DebugServer
import com.redmadrobot.debug_panel.data.storage.entity.DebugUserCredentials
import com.redmadrobot.debug_panel.inapp.toggles.FeatureTogglesConfig

data class DebugPanelConfig(
    val authenticator: Authenticator = DefaultAuthenticator(),
    val preInstalledServers: PreInstalledData<DebugServer> = PreInstalledData(emptyList()),
    val preInstalledAccounts: PreInstalledData<DebugUserCredentials> = PreInstalledData(emptyList()),
    val featureTogglesConfig: FeatureTogglesConfig? = null,
    val sharedPreferences: SharedPreferences = DefaultSharedPreferences()
)
