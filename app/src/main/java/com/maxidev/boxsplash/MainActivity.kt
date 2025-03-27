package com.maxidev.boxsplash

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.allowHardware
import coil3.request.allowRgb565
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.maxidev.boxsplash.navigation.NavGraph
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import okio.FileSystem

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced
        }
        setContent {
            BoxsplashTheme {

                val navController = rememberNavController()

                setSingletonImageLoaderFactory { context -> getAsyncImageLoader(context) }

                Surface {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}

private fun getAsyncImageLoader(context: PlatformContext): ImageLoader {

    val cachePolicy = CachePolicy.ENABLED

    return ImageLoader.Builder(context)
        .memoryCachePolicy(policy = cachePolicy)
        .memoryCache { newMemoryCache(context) }
        .diskCachePolicy(policy = cachePolicy)
        .diskCache { newDiskCache() }
        .networkCachePolicy(policy = cachePolicy)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .crossfade(700)
        .allowRgb565(true)
        .allowHardware(true)
        .coroutineContext(Dispatchers.IO)
        .logger(DebugLogger())
        .build()
}

private fun newMemoryCache(context: PlatformContext): MemoryCache {

    return MemoryCache.Builder()
        .maxSizePercent(context = context, 0.03)
        .strongReferencesEnabled(true)
        .build()
}

private fun newDiskCache(): DiskCache {

    return DiskCache.Builder()
        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512 MB
        .build()
}