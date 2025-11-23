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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterChildForm(
    onNavigateBack: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("male") }
    var guardianName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Register Child",
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
                    Text(
                        text = "Step 1 of 2",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
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
                    onClick = {
                        // TODO: Validate and continue
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF667eea)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("CONTINUE")
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
                .padding(16.dp)
        ) {
            // Child Information Section
            Text(
                text = "Child Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile Photo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Add Photo",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* TODO: Open camera */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF667eea)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Camera")
                    }
                    OutlinedButton(
                        onClick = { /* TODO: Open gallery */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF667eea)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Upload,
                            contentDescription = "Gallery",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Gallery")
                    }
                }
            }

            // First Name
            FormField(
                label = "First Name",
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = "Enter first name",
                isRequired = true
            )

            // Last Name
            FormField(
                label = "Last Name",
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = "Enter last name",
                isRequired = true
            )

            // Date of Birth
            FormField(
                label = "Date of Birth",
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                placeholder = "Select date of birth",
                isRequired = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Select Date",
                        tint = Color(0xFF757575)
                    )
                }
            )

            // Gender
            Text(
                text = "Gender *",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = gender == "male",
                        onClick = { gender = "male" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF667eea)
                        )
                    )
                    Text("Male", modifier = Modifier.padding(start = 8.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = gender == "female",
                        onClick = { gender = "female" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF667eea)
                        )
                    )
                    Text("Female", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Guardian Information Section
            Text(
                text = "Guardian Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Guardian Name
            FormField(
                label = "Guardian Name",
                value = guardianName,
                onValueChange = { guardianName = it },
                placeholder = "Enter guardian name",
                isRequired = true
            )

            // Phone Number
            Text(
                text = "Phone Number *",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .width(64.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF5F5F5),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(0xFFE0E0E0)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+250",
                            fontSize = 14.sp,
                            color = Color(0xFF212121)
                        )
                    }
                }
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text("788 123 456") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF667eea),
                        unfocusedBorderColor = Color(0xFFE0E0E0)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // Email
            FormField(
                label = "Email (Optional)",
                value = email,
                onValueChange = { email = it },
                placeholder = "guardian@example.com",
                isRequired = false
            )

            // Address
            Text(
                text = "Address *",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                placeholder = { Text("Enter home address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF667eea),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isRequired: Boolean,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
            if (isRequired) {
                Text(
                    text = " *",
                    fontSize = 14.sp,
                    color = Color(0xFFF44336)
                )
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF667eea),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterChildFormPreview() {
    RegisterChildForm(
        onNavigateBack = {}
    )
}