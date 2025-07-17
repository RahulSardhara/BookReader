package com.rahul.bookreader.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.rahul.bookreader.componets.ReaderAppBar
import com.rahul.bookreader.componets.SearchForm
import com.rahul.bookreader.model.Item
import com.rahul.bookreader.navigation.ReaderScreens

@Preview(showBackground = true)
@Composable
fun ReaderSearchScreen(navController: NavController = rememberNavController(), viewModel: BookSearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReaderAppBar("Search", icon = Icons.Default.ArrowBack, showProfile = false, navController = navController) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column() {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                ) {
                    viewModel.getAllBooks(it)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController, viewModel)
            }

        }

    }

}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {

    if (viewModel.listOfBooks.value.loading == true) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        viewModel.listOfBooks.value.data?.let {
            if (it.isNotEmpty()) {
                items(items = it) { book ->
                    BookRow(book, navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookRow(book: Item = Item(), navController: NavController = rememberNavController()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(3.dp).clickable{
                navController.navigate(ReaderScreens.DetailsScreen.name + "/${book.id}")
            },
        shape = RectangleShape, elevation = CardDefaults.elevatedCardElevation(7.dp),
    ) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top)
        {
            Image(
                painter = rememberAsyncImagePainter(model = book.volumeInfo?.imageLinks?.thumbnail), contentDescription = "", modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
            )
            Column {
                Text(text = book.volumeInfo?.title ?: "N/A", modifier = Modifier.padding(start = 5.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "Author : ${book.volumeInfo?.authors?.joinToString(", ") ?: "N/A"}", modifier = Modifier.padding(start = 5.dp), maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = "Categories : ${book.volumeInfo?.categories?.joinToString(", ") ?: "N/A"}", modifier = Modifier.padding(start = 5.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "Published Date : ${book.volumeInfo?.publishedDate ?: "NA"}", modifier = Modifier.padding(start = 5.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
0

}

