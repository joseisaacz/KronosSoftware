/**
 * 
 */

document.addEventListener("DOMContentLoaded", async () => {
	
	await navigator.serviceWorker.register('/sw.js');
	subsButton();
	

});

async function init() {
try{
  const registration = await navigator.serviceWorker.register('/sw.js');

  await navigator.serviceWorker.ready.then(async function(registration){
	  console.log(registration);
	  firebase.initializeApp({
	    messagingSenderId: "1012820840434"
});
const messaging = firebase.messaging();
messaging.usePublicVapidKey('BD0sY_XxKfRVPHYB-dEjcrtcHiPUy-wuLd59jR-kjPNDHqCXqaEoV2wsRyh_84Ls3jsyIKpsA6qhF2384aNO5KQ');
messaging.useServiceWorker(registration);	
console.log(messaging)

	  try {
		  await messaging.requestPermission();
		    const currentToken = await messaging.getToken();
		    console.log(currentToken)
			
			  url='/push/register/'+currentToken;
			  fetch(url).then(()=>{
				  let anc=  document.getElementById('subscribeAnchor');
					anc.innerHTML='';
				  	anc.onclick='';
			  })
		  } catch (e) {
		    console.log('Unable to get permission', e);
		    
		  }
	

// navigator.serviceWorker.addEventListener('message', event => {
// if (event.data === 'newData') {
// showData();
// }
// });
		
//		  console.log(url)
//		  messaging.onTokenRefresh(async () => {
//	    console.log('token refreshed');
//	    const newToken = await messaging.getToken();
//	    fetch(url);
//	  });
		  
	  
  });
}

catch(e){
	console.log(e);
}

}


async function subsButton() {
	
	 navigator.serviceWorker.ready.then(serviceWorkerRegistration => {
		 serviceWorkerRegistration.pushManager.getSubscription().then(
			    pushSubscription=> {
			    	let anc=  document.getElementById('subscribeAnchor');
			    	console.log(navigator);
			    	console.log(Notification.permission);
			    	console.log(pushSubscription)
			    	  if(!pushSubscription){
			        	anc.innerHTML='ACTIVAR NOTIFICACIONES';
			        	anc.onclick=init;
			    	  }
			    	  else{
			    		  if(Notification.permission === 'granted'){
			    		  anc.innerHTML='';
				        	anc.onclick='';
			    		  }
			    		  else{
			    			  	anc.innerHTML='ACTIVAR NOTIFICACIONES';
					        	anc.onclick=init
			    		  }
			    		  
			    	  }
			      })
			      
	 })
	
}



