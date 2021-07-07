/**
 * 
 */

$( document ).ready(function() {
    
});

function get(){
	
	$.getJSON('https://www.googleapis.com/youtube/v3/'
			+'search?key=AIzaSyCSfSf_1wS_QK-R8oXwV748vNmTmWzX2D8&channel'
			+'Id=UCXv9rouQ11jqu2b57JJsw4Q&part=snippet,id&order=date&maxResults=5',
			function(data){
				data.items.forEach(item=>{
					let mainContainer=document.getElementById('mainContainer');
					appendVideos(item,mainContainer);
				});
			});

	
}


function appendVideos(item,parent){
	let div=document.createElement('div');
	let img=new Image();
	let anchor=document.createElement('a');
	img.src=item.snippet.thumbnails.default.url;
	img.width=30;
	img.height=30;
	let videoId=item.id.videoId;
	anchor.href='https://www.youtube.com/watch?v='+videoId;
	anchor.appendChild(img);
	div.appendChild(anchor);
	parent.appendChild(div)
	
}
	