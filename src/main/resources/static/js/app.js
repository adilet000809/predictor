new Vue({
    el: '#app',
    data: {
        currentPage: 1,
        categories: [],
        totalPages: 0,
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
        }
    },
    created: function () {
        this.fetchCategories(this.currentPage)
    }
});