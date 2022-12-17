package com.strayalpaca.android.abox.ui.screens.oxquiz_category

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.components.BackButton
import com.strayalpaca.android.abox.ui.components.PostList
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OxQuizCategoryActivity : ComponentActivity() {

    private val viewModel : OxQuizCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ABOXTheme {
                val posts = viewModel.categoryList.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    BackButton()
                    Text(
                        text = stringResource(id = R.string.OXQuiz_category),
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onBackground
                    )
                    PostList(posts = posts.value, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}