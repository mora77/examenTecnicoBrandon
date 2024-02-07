package com.example.examentecnico.recipe.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.examentecnico.R
import com.example.examentecnico.recipe.data.AppMockup
import com.example.examentecnico.recipe.data.network.response.Details
import com.example.examentecnico.ui.theme.colorYellowOrange

@Composable
fun DetailScreen(
    recipesViewModel: RecipesViewModel,
    navController: NavHostController,
    id: String
) {
    val isLoading: Boolean by recipesViewModel.isLoading.observeAsState(initial = true)
    recipesViewModel.getRecipeDetail(id = id)

    Column(
        modifier = if (isLoading) Modifier.fillMaxHeight() else Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        verticalArrangement = if (isLoading) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        } else {
            recipesViewModel.detail.value?.let {
                val context = LocalContext.current
                val pm = context.packageManager
                BodyDetail(
                    navController = navController,
                    modifier = Modifier,
                    recipeDetail = it
                ) {
                    onMapClickMethod(it.latitude, it.longitude, it.name, context, pm)
                }
            }
        }
    }
}

@Composable
fun BodyDetail(
    navController: NavController,
    modifier: Modifier,
    recipeDetail: Details,
    onMapClick: () -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(colorYellowOrange)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_detail),
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(Color.White)
                .padding(all = 12.dp)
        )
        Text(
            text = recipeDetail.name,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(color = Color.White)
                .padding(horizontal = 4.dp, vertical = 12.dp)

        )
        AsyncImage(
            model = AppMockup.BASE_IMAGE_URL + recipeDetail.image,
            contentDescription = recipeDetail.name,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 0.dp)
                .background(Color.White)
                .padding(horizontal = 4.dp, vertical = 0.dp)
                .aspectRatio(4f / 3f, false)
        )
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(color = Color.White)
                .padding(all = 8.dp),
            factory = { TextView(it) },
            update = {
                it.text = HtmlCompat.fromHtml(recipeDetail.text, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
        )

        Text(
            text = stringResource(R.string.recipes_ingredients),
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 12.dp)
                .padding(bottom = 8.dp)
                .background(color = Color.White)
                .padding(horizontal = 4.dp, vertical = 8.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1f, false)
                .verticalScroll(rememberScrollState())
        ) {
            if (recipeDetail.ingredients.isNotEmpty()) recipeDetail.ingredients.forEach { ingred ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 1.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = when (ingred) {
                                recipeDetail.ingredients.first() -> RoundedCornerShape(
                                    8.dp,
                                    8.dp,
                                    0.dp,
                                    0.dp
                                )
                                recipeDetail.ingredients.last() -> RoundedCornerShape(
                                    0.dp,
                                    0.dp,
                                    8.dp,
                                    8.dp
                                )
                                else -> RoundedCornerShape(0.dp)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.requiredWidth(30.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_radio_button),
                            contentDescription = ingred,
                            tint = Color.Black,
                            modifier = Modifier.background(color = Color.White),
                        )
                    }
                    Text(
                        text = ingred,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        modifier = modifier.padding(4.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(40.dp)
                .padding(vertical = 2.dp)
                .background(color = Color.White)
                .padding(4.dp)
        ) {

            Box(modifier = Modifier.requiredWidth(30.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.clock_icon),
                    tint = Color.Black,
                    contentDescription = stringResource(id = R.string.cock_time),
                    modifier = Modifier.background(color = Color.White)
                )
            }
            Text(
                text = stringResource(id = R.string.cock_time),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = recipeDetail.cockTime,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(40.dp)
                .padding(vertical = 2.dp)
                .background(color = Color.White)
                .padding(4.dp)
        ) {

            Box(modifier = Modifier.requiredWidth(30.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.friying_pan_icon),
                    tint = Color.Black,
                    contentDescription = stringResource(id = R.string.food_type),
                    modifier = Modifier.background(color = Color.White)
                )
            }
            Text(
                text = stringResource(id = R.string.food_type),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = recipeDetail.cockTime,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(40.dp)
                .padding(vertical = 2.dp)
                .background(color = Color.White)
                .padding(4.dp)
        ) {

            Box(modifier = Modifier.requiredWidth(30.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    tint = Color.Black,
                    contentDescription = stringResource(id = R.string.cock_rations),
                    modifier = Modifier.background(color = Color.White)
                )
            }
            Text(
                text = stringResource(id = R.string.cock_rations),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = recipeDetail.rations,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }

        TextButton(
            onClick = {
                onMapClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .background(color = Color.White)

        ) {
            Text(
                text = stringResource(id = R.string.map_button),
                color = Color.Blue
            )
        }
    }
}

private fun onMapClickMethod(
    lat: String,
    lon: String, q: String, context: Context,
    pm: PackageManager
) {
    if (lat.isNotBlank() && lon.isNotBlank()) {
        val uri = Uri.parse("geo:$lat,$lon?z=8&q=$q")
        val map = Intent(Intent.ACTION_VIEW, uri)
        map.setPackage("com.google.android.apps.maps")
        if (map.resolveActivity(pm) != null) {
            startActivity(context, map, Bundle());
        }
    }
}

