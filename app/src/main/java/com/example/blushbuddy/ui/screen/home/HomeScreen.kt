package com.example.blushbuddy.ui.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import com.example.blushbuddy.ui.theme.BlackMemberColor
import com.example.blushbuddy.ui.theme.BlushPink
import com.example.blushbuddy.ui.theme.BronzeColor
import com.example.blushbuddy.ui.theme.GoldColor
import com.example.blushbuddy.ui.theme.RoseGold
import com.example.blushbuddy.ui.theme.RoseGoldDark
import com.example.blushbuddy.ui.theme.SilverColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    memberId: Int,
    repository: BlushBuddyRepository,
    onTransactionsClick: () -> Unit,
    onRedeemClick: () -> Unit,
    onLogout: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(
        factory = remember { object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository, memberId) as T
            }
        }}
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BlushBuddy", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RoseGold,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = RoseGold)
            }
            return@Scaffold
        }

        val member = uiState.member ?: return@Scaffold
        val status = viewModel.getMemberStatus(member.totalPoints)
        val statusColor = when {
            member.totalPoints >= 500000 -> BlackMemberColor
            member.totalPoints >= 1000 -> GoldColor
            member.totalPoints >= 500 -> SilverColor
            else -> BronzeColor
        }
        val cardGradient = when {
            member.totalPoints >= 500000 -> listOf(
                Color(0xFF0D0D0D), Color(0xFF1F1F1F), Color(0xFF3A3A3A)
            )
            member.totalPoints >= 1000 -> listOf(
                Color(0xFF7A5200), Color(0xFFBF8C00), Color(0xFFFFD700)
            )
            member.totalPoints >= 500 -> listOf(
                Color(0xFF4A4A4A), Color(0xFF7E7E7E), Color(0xFFC0C0C0)
            )
            else -> listOf(RoseGold, RoseGoldDark, BlushPink)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Membership Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(colors = cardGradient)
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "BlushBuddy",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(statusColor.copy(alpha = 0.3f))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = status,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = member.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            text = member.memberNumber,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${member.totalPoints}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "POIN",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // QR Code Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kartu Member Digital",
                        fontWeight = FontWeight.SemiBold,
                        color = RoseGold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val qrModules = remember(member.memberNumber) {
                        generateQrModules(member.memberNumber)
                    }
                    QrCodeCanvas(modules = qrModules, size = 180.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = member.memberNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ElevatedButton(
                    onClick = onTransactionsClick,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Transaksi")
                }
                ElevatedButton(
                    onClick = onRedeemClick,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CardGiftcard, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Redeem")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(RoseGold.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = RoseGold,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Cara Mendapatkan Poin",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = RoseGoldDark
                            )
                            Text(
                                text = "Rp10.000 pembelian = 1 Poin",
                                style = MaterialTheme.typography.bodySmall,
                                color = RoseGoldDark.copy(alpha = 0.65f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = BlushPink.copy(alpha = 0.6f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "LEVEL KEANGGOTAAN",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = RoseGoldDark.copy(alpha = 0.55f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TierBadge("Bronze", "0–499", BronzeColor, Modifier.weight(1f))
                        TierBadge("Silver", "500–999", SilverColor, Modifier.weight(1f))
                        TierBadge("Gold", "1K–499K", GoldColor, Modifier.weight(1f))
                        TierBadge("Black", "500K+", BlackMemberColor, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun TierBadge(name: String, range: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = "$range pt",
            fontSize = 10.sp,
            color = color.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun QrCodeCanvas(modules: Array<BooleanArray>, size: Dp) {
    Canvas(modifier = Modifier.size(size).background(Color.White)) {
        val n = modules.size
        val moduleSize = this.size.width / n
        for (row in 0 until n) {
            for (col in 0 until n) {
                if (modules[row][col]) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(col * moduleSize, row * moduleSize),
                        size = Size(moduleSize, moduleSize)
                    )
                }
            }
        }
    }
}

private fun generateQrModules(content: String): Array<BooleanArray> {
    val n = 21
    val m = Array(n) { BooleanArray(n) }

    fun finderPattern(startRow: Int, startCol: Int) {
        for (r in 0..6) for (c in 0..6) {
            m[startRow + r][startCol + c] =
                r == 0 || r == 6 || c == 0 || c == 6 || (r in 2..4 && c in 2..4)
        }
    }
    finderPattern(0, 0)
    finderPattern(0, 14)
    finderPattern(14, 0)

    // Timing patterns
    for (i in 8..12) {
        m[6][i] = i % 2 == 0
        m[i][6] = i % 2 == 0
    }

    // Data modules seeded by content hash
    var seed = content.fold(5381L) { acc, c -> acc * 33 + c.code }
    for (r in 0 until n) {
        for (c in 0 until n) {
            if ((r <= 7 && c <= 7) || (r <= 7 && c >= 13) || (r >= 13 && c <= 7)) continue
            if (r == 6 || c == 6) continue
            seed = seed * 6364136223846793005L + 1442695040888963407L
            m[r][c] = (seed ushr 33) % 2L == 0L
        }
    }
    return m
}
