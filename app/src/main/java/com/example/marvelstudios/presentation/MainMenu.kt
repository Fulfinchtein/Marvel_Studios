package com.example.marvelstudios.presentation

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import com.example.marvelstudios.R
import com.example.marvelstudios.data.Hero
import com.example.marvelstudios.presentation.components.CardMenu
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.marvelstudios.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainMenu(navController: NavController) {
    val heroes = remember { Hero.getHeroes() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background__1_),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.1f),
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.marvel_studios),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Choose your hero",
                color = White,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(70.dp))

            val listState = rememberLazyListState()
            val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                flingBehavior = flingBehavior,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(start = 25.dp, end = 25.dp, bottom = 50.dp),
            ) {
                items(heroes) { hero ->
                    CardMenu(
                        name = hero.name,
                        heroImageUrl = hero.heroImageUrl,
                        modifier = Modifier.fillParentMaxSize(),
                        onClick = {
                            navController.navigate(
                                "heroInfo/${Uri.encode(hero.name)}/${Uri.encode(hero.heroImageUrl)}"
                            )
                        }
                    )
                }
            }

        }
    }
}
