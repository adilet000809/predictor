new Vue({
    el: '#category',
    data: {
        currentPage: 1,
        categories: [],
        totalPages: 0,
        form: {
            id: '',
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
                }, console.log
            )
        },
        addModal: function(){
            this.editMode = false;
            $('#addModal').modal('show');
            this.form.id = '';
            this.form.name = '';
        },
        editModal: function (category) {
            this.editMode = true;
            $('#addModal').modal('show');
            this.form.id = category.id;
            this.form.name = category.name;
        },
        deleteModal: function(category){
            $('#deleteModal').modal('show');
            this.form.id = category.id;
        },
        updateCategory: function () {
            this.$http.put('http://localhost:8004/api/admin/category/update', {
                id: this.form.id,
                name: this.form.name,
            }).then(
                function (response) {
                    $('#addModal').modal('hide');
                    this.fetchCategories(this.currentPage)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        },
        addCategory: function () {
            this.$http.post('http://localhost:8004/api/admin/category/add', {
                name: this.form.name
            }).then(
                function (response) {
                    $('#addModal').modal('hide');
                    this.fetchCategories(this.currentPage)
                }
            ).catch(
                function (error) {
                    console.log(error)
                }
            )
        },
        deleteCategory: function () {
            this.$http.put('http://localhost:8004/api/admin/category/delete', {
                id: this.form.id
            }).then(
                function (response) {
                    $('#deleteModal').modal('hide');
                    this.fetchCategories(this.currentPage)
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