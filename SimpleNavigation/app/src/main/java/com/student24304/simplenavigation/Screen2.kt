package com.student24304.simplenavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.student24304.simplenavigation.models.Movie

@Composable
fun Screen2(
    movie: Movie, paddingValues: PaddingValues
) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        item {
            Screen2Content(movie = movie)
        }
    }
}

@Composable
private fun Screen2Content(movie: Movie) {


    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Box(
            modifier = Modifier
                .height(350.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Black, MaterialTheme.colorScheme.background), startY = 0f, endY = 500f
                        ),
                    ),
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    painter = painterResource(id = movie.posterResId),
                    contentScale = ContentScale.Crop,
                    alpha = 0.8f,
                    contentDescription = "Movie Poster"
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)
                    .align(Alignment.BottomCenter),
               verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 150.dp, height = 210.dp),
                    painter = painterResource(id = movie.posterResId),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Movie Poster"
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 15.dp),
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

            }
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = movie.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )

            TextWithLabel(
                modifier = Modifier.padding(top = 20.dp), label = "Starring:", text = movie.starring
            )
            TextWithLabel(label = "Running time:", text = movie.runningTime)




            SelectSeats(
                modifier = Modifier.padding(top = 15.dp), movie = movie
            )
        }

    }
}

@Composable
fun TextWithLabel(
    label: String, text: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun SelectSeats(
    movie: Movie, modifier: Modifier = Modifier
) {
    var selectedSeatNumbers by remember {
        mutableIntStateOf(0)
    }
    val buttonIncreaseNumberSeatsIsEnabled = selectedSeatNumbers < movie.maxSeats
    val buttonDecreaseNumberSeatsIsEnabled = selectedSeatNumbers > 0


    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(
            text = "Select Seats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(enabled = buttonDecreaseNumberSeatsIsEnabled, onClick = {

                --selectedSeatNumbers

            }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }

            Text(text = selectedSeatNumbers.toString())

            IconButton(enabled = buttonIncreaseNumberSeatsIsEnabled, onClick = {

                ++selectedSeatNumbers
            }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(imageVector = Icons.Outlined.Chair, contentDescription = null)
            Text(
                modifier = Modifier.padding(horizontal = 5.dp), text = movie.maxSeats.toString()
            )
        }
    }

}