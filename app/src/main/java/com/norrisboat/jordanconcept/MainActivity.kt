package com.norrisboat.jordanconcept

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.jordanconcept.ui.theme.*
import com.norrisboat.jordanconcept.ui.utils.modifyIf
import com.norrisboat.jordanconcept.ui.views.AddToCartView
import com.norrisboat.jordanconcept.ui.views.CartView
import com.norrisboat.jordanconcept.ui.views.SneakerView
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JordanConceptTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = OffWhite
                ) {
                    HomeView()
                }
            }
        }
    }
}

@Composable
fun HomeView() {

    val circleCanvasTopPadding = 100.dp
    val cartBottomPadding = 20.dp
    val cartViewSize = 50.dp
    val addToCartHeight = 500.dp

    val slowFastEasing = CubicBezierEasing(0.4f, 1.0f, 0.4f, 1.0f)

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val isShowingAddToCart = remember {
        mutableStateOf(false)
    }

    val numberOfItemsInCart = remember {
        mutableStateOf(0)
    }

    val showCircle = remember {
        mutableStateOf(false)
    }

    val showBlur = remember {
        mutableStateOf(false)
    }

    val startCircleAnimation = remember {
        mutableStateOf(true)
    }

    val isCircleMoving = remember {
        mutableStateOf(false)
    }

    val isAddingToCart = remember {
        mutableStateOf(false)
    }

    val isAddedToCart = remember {
        mutableStateOf(false)
    }

    val addToCartOffset by animateDpAsState(
        targetValue = if (isShowingAddToCart.value) screenHeight - addToCartHeight else screenHeight,
        animationSpec = tween(400)
    )

    val itemOffsetY by animateDpAsState(
        targetValue = when {
            isCircleMoving.value -> screenHeight - circleCanvasTopPadding
            isShowingAddToCart.value -> screenHeight - addToCartHeight
            isAddingToCart.value -> screenHeight - (addToCartHeight + 20.dp)
            else -> screenHeight
        },
        animationSpec = if (isCircleMoving.value) tween(
            600,
            easing = slowFastEasing
        ) else tween(400)
    )

    val itemOffsetX by animateDpAsState(
        targetValue = if (isAddingToCart.value) 140.dp else 0.dp,
        animationSpec = tween(600)
    )

    val itemWidth by animateDpAsState(
        targetValue = when {
            isShowingAddToCart.value -> screenWidth / 2
            isAddedToCart.value -> 0.dp
            isAddingToCart.value -> 100.dp
            else -> 0.dp
        },
        animationSpec = tween(600)
    )

    val itemHeight by animateDpAsState(
        targetValue = when {
            isShowingAddToCart.value -> 180.dp
            isAddedToCart.value -> 0.dp
            isAddingToCart.value -> 80.dp
            else -> 0.dp
        },
        animationSpec = tween(600)
    )

    val addingToCartOffset by animateDpAsState(
        targetValue = if (isAddingToCart.value) -cartBottomPadding else 60.dp,
        animationSpec = tween(400)
    )

    val blurRadius by animateDpAsState(
        targetValue = if (showBlur.value) 30.dp else 0.dp,
        animationSpec = tween(200)
    )

    val circleRadius by animateDpAsState(
        targetValue = when {
            startCircleAnimation.value -> screenWidth + 100.dp
            isCircleMoving.value -> cartViewSize / 4
            isAddedToCart.value -> 0.dp
            else -> cartViewSize
        },
        animationSpec = tween(600)
    )

    val circleY by animateDpAsState(
        targetValue = if (isCircleMoving.value) screenHeight - circleCanvasTopPadding - cartBottomPadding - (cartViewSize / 2) else screenWidth / 2,
        animationSpec = if (isCircleMoving.value) tween(
            600,
            easing = slowFastEasing
        ) else tween(600)
    )

    val circleBackgroundColor by animateColorAsState(
        targetValue = if (startCircleAnimation.value) LightWhiteColor else Color.White,
        animationSpec = tween(900)
    )

    val bottomCartBackgroundColor by animateColorAsState(
        targetValue = if (isAddedToCart.value) Teal200 else JordanRed,
        animationSpec = tween(600)
    )

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .blur(blurRadius)
        ) {

            Row(
                modifier = Modifier
                    .height(70.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "shopping bag",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.weight(1.0f))

                Text(text = "Nike", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(modifier = Modifier.weight(1.0f))

                CartView(cartNumber = numberOfItemsInCart.value)
            }

            Spacer(modifier = Modifier.height(20.dp))

            SneakerView {
                isShowingAddToCart.value = !isShowingAddToCart.value
                showBlur.value = isShowingAddToCart.value
            }
        }

        if (showCircle.value) {
            if (startCircleAnimation.value) {
                startCircleAnimation.value = false
                isAddingToCart.value = true
            }

            Canvas(modifier = Modifier
                .fillMaxSize()
                .offset(y = circleCanvasTopPadding),
                onDraw = {
                    drawCircle(
                        color = circleBackgroundColor,
                        radius = circleRadius.toPx(),
                        center = Offset(size.width / 2, circleY.toPx())
                    )
                }
            )

            LaunchedEffect(Unit) {
                delay(600)
                isCircleMoving.value = true
                delay(250)
                isAddedToCart.value = true
                delay(500)

                //reset animation values
                startCircleAnimation.value = true
                showCircle.value = false
                showBlur.value = false
                isCircleMoving.value = false
                isAddingToCart.value = false
                isAddedToCart.value = false
            }

        }

        AddToCartView(modifier = Modifier.offset(y = addToCartOffset)) {
            isShowingAddToCart.value = false
            numberOfItemsInCart.value += 1
            if (!isShowingAddToCart.value) {
                showCircle.value = true
            }
        }

        Image(
            painterResource(R.drawable.jordans),
            "item image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(itemWidth, itemHeight)
                .padding(start = 16.dp, top = 40.dp)
                .offset(y = itemOffsetY)
                .modifyIf(isAddingToCart.value) {
                    this.offset(itemOffsetX)
                }
        )

        CartView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = addingToCartOffset),
            backgroundColor = bottomCartBackgroundColor,
            icon = R.drawable.ic_shopping_bag_white,
            size = cartViewSize,
            iconSize = 36.dp
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    JordanConceptTheme {
        HomeView()
    }
}