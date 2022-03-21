package com.norrisboat.jordanconcept.ui.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.jordanconcept.ui.theme.JordanConceptTheme
import com.norrisboat.jordanconcept.ui.theme.JordanRed
import com.norrisboat.jordanconcept.ui.theme.ProjectGray


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddToCartView(modifier: Modifier = Modifier, onAddToCart: () -> Unit) {

    val selectedValue = remember {
        mutableStateOf("")
    }

    val data = listOf("EU 40", "EU 41", "EU 42", "EU 43", "EU 44")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.White)
    ) {

        Column {
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 40.dp)) {

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                )

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(text = "Men's Air 1 Mid shoe in black", color = Color.Gray)
                    Text(
                        text = "$270.00",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
                color = Color.LightGray
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                text = "SELECT SIZE",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            LazyVerticalGrid(
                modifier = Modifier.height(150.dp),
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(24.dp),
            ) {

                items(data) { item ->

                    val selectedBackgroundColor by animateColorAsState(
                        targetValue = if (item == selectedValue.value) JordanRed else ProjectGray,
                        animationSpec = tween(250)
                    )

                    val selectedTextColor by animateColorAsState(
                        targetValue = if (item == selectedValue.value) Color.White else Color.Black,
                        animationSpec = tween(100)
                    )

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(selectedBackgroundColor)
                            .clickable {
                                selectedValue.value = item
                            }
                    ) {
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            color = selectedTextColor
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedValue.value.isBlank()) ProjectGray else JordanRed),
                onClick = {
                    onAddToCart()
                }
            ) {
                Text(
                    text = "Add to Cart",
                    color = if (selectedValue.value.isBlank()) Color.Black else Color.White
                )
            }

        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddToCartViewPreview() {
    JordanConceptTheme {
        AddToCartView {

        }
    }
}