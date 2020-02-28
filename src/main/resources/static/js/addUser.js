/**
 * 
 */
function putVisible(){
	let combo = document.getElementById('comboDepartments').value;
    let labelName = document.getElementById('labelName');
    let textName = document.getElementById('nameDepartment');
    if(combo==-1){
    	 textName.value = '';
         textName.type = 'text';
         labelName.style.visibility = 'visible';
     } else {
         textName.value = '';
         textName.type = 'hidden';
         labelName.style.visibility = 'hidden';
     }
}

function cancelAction(){
    let labelName = document.getElementById('labelName');
    let textName = document.getElementById('nameDepartment');
    textName.type = 'hidden';
    labelName.style.visibility = 'hidden';
	document.getElementById('user_form').reset();
}

function submit(){
	let frm = document.getElementById('user_form');
	let labelName = document.getElementById('labelName');
    let textName = document.getElementById('nameDepartment');
    textName.type = 'hidden';
    labelName.style.visibility = 'hidden';
	frm.reset();
}