
function addObject(index, candidates)
{   
    var candidate = new Object();
    var uid = $('#friends_list').find(".uid").eq(index).val();
    var first_name = $('#friends_list').find(".first_name").eq(index).html();
    var last_name = $('#friends_list').find(".last_name").eq(index).html();
    var city = $('#friends_list').find(".city").eq(index).val();
    candidate["uid"] = uid;
    candidate["first_name"] = first_name;
    candidate["last_name"] = last_name;
    candidate["city"] = city;
    candidates.push(candidate);
}

function deleteAdded(){

    $('#friends_list li').each(function(index, value){                 
         var checkbox = $(this).find('.mdl-checkbox__input').eq(0);
         if ($(checkbox).prop("checked")){
             $(this).remove();
         }
     });
}
//url - куда передавать сохранённых юзеров
function addFromFriends(url, owner){
    $('button').click(function(){

    var candidates = new Array();

    $('#friends_list li').each(function(index, value){

         var checkbox = $(this).find('.mdl-checkbox__input').eq(0);
         if ($(checkbox).prop("checked")){
             addObject(index, candidates);
             $(this).fadeOut(100); 
         }
    }); 
    deleteAdded();

    var candidate;
    //добавленный вручную кандидат (через текстовое поле)
    if ($("#uid_text").val() != "")
    {
        candidate = new Object();
        candidate["uid"] = $("#uid_text").val();
        candidate["first_name"] = "";
        candidate["last_name"] = "";
        candidate["city"] = -1;
        candidates.push(candidate);
    }
    if (owner)
    {
        //один из объектов - собственник
        candidate = new Object();
        candidate["uid"] = $('#candidate_id').val();
        candidate["first_name"] = "empty";
        candidate["last_name"] = "empty";
        candidate["city"] = -2;
        candidates.push(candidate);
    }

    $.ajax({
            type : "POST",
            contentType : "application/json",
            url : url,
            data : JSON.stringify(candidates),
            dataType : 'json',
            success : function(data) {
                    console.log("SUCCESS: ", data);
            },              
            done: function(e) {
                    console.log("DONE");
            }
    });

});
}

    



