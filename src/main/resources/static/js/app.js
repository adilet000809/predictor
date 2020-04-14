new Vue({
    el: '#app',
    data: {
        currentPage: 1,
        categories: [],
        totalPages: 0,
        form: {
            name: ''
        },
        editMode: false
    },
    methods: {
        fetchCategories: function(page){
            let options = {
                params:{
                    page: page-1
                }
            };
            this.$http.get('http://localhost:8004/api/admin/category', options).then(
                function (response) {
                    this.categories = response.data.content;
                    this.totalPages = parseInt(response.body.totalPages);
                    this.currentPage = page;
                    console.log(response.body.totalPages)
                }, console.log
            )
        },
        addModal: function(){
            this.editMode = false;
            $('#addModal').modal('show');
            this.form.name = '';
        },
        editModal: function (category) {
            this.editMode = true;
            $('#addModal').modal('show');
            this.form.name = category.name;
        },
        updateCategory: function () {

        },
        addCategory: function () {
            let category = {
                name: this.form.name
            };
            this.$http.post('http://localhost:8004/api/admin/category/add', {
                name: this.form.name
            }).then(
                function (response) {
                    console.log(response)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        }
    },
    created: function () {
        this.fetchCategories(this.currentPage)
    }
});