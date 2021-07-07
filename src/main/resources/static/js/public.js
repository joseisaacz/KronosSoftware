document.addEventListener("DOMContentLoaded", function () {

    initTable();
    //subsButton();
});

function initTable() {
    $('#publicTableAct').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ Actas",
            "zeroRecords": "No se encontraron resultados",
            "info": "Mostrando Actas del _START_ al _END_ de un total de _TOTAL_ registros",
            "infoEmpty": "Mostrando Actas del 0 al 0 de un total de 0 registros",
            "infoFiltered": "(filtrado de un total de _MAX_ Actas)",
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
        "iDisplayLength": 5,
        "destroy": true
        
    });
}



function searchAllActs(){
	let _url="/public/AllActsPublish";
    fetch(_url)
    .then(res => {
        console.log(res);
        return res.json();
    })
    .then(act => {
        $('#publicTableAct').DataTable().clear().destroy();
        var parent = $("#actList");
        parent.html("");
        act.forEach(item => {
            list(parent, item);
        });
    }).then(() => {

initTable();
})

    .catch(error => {
        console.log(error);
    });
	
	
}


