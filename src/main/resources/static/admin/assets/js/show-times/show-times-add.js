$(document).ready(function() {
    loadCinemas('newShowTime-CinemaID'); // Load cinemas initially when the document is ready
    loadMovie('newShowTime-MovieID');
    // Event listener for changing the cinema selection
    $('#newShowTime-CinemaID').change(function() {
        var cinemaID = $(this).val();
        console.log("Cinema ID: ", cinemaID);
        if (cinemaID) {
            loadTheater('#newShowTime-TheaterID', cinemaID);
        } else {
            $(`#newShowTime-TheaterID`).empty().append('<option value="">Chọn Theater</option>'); // Clear the theater select if no cinema is selected
        }
    });
    $('#btnCreateShowTime').click(function() {
        var myModal = new bootstrap.Modal(document.getElementById('addShowTimeModal'), {
            keyboard: false
        });
        myModal.show();
    });
});

function addShowTime(){
    var theater_id = $('#newShowTime-TheaterID').val();
    var movie_id = $('#newShowTime-MovieID').val();
    var startTime = $('#newShowTime-StartTime').val();
    var endTime = $('#newShowTime-EndTime').val();
    var price = $('#newShowTime-Price').val();

    $.ajax({
        url: '/api/show-times/set-showtime',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            theater_id: theater_id,
            movie_id: movie_id,
            startTime: startTime,
            endTime: endTime,
            price: price,
        }),
        success: function (response){
            $('#addShowTimeModal').modal('hide');
            $('example').DataTable().ajax.reload();
        },
        error: function (){
            alert("Error adding cinema");
        }
    });
}