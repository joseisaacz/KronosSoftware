/**
 * 
 */
globalAccord=null;

$(document).ready(()=>{
	
	let name = $("#accNumber").val();
	listPdf(name);
	
})


function listPdf(accNumber){
	
	url='/api/accords/get/'+accNumber
	fetch(url).then(response=>response.json())
	.then(accord => {
		globalAccord=accord;
		let parent=$("#pdfBody");
		accord.url.forEach(url=>{
			list(parent,url.url)
		})
	}).finally(()=>{
		initTable()
	})
	
	
}


function list(parent,url){
	console.log(url)
	var tr=$("<tr/>");
	let urlAux=url;
	let names=urlAux.split('/');
	console.log(names);
	console.log(url);
	let finalName=names[names.length-1];
	tr.html(
	"<td>"+finalName+"</td>"+		
	"<td><button type=\"button\" class=\"btn btn-success\" onclick=\"javascript:openPdf('" + url + "')\">Abrir</button></td>"+	
	"<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"javascript:deletePdf('" + url + "','"+finalName+"')\">Eliminar</button></td>"
	);
	tr.attr('id',finalName);
	parent.append(tr);
}

function openPdf(pdf){

	let url='/api/accords/getPdf?path='+pdf;
	fetch(url)
	.then(response=>response.blob())
	.then(data=>window.open(URL.createObjectURL(data)));
	
}

function deletePdf(pdf,finalName){
	//let url='/api/accords/deletePdf/'+globalAccord.accNumber+'?path='+pdf;
	//fetch(url);
	document.getElementById(finalName).remove();

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
	        success: function (msg) {
            alert("ACUERDO AGREGADO EXITOSAMENTE");
	        },
	        error: function (response) {
	            console.log(response);
	            if (response.status === 503) {
	                alert("OCURRIO UN ERROR");
	            } else
	                alert("OCURRIO UN ERROR");
	        }
	    })
		
	}
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



