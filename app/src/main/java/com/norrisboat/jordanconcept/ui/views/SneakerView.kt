package com.norrisboat.jordanconcept.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.jordanconcept.R
import com.norrisboat.jordanconcept.ui.theme.JordanConceptTheme
import com.norrisboat.jordanconcept.ui.theme.JordanRed
import com.norrisboat.jordanconcept.ui.theme.ProjectGray

@Composable
fun SneakerView(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(ProjectGray)
            .clickable { onClick() }
    ) {

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .size(5.dp, 50.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(JordanRed)
        )

        Column(
            modifier = Modifier.padding(start = 24.dp, top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = "Men's Air 1 Mid shoe in black", color = Color.Gray)
            Text(
                text = "Jordan",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Image(
                painterResource(R.drawable.jordans),
                "item image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(text = "Price", color = Color.Gray)
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "$270.00",
                color = JordanRed,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }

    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SneakerViewPreview() {
    JordanConceptTheme {
        SneakerView {}
    }
}