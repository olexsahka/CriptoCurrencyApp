package com.plcoding.cryptocurrencyappyt.data.remote.dto

import com.plcoding.cryptocurrencyappyt.domain.model.TeamMember

data class TeamMemberDto(
    val id: String,
    val name: String,
    val position: String
)

fun TeamMemberDto.toTeamMember() = TeamMember(id = id, name = name, position = position)