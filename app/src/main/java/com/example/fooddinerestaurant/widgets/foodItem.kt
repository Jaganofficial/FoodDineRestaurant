package com.example.fooddinerestaurant.widgets

import android.provider.SyncStateContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddinerestaurant.R

@Composable
fun foodItem(modifier : Modifier=Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)){
        Card(modifier = modifier,elevation = 15.dp, shape = RoundedCornerShape(10.dp)) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(), verticalAlignment =Alignment.Top) {
                //if(poster == null || poster.isEmpty()){
                Card(modifier = Modifier
                    .height(190.dp)
                    .width(138.dp)
                    .padding(7.dp), elevation = 15.dp, shape = RoundedCornerShape(15.dp)) {
                    Image(painter = painterResource(id = com.example.fooddinerestaurant.R.drawable.indian_food), contentDescription = "Image", contentScale = ContentScale.Crop)
                }
//                }
//                else{
//                    Image(modifier = Modifier.weight(1f),painter = rememberAsyncImagePainte(model = SyncStateContract.Constants.IMAGE_BASE_URL+poster, placeholder = painterResource(
//                        id = android.R.drawable.default_poster
//                    )),contentDescription = "poster", contentScale = ContentScale.Crop)
//                }
                Column(
                    Modifier
                        .weight(2f)
                        .padding(10.dp), verticalArrangement = Arrangement.Top) {

                    Text(text = "Meals", color = MaterialTheme.colors.onBackground, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    //Text(text = "x2", color = MaterialTheme.colors.onSecondary, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("x2")
                        },
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 22.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold
                    )
                    //if(overview.length<50){
                        Text(text = "Table No: 4", color = MaterialTheme.colors.onBackground, fontSize = 16.sp,modifier=Modifier.padding(vertical=5.dp))
//                    }
//                    else{
//                        Text(text = overview.substring(0,49)+"...", color = MaterialTheme.colors.onBackground, fontSize = 18.sp)
//                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
                    {
                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(vertical = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    121,
                                    198,
                                    48,
                                    255
                                )
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Done
                                , contentDescription = "add" , tint = Color.White)
                        }

                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(vertical = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(252, 73, 73, 255)
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Close
                                , contentDescription = "close", tint = Color.White )
                        }
                    }
                }
            }

        }
    }
}