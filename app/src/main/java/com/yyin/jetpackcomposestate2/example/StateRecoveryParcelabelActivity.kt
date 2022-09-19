package com.yyin.jetpackcomposestate2.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.yyin.jetpackcomposestate2.ui.theme.JetpackComposeState2Theme

class StateRecoveryParcelabelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeState2Theme {
                CityScreen()
            }
        }
    }

    @Composable
    private fun CityScreen() {

    }
}