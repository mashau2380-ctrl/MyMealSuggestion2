package com.example.mymealsuggestion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMealSuggestion()
        }
    }
}

@Composable
fun MyMealSuggestion() {
    val timeofday = arrayOf(
        "Morning", "Mid morning", "Afternoon",
        "Afternoon Snack", "Dinner", "After dinner"
    )
    val mealdisplay = arrayOf(
        "Avocado toast,Waffles,Strawberries,Blueberries,Whipped cream,Coffee,Orange juice,Eggs",
        "Croissant,Blueberry,Orange Juice,Coffee,Water",
        "Mixed greens,Sliced avocado,Red onion,Nuts",
        "Chips",
        "Steak, Pap, Stew,Pork,Chicken,Lamb chops",
        "Macarons,Ice cream, cake, donut"
    )
    // These images exists in the res/drawable folder
    val mealImages = arrayOf(
        R.drawable.breakfast,
        R.drawable.mid_morning,
        R.drawable.lunch,
        R.drawable.snack,
        R.drawable.dinner,
        R.drawable.dessert
    )

    var usertimeofday by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var showImage by remember { mutableStateOf(false) }
    var currentImage by remember { mutableIntStateOf(R.drawable.placeholder) } // Add a placeholder image

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF8F9FC), Color(0xFFE6F0FF))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "My Meal Suggestion",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF1E3A8A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Image display
            if (showImage) {
                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = "Meal Image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                )  {
                    Text(
                        "Meal Preview",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = usertimeofday,
                onValueChange = { text -> usertimeofday = text },
                label = { Text("Enter time of day (e.g. Morning, Afternoon)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF1E3A8A),
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color(0xFF1E3A8A),
                )
            )

            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val input = usertimeofday.trim()
                        val matchedIndex =
                            timeofday.indexOfFirst { it.equals(input, ignoreCase = true) }
                        if (matchedIndex != -1) {
                            result = mealdisplay[matchedIndex]
                            currentImage = mealImages[matchedIndex]
                            showImage = true
                        } else {
                            result = "Please enter a valid time (e.g. Morning, Afternoon)"
                            showImage = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Suggest Meal", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        usertimeofday = ""
                        result = ""
                        showImage = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B7280)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Meal Suggestion:",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF1E3A8A),
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        result,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF111827),
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}




