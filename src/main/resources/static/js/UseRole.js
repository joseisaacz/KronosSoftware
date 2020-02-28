/**
 * 
 */
function visibleRoles(){
	let combo = document.getElementById('comboRoles').value;
    let labelName = document.getElementById('labelRole');
    let textName = document.getElementById('nameRole');
        if(parseInt(combo,10) ==-2){
        	 textName.value = '';
             textName.type = 'text';
             labelName.style.visibility = 'visible';
         } else {
             textName.value = '';
             textName.type = 'hidden';
             labelName.style.visibility = 'hidden';
         }
}
