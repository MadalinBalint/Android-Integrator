package com.mendelin.androidintegrator.presentation.main

import android.Manifest
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import com.mendelin.androidintegrator.presentation.theme.AndroidIntegratorTheme
import com.mendelin.androidintegrator.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AndroidIntegratorTheme {
                val mainViewModel = koinViewModel<MainViewModel>()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            mainViewModel.onPermissionResult(
                                permission = Manifest.permission.POST_NOTIFICATIONS,
                                isGranted = isGranted
                            )
                        }
                    )

                    LaunchedEffect(Unit) {
                        cameraPermissionResultLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    }
                }

                KoinAndroidContext {
                    MainScreen()
                }
            }
        }
    }
}
