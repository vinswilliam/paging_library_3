package com.example.testcermati.user.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_github")
data class UserGithub(
        @PrimaryKey
        @field:SerializedName("id")
        val id: Long,
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("node_id")
        var nodeId: String? = null,
        @field:SerializedName("avatar_url")
        var avatarUrl: String? = null,
        @field:SerializedName("gravatar_id")
        var gravatarId: String? = null,
        @field:SerializedName("url")
        var url: String? = null,
        @field:SerializedName("html_url")
        var htmlUrl: String? = null,
        @field:SerializedName("followers_url")
        var followersUrl: String? = null,
        @field:SerializedName("subscriptions_url")
        var subscriptionsUrl: String? = null,
        @field:SerializedName("organizations_url")
        var organizationsUrl: String? = null,
        @field:SerializedName("repos_url")
        var reposUrl: String? = null,
        @field:SerializedName("received_events_url")
        var receivedEventsUrl: String? = null,
        @field:SerializedName("type")
        var type: String? = null,
        @field:SerializedName("score")
        var score: Double? = null
) {
    override fun toString(): String {
        return login
    }
}