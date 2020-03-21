/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {

    initTable();
    //subsButton();
});

function initTable() {
    $('#tableAcc').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ Acuerdos",
            "zeroRecords": "No se encontraron resultados",
            "info": "Mostrando Acuerdos del _START_ al _END_ de un total de _TOTAL_ registros",
            "infoEmpty": "Mostrando Acuerdos del 0 al 0 de un total de 0 registros",
            "infoFiltered": "(filtrado de un total de _MAX_ Acuerdos)",
            "sSearch": "Filtrar:",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "sProcessing": "Procesando..."
        },
        "lengthChange": false,
        "iDisplayLength": 5,
        "destroy": true
        
    });
}



function list(parent, accord) {
    var tr = $("<tr/>");
    tr.html(
            "<td style=\"text-align:center \">" + accord.accNumber + "</td>"
            + "<td style=\"text-align:center \">" + formatDate(accord.deadline) + "</td>"
            + "<td style=\"text-align:center \">" + accord.state.description + "</td>"
            + "<td style=\"text-align:center \"> <a href=\"/boss/edit/"+ accord.accNumber+"\">" + "<button type=\"button\" style='text-align: center' class=\"btn btn-primary\">"
            + "Editar</button></a></td>"
            );
    parent.append(tr);
  


}

function stringToDate(str){
	return new Date(str.split('-').join('/'));
	
}

function formatDate(date) {
	let d=stringToDate(date);
    var month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('-');
}


function searchAccord(){
	
	let selectValue=document.getElementById('searchType').value;
	
	if(selectValue==='allAccords'){
		
		searchAccords('/api/boss/allAccords','Todos los Acuerdos');
	}
	else
		if(selectValue==='inTransit'){
			
			searchAccords('/api/boss/inTransit','Acuerdos en tránsito');
		}
	
	else
	if(selectValue==='finished'){
		
		searchAccords('/api/boss/finished','Acuerdos finalizados');
	}		
	
}

function searchAccords(url,text){
	
	fetch(url)
	.then(response=>{
		if(!response.ok)
			throw response;
		return response.json();
	})
	.then(accords=>{
		   $('#tableAcc').DataTable().clear().destroy();
           var parent = $("#accordList");
           parent.html("");
           accords.forEach(item => {
               list(parent, item);
           });
	})
	.then(()=>{
		
		initTable();
		document.getElementById('titleh3').innerHTML=text;
	})
	.catch(response=>{
		console.log(response);
		bootbox.alert("Ha ocurrido un error. Por favor intente de nuevo");
	})
}


