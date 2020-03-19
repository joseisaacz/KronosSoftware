/**
 * 
 */


document.addEventListener("DOMContentLoaded", function () {

    initTable();
    //subsButton();
});


function initTable() {
	
    $('#tableUser').DataTable({
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
        "iDisplayLength": 10,
        "destroy": true
        
    });
}


function returnStatus(status){
	return status===true ? "Activo" : "Inactivo";
}

function list(parent, user) {

    var tr = $("<tr/>");
    tr.html(
            "<td>" + user.tempUser.name + "</td>"
            + "<td>" + user.tempUser.email + "</td>"
            + "<td>" + user.department.name + "</td>"
            + "<td>" + returnStatus(user.status) + "</td>"
            + "<td  style=\"text-align: center\"> <a href=\"/administration/editUser/"+ user.tempUser.email+"\">" + "<button type=\"button\" style='text-align: center' class=\"btn btn-primary\">"
            + "Ver Detalles</button></a></td>"
            );
    parent.append(tr);


}



function changeSelect(selectValue){
	
	let departmemtSelect=document.getElementById('selectDepartment');
	let statusSelect=document.getElementById('selectStatus');
	let emailText=document.getElementById('searchText');
	
	if(selectValue==='allUsers'){
		departmemtSelect.style.display='none';
		statusSelect.style.display='none';
		emailText.style.display='none';
	}
	else if(selectValue==='byDepartment'){
		departmemtSelect.style.display='inline-block';
		statusSelect.style.display='none';
		emailText.style.display='none';
		
	}
	else if(selectValue==='byStatus'){
	
		departmemtSelect.style.display='none';
		statusSelect.style.display='inline-block';
		emailText.style.display='none';
		
	}
	else if(selectValue==='byEmail'){
		departmemtSelect.style.display='none';
		statusSelect.style.display='none';
		emailText.style.display='inline-block';
		
	}
}

function searchButton(){
	
let selectValue=document.getElementById('searchType').value;	
	if(selectValue==='allUsers'){
		
		searchAllUsers();
	}
	else if(selectValue==='byDepartment'){
		
		searchByDepartment();
	}
	else if(selectValue==='byStatus'){
		searchByStatus();
		
		
	}
	else if(selectValue==='byEmail'){
		searchByEmail();
		
	}
}



function searchByDepartment(){
	let departmemtValue=document.getElementById('selectDepartment').value;
	let url='/api/administration/usersByDepartment/'+departmemtValue;
	fetch(url)
		.then(response=>{
		if(response.status !== 200)
			throw new Exception();

		return response.json()
	})
	.then(users=>{
		 $('#tableUser').DataTable().clear().destroy();
         var parent = $("#userList");
         parent.html("");
         users.forEach(item => {
             list(parent, item);
         });
	})
	.then(()=>{
		document.getElementById('titleh3').innerHTML='Usuarios Por Departamentos';
		 initTable();
	})
	.catch(error=>{
		bootbox.alert('Ha ocurrido un error. Por favor intente de nuevo')
	})
}

function searchByStatus(){
	
	let statusValue=document.getElementById('selectStatus').value;
	let url='/api/administration/usersByStatus/'+statusValue;
	fetch(url)
	.then(response=>{
		if(response.status !== 200)
			throw new Exception();

		return response.json()
	})
	.then(users=>{
		 $('#tableUser').DataTable().clear().destroy();
         var parent = $("#userList");
         parent.html("");
         users.forEach(item => {
             list(parent, item);
         });
	})
	.then(()=>{
		if(statusValue==='true')
		    document.getElementById('titleh3').innerHTML='Usuarios Activos';
		else
			 document.getElementById('titleh3').innerHTML='Usuarios Inactivos';
			
		 initTable();
	})
	.catch(error=>{
		bootbox.alert('Ha ocurrido un error. Por favor intente de nuevo')
	})
	
}




function searchAllUsers(){
	
	fetch('/api/administration/allUsers')
	.then(response=>{
		if(response.status !== 200)
			throw new Exception();

		return response.json()
	})
	.then(users=>{
		 $('#tableUser').DataTable().clear().destroy();
         var parent = $("#userList");
         parent.html("");
         users.forEach(item => {
             list(parent, item);
         });
	})
	.then(()=>{
		document.getElementById('titleh3').innerHTML='Todos los Usuarios';
		 initTable();
	})
	.catch(error=>{
		console.log(error)
		bootbox.alert('Ha ocurrido un error. Por favor intente de nuevo')
	})
}

function searchByEmail(){
	let emailValue=document.getElementById('searchText').value;
	let url='/api/administration/userByEmail/'+emailValue;
	fetch(url)
	.then(response=>{
		if(response.status !== 200)
			throw new Exception();

		return response.json()
	})
	.then(users=>{
		 $('#tableUser').DataTable().clear().destroy();
         var parent = $("#userList");
         parent.html("");
         users.forEach(item => {
             list(parent, item);
         });
	})
	.then(()=>{
		document.getElementById('titleh3').innerHTML='Usuario por Email'
		 initTable();
	})
	.catch(error=>{
		bootbox.alert('Ha ocurrido un error. Por favor intente de nuevo')
	})
}
