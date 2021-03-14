$('.close').click(function(){
    $("#error-arlet").remove();
});

jQuery(function($){
    //Grab whatever we need to paginate
    var pageParts = $(".paginate");

    //how many parts do we have?
    var numPages = pageParts.length;
    //how many parts do we want per page?
    var perPage = 1;

    //when the document loads we're on page 1
    //so to start with... hide everything else
    pageParts.slice(perPage).hide();
    
    //apply simplePagination to our placeholder
    $("#page-nav").pagination({
        items: numPages,
        itemsOnPage: perPage,
        cssStyle: "my-theme",

        //we implement the actual pagination
        //in this function. It runs on
        //the event that a user changes page
        onPageClick: function(pageNum){
            //which page parts do we show?
            var start = perPage * (pageNum - 1);
            var end = start + perPage;

            //first hide all page parts
            //them show those just for our page
            pageParts.hide().slice(start, end).show();
        }
    });
});

