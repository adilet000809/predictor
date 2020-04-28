new Vue({
    el: '#app',
    data: {
        currentPage: 1,
        totalPages: 0,
        categories: [],
        tournaments: [],
        events: [],
        form: {
            id: '',
            name: '',
            categoryId: '',
            tournamentId: ''
        },
        tournament: false,
        event: false
    },
    methods: {
        fetchCategories: function(){
            this.$http.get('http://localhost:8004/api/user/categories').then(
                function (response) {
                    this.categories = response.body;
                    console.log(this.categories);
                }, console.log
            )
        },
        fetchTournaments: function(){
            let options = {
                params:{
                    categoryId: this.form.categoryId
                }
            };
            this.$http.get('http://localhost:8004/api/user/tournaments', options).then(
                function (response) {
                    this.tournaments = response.body;
                    console.log(this.tournaments);
                    this.tournament = true;
                }, console.log
            )
        },
        fetchEvents: function(page){
            let options = {
                params:{
                    page: page-1,
                    tournamentId: this.form.tournamentId
                }
            };
            this.$http.get('http://localhost:8004/api/user/event', options).then(
                function (response) {
                    this.events = response.body;
                    this.events.forEach(formatDate);
                    function formatDate(item, index) {
                        item.date = moment(new Date(item.date)).format("YYYY/MM/DD HH:mm:ss");
                    }
                    console.log(this.events);
                    this.event = true;
                }, console.log
            )
        },
        changeCategory: function(){
            console.log(this.form.categoryId);
            this.fetchTournaments();
        },
        changeTournament: function(){
            console.log(this.form.tournamentId);
            this.fetchEvents();
        },
    },
    created: function () {
        this.fetchCategories()
    }
});