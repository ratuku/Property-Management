function doGetSync(theUrl, theData) {
    var ans = null;
    //$('#popupLoader').modal('show');
    console.log('1');
    jQuery.ajax({
        url: theUrl,
        data: theData,
        dataType: "json",
        async: false
    }).done(function (data) {
        ans = data;
        //$('#popupLoader').modal('hide');
        console.log('2');
    }).fail(function (jqXHR, stat, err) {
        ///$('#popupLoader').modal('hide');
        console.log('3');
        console.log(jqXHR, stat, err);
    });
    return ans;
}