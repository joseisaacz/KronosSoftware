/**
 * 
 */
globalAct=null
$(document).ready(()=>{
	let name = $("#sessionDate").val();
	(async ()=>{
		let b = await listPdf(name);
		}
	

)();})
	
async function listPdf(sessionDate){
	console.log(sessionDate);
	let url= '/api/act/getAct/'+sessionDate
	fetch(url).then(response=>response.json())
	.then(act => {
		globalAct=act;
		console.log(act);
		let parent=$("#pdfBody");
		list(parent,act.url)
		}
		).finally(()=>{
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
	"<td><button type=\"button\" class=\"btn btn-success\" onclick=\"javascript:openPdf('" + url + "')\">Abrir</button></td>"
	);
	tr.attr('id',finalName);
	parent.append(tr);
}

function openPdf(pdf){

	let url='/api/act/getPdf?path='+pdf;
	fetch(url)
	.then(response=>response.blob())
	.then(data=>{
		console.log(data)
		window.open(URL.createObjectURL(data))
		});
	
}


function uploadPdf(){
	console.log(globalAct);
	let _url='/api/act/uploadPdf/'+globalAct.sessionDate;
	let form=document.getElementById('actForm');
		
		let _data=new FormData(form);		
		bootbox.confirm("¿Esta seguro que desea actualizar este archivo ?", result => {
			
			if(result){
		
	 $.ajax({
	        type: "POST",
	        encType: "multipart/form-data",
	        url: _url,
	        cache: false,
	        processData: false,
	        contentType: false,
	        data: _data,
	        success : act=>{
	        		updateTable();
	        },
            
	        error: function (response) {
	            console.log(response);
	           
	        }
	    })
			} })
		

}


function updateTable(){
	$('#pdfTable').DataTable().clear().destroy();
	listPdf(formatDate(globalAct.sessionDate))
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

function stringToDate(str){
	return new Date(str.split('-').join('/'));
	
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

function cleanPdfForm(){
	document.getElementById('act').value='';
}

async function submitForm(){
	$("#actFormPrin").submit();
}
