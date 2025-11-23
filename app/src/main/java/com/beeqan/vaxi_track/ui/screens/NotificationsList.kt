package com.beeqan.vaxi_track.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

data class NotificationItem(
    val id: Int,
    val type: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val icon: ImageVector,
    val iconBg: Color,
    val iconColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsList(
    onNavigateBack: () -> Unit
) {
    val notifications = listOf(
        NotificationItem(
            id = 1,
            type = "reminder",
            title = "Vaccination Reminder",
            message = "Emma's Polio dose 2 is due in 4 days",
            time = "2 hours ago",
            isRead = false,
            icon = Icons.Default.Schedule,
            iconBg = Color(0xFFFFE0B2),
            iconColor = Color(0xFFFF9800)
        ),
        NotificationItem(
            id = 2,
            type = "success",
            title = "Vaccination Recorded",
            message = "BCG vaccine successfully recorded for Emma",
            time = "1 day ago",
            isRead = true,
            icon = Icons.Default.CheckCircle,
            iconBg = Color(0xFFC8E6C9),
            iconColor = Color(0xFF4CAF50)
        ),
        NotificationItem(
            id = 3,
            type = "appointment",
            title = "Appointment Scheduled",
            message = "Appointment confirmed for Nov 20, 2025",
            time = "3 days ago",
            isRead = true,
            icon = Icons.Default.CalendarToday,
            iconBg = Color(0xFFBBDEFB),
            iconColor = Color(0xFF2196F3)
        ),
        NotificationItem(
            id = 4,
            type = "reminder",
            title = "Vaccination Due",
            message = "Noah's annual checkup is coming up next month",
            time = "5 days ago",
            isRead = true,
            icon = Icons.Default.Schedule,
            iconBg = Color(0xFFFFE0B2),
            iconColor = Color(0xFFFF9800)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification = notification)
                Divider(color = Color(0xFFE0E0E0))
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Handle notification click */ },
        color = if (!notification.isRead) Color(0xFFE3F2FD) else Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(notification.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.icon,
                    contentDescription = notification.type,
                    tint = notification.iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    fontSize = 16.sp,
                    fontWeight = if (!notification.isRead) FontWeight.SemiBold else FontWeight.Normal,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            // Unread indicator
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF667eea))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsListPreview() {
    NotificationsList(
        onNavigateBack = {}
    )
}