const schedulesListView = document.getElementById('schedulesList');
const updateResults = document.getElementById('scheduleSearch');
const schedules = [];
let displayedSchedules = [];

fetch("http://localhost:8080/api/availableTrainings").then(response => {
    console.log(response);
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
    for(var dateTraining in schedList ) {
     var obj = schedList[dateTraining];
     content += `<div>Date: ${obj['date']}</div>`;
     content = content + obj['scheduleList'].map(function (s) {
         return `<div class="job-box d-md-flex align-items-center justify-content-between mb-20" style="background:#f5f5f5;">
<div class="job-left my-1 d-md-flex align-items-center flex-wrap">
    <div class="img-holder mr-md-1 mb-md-0 mb-4 mx-auto mx-md-0 d-md-none d-lg-flex">
        FD
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
        </ul>
    </div>
</div>
<div class="job-right my-4 flex-shrink-0">
    <form action="/user/schedule/add" method="post">
            <input type="hidden" name="scheduleId" value="${s.id}"/>
            <button type="submit" class="btn d-block w-100 d-sm-inline-block btn-light">Apply now</button>
        </form>
</div>
</div>`
     }).join('');

    }

    schedulesListView.innerHTML = content;
}