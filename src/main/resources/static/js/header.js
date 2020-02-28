/**
 * 
 */




console.log(document.getElementById('subscribe'));

const subsButton= async() => {
    const permission = await window.Notification.requestPermission();
    if(permission !== 'granted'){
    	document.getElementById('subscribe').innerHTML='ACTIVAR NOTIFICACIONES';
    }
	
}