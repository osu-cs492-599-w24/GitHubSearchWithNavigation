package edu.oregonstate.cs492.githubsearchwithnavigation.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.oregonstate.cs492.githubsearchwithnavigation.R

class GitHubRepoDetailFragment : Fragment(R.layout.fragment_github_repo_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoNameTV: TextView = view.findViewById(R.id.tv_repo_name)
        val repoStarsTV: TextView = view.findViewById(R.id.tv_repo_stars)
        val repoDescriptionTV: TextView = view.findViewById(R.id.tv_repo_description)

        repoNameTV.text = "Dummy repo name"
        repoStarsTV.text = "8192"
        repoDescriptionTV.text = "This is the description of this repo."
    }
}