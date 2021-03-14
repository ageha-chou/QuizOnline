$('.close').click(function(){
    $("#error-arlet").remove();
});

function deleteAccept(){
    if(window.confirm("Do you want to delete?")){
        return true;
    }
    return false;
}

var page;
$(".page-item").click(function(){
    page = $(".current").text();
});
