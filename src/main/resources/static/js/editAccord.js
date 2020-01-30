/**
 * 
 */
let globalAccord=null;

$(document).ready(()=>{
	
	let name = $("#accNumber").val();
	listPdf(name);
	
})


function listPdf(accNumber){
	
	url='/api/accords/get/'+accNumber
	fetch(url).then(response=>response.json())
	.then(accord => {
		goblalAccord=accord;
		console.log(accord);
		let parent=$("#pdfBody");
		accord.url.forEach(url=>{
			list(parent,url.url)
		})
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








