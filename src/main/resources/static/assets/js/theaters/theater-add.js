$(document).ready(function() {
    loadCinemas('newTheaterCinemaID');
    $('#btnCreateTheater').click(function() {
        var myModal = new bootstrap.Modal(document.getElementById('addTheaterModal'), {
            keyboard: false
        });
        myModal.show();
    });
});

function addTheater(){
    var id = $('#newMovieID').val();
    var cinema_id = $('#newTheaterCinemaID').val();
    var name = $('#newTheaterName').val();
    var seating_capacity = $('#newTheaterSeatingCapacity').val();
    $.ajax({
        url: '/api/theaters/set-theater',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            cinema_id: cinema_id,
            seating_capacity: seating_capacity,
            name: name
        }),
        success: function (response){
            $('#addTheaterModal').modal('hide');
            $('example').DataTable().ajax.reload();
        },
        error: function (){
            alert("Error adding cinema");
        }
    });
}