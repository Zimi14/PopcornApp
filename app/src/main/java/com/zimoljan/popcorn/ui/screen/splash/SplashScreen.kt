package com.zimoljan.popcorn.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.zimoljan.popcorn.R


@Composable
fun SplashScreen() {
    Box {
        // Logo
        Logo(
            modifier = Modifier.align(Alignment.Center)
        )

        // Version Number
        VersionText(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun VersionText(
    modifier: Modifier = Modifier
) {
    Text(
        text = SplashViewModel.versionNumber,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        modifier = modifier
            .padding(bottom = 100.dp)
    )
}

@Composable
private fun Logo(modifier: Modifier = Modifier) {
    Image(
        imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
        modifier = modifier
            .requiredSize(100.dp),
        contentScale = ContentScale.Inside,
        contentDescription = null
    )
}