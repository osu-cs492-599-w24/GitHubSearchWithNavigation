package edu.oregonstate.cs492.githubsearchwithnavigation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import edu.oregonstate.cs492.githubsearchwithnavigation.R
import edu.oregonstate.cs492.githubsearchwithnavigation.util.LoadingStatus

class MainActivity : AppCompatActivity() {
    private val viewModel: GitHubSearchViewModel by viewModels()
    private val adapter = GitHubRepoListAdapter()

    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBoxET: EditText = findViewById(R.id.et_search_box)
        val searchBtn: Button = findViewById(R.id.btn_search)

        searchErrorTV = findViewById(R.id.tv_search_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        searchResultsListRV = findViewById(R.id.rv_search_results)
        searchResultsListRV.layoutManager = LinearLayoutManager(this)
        searchResultsListRV.setHasFixedSize(true)

        searchResultsListRV.adapter = adapter

        viewModel.searchResults.observe(this) {
            searchResults -> adapter.updateRepoList(searchResults)
        }

        viewModel.loadingStatus.observe(this) {
            loadingStatus ->
                when (loadingStatus) {
                    LoadingStatus.LOADING -> {
                        searchResultsListRV.visibility = View.INVISIBLE
                        loadingIndicator.visibility = View.VISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                    LoadingStatus.ERROR -> {
                        searchResultsListRV.visibility = View.INVISIBLE
                        loadingIndicator.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.VISIBLE
                    }
                    else -> {
                        searchResultsListRV.visibility = View.VISIBLE
                        loadingIndicator.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                }
        }

        viewModel.error.observe(this) {
            error -> searchErrorTV.text = getString(
            R.string.search_error,
                error
            )
        }

        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                viewModel.loadSearchResults(query)
                searchResultsListRV.scrollToPosition(0)
            }
        }
    }
}