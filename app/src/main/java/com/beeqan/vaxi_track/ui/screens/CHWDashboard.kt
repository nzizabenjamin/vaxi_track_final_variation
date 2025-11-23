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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StatCard(
    val icon: ImageVector,
    val number: String,
    val label: String,
    val backgroundColor: Color,
    val iconColor: Color,
    val hasProgress: Boolean = false
)

data class Patient(
    val id: Int,
    val name: String,
    val age: String,
    val vaccine: String,
    val initial: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CHWDashboard(
    onNavigateToRecordVaccination: () -> Unit,
    onNavigateToRegisterChild: () -> Unit
) {
    val stats = listOf(
        StatCard(
            icon = Icons.Default.ChildCare,
            number = "156",
            label = "Children Registered",
            backgroundColor = Color(0xFFBBDEFB),
            iconColor = Color(0xFF2196F3)
        ),
        StatCard(
            icon = Icons.Default.Warning,
            number = "12",
            label = "Due This Week",
            backgroundColor = Color(0xFFFFE0B2),
            iconColor = Color(0xFFFF9800)
        ),
        StatCard(
            icon = Icons.Default.CheckCircle,
            number = "87%",
            label = "Coverage Rate",
            backgroundColor = Color(0xFFC8E6C9),
            iconColor = Color(0xFF4CAF50),
            hasProgress = true
        ),
        StatCard(
            icon = Icons.Default.CalendarToday,
            number = "5",
            label = "Today's Appointments",
            backgroundColor = Color(0xFFE1BEE7),
            iconColor = Color(0xFF9C27B0)
        )
    )

    val patients = listOf(
        Patient(
            id = 1,
            name = "Aisha Uwera",
            age = "9 months",
            vaccine = "MMR Vaccine",
            initial = "AU",
            color = Color(0xFFEC4899)
        ),
        Patient(
            id = 2,
            name = "Jean Mbabazi",
            age = "2 years",
            vaccine = "Polio Booster",
            initial = "JM",
            color = Color(0xFF3B82F6)
        ),
        Patient(
            id = 3,
            name = "Grace Imena",
            age = "6 months",
            vaccine = "DPT Vaccine",
            initial = "GI",
            color = Color(0xFF10B981)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CHW Dashboard",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open drawer */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF667eea)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToRegisterChild,
                containerColor = Color(0xFF667eea),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Register Child"
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF667eea),
                        selectedTextColor = Color(0xFF667eea)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Patients"
                        )
                    },
                    label = { Text("Patients") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "Reports"
                        )
                    },
                    label = { Text("Reports") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") }
                )
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
            // Statistics Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    CHWStatCard(stats[0])
                    CHWStatCard(stats[1])
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    CHWStatCard(stats[2])
                    CHWStatCard(stats[3])
                }
            }

            // Section Title
            Text(
                text = "Due Vaccinations Today",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Patient List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                patients.forEach { patient ->
                    PatientCard(
                        patient = patient,
                        onRecordClick = onNavigateToRecordVaccination
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CHWStatCard(stat: StatCard) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = stat.backgroundColor,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = stat.label,
                tint = stat.iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stat.number,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            Text(
                text = stat.label,
                fontSize = 12.sp,
                color = Color(0xFF757575)
            )
            if (stat.hasProgress) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.87f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFF4CAF50),
                    trackColor = Color.White
                )
            }
        }
    }
}

@Composable
fun PatientCard(
    patient: Patient,
    onRecordClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(patient.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = patient.initial,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = patient.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Text(
                    text = "${patient.age} • ${patient.vaccine}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            // Record Button
            Button(
                onClick = onRecordClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF667eea)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("RECORD")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CHWDashboardPreview() {
    CHWDashboard(
        onNavigateToRecordVaccination = {},
        onNavigateToRegisterChild = {}
    )
}