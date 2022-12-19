package com.strayalpaca.android.abox.ui.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.model.const.INTENT_KEY_POST_INDEX
import com.strayalpaca.android.abox.ui.components.HomeCategoryList
import com.strayalpaca.android.abox.ui.components.PostItem
import com.strayalpaca.android.abox.ui.screens.avsb.AvsBActivity
import com.strayalpaca.android.abox.ui.screens.oxquiz.OxQuizActivity
import com.strayalpaca.android.domain.model.PostType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomPost.collect { randomPost ->
                    val intent = when(randomPost.postType) {
                        PostType.AB -> {
                            Intent(this@HomeActivity, AvsBActivity::class.java)
                        }
                        PostType.OX -> {
                            Intent(this@HomeActivity, OxQuizActivity::class.java)
                        }
                    }
                    intent.putExtra(INTENT_KEY_POST_INDEX, randomPost.index)
                    startActivity(intent)
                }
            }
        }

        setContent {
            val scrollState = rememberScrollState()
            val popularPostList = viewModel.popularPost.collectAsState()
            val recentPostList = viewModel.recentPost.collectAsState()
            ABOXTheme {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.abox_logo),
                        contentDescription = "app logo",
                        modifier = Modifier
                            .width(46.dp)
                            .height(60.dp)
                            .offset(x = 20.dp, y = 20.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                    )

                    HomeCategoryList(viewModel::getRandomPost)

                    Text(
                        text = stringResource(id = R.string.weekly_top_10),
                        modifier = Modifier.padding(start = 20.dp),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .height(250.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(popularPostList.value) { post ->
                            PostItem(post = post)
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.new_AvsB),
                        modifier = Modifier.padding(top = 68.dp, start = 20.dp),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .fillMaxWidth()
                            .height(250.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(recentPostList.value) { post ->
                            PostItem(post = post)
                        }
                    }
                }
            }

            viewModel.getHomeData()
        }
    }
}