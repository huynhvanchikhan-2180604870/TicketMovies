var gird;
gird = $('#example').DataTable({
    serverSide: true,
    processing: true,
    stateSave: true,
    filter: false,
    pageLength: 10,
    //paging:true,
    lengthChange: true,
    sAjaxSource: '/api/show-times/get-showtimes',
    fnServerData: function (sSource, aoData, fnCallback) {
        aoData.push({"name": "name", "value": $('#searchInput').val()}); // Correctly captures the name
        $.getJSON(sSource, aoData, function (json) {
            fnCallback(json)
        });
    },
    columns: [
        {data: 'id'},
        {
            data: 'theater.name'
        },
        {
            data: 'movie.title'
        },
        {
            data: 'startTime', render: function (data, type, full) {
                if (data) {
                    // Đặt locale tiếng Việt và định dạng ngày giờ
                    return moment(data).locale('vi').format('DD-MMM-YYYY (HH:mm)');
                } else {
                    return 'No release date';
                }
            }
        },
        {
            data: 'endTime', render: function (data, type, full) {
                if (data) {
                    // Đặt locale tiếng Việt và định dạng ngày giờ
                    return moment(data).locale('vi').format('DD-MMM-YYYY (HH:mm)');
                } else {
                    return 'No release date';
                }
            }
        },
        {data: 'price'},
        {
            mData: null, bSortable: false, mRender: function (data, type, full) {

                return `<a class="btn btn-outline-dark" onclick="clickEdit('${full.id}')" >Edit</a>
                            <a class="btn btn-outline-danger" onclick="clickDelete('${full.id}')" >Delete</a>`;

            }
        }
    ],

    scroller: {
        loadingIndicator: true
    }
});

// Trigger reload when the search box is used
$(document).ready(function () {
    $('#searchInput, #SearchMark').on('keyup', function () {
        grid.ajax.reload(null, false); // Use null to keep the current paging and false to not reset paging.
    });
    loadMovie(null);
});

function loadCinemas(idSelect) {
    $.getJSON('/api/cinemas/get-all-cinemas', function (response) {
        if (response.httpStatus === "OK" && response.data) {
            var select = $(`#${idSelect}`);
            select.empty().append('<option value="">Chọn Cinema</option>');

            response.data.forEach(function (cinema) {
                select.append($('<option>', {
                    value: cinema.id,
                    text: cinema.name
                }));
            });
        } else {
            console.error('Failed to load Cinema');
        }
    });
}

async function loadMovie(selectID) {
    try {
        let response = await $.ajax({
            url: '/api/movies/get-all',
            method: 'GET'
        });
        console.log(selectID);
        var select = $(`#${selectID}`);
        select.empty().append('<option value="">Chọn Movies</option>');
        if (response.httpStatus === "OK" && response.data) {
            console.log("Response Movie: ", response.data);
            response.data.forEach(function (movie) {
                console.log("Data Movie: ", movie);
                select.append($('<option>', {
                    value: movie.id,
                    text: movie.title
                }));
            });
        }
    } catch (error) {
        console.error('Error fetching theaters:', error);
    }
}

async function loadTheater(idSelect, cinemaID) {
    try {
        let response = await $.ajax({
            url: `/api/theaters/get-all/` + cinemaID, // Pass the cinemaID to your API
            method: 'GET'
        });
        console.log("theater data: ", response.data);
        var select = $(idSelect);
        select.empty().append('<option value="">Chọn Theater</option>');

        if (response.httpStatus === "OK" && response.data) {
            response.data.forEach(function (theater) {
                if (theater.cinema.id === cinemaID) { // Correct the variable name here
                    select.append($('<option>', {
                        value: theater.id,
                        text: theater.name
                    }));
                }
            });
        } else {
            console.error('Failed to load theaters', response);
        }
    } catch (error) {
        console.error('Error fetching theaters:', error);
    }
}




