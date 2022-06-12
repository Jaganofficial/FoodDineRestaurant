package com.example.fooddinerestaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddinerestaurant.ui.theme.FoodDineRestaurantTheme
import com.example.fooddinerestaurant.widgets.foodItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDineRestaurantTheme {

                val drawerState = remember{
                    mutableStateOf(false)
                }

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        drawerState.value = false
                    },
                    topBar = {
                        CustomTopBar{
                            drawerState.value = !drawerState.value
                        }
                    }
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                            .clickable {
                                if (drawerState.value) {
                                    drawerState.value = false
                                }
                            }){

                        showMainContent(modifier=Modifier.clickable {
                            if(drawerState.value)
                            {
                                drawerState.value=false
                            }
                        })
                        NavigationDrawer(drawerState.value)
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavigationDrawer(value: Boolean) {
        AnimatedVisibility(visible = value) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.65f)
                    .background(
                        MaterialTheme.colors.primary
                    )
                    ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {

                        },text = "Live Orders", textAlign =  TextAlign.Center,color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)

                    Divider(modifier = Modifier.padding(8.dp), color = Color.LightGray)

                    Text(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {

                        },text = "On Progress", textAlign =  TextAlign.Center, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)

                    Divider(modifier = Modifier.padding(8.dp), color = Color.LightGray)

                    Text(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {

                        },text = "Table Details", textAlign =  TextAlign.Center, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)

                    Divider(modifier = Modifier.padding(8.dp), color = Color.LightGray)

                    Text(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {
                                   intent= Intent(this@MainActivity,editMenuList::class.java)
                                   startActivity(intent)
                        },text = "Add/Edit MenuList", textAlign =  TextAlign.Center, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)

                    Divider(modifier = Modifier.padding(8.dp), color = Color.LightGray)
                }
            }
        }
    }



    @Composable
    fun CustomTopBar(updateDrawerState: () -> Unit){
        TopAppBar(elevation = 10.dp) {
            Box(Modifier.background(MaterialTheme.colors.primary)){
                Icon(modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        updateDrawerState()
                    },imageVector = Icons.Default.Menu, contentDescription = "menu", tint = Color.LightGray )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Food ")
                            withStyle(
                                style = SpanStyle(
                                    Color(255, 102, 102),
                                    fontSize = 25.sp,
                                )
                            )
                            {
                                append("Dine ")
                            }
                            append("In")
                        },
                        color = Color.White,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

            }
        }
    }


    @Composable
    private fun showMainContent(modifier: Modifier){
        LazyColumn()
        {
            items(15)
            {
                foodItem(modifier)
            }
        }
    }
}

