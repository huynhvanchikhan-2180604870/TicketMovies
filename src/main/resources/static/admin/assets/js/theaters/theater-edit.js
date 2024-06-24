function clickEdit(id) {
    loadCinemas('TheaterCinemaID');
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#TheaterID').val(data.id);
        $('#TheaterCinemaID').val(data.category_id);
        $('#TheaterSeatingCapacity').val(data.seating_capacity);
        $('#TheaterName').val(data.name);
        $('#editTheaterModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function saveTheater(){
    var id = $('#TheaterID').val();
    var cinema_id = $('#TheaterCinemaID').val();
    var name = $('#TheaterName').val();
    var seating_capacity = $('#TheaterSeatingCapacity').val();
    console.log("Saving Theater:", {id,cinema_id, seating_capacity}); // Log all data

    $.ajax({
        url: '/api/theaters/update-theater/' + id,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            cinema_id: cinema_id,
            seating_capacity: seating_capacity,
            name: name
        }),
        success: function(response) {
            $('#editTheaterModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Update successful");
        },
        error: function(xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert('Error updating movie: ' + xhr.responseText);
        }
    });
}


