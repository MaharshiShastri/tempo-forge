package com.tempo.instruments.ui.equipment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.tempo.instruments.data.MachineReading

@Composable
fun TemperatureChart(readings: List<MachineReading>, modifier: Modifier= Modifier){
    if (readings.size < 2){return}

    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)){
        val temperatures = readings.map{it.temperature}

        val minTemperature = temperatures.minOrNull() ?: return@Canvas
        val maxTemperature = temperatures.maxOrNull() ?: return@Canvas

        val temperatureRange = (maxTemperature - minTemperature).coerceAtLeast(1.0)

        val path = Path()

        temperatures.forEachIndexed { index, temperature ->
            val x = if(temperatures.size == 1){0f} else{size.width * index / (temperatures.size-1)}

            val normalized = ((temperature - minTemperature)/temperatureRange).toFloat()

            val y = size.height * (1f - normalized)

            if(index==0){
                path.moveTo(x, y)
            }else{
                path.lineTo(x, y)
            }
        }

        drawPath(path=path, color= Color.Red, style=Stroke(width = 5f))
    }

}