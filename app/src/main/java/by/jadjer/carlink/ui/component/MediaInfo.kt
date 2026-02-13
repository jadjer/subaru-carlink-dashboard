package by.jadjer.carlink.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun MediaInfo(
    modifier: Modifier = Modifier,
    source: String = "OFF",
    volume: Int = 0,
    visible: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    val containerHeight = 60.dp
    val circleSize = 60.dp
    val containerBorderWidth = 2.dp
    val circleRingWidth = 4.dp
    val innerCircleBorderWidth = 3.dp
    val outerCircleScale = 0.95f
    val innerCircleScale = 0.85f
    val contentPaddingStart = 50.dp
    val contentPaddingEnd = 50.dp

    // Material 3 цвета из текущей темы
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val outlineColor = MaterialTheme.colorScheme.outline
    val primaryColor = MaterialTheme.colorScheme.primary

    // Типографика M3
    val sourceTextStyle = MaterialTheme.typography.bodyLarge
    val volumeTextStyle = MaterialTheme.typography.headlineSmall

    // Форма со стандартным скруглением medium (8.dp)
    val containerShape = MaterialTheme.shapes.medium

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(containerHeight),
            contentAlignment = Alignment.CenterEnd
        ) {
            // Основной контейнер – Surface
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = circleSize / 2),
                shape = containerShape,
                color = surfaceColor,          // фон внутри границ
                border = BorderStroke(containerBorderWidth, outlineColor), // граница поверх
                tonalElevation = 0.dp,         // убираем тень (по желанию)
                shadowElevation = 0.dp
            ) {
                // Содержимое – автоматически обрезается по shape
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = source,
                        modifier = Modifier.padding(5.dp),
                        color = onSurfaceColor,
                        style = sourceTextStyle
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = contentPaddingStart, end = contentPaddingEnd),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        content()
                    }
                }
            }

            // Круг с громкостью (оставляем кастомную отрисовку, но цвета из темы)
            Surface(
                modifier = Modifier
                    .size(circleSize)
                    .drawBehind {
                        // внешнее кольцо (primary)
                        drawCircle(
                            color = primaryColor,
                            radius = size.minDimension / 2 * outerCircleScale,
                            style = Stroke(width = circleRingWidth.toPx())
                        )
                        // внутреннее кольцо (outline)
                        drawCircle(
                            color = outlineColor,
                            radius = size.minDimension / 2 * innerCircleScale,
                            style = Stroke(width = innerCircleBorderWidth.toPx())
                        )
                    },
                shape = CircleShape,
                color = surfaceColor,          // фон круга
                border = null,                // обводки нарисованы выше
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = volume.toString().padStart(2, '0'),
                        color = onSurfaceColor,
                        style = volumeTextStyle
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaInfoPreview() {
    MediaInfo(source = "CD", volume = 5, content = {
        Text("SVETLOE", color = Color.White, fontSize = 20.sp)
    })
}