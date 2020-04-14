package com.example.predictor.entity

import javax.persistence.*

@Entity
@Table(name = "event")
class Event(

        @Column(name = "team1")
        val team1: String,

        @Column(name = "team2")
        val team2: String,

        @Column(name = "scoreTeam1", nullable = true)
        var scoreTeam1: Double? = null,

        @Column(name = "scoreTeam2", nullable = true)
        var scoreTeam2: Double? = null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "tournament_id")
        val tournament: Tournament

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