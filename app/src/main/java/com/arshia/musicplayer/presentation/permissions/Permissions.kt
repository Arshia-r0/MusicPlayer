package com.arshia.musicplayer.presentation.permissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.arshia.musicplayer.presentation.navigation.MainNavigation


@Composable
fun Permissions(context: Activity) {

    val permission = if(Build.VERSION.SDK_INT > 32) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else { Manifest.permission.READ_EXTERNAL_STORAGE }
    if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
        return MainNavigation()
    }

    val showDialog = remember { mutableStateOf(true) }

    val requestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = { showDialog.value = false }
    )

    when {
        context.shouldShowRequestPermissionRationale(permission) -> {
            RequestDialog(
                onConfirm = {
                    showDialog.value = false
                    context.openAppSettings()
                },
                onDismiss = {
                    showDialog.value = false
                },
                text = "You need to give permission for audio files in the settings.",
                buttonText = "Go to settings"
            )
        }
        showDialog.value -> RequestDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = {
                requestLauncher.launch(permission)
            },
            text = "Access to audio is required.",
            buttonText = "OK"
        )
        else -> return MainNavigation()
    }

}

@Composable
private fun RequestDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    text: String,
    buttonText: String
) {
    Scaffold { ip ->
        AlertDialog(
            modifier = Modifier
                .padding(ip),
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(buttonText)
                }
            },
            title = {
                Text(
                    text = "Request permission",
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    text = text,
                    fontSize = 15.sp
                )
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}