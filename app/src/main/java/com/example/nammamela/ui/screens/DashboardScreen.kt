package com.example.nammamela.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nammamela.data.model.Play
import com.example.nammamela.ui.theme.CreamWhite
import com.example.nammamela.ui.theme.PeachDark
import com.example.nammamela.ui.theme.PeachPrimary
import com.example.nammamela.ui.theme.TextDark
import com.example.nammamela.ui.theme.TextLight
import com.example.nammamela.ui.viewmodel.PlayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: PlayViewModel,
    onNavigateToCast: () -> Unit,
    onNavigateToSeats: () -> Unit,
    onNavigateToFanWall: () -> Unit,
    onNavigateToEventDetail: (Int) -> Unit = {},
    onNavigateToAIHelper: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val allPlays by viewModel.allPlays.collectAsState()
    var showLogoutMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Namma-Mela 🎭",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = PeachDark
                        )
                        Text(
                            "Your Cultural Events Companion",
                            fontSize = 12.sp,
                            color = TextLight
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showLogoutMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = PeachDark,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = showLogoutMenu,
                        onDismissRequest = { showLogoutMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Logout", color = TextDark) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.ExitToApp,
                                    contentDescription = null,
                                    tint = PeachDark
                                )
                            },
                            onClick = {
                                showLogoutMenu = false
                                onLogout()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamWhite)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAIHelper,
                containerColor = PeachPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("🤖", fontSize = 24.sp)
            }
        },
        containerColor = CreamWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(PeachPrimary, PeachDark)
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text("Upcoming Events", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Tap an event to explore details", fontSize = 12.sp, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }

            if (allPlays.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PeachPrimary)
                    }
                }
            } else {
                items(allPlays) { play ->
                    EventCard(play = play, onClick = { onNavigateToEventDetail(play.id) })
                }
            }

            item { Spacer(modifier = Modifier.height(72.dp)) }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCard(play: Play, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                GlideImage(
                    model = play.posterUrl,
                    contentDescription = play.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                startY = 80f
                            )
                        )
                )
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = PeachPrimary.copy(alpha = 0.92f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(play.duration, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
                Text(
                    text = play.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
                )
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = PeachPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${play.date}  •  ${play.time}", fontSize = 12.sp, color = TextLight, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = PeachDark, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(play.venue, fontSize = 12.sp, color = TextLight, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = play.description,
                    fontSize = 13.sp,
                    color = TextDark.copy(alpha = 0.72f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PeachPrimary, contentColor = Color.White)
                ) {
                    Text("View Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
