new Vue({
    el: '#main',
    data: {
        success: false,
        error: false
    },
    methods: {
        predict: function(event){
            this.error = false;
            this.success = false;
            let eventId = event.target.getAttribute('event-id');
            let prediction = event.target.getAttribute('data');
            this.$http.post('http://localhost:8004/api/user/predict', {
                eventId: eventId,
                prediction: prediction
            }).then(
                function (response) {
                    this.error = false;
                    this.success = true;
                },
            ).catch(
                function (error) {
                    this.error = true;
                    this.success = false;
                }
            )
        },
    },
});