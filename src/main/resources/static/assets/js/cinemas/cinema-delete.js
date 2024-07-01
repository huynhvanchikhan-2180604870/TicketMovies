function clickDelete(id) {
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;  // Ensure you are comparing the right ID field
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#deleteCinemaID').val(data.id);  // Store ID in hidden input
        $('#deleteModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function deleteCinema(){
    var id = $('#deleteCinemaID').val(); // Retrieve the ID from hidden input
    $.ajax({
        url: '/api/cinemas/delete-cinema/' + id,
        type: 'DELETE',
        success: function (result) {
            $('#deleteModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Delete successful");
        },
        error: function (err) {
            console.error("Error deleting cinema: ", err);
            alert('Error deleting cinema.');
            $('#deleteModal').modal('hide');
        }
    });
}
