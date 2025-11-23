package com.beeqan.vaxi_track.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RoleOption(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun RoleSelectionScreen(
    onNavigateToParentDashboard: () -> Unit,
    onNavigateToCHWDashboard: () -> Unit
) {
    val roles = listOf(
        RoleOption(
            id = "parent",
            title = "Parent/Guardian",
            description = "Track your child's vaccinations",
            icon = Icons.Default.Group
        ),
        RoleOption(
            id = "chw",
            title = "Health Worker",
            description = "Manage community immunizations",
            icon = Icons.Default.LocalHospital
        ),
        RoleOption(
            id = "officer",
            title = "Health Officer",
            description = "View reports and analytics",
            icon = Icons.Default.BarChart
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Your Role",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose how you'll use VaxiTrack",
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
        }

        // Role Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            roles.forEach { role ->
                RoleCard(
                    role = role,
                    onClick = {
                        when (role.id) {
                            "parent" -> onNavigateToParentDashboard()
                            "chw" -> onNavigateToCHWDashboard()
                            else -> onNavigateToParentDashboard() // Placeholder
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun RoleCard(
    role: RoleOption,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF667eea)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = role.icon,
                        contentDescription = role.title,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = role.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = role.description,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = Color(0xFF757575),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectionScreenPreview() {
    RoleSelectionScreen(
        onNavigateToParentDashboard = {},
        onNavigateToCHWDashboard = {}
    )
}