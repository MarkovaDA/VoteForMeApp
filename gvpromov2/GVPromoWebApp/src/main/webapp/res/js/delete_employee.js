var employee;
function delete_emplyee(url){
    $('button').click(function(){
          employee = new Object();
          var index = parseInt($(this).attr('employee_id'));        
          employee["id"] = index;
          //передаём сущность на сервер для удаления
          $.ajax({
              type : "POST",
              contentType : "application/json",
              url : url,
              data : JSON.stringify(employee),
              dataType : 'json'                
          });
          $(this).parent().parent().fadeOut(200);
    });
}


