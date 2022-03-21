package com.norrisboat.jordanconcept.ui.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.jordanconcept.R
import com.norrisboat.jordanconcept.ui.theme.JordanConceptTheme
import com.norrisboat.jordanconcept.ui.theme.JordanRed

@Composable
fun CartView(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    iconSize: Dp = 24.dp,
    cartNumber: Int = 0,
    backgroundColor: Color = JordanRed,
    @DrawableRes icon: Int = R.drawable.ic_shopping_bag
) {
    Box(
        modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {

        Circle(size = size, backgroundColor = backgroundColor) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "shopping bag",
                modifier = Modifier.size(iconSize),
                tint = Color.White
            )
        }

        if (cartNumber > 0) {
            Circle(
                modifier = Modifier.offset(15.dp, (-15).dp),
                size = 25.dp,
                backgroundColor = Color.Black
            ) {
                Text(text = cartNumber.toString(), color = Color.White, fontSize = 12.sp)
            }
        }

    }
}

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    size: Dp,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CartViewPreview() {
    JordanConceptTheme {
        CartView(cartNumber = 5)
    }
}