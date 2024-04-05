package com.example.musicapplicaiton.Presentation.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.min

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicHome(viewModel: MusicHomeViewModel) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(color = Color.Black)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchSongs()
    }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(containerColor = Color.Black) {
        Column(modifier = Modifier.fillMaxSize()) {
//            Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fadingEdges(scrollState)
                    .weight(1f)
            ) {
                if (selectedTabIndex == 0) {
                    viewModel.state.songList.forEach {
                        songItem(
                            title = it.name ?: "",
                            subTitle = it.artist ?: ""
                        )
                    }
                } else {
                    viewModel.state.songList.forEach {
                        if (it.top_track == true)
                            songItem(
                                title = it.name ?: "",
                                subTitle = it.artist ?: ""
                            )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 25.dp)
            ) {
                CustomTabRow(
                    modifier = Modifier
                        .padding(bottom = 25.dp),
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
            }
        }
    }
}

@Composable
fun CustomDotIndicator(
    color: Color, dotSize: Dp, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dotSize)
            .clip(shape = CircleShape)
            .background(color = color)
    )
}

@Composable
fun CustomTabRow(
    modifier: Modifier, selectedTabIndex: Int, onTabSelected: (Int) -> Unit
) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(Color.Transparent.copy(alpha = 0.4f), Color.Transparent)
//                )
//            ),
//        elevation = 5.dp
//    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.clickable { onTabSelected(0) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "For You",
                color = if (selectedTabIndex == 0) Color.White else Color.Gray.copy(alpha = 0.5f)
            )
            if (selectedTabIndex == 0) CustomDotIndicator(color = Color.White, dotSize = 10.dp)
        }
        Column(
            modifier = Modifier.clickable { onTabSelected(1) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Top Track",
                color = if (selectedTabIndex == 1) Color.White else Color.Gray.copy(alpha = 0.5f)
            )
            if (selectedTabIndex == 1) CustomDotIndicator(color = Color.White, dotSize = 10.dp)
        }
    }
//    }
}


@Composable
fun songItem(
    title: String,
    subTitle: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick?.invoke() }
        .padding(top = 18.dp, bottom = 18.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            leadingIcon?.let {
            Card(
                modifier = Modifier.size(55.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                border = BorderStroke(1.dp, Color.Red),
                shape = CircleShape,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    leadingIcon?.invoke()
                }
            }
//            }
            Column(
                modifier = Modifier
                    .padding(start = 15.dp) // Adjust start padding if there is a leading icon
                    .weight(1f)
            ) {
                Text(
                    text = title, style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp, // Adjust font size as needed
                        lineHeight = 20.sp
                    ), color = Color.White
                )
                Text(
                    text = subTitle, style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp, // Adjust font size as needed
                        lineHeight = 16.sp
                    ), color = Color.Gray.copy(alpha = 0.7f)
                )
            }
        }
    }
}


fun Modifier.fadingEdges(
    scrollState: ScrollState,
    topEdgeHeight: Dp = 72.dp,
    bottomEdgeHeight: Dp = 72.dp
): Modifier = this.then(
    Modifier
        // adding layer fixes issue with blending gradient and content
        .graphicsLayer { alpha = 0.99F }
        .drawWithContent {
            drawContent()

            val topColors = listOf(Color.Transparent, Color.Black)
            val topStartY = scrollState.value.toFloat()
            val topGradientHeight = min(topEdgeHeight.toPx(), topStartY)
            drawRect(
                brush = Brush.verticalGradient(
                    colors = topColors,
                    startY = topStartY,
                    endY = topStartY + topGradientHeight
                ),
                blendMode = BlendMode.DstIn
            )

            val bottomColors = listOf(Color.Black, Color.Transparent)
            val bottomEndY = size.height - scrollState.maxValue + scrollState.value
            val bottomGradientHeight =
                min(bottomEdgeHeight.toPx(), scrollState.maxValue.toFloat() - scrollState.value)
            if (bottomGradientHeight != 0f) drawRect(
                brush = Brush.verticalGradient(
                    colors = bottomColors,
                    startY = bottomEndY - bottomGradientHeight,
                    endY = bottomEndY
                ),
                blendMode = BlendMode.DstIn
            )
        }
)

