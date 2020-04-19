package com.example.predictor.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "event")
data class Event(

        @Column(name = "team1")
        var team1: String,

        @Column(name = "team2")
        var team2: String,

        @Column(name = "scoreTeam1", nullable = true)
        var scoreTeam1: Int? = null,

        @Column(name = "scoreTeam2", nullable = true)
        var scoreTeam2: Int? = null,

        @Column(name = "date")
        var date: Date,

        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "tournament_id")
        var tournament: Tournament

) : BaseEntity() {

    fun getResult(): String{
        return if (scoreTeam1==scoreTeam2){
            "Draw"
        } else{
            if (scoreTeam1!! > scoreTeam2!!){
                team1
            } else{
                team2
            }
        }
    }

}