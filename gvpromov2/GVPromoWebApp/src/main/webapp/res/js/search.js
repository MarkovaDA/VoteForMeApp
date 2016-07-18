
function search(){
$('#search').keyup(function(){
            var pattern = $(this).val();
            
            $('#friends_list li').each(function(index, value){
                var name = $(this).find('.first_name').eq(0).html() + $(this).find('.last_name').eq(0).html();
                if (name.indexOf(pattern) !== 0)
                    $(this).hide();
                else $(this).show();
            });
});  
}


