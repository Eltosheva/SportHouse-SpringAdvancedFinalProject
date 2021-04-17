const schedulesListView = document.getElementById('schedulesList');
const updateResults = document.getElementById('scheduleSearch');
const filterByPlace = document.getElementById('placeFilter')
const filterBySport = document.getElementById('sportFilter')

const schedules = [];
let displayedSchedules = [];

fetch("http://localhost:8080/api/availableTrainings").then(response => {
    return response.json()
}).then(data => {
    for (let s of data) {
        schedules.push(s);
    }
    displayedSchedules = [...schedules];
    showSchedules(displayedSchedules);
})

const showSchedules = (schedList) => {
    if (schedList.length === 0) {
        schedulesListView.innerHTML = "<h1>No results found.</h1>";
        return;
    }
    var content = '';
    for (var dateTraining in schedList) {
        var obj = schedList[dateTraining];
        content += `<div>Date: ${obj['date']}</div>`;
        content = content + obj['scheduleList'].map(function (s) {
            var rowContent = `<div class="job-box d-md-flex align-items-center justify-content-between mb-2" style="background:#f5f5f5;">
<div class="job-left my-1 d-md-flex align-items-center flex-wrap">
    <div class="img-holder mr-md-1 mb-md-0 mb-4 mx-auto mx-md-0 d-md-none d-lg-flex">
        <img width="75px" height="75px" src="https://cdn3.iconfinder.com/data/icons/sport-outline-2/512/workout_time-512.png" alt="Training icon"/>
    </div>
    <div class="job-content">
        <h5 class="text-center text-md-left">Start: ${s.startTime} End: ${s.endTime}</h5>
        <ul class="d-md-flex flex-wrap text-capitalize ff-open-sans">
            <li class="mr-md-1">
                <i class="zmdi zmdi-pin mr-2">${s.description}</i>
            </li>
            <li class="mr-md-1">
                <i class="zmdi zmdi-money mr-2">${s.date}</i>
            </li>
            <li class="mr-md-1">
                <i class="zmdi zmdi-time mr-2">${s.place.name}</i>
            </li>
            <li class="mr-md-1">
                <i class="zmdi zmdi-time mr-2">${s.sportName}</i>
            </li>
        </ul>
    </div>
</div>`
            if (s.isActive) {
                rowContent += `<div class="job-right my-4 flex-shrink-0">
    <form action="/user/schedule/add" method="post">
        <input type="hidden" name="scheduleId" value="${s.id}"/>
  
        <button type="submit" class="btn d-block w-100 d-sm-inline-block btn-light">Apply now</button>
    </form></div>`
            }
            rowContent += `</div>`
            return rowContent
        }).join('');

    }

    schedulesListView.innerHTML = content;
}

updateResults.onclick = function () {
    let displayedSchedulesTmp = [...schedules];
    displayedSchedules = [];

    for (let dateTraining in displayedSchedulesTmp) {
        let obj = displayedSchedulesTmp[dateTraining];
        let tmpList = obj['scheduleList'].filter(function (s) {
            if (filterByPlace.value !== '' && s.place.id !== filterByPlace.value) return false;
            if (filterBySport.value !== '' && s.sportId !== filterBySport.value) return false;
            return true;
        });

        if (tmpList.length > 0) {
            obj['scheduleList'] = [...tmpList];
            displayedSchedules.push(obj);
        }
    }

    showSchedules(displayedSchedules);
}