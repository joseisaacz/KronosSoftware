function submit(){
	let x= document.getElementById('commissionName')
	if(x.value.length===0){alert("No puede tener campos vacios")}
	document.getElementById('commission_form').reset();
} 
function cancelAction(){
	document.getElementById('commission_form').reset();
} 