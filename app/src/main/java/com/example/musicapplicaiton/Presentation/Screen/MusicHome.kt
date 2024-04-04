package com.example.musicapplicaiton.Presentation.Screen

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicHome(viewModel: MusicHomeViewModel) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(color = Color.Black)

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchSongs()
    }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(containerColor = Color.Black) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn {
                    itemsIndexed(viewModel.state.songList) { index, song ->
                        if (selectedTabIndex == 0) {
                            songItem(title = song.name ?: "", subTitle = song.artist ?: "")
                        }
                    }
                }

                LazyColumn {
                    itemsIndexed(viewModel.state.songList) { index, song ->
                        if (selectedTabIndex == 1) {
                            if (song.top_track == true) {
                                songItem(title = song.name ?: "", subTitle = song.artist ?: "")
                            }
                        }
                    }
                }
            }

            CustomTabRow(modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
                selectedTabIndex = selectedTabIndex,
                { selectedTabIndex = it })

            // Tab Row at the bottom
            /*TabRow(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black,
                selectedTabIndex = selectedTabIndex
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 }
                ) {
                    Text(text = "Top Click", color = Color.White)
                }
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 }
                ) {
                    Text(text = "For You", color = Color.White)
                }
            }*/
        }
    }
}

@Composable
fun CustomDotIndicator(
    color: Color,
    dotSize: Dp,
    modifier: Modifier = Modifier
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
    modifier: Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        modifier = modifier.height(40.dp),
        containerColor = Color.Transparent,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            CustomDotIndicator(
                color = Color.White,
                dotSize = 10.dp,
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Gray.copy(alpha = 0.5f))
                )
            )
        ) {
            Text(text = "Top Click", color = Color.White)
        }
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Gray.copy(alpha = 0.5f), Color.Gray.copy(alpha = 0.1f))
                )
            )
        ) {
            Text(text = "For You", color = Color.White)
        }
    }
}


@Composable
fun songItem(
    title: String,
    subTitle: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .padding(top = 18.dp, bottom = 18.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            leadingIcon?.let {
                Card(
                    modifier = Modifier
                        .size(55.dp),
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
                    text = title,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp, // Adjust font size as needed
                        lineHeight = 20.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = subTitle,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp, // Adjust font size as needed
                        lineHeight = 16.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}

