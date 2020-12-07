package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Match
import org.springframework.data.repository.CrudRepository

interface MatchRepository : CrudRepository<Match, Long>
