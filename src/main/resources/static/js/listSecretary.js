document.addEventListener("DOMContentLoaded", function () {

    initTable();
    //subsButton();
});

function initTable() {
    $('#tableAcc').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ Acuerdos",
            "zeroRecords": "No se encontraron resultados",
            "info": "Mostrando Acuerdos del _START_ al _END_ de un total de _TOTAL_ registros",
            "infoEmpty": "Mostrando Acuerdos del 0 al 0 de un total de 0 registros",
            "infoFiltered": "(filtrado de un total de _MAX_ Acuerdos)",
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

function searchAccord(){
	
	let searchType=document.getElementById('searchType');
	switch(searchType.value){
	case 'allAccords':
		searchAllAccords();
		break;
		
	case 'missingAssign':
		searchMissingAssign();
		break;
	
	case'alreadyAssigned':
		searchAlreadyAssigned();
		break;
	}
}
var accCounter=0;
function list(parent, accord) {
    let now = new Date();
    console.log(now);
    let date = new Date(accord.deadline);

    var tr = $("<tr/>");
    tr.html(
            "<td>" + accord.accNumber + "</td>"
            + "<td>" + formatDate(accord.incorporatedDate) + "</td>"
            + "<td>" + formatDate(accord.sessionDate) + "</td>"
            + "<td>" + formatDate(accord.deadline) + "</td>"
            + "<td>" + accord.type.description + "</td>"
            + "<td id=\"stateTD" + accCounter + "\">" + accord.state.description + "</td>"
            + "<td> <a href=\"/accords/edit/"+ accord.accNumber+"\">" + "<button type=\"button\" style='text-align: center' class=\"btn btn-primary\">"
            + "Editar</button></a></td>"
            );
    parent.append(tr);
  
    let state = 'stateTD' + accCounter;
    if (now > date)
        tr.css('backgroundColor', 'rgb(242, 110, 110)');
    else
    if (parseInt(accord.state.id, 10) === 0)
        document.getElementById(state).style.backgroundColor = '#00D781';

    else
    if (parseInt(accord.state.id, 10) === 1)
        document.getElementById(state).style.color = 'red';

    else if (parseInt(accord.state.id, 10) === 2)
        document.getElementById(state).style.backgroundColor = '#FFE57A';




    accCounter++;

}

function searchAllAccords() {
    let _url = "/api/accords/secretary/searchAllAccords";
    fetch(_url)
            .then(res => {
                console.log(res);
                return res.json();
            })
            .then(accords => {
                $('#tableAcc').DataTable().clear().destroy();
                var parent = $("#accordList");
                parent.html("");
                accords.forEach(item => {
                    list(parent, item);
                });
            }).then(() => {

        initTable();
    })

            .catch(error => {
                console.log(error);
            });
}


function searchMissingAssign() {
    let _url = "/api/accords/secretary/notAssignedAccords";
    fetch(_url)
            .then(res => {
                console.log(res);
                return res.json();
            })
            .then(accords => {
                $('#tableAcc').DataTable().clear().destroy();
                var parent = $("#accordList");
                parent.html("");
                accords.forEach(item => {
                    list(parent, item);
                });
            }).then(() => {

        initTable();
    })

            .catch(error => {
                console.log(error);
            });
}


function searchAlreadyAssigned() {
    let _url = "/api/accords/secretary/alreadyAssignedAccords";
    fetch(_url)
            .then(res => {
                console.log(res);
                return res.json();
            })
            .then(accords => {
                $('#tableAcc').DataTable().clear().destroy();
                var parent = $("#accordList");
                parent.html("");
                accords.forEach(item => {
                    list(parent, item);
                });
            }).then(() => {

        initTable();
    })

            .catch(error => {
                console.log(error);
            });
}

function formatDate(date) {
	let d=stringToDate(date);
    var month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('-');
}

function stringToDate(str){
	return new Date(str.split('-').join('/'));
	
}
