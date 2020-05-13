/**
 * 
 */

$(document).ready(()=>{
	$("#comboDepartments").css("pointer-events","none");
	
})

function changePassword(checkbox){
	let passInput=document.getElementById('password').type;
	if(checkbox.checked)
		passInput='text'
	else
		passInput='password';
}

function updateName(){
	
	const csrfToken = document.getElementById('tokenId').value;
	const headers = new Headers({
		'Content-Type': 'application/json',
	    'X-CSRF-TOKEN': csrfToken
	});
	
	const _name = document.getElementById('name').value;

	if(_name==='')
		bootbox.alert('El campo de nombre no debe estar vacio');
	
	else{
	
	const _email = document.getElementById('email').value;
	const user={
			tempUser:{
				email:_email,
				name: _name
			}
	};
	
	fetch('/api/administration/updateName',{
		method:'PUT',
		headers,
	    credentials: 'include',
		body:JSON.stringify(user)	
	})
	.then(response=>{
		if(response.ok)
			toastr.success('Nombre Actualizado con Éxito');
		else
			toastr.error('Ocurrió un error. Por favor intene más tarde')
	})
	
	}
	
}


