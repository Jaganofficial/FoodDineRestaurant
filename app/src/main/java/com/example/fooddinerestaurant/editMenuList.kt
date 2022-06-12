package com.example.fooddinerestaurant

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.firebase.database.FirebaseDatabase


class editMenuList : AppCompatActivity() {
    var itemName: String? = null

    var itemDescprition: String? = null
    var itemPrice: String? = null
    var itemCategory: String? = null
    var itemBitmap: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            supportActionBar?.setTitle("Add to MenuList")
            showContent()
        }


    }

    @Composable
    private fun showContent() {

        var value by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }



        Column {

            ImageSelectorAndCropper()

            OutlinedTextField(
                singleLine = true,
                value = value,
                onValueChange = {
                    value = it
                    itemName = it
                },
                label = { Text("Item Name") },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            OutlinedTextField(
                singleLine = false,
                value = "" + price,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    price = it
                    itemPrice = it
                },
                label = { Text("Price in Rupees") },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            OutlinedTextField(
                singleLine = false,
                value = description,
                onValueChange = {
                    description = it
                    itemDescprition = it
                },
                label = { Text("Description") },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            dropDown()

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                Button(onClick = {
                    if (itemName != null && itemPrice != null && itemDescprition != null && itemCategory != null && itemBitmap != null) {

                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("MenuList")
                        myRef.child("itemName").setValue(itemName).addOnSuccessListener{
                            Toast.makeText(
                                this@editMenuList,
                                "Added to MenuList!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        myRef.child("itemPrice").setValue(itemPrice)

                        myRef.child("itemDescprition").setValue(itemDescprition)

                        myRef.child("itemCategory").setValue(itemCategory)

                        myRef.child("itemBitmap").setValue(itemBitmap)

                    }
                    else
                    {
                        Toast.makeText(
                            this@editMenuList,
                            "Please fill all the fields!",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }) {
                    Text(text = "Add to Menu")
                }
            }
        }
    }

    @Composable
    private fun dropDown() {

        var mExpanded by remember { mutableStateOf(false) }

        val mCities = listOf("Vegetarian", "Non-Vegetarian", "Desert")

        var mSelectedText by remember { mutableStateOf("") }

        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

        // Up Icon when expanded and down icon when collapsed
        val icon = if (mExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(
            Modifier
                .padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedTextField(
                value = mSelectedText, textStyle = TextStyle(fontWeight = FontWeight.ExtraBold),
                onValueChange = {
                    mSelectedText = it
                    itemCategory = it
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Category") },
                trailingIcon = {
                    Icon(icon, "Category of the food",
                        Modifier.clickable {
                            mExpanded = !mExpanded
                        })
                }
            )

            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                mCities.forEach { label ->
                    DropdownMenuItem(onClick = {
                        mSelectedText = label
                        itemCategory = label
                        mExpanded = false
                    }) {
                        Column() {
                            Text(text = label, style = TextStyle(fontWeight = FontWeight.Bold))
                            Divider(modifier = Modifier.padding(horizontal = 5.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ImageSelectorAndCropper() {

        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val context = LocalContext.current
        val bitmap = remember {
            mutableStateOf<Bitmap?>(null)
        }

        val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                imageUri = result.uriContent
                Log.e("CROP", "Hii")
            } else {
                // an error occurred cropping
                val exception = result.error
                Log.e("CROP", exception.toString())
            }
        }

        val imagePickerLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                val cropOptions = CropImageContractOptions(uri, CropImageOptions())
                Log.e("CROP", "hello " + cropOptions)
                imageCropLauncher.launch(cropOptions)
            }

        if (imageUri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Surface(modifier = Modifier
                .size(180.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
                shape = RoundedCornerShape(85.dp),
                color = Color.LightGray
            ) {
                if (bitmap.value != null) {
                    itemBitmap = bitmap.value.toString()
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = "Camera",
                        modifier = Modifier.clickable {
                            imagePickerLauncher.launch("image/*")
                        })
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera", contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}