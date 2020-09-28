

        console.log("Init!!!");

        //alert("Hello");

        var url = window.location;

        console.log("url; " + url);

        //var data =

        $('#submitButtonr').click( function () {
            //alert("Hello");

                //submit4();

                //alert("Hello");
            });

        function submit4() {

            console.log("Submit!!!");

            const username = $('#username').val();
            const password = $('#password').val();
            let payload = {};
            let ans = null;
            payload.username = username;
            payload.password = password;

            console.log("JSON.stringify(payload): \n" + JSON.stringify(payload));

            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(payload)
            }).done(function (data, textStatus, request) {
                ans = data;

                alert(request.getResponseHeader('Authorization'));

            }).fail(function (jqXHR, stat, err) {
                console.log('failed');
                alert("failed");
                console.log(jqXHR, stat, err);
            });

            return ans;

        }






