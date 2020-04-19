new Vue({
    el: '#tournament',
    data: {
        currentPage: 1,
        tournaments: [],
        categories: [],
        totalPages: 0,
        form: {
            id: '',
            name: '',
            categoryId: ''
        },
        editMode: false
    },
    methods: {
        fetchCategories: function(){
            this.$http.get('http://localhost:8004/api/admin/categories').then(
                function (response) {
                    this.categories = response.body;
                    console.log(response.body);
                }
            )
        },
        fetchTournaments: function(page){
            let options = {
                params:{
                    page: page-1
                }
            };
            this.$http.get('http://localhost:8004/api/admin/tournament', options).then(
                function (response) {
                    this.tournaments = response.data.content;
                    this.totalPages = parseInt(response.body.totalPages);
                    this.currentPage = page;
                    console.log(response.body.totalPages)
                }, console.log
            )
        },
        addModal: function(){
            this.editMode = false;
            $('#addModal').modal('show');
            this.fetchCategories();
            this.form.id = '';
            this.form.name = '';
            this.form.categoryId = '';
        },
        editModal: function (tournament) {
            this.editMode = true;
            $('#addModal').modal('show');
            this.fetchCategories();
            this.form.id = tournament.id;
            this.form.name = tournament.name;
            this.form.categoryId = tournament.category.id;
        },
        deleteModal: function(tournament){
            $('#deleteModal').modal('show');
            this.form.id = tournament.id;
        },
        updateTournament: function () {
            this.$http.put('http://localhost:8004/api/admin/tournament/update', {
                id: this.form.id,
                name: this.form.name,
                categoryId: this.form.categoryId
            }).then(
                function (response) {
                    console.log(response);
                    $('#addModal').modal('hide');
                    this.fetchTournaments(this.currentPage)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        },
        addTournament: function () {
            this.$http.post('http://localhost:8004/api/admin/tournament/add', {
                name: this.form.name,
                categoryId: this.form.categoryId
            }).then(
                function (response) {
                    console.log(response);
                    $('#addModal').modal('hide');
                    this.fetchTournaments(this.currentPage)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        },
        deleteTournament: function () {
            this.$http.put('http://localhost:8004/api/admin/tournament/delete', {
                id: this.form.id
            }).then(
                function (response) {
                    console.log(response);
                    $('#deleteModal').modal('hide');
                    this.fetchTournaments(this.currentPage)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        }
    },
    created: function () {
        this.fetchTournaments(this.currentPage);
    }
});