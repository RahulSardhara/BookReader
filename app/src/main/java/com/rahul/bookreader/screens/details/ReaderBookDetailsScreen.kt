package com.rahul.bookreader.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rahul.bookreader.componets.ReaderAppBar
import com.rahul.bookreader.componets.RoundedButtons
import com.rahul.bookreader.model.Item
import com.rahul.bookreader.model.MBook

@Composable
fun ReaderBookDetailsScreen(navController: NavController = rememberNavController(), bookItemId: String = "", viewModel: DetailsViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        ReaderAppBar(title = "Book Details", icon = Icons.Default.ArrowBack, showProfile = false, navController = navController) {
            navController.popBackStack()
        }
    }) {
        var bookInfo by remember { mutableStateOf<Item?>(null) }
        LaunchedEffect(key1 = bookItemId) {
            viewModel.getBookInfo(bookItemId) {
                bookInfo = it
            }
        }
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(top = 1.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                bookInfo?.let {
                    ShowBookDetails(it, navController)
                }

            }

        }
    }


}

@Composable
fun ShowBookDetails(bookInfo: Item?, navController: NavController) {
    bookInfo?.let {
        val bookData = bookInfo.volumeInfo
        val googleBookId = bookInfo.id

        Card(modifier = Modifier.padding(34.dp), shape = CircleShape, elevation = CardDefaults.elevatedCardElevation(4.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = bookData?.imageLinks?.thumbnail), contentDescription = "book image",
                modifier = Modifier
                    .width(90.dp)
                    .heightIn(90.dp)
                    .padding(1.dp)
            )
        }

        Text(text = bookData?.title.toString(), style = MaterialTheme.typography.titleMedium, maxLines = 19, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp))
        Text(text = "Authors :${bookData?.authors?.joinToString(", ")}", style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp))
        Text(text = "Page Count : ${bookData?.pageCount}", style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp))
        Text(text = "Categories: ${bookData?.categories?.joinToString(", ")}", style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp))
        Text(text = "Published Date : ${bookData?.publishedDate ?: "NA"}", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(start = 5.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(5.dp))

        val cleanDescription = HtmlCompat.fromHtml(bookData?.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        val localeDims = LocalContext.current.resources.displayMetrics
        Surface(
            modifier = Modifier
                .height(localeDims.heightPixels.dp.times(0.09f))
                .padding(4.dp)
                .padding(start = 16.dp, end = 16.dp), shape = RectangleShape,
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            LazyColumn(modifier = Modifier.padding(3.dp)) {
                item {
                    Text(text = cleanDescription)
                }
            }
        }

        Row(modifier = Modifier.padding(top = 6.dp), horizontalArrangement = Arrangement.SpaceEvenly) {

            RoundedButtons(label = "Save", radius = 10) {
                val mBook = MBook(
                    title = bookData?.title ?: "",
                    authors = bookData?.authors?.joinToString(", ") ?: "N/A",
                    notes = "",
                    photoUrl = bookData?.imageLinks?.thumbnail ?: "",
                    categories = bookData?.categories?.joinToString(", "),
                    publishedDate = bookData?.publishedDate,
                    rating = null,
                    description = cleanDescription,
                    pageCount = bookData?.pageCount.toString(),
                    startedReading = null,
                    finishedReading = null,
                    userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                    googleBookId = googleBookId ?: ""
                )
                saveToFirebase(mBook, navController)
            }
            Spacer(modifier = Modifier.width(25.dp))
            RoundedButtons("Cancel", 10) {
                navController.popBackStack()
            }
        }


    }


}

fun saveToFirebase(mBook: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    db.collection("books")
        .add(mBook)
        .addOnSuccessListener { documentReference ->
            val docId = documentReference.id
            db.collection("books").document(docId)
                .update(hashMapOf("id" to docId) as Map<String, Any>)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.popBackStack()
                    }
                }
                .addOnFailureListener { e ->
                    navController.popBackStack()
                    // Handle failure, e.g., show an error message
                }

        }
}
