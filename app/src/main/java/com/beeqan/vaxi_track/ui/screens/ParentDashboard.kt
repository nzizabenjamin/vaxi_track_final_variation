package com.beeqan.vaxi_track.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

data class ChildInfo(
    val id: Int,
    val name: String,
    val age: String,
    val dob: String,
    val nextDue: String?,
    val status: String,
    val initial: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDashboard(
    onNavigateToTimeline: (ChildInfo) -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToRegisterChild: () -> Unit
) {
    val children = listOf(
        ChildInfo(
            id = 1,
            name = "Emma Johnson",
            age = "6 months old",
            dob = "May 15, 2025",
            nextDue = "Polio (OPV) - Nov 20",
            status = "due",
            initial = "EJ",
            color = Color(0xFFEC4899)
        ),
        ChildInfo(
            id = 2,
            name = "Noah Johnson",
            age = "3 years old",
            dob = "Jan 10, 2022",
            nextDue = null,
            status = "complete",
            initial = "NJ",
            color = Color(0xFF3B82F6)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Children",
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
                    Box {
                        IconButton(onClick = onNavigateToNotifications) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                        // Notification badge
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .offset(x = 4.dp, y = 4.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF44336))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF667eea)
                )
            )
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
                        selectedTextColor = Color(0xFF667eea),
                        unselectedIconColor = Color(0xFF757575),
                        unselectedTextColor = Color(0xFF757575)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Schedule"
                        )
                    },
                    label = { Text("Schedule") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF667eea),
                        selectedTextColor = Color(0xFF667eea),
                        unselectedIconColor = Color(0xFF757575),
                        unselectedTextColor = Color(0xFF757575)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Records"
                        )
                    },
                    label = { Text("Records") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF667eea),
                        selectedTextColor = Color(0xFF667eea),
                        unselectedIconColor = Color(0xFF757575),
                        unselectedTextColor = Color(0xFF757575)
                    )
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
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF667eea),
                        selectedTextColor = Color(0xFF667eea),
                        unselectedIconColor = Color(0xFF757575),
                        unselectedTextColor = Color(0xFF757575)
                    )
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
            // Header Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 2.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Hello, Sarah Johnson",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem("2", "Children", Color(0xFF667eea))
                        StatItem("1", "Due Soon", Color(0xFFF57C00))
                        StatItem("89%", "Complete", Color(0xFF4caf50))
                    }
                }
            }

            // Children List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                children.forEach { child ->
                    ChildCard(
                        child = child,
                        onViewClick = { onNavigateToTimeline(child) }
                    )
                }
            }

            // Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QuickActionButton(
                    icon = Icons.Default.Add,
                    label = "Add Child",
                    onClick = onNavigateToRegisterChild
                )
                QuickActionButton(
                    icon = Icons.Default.CalendarToday,
                    label = "Calendar",
                    onClick = { }
                )
                QuickActionButton(
                    icon = Icons.Default.Help,
                    label = "Get Help",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatItem(value: String, label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}

@Composable
fun ChildCard(
    child: ChildInfo,
    onViewClick: () -> Unit
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
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(child.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = child.initial,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = child.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Text(
                    text = child.age,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                if (child.status == "due" && child.nextDue != null) {
                    Text(
                        text = child.nextDue,
                        fontSize = 14.sp,
                        color = Color(0xFFF57C00)
                    )
                } else {
                    Text(
                        text = "Up to date ✓",
                        fontSize = 14.sp,
                        color = Color(0xFF4caf50)
                    )
                }
            }

            // View Button
            OutlinedButton(
                onClick = onViewClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF667eea)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF667eea))
                )
            ) {
                Text("VIEW")
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = Color(0xFF667eea)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF212121)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParentDashboardPreview() {
    ParentDashboard(
        onNavigateToTimeline = {},
        onNavigateToNotifications = {},
        onNavigateToRegisterChild = {}
    )
}