
$(function () {
    // JS code
    console.log("Init!!! Index");

    console.log("token: "+ token);
    //console.log("data1: "+ data1);
    let dataJSON = {};
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/home/"+email,
        contentType: "application/json; charset=utf-8",
        headers: {"Authorization": "Bearer " + token},
        success: function (result) {
            console.log(result);

        },
        error: function (error) {
            console.log(error);
        }
    });
});