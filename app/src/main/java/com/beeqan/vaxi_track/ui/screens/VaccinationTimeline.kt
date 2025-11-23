package com.beeqan.vaxi_track.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Vaccination(
    val id: Int,
    val name: String,
    val date: String,
    val location: String,
    val status: String, // "completed", "due", "upcoming"
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccinationTimeline(
    child: ChildInfo,
    onNavigateBack: () -> Unit
) {
    val vaccinations = listOf(
        Vaccination(
            id = 1,
            name = "BCG Vaccine",
            date = "May 16, 2025",
            location = "City Health Center",
            status = "completed",
            color = Color(0xFF4caf50)
        ),
        Vaccination(
            id = 2,
            name = "Polio (OPV) - Dose 1",
            date = "June 15, 2025",
            location = "City Health Center",
            status = "completed",
            color = Color(0xFF4caf50)
        ),
        Vaccination(
            id = 3,
            name = "Polio (OPV) - Dose 2",
            date = "Nov 20, 2025 (in 3 days)",
            location = "City Health Center",
            status = "due",
            color = Color(0xFFF57C00)
        ),
        Vaccination(
            id = 4,
            name = "Measles Vaccine",
            date = "Nov 15, 2026",
            location = "To be scheduled",
            status = "upcoming",
            color = Color(0xFF9E9E9E)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${child.name.split(" ")[0]}'s Vaccines",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Show more options */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF667eea)
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { /* TODO: Download card */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF667eea)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download Vaccination Card")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Child Info Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(child.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = child.initial,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = child.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Born: ${child.dob} • ${child.age}",
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            // Timeline
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                vaccinations.forEachIndexed { index, vaccination ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Timeline indicator
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(vaccination.color)
                            )
                            if (index < vaccinations.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(100.dp)
                                        .background(Color(0xFFE0E0E0))
                                )
                            }
                        }

                        // Vaccination Card
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            shadowElevation = 2.dp,
                            color = Color.White
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = vaccination.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF212121)
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = Color(0xFF757575),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = vaccination.date,
                                        fontSize = 14.sp,
                                        color = Color(0xFF757575)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = Color(0xFF757575),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = vaccination.location,
                                        fontSize = 14.sp,
                                        color = Color(0xFF757575)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Status Badge
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = vaccination.color.copy(alpha = 0.1f)
                                ) {
                                    Text(
                                        text = when (vaccination.status) {
                                            "completed" -> "✓ COMPLETED"
                                            "due" -> "⏰ DUE SOON"
                                            else -> "📅 UPCOMING"
                                        },
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = vaccination.color,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VaccinationTimelinePreview() {
    VaccinationTimeline(
        child = ChildInfo(
            id = 1,
            name = "Emma Johnson",
            age = "6 months old",
            dob = "May 15, 2025",
            nextDue = "Polio (OPV) - Nov 20",
            status = "due",
            initial = "EJ",
            color = Color(0xFFEC4899)
        ),
        onNavigateBack = {}
    )
}