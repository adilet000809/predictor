new Vue({
    el: '#main',
    data: {
    },
    methods: {
        predict: function(event){
            let eventId = event.target.getAttribute('event-id');
            let prediction = event.target.getAttribute('data');
            this.$http.post('http://localhost:8004/api/user/predict', {
                eventId: eventId,
                prediction: prediction
            }).then(
                function (response) {
                    console.log(response);
                }, console.log
            )
        },
    },
});