/**
 * 
 */

function changeSwitch(input) {
    if (input.checked)
        document.getElementById('generalSession').disabled = false;
    else
        document.getElementById('generalSession').disabled = true;
}

function isValidDate(date) {
    let fecha = date;

    // array with all the posible holidays of the year excluding easter
    let holiday = [
        '01-01', // New year
        '04-11', // Rivas battle
        '05-01', // Labor day
        '07-25', // Annexation of Guanacaste
        '08-02', // Our Lady of Los Ángeles
        '08-15', // Mother's day
        '09-15', // Independance's day
        '10-12', // Day of the Cultures
        '12-25'  // Christmas Day
    ];

    for (let i = 0; i < holiday.length; i++) {
        let aux = '' + fecha.getFullYear + '-' + holiday[i];
        if (date === aux)
            return false;
    }

    if (fecha.getMonth() === 2 || fecha.getMonth() === 3) {
        let URL = "https://www.googleapis.com/calendar/v3/calendars/" +
                "es.cr%23holiday%40group.v.calendar.google.com/events?key=AIzaSyAJuTl"
                + "-gxVHcY80RvGL8T9fzY8sgfwU8Xw";
        var result = true;
        $.getJSON(URL).then(data => {
            data.items.forEach(item => {
                // if the day is eastern
                if (item.summary === 'Jueves Santo' || item.summary === 'Viernes Santo') {
                    if (date === item.start.date) {
                        result = false;
                        return false;
                    }

                }
            })

            return result;
        });

    }
    return true;
}



function changeComboType() {
    let combo = document.getElementById('comboTypes').value;
    let labelName = document.getElementById('labelName');
    let labelEmail = document.getElementById('labelEmail');
    let textName = document.getElementById('username');
    let textEmail = document.getElementById('email');
    if (combo !== 'A') {

        textName.value = '';
        textEmail.value = '';
        textName.type = 'text';
        textEmail.type = 'text';
        labelName.style.visibility = 'visible';
        labelEmail.style.visibility = 'visible';
    } else {
        textName.value = '';
        textEmail.value = '';
        textName.type = 'hidden';
        textEmail.type = 'hidden';
        labelName.style.visibility = 'hidden';
        labelEmail.style.visibility = 'hidden';
    }

}




function getDeadline_NaturalDays(_date) {
    let days = parseInt(document.getElementById('days').value, 10);
    // adding the days to the date
    let date = addDays(days, _date);

    // if the resulting date is a weekend
    if (date.getDay() === 6 || date.getDay() === 0) {
        date = fixWeekend(date);
    }

    // if the resulting date is not a holiday
    // then return it
    // else just add one day to the resulting date
    // until isnt a holiday or a weekend
    if (isValidDate(date)) {
        return date;
    } else {

        for (; ; ) {
            date = addDays(1, date);
            if (date.getDay() === 6 || date.getDay() === 0) {
                date = fixWeekend(date);
            }
            if (isValidDate(date))
                return date;
        }

    }

}




// this function gets the deadline without counting the weekends
// or the holidays
function getDeadline_BussinessDays(date) {
 let days = parseInt(document.getElementById('days').value, 10);
 // counting the current day
 let aux = 1;

 for (; ; ) {
     // if the counting is equal to the amount of days selected
     if (aux === days) {
         // if isnt a holiday or a weekend return the current date
         if (date.getDay() !== 0 && date.getDay() !== 6 && isValidDate(date)) {
             return date;
         } else {

             // if the resulting date is a weekend, fix it
             if (date.getDay() === 0 || date.getDay() === 6) {
                 date = fixWeekend(date);
             } else {
                 // add one day to the resulting date to avoid the holiday
                 date = addDays(1, date);
             }
             for (; ; ) {
                 // return the resulting date if isnt a weekend or a holiday
                 if (date.getDay() !== 0 && date.getDay() !== 6 && isValidDate(date))
                     return date;
                 else
                     date = addDays(1, date);
                 		
                 
                 // else just keep adding one day to the resulting date until
					// isnt a
                 // holiday or a weekend
             }
         }
     }

     // it only counts when isnt weekend or holiday
     if (date.getDay() !== 0 && date.getDay() !== 6 && isValidDate(date))
         aux++;

     // adds one day to the date
     date = addDays(1, date);
 }


}

// this function adds 1 day to the date if the day is sunday
// and 2 days if the day is saturday
function fixWeekend(date) {
  if (date.getDay() === 6)
      return addDays(2, date);
  else
      return addDays(1, date);


}


function formatDate(d) {
    var month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('-');
}




function setDeadline() {
    date = new Date();
    let output = document.getElementById('deadline');
    let select = document.getElementById('comboDays').value;
    if (document.getElementById('days').value !== '') {
    	let days = parseInt(document.getElementById("days").value, 10);
    	 if (days > 60)
    		 if (!confirm("Usted ha seleccionado un plazo de: " + days + " días.\n ¿Desea Continuar?"))
    			 { 
    			 	document.getElementById('days').value = '';
    			 	output.value='';
    			 	return;
    			 
    			 } 
    	 
             
          let newDate = select === '1' ? getDeadline_BussinessDays(date) : getDeadline_NaturalDays(date);
          let format = formatDate(newDate);
            
    	  output.value = format;
    } 
    else {
    	
    	alert('Por favor ingrese un plazo');
        
    }
}


function addDays(days, date) {

    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}


function changeSwitch(input) {
	
    document.getElementById('sessionDate').value='';
	
    if (input.checked){
        document.getElementById('sessionDate').disabled = false;
        document.getElementById('sessionDate').required = true;
    }
    
    else{
        document.getElementById('sessionDate').disabled = true;
        document.getElementById('sessionDate').required = false;
        
    }
}


