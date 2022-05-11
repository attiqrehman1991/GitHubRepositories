package com.example.github.repositories.home.data_model.remote

import com.google.gson.annotations.SerializedName

data class RepositoryRemoteModel(
    val incomplete_results: Boolean?,
    val items: List<Item>?,
    val total_count: Int?
)

data class Item(
    val description: String,
    val full_name: String,
    val owner: Owner,
    val created_at: String,
    val html_url: String,
    val id: Int
) {
    val disabled: Boolean? = null
    val downloads_url: String? = null
    val events_url: String? = null
    val fork: Boolean? = null
    val forks: Int? = null
    val forks_count: Int? = null
    val forks_url: String? = null
    val git_commits_url: String? = null
    val git_refs_url: String? = null
    val git_tags_url: String? = null
    val git_url: String? = null
    val has_downloads: Boolean? = null
    val has_issues: Boolean? = null
    val has_pages: Boolean? = null
    val has_projects: Boolean? = null
    val has_wiki: Boolean? = null
    val homepage: String? = null
    val hooks_url: String? = null
    val is_template: Boolean? = null
    val issue_comment_url: String? = null
    val issue_events_url: String? = null
    val issues_url: String? = null
    val keys_url: String? = null
    val labels_url: String? = null
    val language: String? = null
    val languages_url: String? = null
    val license: License? = null
    val merges_url: String? = null
    val milestones_url: String? = null
    val mirror_url: Any? = null
    val name: String? = null
    val node_id: String? = null
    val notifications_url: String? = null
    val open_issues: Int? = null
    val open_issues_count: Int? = null
    val allow_forking: Boolean? = null
    val archive_url: String? = null
    val archived: Boolean? = null
    val assignees_url: String? = null
    val blobs_url: String? = null
    val branches_url: String? = null
    val clone_url: String? = null
    val collaborators_url: String? = null
    val comments_url: String? = null
    val commits_url: String? = null
    val compare_url: String? = null
    val contents_url: String? = null
    val contributors_url: String? = null
    val default_branch: String? = null
    val deployments_url: String? = null

    @SerializedName("private")
    val isPrivate: Boolean? = null
    val pulls_url: String? = null
    val pushed_at: String? = null
    val releases_url: String? = null
    val score: Double? = null
    val size: Int? = null
    val ssh_url: String? = null
    val stargazers_count: Int? = null
    val stargazers_url: String? = null
    val statuses_url: String? = null
    val subscribers_url: String? = null
    val subscription_url: String? = null
    val svn_url: String? = null
    val tags_url: String? = null
    val teams_url: String? = null
    val topics: List<String>? = null
    val trees_url: String? = null
    val updated_at: String? = null
    val url: String? = null
    val visibility: String? = null
    val watchers: Int? = null
    val watchers_count: Int? = null
}

data class License(
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
    val url: String
)

data class Owner(
    val login: String,
    val avatar_url: String?=null
) {
    val events_url: String? = null
    val followers_url: String? = null
    val following_url: String? = null
    val gists_url: String? = null
    val gravatar_id: String? = null
    val html_url: String? = null
    val id: Int? = null
    val node_id: String? = null
    val organizations_url: String? = null
    val received_events_url: String? = null
    val repos_url: String? = null
    val site_admin: Boolean? = null
    val starred_url: String? = null
    val subscriptions_url: String? = null
    val type: String? = null
    val url: String? = null
}