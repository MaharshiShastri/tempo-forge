package com.tempo.instruments.ui.equipment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.tempo.instruments.data.MachineReading
import kotlin.math.roundToInt

@Composable
fun TemperatureChart(
    readings: List<MachineReading>,
    modifier: Modifier = Modifier
) {
    if (readings.size < 2) {
        return
    }

    val textMeasurer = rememberTextMeasurer()

    val labelStyle = MaterialTheme.typography.labelSmall
    val axisColor = MaterialTheme.colorScheme.onSurfaceVariant
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val lineColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        /*
         * Leave room around the actual graph for:
         *
         * Y-axis labels
         * X-axis labels
         */
        val leftPadding = 55.dp.toPx()
        val rightPadding = 16.dp.toPx()
        val topPadding = 20.dp.toPx()
        val bottomPadding = 40.dp.toPx()

        val chartWidth = size.width - leftPadding - rightPadding
        val chartHeight = size.height - topPadding - bottomPadding

        // Make sure readings are ordered oldest -> newest.
        val sortedReadings = readings.sortedBy { it.timestamp }

        val temperatures = sortedReadings.map { it.temperature }

        val minTemperature = temperatures.minOrNull() ?: return@Canvas
        val maxTemperature = temperatures.maxOrNull() ?: return@Canvas

        /*
         * Give the graph a little breathing room above/below
         * the actual values.
         */
        val padding = 5.0

        val minTemp = minTemperature - padding
        val maxTemp = maxTemperature + padding

        val temperatureRange = (maxTemp - minTemp)
            .coerceAtLeast(1.0)

        /*
         * Convert a temperature into an X/Y position.
         */
        fun temperatureToY(temperature: Double): Float {
            val normalized =
                ((temperature - minTemp) / temperatureRange).toFloat()

            return topPadding + chartHeight * (1f - normalized)
        }

        fun indexToX(index: Int): Float {
            return if (sortedReadings.size == 1) {
                leftPadding
            } else {
                leftPadding +
                        chartWidth * index /
                        (sortedReadings.size - 1)
            }
        }

        // ---------------------------------------------------------
        // Y AXIS / GRID
        // ---------------------------------------------------------

        val numberOfGridLines = 5

        for (i in 0..numberOfGridLines) {

            val fraction = i.toFloat() / numberOfGridLines

            val y = topPadding + chartHeight * fraction

            // Horizontal grid line
            drawLine(
                color = gridColor,
                start = Offset(leftPadding, y),
                end = Offset(leftPadding + chartWidth, y),
                strokeWidth = 1.dp.toPx()
            )

            // Temperature label
            val temperature =
                maxTemp - temperatureRange * fraction

            val label = "${temperature.roundToInt()}°"

            val textLayout = textMeasurer.measure(
                text = label,
                style = labelStyle
            )

            drawText(
                textLayoutResult = textLayout,
                topLeft = Offset(
                    x = leftPadding - textLayout.size.width - 8.dp.toPx(),
                    y = y - textLayout.size.height / 2
                ),
                color = axisColor
            )
        }

        // ---------------------------------------------------------
        // X AXIS
        // ---------------------------------------------------------

        drawLine(
            color = axisColor,
            start = Offset(leftPadding, topPadding + chartHeight),
            end = Offset(
                leftPadding + chartWidth,
                topPadding + chartHeight
            ),
            strokeWidth = 1.dp.toPx()
        )

        /*
         * Show up to 5 time labels.
         */
        val labelCount = minOf(5, sortedReadings.size)

        for (i in 0 until labelCount) {

            val index =
                if (labelCount == 1) {
                    0
                } else {
                    i * (sortedReadings.size - 1) /
                            (labelCount - 1)
                }

            val reading = sortedReadings[index]

            val x = indexToX(index)

            val label = formatTimestamp(reading.timestamp)

            val textLayout = textMeasurer.measure(
                text = label,
                style = labelStyle
            )

            drawText(
                textLayoutResult = textLayout,
                topLeft = Offset(
                    x = x - textLayout.size.width / 2,
                    y = topPadding + chartHeight + 10.dp.toPx()
                ),
                color = axisColor
            )
        }

        // ---------------------------------------------------------
        // TEMPERATURE LINE
        // ---------------------------------------------------------

        val path = Path()

        sortedReadings.forEachIndexed { index, reading ->

            val x = indexToX(index)
            val y = temperatureToY(reading.temperature)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // ---------------------------------------------------------
        // DATA POINTS
        // ---------------------------------------------------------

        sortedReadings.forEachIndexed { index, reading ->

            val x = indexToX(index)
            val y = temperatureToY(reading.temperature)

            drawCircle(
                color = lineColor,
                radius = 5.dp.toPx(),
                center = Offset(x, y)
            )

            /*
             * Small hole in the middle makes the point
             * look more like a chart marker.
             */
            drawCircle(
                color = surfaceColor,
                radius = 2.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}

/*
 * Convert your timestamp into something like:
 *
 * 10:00
 * 10:30
 * 11:00
 */
private fun formatTimestamp(timestamp: Long): String {

    val totalMinutes =
        (timestamp / 60_000L) % (24 * 60)

    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return "%02d:%02d".format(hours, minutes)
}