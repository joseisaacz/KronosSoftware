/**
 * 
 */
globalAccord=null;

$(document).ready(()=>{
	
	let name = $("#accNumber").val();
	listPdf(name);
	$("#comboTypes").on('change',comboBoxType)
})


function listPdf(accNumber){
	
	url='/api/accords/get/'+accNumber
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
	return (role != 'Concejo Municipal' && role != 'Secretaria de Alcaldia') ? "disabled" : "";
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
	"<td><input type=\"checkbox\" id=\""+finalName+"checkBox\" style=\"text-aling: center; vertical-align:middle \"></td>"
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
	if(count > 0){
		
		let _data=new FormData(form);		
		
	 $.ajax({
	        type: "POST",
	        encType: "multipart/form-data",
	        url: _url,
	        cache: false,
	        processData: false,
	        contentType: false,
	        data: _data,
	        success : updateTable,
            
	        error: function (response) {
	            console.log(response);
	           
	        }
	    })
		
	}
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
        "info": false
    });
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

