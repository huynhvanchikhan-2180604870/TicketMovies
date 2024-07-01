function clickEdit(id) {
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#cinemaID').val(data.id);
        $('#cinemaName').val(data.name);
        $('#cinemaLocation').val(data.location);
        $('#cinemaTotolTheaters').val(data.total_theaters);
        $('#editCinemaModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function saveCinema(){
    var id = $('#cinemaID').val();
    console.log("Sending ID:", id); // Debugging to check if ID is null

    var name = $('#cinemaName').val();
    var location = $('#cinemaLocation').val();
    var totalTheater = $('#cinemaTotolTheaters').val();

    $.ajax({
        url: '/api/cinemas/update-cinema/' + id,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ name: name, location: location, total_theaters: totalTheater }),
        success: function(response) {
            $('#editCinemaModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Update successful");
        },
        error: function(xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert('Error updating cinema: ' + xhr.responseText);
        }
    });
}