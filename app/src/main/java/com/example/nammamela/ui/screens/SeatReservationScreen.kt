package com.example.nammamela.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammamela.ui.theme.CreamWhite
import com.example.nammamela.ui.theme.SeatAvailableGreen
import com.example.nammamela.ui.theme.SeatReservedRed
import com.example.nammamela.data.model.Seat
import com.example.nammamela.ui.viewmodel.SeatMapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatReservationScreen(
    playId: Int,
    viewModel: SeatMapViewModel,
    onBackClick: () -> Unit = {}
) {
    val seats by viewModel.getSeatsForPlay(playId).collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedSeat by remember { mutableStateOf<Seat?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reserve Seats", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CreamWhite,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = CreamWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Stage indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                    .background(Color.LightGray.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text("STAGE", fontWeight = FontWeight.Bold, color = Color.DarkGray, letterSpacing = 2.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendItem(color = SeatAvailableGreen, text = "Available")
                LegendItem(color = SeatReservedRed, text = "Reserved")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (seats.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(seats) { seat ->
                        SeatItem(
                            seat = seat,
                            onClick = {
                                if (!seat.reserved) {
                                    selectedSeat = seat
                                    showDialog = true
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Seat ${seat.seatNumber} is already reserved.")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showDialog && selectedSeat != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Reservation") },
                text = { Text("Reserve seat ${selectedSeat?.seatNumber}?") },
                confirmButton = {
                    TextButton(onClick = {
                        selectedSeat?.let { seat ->
                            viewModel.reserveSeat(seat)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Seat ${seat.seatNumber} reserved!")
                            }
                        }
                        showDialog = false
                    }) { Text("Confirm") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun SeatItem(seat: Seat, onClick: () -> Unit) {
    val backgroundColor = if (seat.reserved) SeatReservedRed else SeatAvailableGreen
    val contentColor = if (seat.reserved) Color.White else Color.Black

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = seat.seatNumber, color = contentColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}
