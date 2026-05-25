package com.apay.comprobante

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.composables.icons.lucide.*
import com.apay.ui.components.*
import com.apay.ui.theme.*
import java.io.File
import java.util.concurrent.Executors

@Composable
fun ComprobanteScreen(
    onVolver: () -> Unit,
    viewModel: ComprobanteViewModel = hiltViewModel()
) {
    val state         by viewModel.uiState.collectAsState()
    val context       = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.iniciarCaptura()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val s = state) {
            is ComprobanteUiState.Idle -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    A2Body(text = "Captura la foto del comprobante de pago")
                    A2Caption(text = "Asegúrate de que el comprobante esté bien iluminado y sea legible")
                    Spacer(Modifier.height(16.dp))
                    A2Button(
                        text     = "Abrir cámara",
                        icon     = Lucide.Camera,
                        modifier = Modifier.fillMaxWidth(),
                        onClick  = {
                            if (ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                viewModel.iniciarCaptura()
                            } else {
                                cameraPermLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    )
                }
            }

            is ComprobanteUiState.Capturando -> {
                CameraPreviewWithCapture(
                    modifier  = Modifier.fillMaxWidth().height(500.dp),
                    onCapture = { uri -> viewModel.onFotoCapturada(uri) },
                    onClose   = { viewModel.cancelar() }
                )
            }

            is ComprobanteUiState.Preview -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model    = s.uri,
                        contentDescription = "Comprobante",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        A2Button(
                            text    = "Repetir",
                            onClick = { viewModel.cancelar() },
                            variant = A2ButtonVariant.Secondary,
                            modifier = Modifier.weight(1f)
                        )
                        A2Button(
                            text    = "Enviar",
                            onClick = { viewModel.enviarComprobante(context, s.uri) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            is ComprobanteUiState.Enviando -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = PrimaryElectric)
                    A2BodySmall(text = "Enviando comprobante...")
                }
            }

            is ComprobanteUiState.Enviado -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    A2Tag(text = "✓ Comprobante enviado", variant = A2TagVariant.Filled, color = PrimaryElectric)
                    A2Caption(text = "Será revisado por el equipo")
                    Spacer(Modifier.height(8.dp))
                    A2Button(
                        text    = "Enviar otro",
                        onClick = { viewModel.cancelar() },
                        variant = A2ButtonVariant.Secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            is ComprobanteUiState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    A2Banner(message = s.mensaje, type = A2BannerType.Error)
                    A2Button(
                        text    = "Reintentar",
                        onClick = { viewModel.cancelar() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraPreviewWithCapture(
    modifier: Modifier,
    onCapture: (Uri) -> Unit,
    onClose: () -> Unit
) {
    val context       = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val ic = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build()
                    imageCapture = ic

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            ic
                        )
                    } catch (e: Exception) { /* ignore */ }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
        )

        // Close button
        IconButton(
            onClick  = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(SuperficieCristal.copy(0.8f), CircleShape)
        ) {
            Icon(Lucide.X, "Cerrar", tint = TextPrimary)
        }

        // Capture button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            IconButton(
                onClick  = {
                    val outputDir  = context.cacheDir
                    val outputFile = File(outputDir, "comprobante_${System.currentTimeMillis()}.jpg")
                    val outputOpts = ImageCapture.OutputFileOptions.Builder(outputFile).build()

                    imageCapture?.takePicture(outputOpts, cameraExecutor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                onCapture(Uri.fromFile(outputFile))
                            }
                            override fun onError(exception: ImageCaptureException) { /* ignore */ }
                        }
                    )
                },
                modifier = Modifier
                    .size(72.dp)
                    .background(PrimaryElectric, CircleShape)
            ) {
                Icon(
                    Lucide.Camera, "Capturar",
                    tint     = TextPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { cameraExecutor.shutdown() }
    }
}
