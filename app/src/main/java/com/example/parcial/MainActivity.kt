package com.example.parcial

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcial.navigation.AppNavigation
import com.example.parcial.utils.NotificationHelper
import com.example.parcial.viewmodel.DrinkViewModel
import androidx.activity.compose.rememberLauncherForActivityResult


class MainActivity : ComponentActivity() {

    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationHelper =
            NotificationHelper(this)

        notificationHelper.createChannel()

        setContent {

            val viewModel: DrinkViewModel =
                viewModel()

            var showRationale by remember {
                mutableStateOf(false)
            }

            val launcher =
                rememberLauncherForActivityResult(
                    contract =
                        ActivityResultContracts
                            .RequestPermission()
                ) { granted ->

                    if (!granted) {
                        showRationale = true
                    }
                }

            LaunchedEffect(Unit) {

                if (
                    Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.TIRAMISU
                ) {

                    if (
                        ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        launcher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    }
                }
            }

            if (showRationale) {

                AlertDialog(
                    onDismissRequest = {
                        showRationale = false
                    },

                    title = {
                        Text("Permiso requerido")
                    },

                    text = {
                        Text(
                            "Las notificaciones están deshabilitadas."
                        )
                    },

                    confirmButton = {

                        Button(
                            onClick = {
                                showRationale = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                AppNavigation(
                    viewModel,
                    notificationHelper
                )
            }
        }
    }
}