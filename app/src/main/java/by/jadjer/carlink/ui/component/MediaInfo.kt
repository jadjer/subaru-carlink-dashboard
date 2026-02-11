package by.jadjer.carlink.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun MediaInfo(
    volume: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val containerHeight = 60.dp
    val circleSize = 58.dp
    val containerBorderWidth = 2.dp
    val circleRingWidth = 3.dp
    val innerCircleBorderWidth = 2.dp
    val innerCircleScale = 0.85f
    val contentPaddingEnd = 50.dp
    val containerColor = Color.Black
    val borderColor = Color.White
    val accentColor = Color(0xFF007FFF)
    val volumeTextColor = Color.White

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = circleSize / 2)
                .border(
                    width = containerBorderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(containerColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = contentPaddingEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                content()
            }
        }

        Box(
            modifier = Modifier
                .size(circleSize)
                .background(containerColor, CircleShape)
                .drawBehind {
                    drawCircle(
                        color = accentColor,
                        radius = size.minDimension / 2,
                        style = Stroke(width = circleRingWidth.toPx())
                    )
                    drawCircle(
                        color = borderColor,
                        radius = size.minDimension / 2 * innerCircleScale,
                        style = Stroke(width = innerCircleBorderWidth.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = volume.toString().padStart(2, '0'),
                color = volumeTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaInfoPreview() {
    MediaInfo(5, content = {
        Text("SVETLOE", color = Color.White, fontSize = 20.sp)
        Text("90.40", color = Color.White, fontSize = 20.sp)
    })
}