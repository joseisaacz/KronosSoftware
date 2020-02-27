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

function visibleRoles(){
	let combo = document.getElementById('comboRoles').value;
    let labelName = document.getElementById('labelRole');
    let textName = document.getElementById('nameRole');
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
	let labelName0 = document.getElementById('labelRole');
    let textName0 = document.getElementById('nameRole');
    labelName0.style.visibility = 'hidden';
    textName0.type = 'hidden';
    let labelName = document.getElementById('labelName');
    let textName = document.getElementById('nameDepartment');
    textName.type = 'hidden';
    labelName.style.visibility = 'hidden';
	document.getElementById('user_form').reset();
}