/**
 * 
 */

globalAccord=null;

$(document).ready(()=>{
	let name = $("#accNumber").val();
	(async ()=>{
		let b = await listPdf(name);
		$("#comboTypes").on('change',comboBoxType)
		
		}
	

)();
	
	fetch('/api/notifications/getResponsables/'+name).
	then(response=>{
		console.log(response);
		if(response.status !== 200)
			throw 'Error al cargar los responsables';
		return response.json();
	})
	.then(resp=>{
		if(resp.length>0){
			//document.getElementById('selectDepartment').disabled=true;
			//document.getElementById('okButton').style.visibility='hidden';
			document.getElementById('okButton').style.visibility='show';
			document.getElementById('accordFormPrin').action='/townHall/edit/newResponsables';
		}
		 var parent = $("#usersBody");
         parent.html("");
         resp.forEach(item => {
             listUser(parent, item);
         });
		
	})
	.then(()=>initusersTable())
	.catch(ex=>console.log(ex))
})	


async function listPdf(accNumber){
	
	let url='/api/accords/get/'+accNumber
	fetch(url).then(response=>response.json())
	.then(accord => {
		globalAccord=accord;
		console.log(accord);
		let parent=$("#pdfBody");
		accord.url.forEach(url=>{
			list(parent,url.url,url.finalResponse)
		})
	}).finally(()=>{
		initTable()
	})
	
}



function isInRole(){
	let role = document.getElementById('role').value;
	return (role != 'Concejo Municipal') ? "disabled" : "";
}

function list(parent,url,finalResponse){
	console.log(isInRole());
	console.log(url)
	var tr=$("<tr/>");
	let urlAux=url;
	let role = document.getElementById('role').value;
	let names=urlAux.split('/');
	console.log(names);
	console.log(url);
	let finalName=names[names.length-1];
	tr.html(
	"<td>"+finalName+"</td>"+
	"<td><button type=\"button\" class=\"btn btn-success\" onclick=\"javascript:openPdf('" + url + "')\">Abrir</button></td>"+	
	"<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"javascript:deletePdf('" + url + "','"+finalName+"')\" "+isInRole()+">Eliminar</button></td>"+
	"<td><input type=\"radio\" name=\"finalResponse\" id=\""+finalName+"checkBox\" value=\""+finalName+"\""+isInRole()+" style=\"text-aling: center; vertical-align:middle \"></td>"
	);
	tr.attr('id',finalName);
	parent.append(tr);
	let checkBox=document.getElementById(finalName+"checkBox");
	if(finalResponse)
		checkBox.checked=true;
}

function openPdf(pdf){

	let url='/api/accords/getPdf?path='+pdf;
	fetch(url)
	.then(response=>response.blob())
	.then(data=>{
		console.log(data)
		window.open(URL.createObjectURL(data))
		});
	
}

function deletePdf(pdf,finalName){
	let table=document.getElementById('pdfTable');
	if(table.rows.length-1 === 1){
		bootbox.alert("Por favor agregue otro documento antes de eliminar");
	}
	else
	bootbox.confirm("¿Esta seguro que desea eliminar este archivo ?", result => {
		
		if(result){
			
			let url='/api/accords/deletePdf/'+globalAccord.accNumber+'?path='+pdf;
			fetch(url)
			.then(()=>{document.getElementById(finalName).remove()});
			
			}
		})
		
	}
	
function comboBoxType(){
	let comboType= $("#comboTypes").val();
	let username=$("#username");
	let email=$("#email");
	
	if(comboType != 'A'){
		username.prop("disabled",false)
		email.prop("disabled",false)
	}
	else{
		username.val('');
		email.val('');
		username.prop("disabled",true)
		email.prop("disabled",true)
	}
		
}


function uploadPdf(){
	console.log(globalAccord);
	let _url='/api/accords/uploadPdf/'+globalAccord.accNumber;
	let form=document.getElementById('accordForm');
	
	let count= $("input:file")[0].files.length;
		
		let _data=new FormData(form);		
		
	 $.ajax({
	        type: "POST",
	        encType: "multipart/form-data",
	        url: _url,
	        cache: false,
	        processData: false,
	        contentType: false,
	        data: _data,
	        success : accord=>{

	        		if(accord.state.id === 0){
	        			document.getElementById('state').value=0;
	        		}
	        		updateTable();

	        },
            
	        error: function (response) {
	            console.log(response);
	           
	        }
	    })
		

}

function updateTable(){
	$('#pdfTable').DataTable().clear().destroy();
	listPdf(globalAccord.accNumber)
}


function initTable() {
    $('#pdfTable').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ Acuerdos",
            "zeroRecords": "",
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
        "destroy": true,
        "searching":false,
        "info": false,
        "iDisplayLength": 3
    });
}


function initusersTable() {
    $('#usersTable').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ Acuerdos",
            "zeroRecords": "",
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
        "destroy": true,
        "searching":false,
        "info": false,
        "iDisplayLength": 3
    });
    
    let table=$('#usersTable').DataTable();
    
    $('#usersBody').on('dblclick', 'tr', function () {
    	
    	bootbox.confirm('Está seguro que desea eliminar este usuario',result => {
    		console.log(result)
    		if(result){
    		    table
    	        .row(this)
    	        .remove()
    	        .draw();
    		}
    	});

    	} );
}


function deleteAccord(){

	bootbox.confirm("¿Está seguro que desea eliminar el acuerdo?",result=>{
		
		if(result){
			let url='/accords/deleteAccord/'+globalAccord.accNumber;
			 fetch(url).then(()=>{
				 window.location.replace('/accords/list');
			 })
		}
		
	})

}

function cleanPdfForm(){
	if(globalAccord != null){
		let finalResponseArray = globalAccord.url.filter(item=>item.finalResponse==true);
		if(!finalResponseArray.length){
			let radioButtons=document.getElementsByName("finalResponse");
			for(let radio of radioButtons){
				radio.checked=false;
			}
		}
		
	}
	document.getElementById('accord').value='';
}


function changeSelectUsers(select){
	
	if(parseInt(select.value,10) !== -1){
		 document.getElementById('users').options.length=0;
		showUsers();
		let selectUsers= document.getElementById('users');
		let url='/api/users/getUsersByDepartment/'+select.value;
		fetch(url)
		.then(data=>{
			if(!data.ok)
				throw "Ha ocurrido un error. Por favor intente más tarde.";
			
			return data.json();
			})
		.then(users=>{
			if(users.length === 0)
				throw "Por favor contacte al administrador para " +
						"agregar usuarios a este departamento"
				
			for(let user of users){
				let option=document.createElement('option');
				option.text=user.tempUser.name;
				option.value=user.tempUser.email;
				selectUsers.appendChild(option);
				
			}
		}).catch((error)=>{
			hideUsers();
			select.value='-1';
			bootbox.alert(error);
			
		})
		
		
	}
	else{
		hideUsers();
	}
}

function hideUsers(){
	 document.getElementById('users').style.visibility='hidden';
	 document.getElementById('userLabel').style.visibility='hidden';
	 document.getElementById('addUserButton').style.visibility='hidden';
}

function showUsers(){
	document.getElementById('addUserButton').style.visibility='';
	document.getElementById('users').style.visibility='';
	document.getElementById('userLabel').style.visibility='';
}

function addUser(){
	let table=document.getElementById('usersBody');
	
	
	let selectDeps=document.getElementById('selectDepartment');
	let selectUsers=document.getElementById('users');
	let depName=selectDeps.options[selectDeps.selectedIndex].innerHTML;
	let userName=selectUsers.options[selectUsers.selectedIndex].value;

	
	$('#usersTable').DataTable().row.add([userName,depName]).draw();
}




function tableToJson() {	
var myRows = [];
var $headers = $("th");
var $rows = $("#usersBody tr").each(function(index) {
  $cells = $(this).find("td");
  myRows[index] = {};
  $cells.each(function(cellIndex) {
    myRows[index][$($headers[cellIndex]).html()] = $(this).html();
  });    
});



return myRows;
}

async function JsontoString(){
	let json=JSON.stringify(tableToJson());
	console.log(json);
	let hidden=document.getElementById('hiddenId');
	hidden.value=json;
	
	return hidden;
}

async function submitForm(){
	let a =await JsontoString();
	$("#accordFormPrin").submit();
}


function listUser(parent, item) {


    var tr = $("<tr/>");
    tr.html(
            "<td>" +item.user + "</td>" +
            "<td>" +item.dep.name + "</td>"

            );
    parent.append(tr);
  
}

async function submitFormEdit(){
	let a =await JsontoString();
	$("#accordFormPrin").submit();
}


