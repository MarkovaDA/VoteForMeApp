<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
  <script>
    var baseURL = "http://localhost:8084/GVPromoWebApp/api/store_region";
    
    function sendEntity2(areas, region_id){
        $.ajax({
	  url: "api/store_area?region_id=" + region_id,
          method: "POST",
          contentType : "application/json",
	  dataType: "json",
          data: JSON.stringify(areas)
	});
 
    }
var regions_code_array = 
['6',
'10',
'9',
'7',
'12',
'13',
'14',
'15',
'17',
'73',
'18',
'19',
'21',
'23',
'226',
'24',
'26',
'27',
'29',
'30',
'31',
'33',
'2',
'39',
'1',
'38',
'40',
'41',
'42',
'44',
'45',
'43',
'46',
'47',
'48',
'3',
'9718',
'8',
'16',
'11',
'20',
'25',
'95',
'22',
'28'];

    var regions = [1,2,3];
    
    function sendEntity(object){
      $.ajax({
	  url: "api/store_region",
          method: "POST",
	  contentType : "application/json",
	  dataType: "json",
	  data: JSON.stringify(object)
	});
    }
    $(document).ready(function(){
	var url = "";
        //проходимся по всем регионам (пока только один регион)
        var j;
        for(var i= 0; i < regions_code_array.length; i++)
        {   j = i;
            url = "http://htmlweb.ru/geo/api.php?area_rajon="+regions_code_array[i]+"&xml";
            console.log(url);
            $.ajax({
              async: false,
              url: url,
              dataType: "xml"
            })
           .success(function(data)
           {    var regions = new Array();
                /*var areas = new Array(); 
                if (data.error !== undefined)
                    return;
                for(var i in data)
                {
                    var area = new Object();
                    area['apiId'] = data[i].id;
                    area['name'] = data[i].name;
                    areas.push(area);
                }               
                console.log(regions_code_array[j]);
                sendEntity2(areas, regions_code_array[j]);*/
                console.log(data);
                $(data).find("msg").each(function()
                {
                  var region = new Object();
                  region['apiId'] = $(this).find("id").text();
                  region['name'] = $(this).find("name").text();              	      
                  regions.push(region);
                });
                if (regions.length !== 0){
                    console.log(regions_code_array[j]);
                    sendEntity2(regions, regions_code_array[j]);
                }
            })
            .done(function() {
                console.log("done");
            });
        }
    });
  </script>
</head>
<body>
    <h1>тестовый скрипт для заполнения базы</h1>
</body>
</html>
