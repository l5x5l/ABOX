package com.strayalpaca.android.abox.ui.screens.avsb_list

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.components.BackButton
import com.strayalpaca.android.abox.ui.components.PostPagingList
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvsBListActivity : ComponentActivity() {
    private val viewModel : AvsBListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val posts = viewModel.postPager.collectAsLazyPagingItems()

            ABOXTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    BackButton()
                    Text(
                        text = stringResource(id = R.string.AvsB_category),
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onBackground
                    )
                    PostPagingList(posts = posts, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}