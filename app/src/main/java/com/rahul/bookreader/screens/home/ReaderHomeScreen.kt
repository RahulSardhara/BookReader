package com.rahul.bookreader.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rahul.bookreader.componets.FABContent
import com.rahul.bookreader.componets.ListCard
import com.rahul.bookreader.componets.ReaderAppBar
import com.rahul.bookreader.componets.TitleSection
import com.rahul.bookreader.model.MBook
import com.rahul.bookreader.navigation.ReaderScreens

@Composable
fun ReaderHomeScreen(navController: NavController = rememberNavController(), viewModel: HomeScreenViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        ReaderAppBar(title = "Book Reader", navController = navController)
    }, floatingActionButton = {
        FABContent {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HomeContent(navController = navController, viewModel)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeContent(navController: NavController = rememberNavController(), viewModel: HomeScreenViewModel = hiltViewModel()) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    listOfBooks = if (viewModel.data.value.data.isNullOrEmpty()) {
        viewModel.data.value.data?.toList()?.filter { mBook ->
            mBook.userId == currentUser?.uid
        } ?: emptyList()
    } else {
        viewModel.data.value.data ?: emptyList()
    }

    val currentUserNamer = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0) ?: "Guest"
    Column(modifier = Modifier.padding(0.dp), verticalArrangement = Arrangement.Top) {

        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your Reading \n" + "Right Now...", modifier = Modifier.padding(start = 5.dp))
            Spacer(modifier = Modifier.fillMaxWidth(0.8f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(45.dp), tint = Color.Red.copy(0.7f))
                Text(
                    text = currentUserNamer, modifier = Modifier.padding(start = 5.dp), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Red.copy(0.7f), overflow = TextOverflow.Clip
                )
                HorizontalDivider()
            }
        }
        ListCard(listOfBooks.getOrNull(0))
        ReadingRightNowArea(boolList = listOf(), navController = navController)
        TitleSection(label = "Reading List", modifier = Modifier.padding(start = 5.dp))
        BookListArea(listOfBooks = listOfBooks, navController)

    }


}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    val addedBook =listOfBooks

    HorizontalScrollableComponent(listOfBooks){
        navController.navigate(ReaderScreens.UpdateScreen.name + "/${it.googleBookId}")

    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,onCardPressed: (MBook) -> Unit = {}) {
    val scrollSate = rememberScrollState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)
        .horizontalScroll(scrollSate)) {
        for (book in listOfBooks) {
            ListCard(book = book){
            onCardPressed.invoke(book)
            }
        }
    }
}


@Composable
fun ReadingRightNowArea(boolList: List<MBook>, navController: NavController) {


}
