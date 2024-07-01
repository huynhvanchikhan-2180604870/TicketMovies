function clickEdit(id) {
    loadCinemas('ShowTime-CinemaID'); // Load cinemas initially when the document is ready
    loadMovie('ShowTime-MovieID');
    // Event listener for changing the cinema selection
    $('#ShowTime-CinemaID').change(function() {
        var cinemaID = $(this).val();
        if (cinemaID) {
            loadTheater('#ShowTime-TheaterID', cinemaID);
        } else {
            $(`#ShowTime-TheaterID`).empty().append('<option value="">Chọn Theater</option>'); // Clear the theater select if no cinema is selected
        }
    });
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#ShowTime-ID').val(data.id);
        $('#ShowTime-EndTime').val(data.endTime);
        $('#ShowTime-StartTime').val(data.startTime);
        $('#ShowTime-Price').val(data.price);
        $('#ShowTime-CinemaID').val(data.cinema_id);
        $('#ShowTime-TheaterID').val(data.theater_id);
        $('#editShowTimeModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function saveShowTime(){
    var id = $('#ShowTime-ID').val();
    var theater_id = $('#ShowTime-TheaterID').val();
    var movie_id = $('#ShowTime-MovieID').val();
    var startTime = $('#ShowTime-StartTime').val();
    var endTime = $('#ShowTime-EndTime').val();
    var price = $('#ShowTime-Price').val();
    console.log("Saving Theater:", {id,theater_id, movie_id}); // Log all data

    $.ajax({
        url: '/api/show-times/update-showtime/' + id,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            theater_id: theater_id,
            movie_id: movie_id,
            startTime: startTime,
            endTime: endTime,
            price: price,
        }),
        success: function(response) {
            $('#editShowTimeModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            alert('Error updating movie: ' + response);
            console.log("Update successful");
        },
        error: function(xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert('Error updating movie: ' + xhr.responseText);
        }
    });
}


